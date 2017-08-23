/*
 * ngDialog - easy modals and popup windows
 * http://github.com/likeastore/ngDialog
 * (c) 2013 MIT License, https://likeastore.com
 */

(function (window, angular, undefined) {
	'use strict';

	var module = angular.module('ngDialog', []);

	var $el = angular.element;
	var isDef = angular.isDefined;
	var style = (document.body || document.documentElement).style;
	var animationEndSupport = isDef(style.animation) || isDef(style.WebkitAnimation) || isDef(style.MozAnimation) || isDef(style.MsAnimation) || isDef(style.OAnimation);
	var animationEndEvent = 'animationend webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend';
	var forceBodyReload = false;

	module.provider('ngDialog', function () {
		var defaults = this.defaults = {
			className: 'ngdialog-theme-default',
			plain: false,
			showClose: true,
			closeByDocument: true,
			closeByEscape: true,
			appendTo: false
		};

		this.setForceBodyReload = function (_useIt) {
			forceBodyReload = _useIt || false;
		};

		var globalID = 0, dialogsCount = 0, closeByDocumentHandler, defers = {};

		this.$get = ['$document', '$templateCache', '$compile', '$q', '$http', '$rootScope', '$timeout', '$window',
			function ($document, $templateCache, $compile, $q, $http, $rootScope, $timeout, $window) {
				var $body = $document.find('body');
				if (defaults.forceBodyReload) {
					$rootScope.$on('$locationChangeSuccess', function () {
						$body = $document.find('body');
					});
				}

				var privateMethods = {
					onDocumentKeydown: function (event) {
						if (event.keyCode === 27) {
							publicMethods.close();
						}
					},

					setBodyPadding: function (width) {
						var originalBodyPadding = parseInt(($body.css('padding-right') || 0), 10);
						$body.css('padding-right', (originalBodyPadding + width) + 'px');
						$body.data('ng-dialog-original-padding', originalBodyPadding);
					},

					resetBodyPadding: function () {
						var originalBodyPadding = $body.data('ng-dialog-original-padding');
						if (originalBodyPadding) {
							$body.css('padding-right', originalBodyPadding + 'px');
						} else {
							$body.css('padding-right', '');
						}
					},

					closeDialog: function ($dialog) {
						var id = $dialog.attr('id');
						if (typeof window.Hammer !== 'undefined') {
							window.Hammer($dialog[0]).off('tap', closeByDocumentHandler);
						} else {
							$dialog.unbind('click');
						}

						if (dialogsCount === 1) {
							$body.unbind('keydown');
						}

						if (!$dialog.hasClass("ngdialog-closing")){
							dialogsCount -= 1;
						}

						if (animationEndSupport) {
							$dialog.unbind(animationEndEvent).bind(animationEndEvent, function () {
								$dialog.scope().$destroy();
								$dialog.remove();
								if (dialogsCount === 0) {
									$body.removeClass('ngdialog-open');
									privateMethods.resetBodyPadding();
								}
							}).addClass('ngdialog-closing');
						} else {
							$dialog.scope().$destroy();
							$dialog.remove();
							if (dialogsCount === 0) {
								$body.removeClass('ngdialog-open');
								privateMethods.resetBodyPadding();
							}
						}
						if (defers[id]) {
							defers[id].resolve({
								id: id,
								$dialog: $dialog,
								remainingDialogs: dialogsCount
							});
							delete defers[id];
						}
						$rootScope.$broadcast('ngDialog.closed', $dialog);
					}
				};

				var publicMethods = {

					/*
					 * @param {Object} options:
					 * - template {String} - id of ng-template, url for partial, plain string (if enabled)
					 * - plain {Boolean} - enable plain string templates, default false
					 * - scope {Object}
					 * - controller {String}
					 * - className {String} - dialog theme class
					 * - showClose {Boolean} - show close button, default true
					 * - closeByEscape {Boolean} - default true
					 * - closeByDocument {Boolean} - default true
					 *
					 * @return {Object} dialog
					 */
					open: function (opts) {
						var self = this;
						var options = angular.copy(defaults);

						opts = opts || {};
						angular.extend(options, opts);

						globalID += 1;

						self.latestID = 'ngdialog' + globalID;

						var defer;
						defers[self.latestID] = defer = $q.defer();

						var scope = angular.isObject(options.scope) ? options.scope.$new() : $rootScope.$new();
						var $dialog, $dialogParent;

						$q.when(loadTemplate(options.template)).then(function (template) {
							template = angular.isString(template) ?
								template :
								template.data && angular.isString(template.data) ?
									template.data :
									'';

							$templateCache.put(options.template, template);

							if (options.showClose) {
								template += '<div class="ngdialog-close"></div>';
							}

							self.$result = $dialog = $el('<div id="ngdialog' + globalID + '" class="ngdialog"></div>');
							$dialog.html('<div class="ngdialog-overlay"></div><div class="ngdialog-content">' + template + '</div>');

							if (options.controller && angular.isString(options.controller)) {
								$dialog.attr('ng-controller', options.controller);
							}

							if (options.className) {
								$dialog.addClass(options.className);
							}

							if (options.data && angular.isString(options.data)) {
								scope.ngDialogData = options.data.replace(/^\s*/, '')[0] === '{' ? angular.fromJson(options.data) : options.data;
							}

							if (options.appendTo && angular.isString(options.appendTo)) {
								$dialogParent = angular.element(document.querySelector(options.appendTo));
							} else {
								$dialogParent = $body;
							}

							scope.closeThisDialog = function() {
								privateMethods.closeDialog($dialog);
							};

							$timeout(function () {
								$compile($dialog)(scope);

								var widthDiffs = $window.innerWidth - $body.prop('clientWidth');
								$body.addClass('ngdialog-open');
								var scrollBarWidth = widthDiffs - ($window.innerWidth - $body.prop('clientWidth'));
								if (scrollBarWidth > 0) {
									privateMethods.setBodyPadding(scrollBarWidth);
								}
								$dialogParent.append($dialog);

								$rootScope.$broadcast('ngDialog.opened', $dialog);
							});

							if (options.closeByEscape) {
								$body.bind('keydown', privateMethods.onDocumentKeydown);
							}

							closeByDocumentHandler = function (event) {
								var isOverlay = options.closeByDocument ? $el(event.target).hasClass('ngdialog-overlay') : false;
								var isCloseBtn = $el(event.target).hasClass('ngdialog-close');

								if (isOverlay || isCloseBtn) {
									publicMethods.close($dialog.attr('id'));
								}
							};

							if (typeof window.Hammer !== 'undefined') {
								window.Hammer($dialog[0]).on('tap', closeByDocumentHandler);
							} else {
								$dialog.bind('click', closeByDocumentHandler);
							}

							dialogsCount += 1;

							return publicMethods;
						});

						return {
							id: 'ngdialog' + globalID,
							closePromise: defer.promise,
							close: function() {
								privateMethods.closeDialog($dialog);
							}
						};

						function loadTemplate (tmpl) {
							if (!tmpl) {
								return 'Empty template';
							}

							if (angular.isString(tmpl) && options.plain) {
								return tmpl;
							}

							return $templateCache.get(tmpl) || $http.get(tmpl, { cache: true });
						}
					},

					/*
					 * @param {Object} options:
					 * - template {String} - id of ng-template, url for partial, plain string (if enabled)
					 * - plain {Boolean} - enable plain string templates, default false
					 * - scope {Object}
					 * - controller {String}
					 * - className {String} - dialog theme class
					 * - showClose {Boolean} - show close button, default true
					 * - closeByEscape {Boolean} - default false
					 * - closeByDocument {Boolean} - default false
					 *
					 * @return {Object} dialog
					 */
					openConfirm: function (opts) {
						var defer = $q.defer();

						var options = {
							closeByEscape: false,
							closeByDocument: false
						};
						angular.extend(options, opts);

						options.scope = angular.isObject(options.scope) ? options.scope.$new() : $rootScope.$new();
						options.scope.confirm = function (value) {
							defer.resolve(value);
							openResult.close();
						};

						var openResult = publicMethods.open(options);
						openResult.closePromise.then(function () {
							defer.reject();
						});

						return defer.promise;
					},

					openSuccessTip:function(content){
						return toastr.success(content);
					},
					success:function(content){
						return toastr.success(content);
					},
					openErrorTip:function(content){
						return toastr.error(content);
					},
					error:function(content){
						return toastr.error(content);
					},
					openInfoTip:function(content){
						return toastr.info(content);
					},
					info:function(content){
						return toastr.info(content);
					},
					openWarningTip:function(content){
						return toastr.warning(content);
					},
					warn:function(content){
						return toastr.warning(content);
					},
					openAlertTip:function(content){
						//toastr.options.positionClass = "toast-top-full-width";
						return toastr.warning(content);
					},
					alert:function(content){ 
						return toastr.warning(content);
					},
					openLoadingTip:function(content){ 
						return toastr.loading(content);
					},
					loading:function(content){ 
						return toastr.loading(content);
					},
					closeLoadingTip:function($ele){
						toastr.removeLoading($ele); 
					},
					hideLoading:function($ele){
						toastr.removeLoading($ele); 
					},
					/*
					 * @param {String} id
					 * @return {Object} dialog
					 */
					close: function (id) {
						var $dialog = $el(document.getElementById(id));

						if ($dialog.length) {
							privateMethods.closeDialog($dialog);
						} else {
							publicMethods.closeAll();
						}

						return publicMethods;
					},

					closeAll: function () {
						var $all = document.querySelectorAll('.ngdialog');

						angular.forEach($all, function (dialog) {
							privateMethods.closeDialog($el(dialog));
						});
					}
				};

				return publicMethods;
			}];
	});

	module.directive('ngDialog', ['ngDialog', function (ngDialog) {
		return {
			restrict: 'A',
			scope : {
				ngDialogScope : '='
			},
			link: function (scope, elem, attrs) {
				elem.on('click', function (e) {
					e.preventDefault();

					var ngDialogScope = angular.isDefined(scope.ngDialogScope) ? scope.ngDialogScope : 'noScope';
					angular.isDefined(attrs.ngDialogClosePrevious) && ngDialog.close(attrs.ngDialogClosePrevious);

					ngDialog.open({
						template: attrs.ngDialog,
						className: attrs.ngDialogClass,
						controller: attrs.ngDialogController,
						scope: ngDialogScope ,
						data: attrs.ngDialogData,
						showClose: attrs.ngDialogShowClose === 'false' ? false : true,
						closeByDocument: attrs.ngDialogCloseByDocument === 'false' ? false : true,
						closeByEscape: attrs.ngDialogCloseByEscape === 'false' ? false : true
					});
				});
			}
		};
	}]);



})(window, window.angular);
 

angular.module('TNotify', [])
  .directive('tNotify', function() {
    return {
      restrict: 'E',
      replace: true,
      scope: true,
      template: '<div class="tnotify-overlay">' +
                  '<div class="tnotify">' +
                    '<div class="tnotify-inner" ng-class="{remind: type === \'remind\'}">' +
                      '<div class="tnotify-title" ng-if="title">{{ title }}</div>' +
                      '<div class="tnotify-text" ng-if="text">{{ text }}</div>' +
                      '<input type="{{ inputType }}" placeholder="{{ inputPlaceHolder }}" class="tnotify-text-input" ng-if="type === \'prompt\'" ng-model="form.input">' +
                    '</div>' +
                    '<div class="tnotify-buttons" ng-if="type !== \'remind\'">' +
                      '<span class="tnotify-button" ng-if="type !== \'alert\'" ng-click="onCancel()">{{ cancelText }}</span>' +
                      '<span class="tnotify-button tnotify-button-bold" ng-click="onOk(input)">{{ okText }}</span>' +
                    '</div>' +
                  '</div>' +
                '</div>'
    };
  })
  .factory('transition', ['$window', function($window){
    var transElement = $window.document.createElement("div");
    var transitionEndEventNames = {
      'WebkitTransition': 'webkitTransitionEnd',
      'MozTransition': 'transitionend',
      'OTransition': 'oTransitionEnd',
      'transition': 'transitionend'
    };
    var animationEndEventNames = {
      'WebkitTransition': 'webkitAnimationEnd',
      'MozTransition': 'animationend',
      'OTransition': 'oAnimationEnd',
      'transition': 'animationend'
    };
    var findEndEventName = function(endEventNames) {
      for (var name in endEventNames){
        if (transElement.style[name] !== undefined) {
          return endEventNames[name];
        }
      }
    };
    return {
      transitionEndEventName: findEndEventName(transitionEndEventNames),
      animationEndEventName: findEndEventName(animationEndEventNames)
    };
  }])
  .provider('TNotify', function(){
    var base = {
      title: null,
      text: null,
      form: {
        input: ''
      },
      inputType: 'text',
      inputPlaceHolder: '',
      cancelText: '取消',
      okText: '确定'
    };
    var dicts = ['title', 'text', 'cancelText', 'okText', 'inputType', 'inputPlaceHolder'];
    this.set = function(key, value){
      var self = this;
      if(angular.isObject(key)){
        for(var name in key){
          self.set(name, key[name]);
        }
      }else{
        if(key && (dicts.indexOf(key) > -1)){
          if(value){
            base[key] = value;
          }
        }
      }
    };

    this.$get = [
      '$rootScope',
      '$compile',
      '$animate',
      '$q',
      '$document',
      '$timeout',
      'transition',
      function($rootScope, $compile, $animate, $q, $document, $timeout, transition){

        function show(opt){
          var deferred, $scope, $element;

          var inClass = 'tnotify-in',
            outClass = 'tnotify-out',
            animateClass = 'tnotify-animate',
            tnotifyEnd = 'tnotifyEnd';

          deferred = $q.defer();
          transition.transitionEndEventName = transition.transitionEndEventName || tnotifyEnd;

          $scope = $rootScope.$new(true);
          angular.extend($scope, base, opt);

          $element = $compile('<t-notify></t-notify>')($scope);

          $scope.onCancel = function(){
            if($scope.type === 'confirm'){
              deferred.resolve(false);
            }else if($scope.type === 'prompt'){
              deferred.resolve(null);
              $scope.form.input = '';
            }
            $scope.$destroy();
          };

          $scope.onOk = function(){
            if($scope.type === 'prompt'){
              deferred.resolve($scope.form.input || '');
              $scope.form.input = '';
            }else{
              deferred.resolve(true);
            }
            $scope.$destroy();
          };

          $animate.enter(
            $element,
            angular.element($document[0].body),
            angular.element($document[0].body.lastChild)
          );
          // .then(function(){

            $scope.$on('$destroy', function(){
              $element.one(transition.transitionEndEventName, function(){
                $element.remove();
              });
              $element.addClass(outClass);
              if(transition.transitionEndEventName === tnotifyEnd){
                // 手动触发
                $element.triggerHandler(tnotifyEnd);
              }
            });
            $element.addClass(animateClass).addClass(inClass);

            if($scope.type === 'remind'){
              if(transition.transitionEndEventName === tnotifyEnd){
                // 650 = .4s + 250ms
                $timeout($scope.onOk, 650);
              }else{
                $element.one(transition.transitionEndEventName, function(){
                  $timeout($scope.onOk, 250);
                });
              }
            }
          // });

          return deferred.promise;
        }

        function objectify(opt){
          if(angular.isString(opt)){
            return {
              text: opt
            };
          }else if(angular.isObject(opt)){
            return opt;
          }else{
            throw new Error('expect a string or a object');
            return {};
          }
        }

        function alert(opt){
          opt = objectify(opt);
          opt.type = 'alert';
          return show(opt);
        }
        function confirm(opt){
          opt = objectify(opt);
          opt.type = 'confirm';
          return show(opt);
        }
        function prompt(opt){
          opt = objectify(opt);
          opt.type = 'prompt';
          return show(opt);
        }
        function remind(opt){
          opt = objectify(opt);
          opt.type = 'remind';
          return show(opt);
        }
        return {
          alert: alert,
          confirm: confirm,
          prompt: prompt,
          remind: remind
        };
      }
    ];
  });