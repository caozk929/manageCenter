/*
 * myTooltips - easy modals and popup windows
 * http://github.com/likeastore/myTooltips
 * (c) 2013-2015 MIT License, https://likeastore.com
 */

(function (root, factory) {
    if (typeof module !== 'undefined' && module.exports) {
        // CommonJS
        module.exports = factory(require('angular'));
    } else if (typeof define === 'function' && define.amd) {
        // AMD
        define(['angular'], factory);
    } else {
        // Global Variables
        factory(root.angular);
    }
}(this, function (angular) {
    'use strict';

    var m = angular.module('myTooltips', []);

    var $el = angular.element;
    var isDef = angular.isDefined;
    var style = (document.body || document.documentElement).style;
    var animationEndSupport = isDef(style.animation) || isDef(style.WebkitAnimation) || isDef(style.MozAnimation) || isDef(style.MsAnimation) || isDef(style.OAnimation);
    var animationEndEvent = 'animationend webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend';
    var focusableElementSelector = 'a[href], area[href], input:not([disabled]), select:not([disabled]), textarea:not([disabled]), button:not([disabled]), iframe, object, embed, *[tabindex], *[contenteditable]';
    var disabledAnimationClass = 'myTooltips-disabled-animation';
    var forceBodyReload = false;
    var scopes = {};
    var openIdStack = [];
    var keydownIsBound = false;

    m.provider('myTooltips', function () {
        var defaults = this.defaults = {
            className: 'myTooltips-theme-default',
            top:0,
            left:0
        };

        this.setDefaults = function (newDefaults) {
            angular.extend(defaults, newDefaults);
        };

        var globalID = 0, dialogsCount = 0, closeByDocumentHandler, defers = {};

        this.$get = ['$document', '$templateCache', '$compile', '$q', '$http', '$rootScope', '$timeout', '$window', '$controller', '$injector',
            function ($document, $templateCache, $compile, $q, $http, $rootScope, $timeout, $window, $controller, $injector) {
                var $body = $document.find('body');
                if (forceBodyReload) {
                    $rootScope.$on('$locationChangeSuccess', function () {
                        $body = $document.find('body');
                    });
                }

                var privateMethods = {
                    onDocumentKeydown: function (event) {
                        if (event.keyCode === 27) {
                            publicMethods.close('$escape');
                        }
                    },

                    activate: function($dialog) {
                        var options = $dialog.data('$myTooltipsOptions');

                        if (options.trapFocus) {
                            $dialog.on('keydown', privateMethods.onTrapFocusKeydown);

                            // Catch rogue changes (eg. after unfocusing everything by clicking a non-focusable element)
                            $body.on('keydown', privateMethods.onTrapFocusKeydown);
                        }
                    },

                    deactivate: function ($dialog) {
                        $dialog.off('keydown', privateMethods.onTrapFocusKeydown);
                        $body.off('keydown', privateMethods.onTrapFocusKeydown);
                    },

                    deactivateAll: function () {
                        angular.forEach(function(el) {
                            var $dialog = angular.element(el);
                            privateMethods.deactivate($dialog);
                        });
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

                    performCloseDialog: function ($dialog, value) {
                        var options = $dialog.data('$myTooltipsOptions');
                        var id = $dialog.attr('id');
                        var scope = scopes[id];

                        if (!scope) {
                            // Already closed
                            return;
                        }

                        if (typeof $window.Hammer !== 'undefined') {
                            var hammerTime = scope.hammerTime;
                            hammerTime.off('tap', closeByDocumentHandler);
                            hammerTime.destroy && hammerTime.destroy();
                            delete scope.hammerTime;
                        } else {
                            $dialog.unbind('click');
                        }

                        if (dialogsCount === 1) {
                            $body.unbind('keydown');
                        }

                        if (!$dialog.hasClass('myTooltips-closing')){
                            dialogsCount -= 1;
                        }

                        var previousFocus = $dialog.data('$myTooltipsPreviousFocus');
                        if (previousFocus) {
                            previousFocus.focus();
                        }

                        $rootScope.$broadcast('myTooltips.closing', $dialog, value);
                        dialogsCount = dialogsCount < 0 ? 0 : dialogsCount;
                        if (animationEndSupport && !options.disableAnimation) {
                            scope.$destroy();
                            $dialog.unbind(animationEndEvent).bind(animationEndEvent, function () {
                                $dialog.remove();
                                if (dialogsCount === 0) {
                                    $body.removeClass('myTooltips-open');
                                    privateMethods.resetBodyPadding();
                                }
                                $rootScope.$broadcast('myTooltips.closed', $dialog, value);
                            }).addClass('myTooltips-closing');
                        } else {
                            scope.$destroy();
                            $dialog.remove();
                            if (dialogsCount === 0) {
                                $body.removeClass('myTooltips-open');
                                privateMethods.resetBodyPadding();
                            }
                            $rootScope.$broadcast('myTooltips.closed', $dialog, value);
                        }
                        if (defers[id]) {
                            defers[id].resolve({
                                id: id,
                                value: value,
                                $dialog: $dialog,
                                remainimyTooltipss: dialogsCount
                            });
                            delete defers[id];
                        }
                        if (scopes[id]) {
                            delete scopes[id];
                        }
                        openIdStack.splice(openIdStack.indexOf(id), 1);
                        if (!openIdStack.length) {
                            $body.unbind('keydown', privateMethods.onDocumentKeydown);
                            keydownIsBound = false;
                        }
                    },

                    closeDialog: function ($dialog, value) {
                        var preCloseCallback = $dialog.data('$myTooltipsPreCloseCallback');

                        if (preCloseCallback && angular.isFunction(preCloseCallback)) {

                            var preCloseCallbackResult = preCloseCallback.call($dialog, value);

                            if (angular.isObject(preCloseCallbackResult)) {
                                if (preCloseCallbackResult.closePromise) {
                                    preCloseCallbackResult.closePromise.then(function () {
                                        privateMethods.performCloseDialog($dialog, value);
                                    });
                                } else {
                                    preCloseCallbackResult.then(function () {
                                        privateMethods.performCloseDialog($dialog, value);
                                    }, function () {
                                        return;
                                    });
                                }
                            } else if (preCloseCallbackResult !== false) {
                                privateMethods.performCloseDialog($dialog, value);
                            }
                        } else {
                            privateMethods.performCloseDialog($dialog, value);
                        }
                    },

                    onTrapFocusKeydown: function(ev) {
                        var el = angular.element(ev.currentTarget);
                        var $dialog;

                        if (el.hasClass('myTooltips')) {
                            $dialog = el;
                        } else {
                            $dialog = privateMethods.getActiveDialog();

                            if ($dialog === null) {
                                return;
                            }
                        }

                        var isTab = (ev.keyCode === 9);
                        var backward = (ev.shiftKey === true);

                        if (isTab) {
                            privateMethods.handleTab($dialog, ev, backward);
                        }
                    },

                    handleTab: function($dialog, ev, backward) {
                        var focusableElements = privateMethods.getFocusableElements($dialog);

                        if (focusableElements.length === 0) {
                            if (document.activeElement) {
                                document.activeElement.blur();
                            }
                            return;
                        }

                        var currentFocus = document.activeElement;
                        var focusIndex = Array.prototype.indexOf.call(focusableElements, currentFocus);

                        var isFocusIndexUnknown = (focusIndex === -1);
                        var isFirstElementFocused = (focusIndex === 0);
                        var isLastElementFocused = (focusIndex === focusableElements.length - 1);

                        var cancelEvent = false;

                        if (backward) {
                            if (isFocusIndexUnknown || isFirstElementFocused) {
                                focusableElements[focusableElements.length - 1].focus();
                                cancelEvent = true;
                            }
                        } else {
                            if (isFocusIndexUnknown || isLastElementFocused) {
                                focusableElements[0].focus();
                                cancelEvent = true;
                            }
                        }

                        if (cancelEvent) {
                            ev.preventDefault();
                            ev.stopPropagation();
                        }
                    },

                    autoFocus: function($dialog) {
                        var dialogEl = $dialog[0];

                        // Browser's (Chrome 40, Forefix 37, IE 11) don't appear to honor autofocus on the dialog, but we should
                        var autoFocusEl = dialogEl.querySelector('*[autofocus]');
                        if (autoFocusEl !== null) {
                            autoFocusEl.focus();

                            if (document.activeElement === autoFocusEl) {
                                return;
                            }

                            // Autofocus element might was display: none, so let's continue
                        }

                        var focusableElements = privateMethods.getFocusableElements($dialog);

                        if (focusableElements.length > 0) {
                            focusableElements[0].focus();
                            return;
                        }

                        // We need to focus something for the screen readers to notice the dialog
                        var contentElements = privateMethods.filterVisibleElements(dialogEl.querySelectorAll('h1,h2,h3,h4,h5,h6,p,span'));

                        if (contentElements.length > 0) {
                            var contentElement = contentElements[0];
                            $el(contentElement).attr('tabindex', '-1').css('outline', '0');
                            contentElement.focus();
                        }
                    },

                    getFocusableElements: function ($dialog) {
                        var dialogEl = $dialog[0];

                        var rawElements = dialogEl.querySelectorAll(focusableElementSelector);

                        return privateMethods.filterVisibleElements(rawElements);
                    },

                    filterVisibleElements: function (els) {
                        var visibleFocusableElements = [];

                        for (var i = 0; i < els.length; i++) {
                            var el = els[i];

                            if (el.offsetWidth > 0 || el.offsetHeight > 0) {
                                visibleFocusableElements.push(el);
                            }
                        }

                        return visibleFocusableElements;
                    },

                    getActiveDialog: function () {
                        var dialogs = document.querySelectorAll('.myTooltips');

                        if (dialogs.length === 0) {
                            return null;
                        }

                        // TODO: This might be incorrect if there are a mix of open dialogs with different 'appendTo' values
                        return $el(dialogs[dialogs.length - 1]);
                    },

                    applyAriaAttributes: function ($dialog, options) {
                        if (options.ariaAuto) {
                            if (!options.ariaRole) {
                                var detectedRole = (privateMethods.getFocusableElements($dialog).length > 0) ?
                                    'dialog' :
                                    'alertdialog';

                                options.ariaRole = detectedRole;
                            }

                            if (!options.ariaLabelledBySelector) {
                                options.ariaLabelledBySelector = 'h1,h2,h3,h4,h5,h6';
                            }

                            if (!options.ariaDescribedBySelector) {
                                options.ariaDescribedBySelector = 'article,section,p';
                            }
                        }

                        if (options.ariaRole) {
                            $dialog.attr('role', options.ariaRole);
                        }

                        privateMethods.applyAriaAttribute(
                            $dialog, 'aria-labelledby', options.ariaLabelledById, options.ariaLabelledBySelector);

                        privateMethods.applyAriaAttribute(
                            $dialog, 'aria-describedby', options.ariaDescribedById, options.ariaDescribedBySelector);
                    },

                    applyAriaAttribute: function($dialog, attr, id, selector) {
                        if (id) {
                            $dialog.attr(attr, id);
                        }

                        if (selector) {
                            var dialogId = $dialog.attr('id');

                            var firstMatch = $dialog[0].querySelector(selector);

                            if (!firstMatch) {
                                return;
                            }

                            var generatedId = dialogId + '-' + attr;

                            $el(firstMatch).attr('id', generatedId);

                            $dialog.attr(attr, generatedId);

                            return generatedId;
                        }
                    }
                };

                var publicMethods = {

                    /*
                     * @param {Object} options:
                     * - template {String} - id of ng-template, url for partial, plain string (if enabled)
                     * - plain {Boolean} - enable plain string templates, default false
                     * - scope {Object}
                     * - controller {String}
                     * - controllerAs {String}
                     * - className {String} - dialog theme class
                     * - disableAnimation {Boolean} - set to true to disable animation
                     * - showClose {Boolean} - show close button, default true
                     * - closeByEscape {Boolean} - default true
                     * - closeByDocument {Boolean} - default true
                     * - preCloseCallback {String|Function} - user supplied function name/function called before closing dialog (if set)
                     *
                     * @return {Object} dialog
                     */
                    open: function (opts) {
                        var options = angular.copy(defaults);
                        var localID = ++globalID;
                        var dialogID = 'myTooltips' + localID;
                        openIdStack.push(dialogID);

                        opts = opts || {};
                        angular.extend(options, opts);

                        var defer;
                        defers[dialogID] = defer = $q.defer();

                        var scope;
                        scopes[dialogID] = scope = angular.isObject(options.scope) ? options.scope.$new() : $rootScope.$new();

                        var $dialog, $dialogParent;

                        var resolve = angular.extend({}, options.resolve);

                        angular.forEach(resolve, function (value, key) {
                            resolve[key] = angular.isString(value) ? $injector.get(value) : $injector.invoke(value, null, null, key);
                        });

                        $q.all({
                            template: loadTemplate(options.template || options.templateUrl),
                            locals: $q.all(resolve)
                        }).then(function (setup) {
                            var template = setup.template,
                                locals = setup.locals;

                            if (options.showClose) {
                                template += '<div class="myTooltips-close"></div>';
                            }

                            $dialog = $el('<div id="myTooltips' + localID + '" class="myTooltips"'+(options.top>0 ? 'style=" z-index: 0;"' : '')+'></div>');

                            var _top = "";
                            if(options.top>0)
                            {
                                _top="top:"+options.top+"px;";
                            }

                            var _left = "";
                            if(options.left>0)
                            {
                                _left="left:"+options.left+"px;";
                            }

                            if(options.top<=0)
                            {
                            $dialog.html((options.overlay ?
                                '<div class="myTooltips-overlay"></div><div class="myTooltips-custom-downdialog-top"></div><div class="myTooltips-content" role="document">' + template + '</div>' :
                                '<div class="myTooltips-content" role="document" style="'+_top+_left+'">' + template + '</div>'));
                            }
                            else
                            {
                                $dialog.html((options.overlay ?
                                '<div class="myTooltips-overlay"></div><div class="myTooltips-content" role="document"><div class="myTooltips-custom-downdialog-top"></div>' + template + '<div class="myTooltips-custom-downdialog-bottom"></div></div>' :
                                '<div class="myTooltips-content" role="document" style="'+_top+_left+'"><div class="myTooltips-custom-downdialog-top"></div><div class="myTooltips-custom-downdialog-content">' + template + '</div><div class="myTooltips-custom-downdialog-bottom"></div></div>'));
                            }
                            $dialog.data('$myTooltipsOptions', options);

                            if (options.data && angular.isString(options.data)) {
                                var firstLetter = options.data.replace(/^\s*/, '')[0];
                                scope.myTooltipsData = (firstLetter === '{' || firstLetter === '[') ? angular.fromJson(options.data) : options.data;
                            } else if (options.data && angular.isObject(options.data)) {
                                scope.myTooltipsData = options.data;
                            }

                            if (options.controller && (angular.isString(options.controller) || angular.isArray(options.controller) || angular.isFunction(options.controller))) {

                                var label;

                                if (options.controllerAs && angular.isString(options.controllerAs)) {
                                    label = options.controllerAs;
                                }

                                var controllerInstance = $controller(options.controller, angular.extend(
                                    locals,
                                    {
                                        $scope: scope,
                                        $element: $dialog
                                    }),
                                    null,
                                    label
                                );
                                $dialog.data('$myTooltipsControllerController', controllerInstance);
                            }

                            if (options.className) {
                                $dialog.addClass(options.className);
                            }

                            if (options.disableAnimation) {
                                $dialog.addClass(disabledAnimationClass);
                            }

                            if (options.appendTo && angular.isString(options.appendTo)) {
                                $dialogParent = angular.element(document.querySelector(options.appendTo));
                            } else {
                                $dialogParent = $body;
                            }

                            privateMethods.applyAriaAttributes($dialog, options);

                            if (options.preCloseCallback) {
                                var preCloseCallback;

                                if (angular.isFunction(options.preCloseCallback)) {
                                    preCloseCallback = options.preCloseCallback;
                                } else if (angular.isString(options.preCloseCallback)) {
                                    if (scope) {
                                        if (angular.isFunction(scope[options.preCloseCallback])) {
                                            preCloseCallback = scope[options.preCloseCallback];
                                        } else if (scope.$parent && angular.isFunction(scope.$parent[options.preCloseCallback])) {
                                            preCloseCallback = scope.$parent[options.preCloseCallback];
                                        } else if ($rootScope && angular.isFunction($rootScope[options.preCloseCallback])) {
                                            preCloseCallback = $rootScope[options.preCloseCallback];
                                        }
                                    }
                                }

                                if (preCloseCallback) {
                                    $dialog.data('$myTooltipsPreCloseCallback', preCloseCallback);
                                }
                            }

                            scope.closeThisDialog = function (value) {
                                privateMethods.closeDialog($dialog, value);
                            };

                            $timeout(function () {
                                var $activeDialogs = document.querySelectorAll('.myTooltips');
                                privateMethods.deactivateAll($activeDialogs);

                                $compile($dialog)(scope);
                                var widthDiffs = $window.innerWidth - $body.prop('clientWidth');
                                $body.addClass('myTooltips-open');
                                var scrollBarWidth = widthDiffs - ($window.innerWidth - $body.prop('clientWidth'));
                                if (scrollBarWidth > 0) {
                                    privateMethods.setBodyPadding(scrollBarWidth);
                                }




                                $dialogParent.append($dialog);
                                

                                privateMethods.activate($dialog);

                                if (options.trapFocus) {
                                    privateMethods.autoFocus($dialog);
                                }

                                if (options.name) {
                                    $rootScope.$broadcast('myTooltips.opened', {dialog: $dialog, name: options.name});
                                } else {
                                    $rootScope.$broadcast('myTooltips.opened', $dialog);
                                }
                            });

                            if (!keydownIsBound) {
                                $body.bind('keydown', privateMethods.onDocumentKeydown);
                                keydownIsBound = true;
                            }

                            if (options.closeByNavigation) {
                                $rootScope.$on('$locationChangeSuccess', function () {
                                    privateMethods.closeDialog($dialog);
                                });
                            }

                            if (options.preserveFocus) {
                                $dialog.data('$myTooltipsPreviousFocus', document.activeElement);
                            }

                            closeByDocumentHandler = function (event) {
                                var isOverlay = options.closeByDocument ? $el(event.target).hasClass('myTooltips-overlay') : false;
                                var isCloseBtn = $el(event.target).hasClass('myTooltips-close');

                                if (isOverlay || isCloseBtn) {
                                    publicMethods.close($dialog.attr('id'), isCloseBtn ? '$closeButton' : '$document');
                                }
                            };

                            if (typeof $window.Hammer !== 'undefined') {
                                var hammerTime = scope.hammerTime = $window.Hammer($dialog[0]);
                                hammerTime.on('tap', closeByDocumentHandler);
                            } else {
                                $dialog.bind('click', closeByDocumentHandler);
                            }

                            dialogsCount += 1;

                            return publicMethods;
                        });

                        return {
                            id: dialogID,
                            closePromise: defer.promise,
                            close: function (value) {
                                privateMethods.closeDialog($dialog, value);
                            }
                        };

                        function loadTemplateUrl (tmpl, config) {
                            $rootScope.$broadcast('myTooltips.templateLoading', tmpl);
                            return $http.get(tmpl, (config || {})).then(function(res) {
                                $rootScope.$broadcast('myTooltips.templateLoaded', tmpl);
                                return res.data || '';
                            });
                        }

                        function loadTemplate (tmpl) {
                            if (!tmpl) {
                                return 'Empty template';
                            }

                            if (angular.isString(tmpl) && options.plain) {
                                return tmpl;
                            }

                            if (typeof options.cache === 'boolean' && !options.cache) {
                                return loadTemplateUrl(tmpl, {cache: false});
                            }

                            return loadTemplateUrl(tmpl, {cache: $templateCache});
                        }
                    },

                    /*
                     * @param {Object} options:
                     * - template {String} - id of ng-template, url for partial, plain string (if enabled)
                     * - plain {Boolean} - enable plain string templates, default false
                     * - name {String}
                     * - scope {Object}
                     * - controller {String}
                     * - controllerAs {String}
                     * - className {String} - dialog theme class
                     * - showClose {Boolean} - show close button, default true
                     * - closeByEscape {Boolean} - default false
                     * - closeByDocument {Boolean} - default false
                     * - preCloseCallback {String|Function} - user supplied function name/function called before closing dialog (if set); not called on confirm
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
                            var $dialog = $el(document.getElementById(openResult.id));
                            privateMethods.performCloseDialog($dialog, value);
                        };

                        var openResult = publicMethods.open(options);
                        openResult.closePromise.then(function (data) {
                            if (data) {
                                return defer.reject(data.value);
                            }
                            return defer.reject();
                        });

                        return defer.promise;
                    },

                    isOpen: function(id) {
                        var $dialog = $el(document.getElementById(id));
                        return $dialog.length > 0;
                    },

                    /*
                     * @param {String} id
                     * @return {Object} dialog
                     */
                    close: function (id, value) {
                        var $dialog = $el(document.getElementById(id));

                        if ($dialog.length) {
                            privateMethods.closeDialog($dialog, value);
                        } else {
                            if (id === '$escape') {
                                var topDialogId = openIdStack[openIdStack.length - 1];
                                $dialog = $el(document.getElementById(topDialogId));
                                if ($dialog.data('$myTooltipsOptions').closeByEscape) {
                                    privateMethods.closeDialog($dialog, value);
                                }
                            } else {
                                publicMethods.closeAll(value);
                            }
                        }

                        return publicMethods;
                    },

                    closeAll: function (value) {
                        var $all = document.querySelectorAll('.myTooltips');

                        // Reverse order to ensure focus restoration works as expected
                        for (var i = $all.length - 1; i >= 0; i--) {
                            var dialog = $all[i];
                            privateMethods.closeDialog($el(dialog), value);
                        }
                    },

                    getOpenDialogs: function() {
                        return openIdStack;
                    },

                    getDefaults: function () {
                        return defaults;
                    }
                };

                return publicMethods;
            }];
    });

    m.directive('myTooltips', ['myTooltips', function (myTooltips) {
        return {
            restrict: 'A',
            scope: {
                myTooltipsScope: '='
            },
            link: function (scope, elem, attrs) {
                elem.on('click', function (e) {
                    e.preventDefault();

                    var myTooltipsScope = angular.isDefined(scope.myTooltipsScope) ? scope.myTooltipsScope : 'noScope';
                    angular.isDefined(attrs.myTooltipsClosePrevious) && myTooltips.close(attrs.myTooltipsClosePrevious);

                    var defaults = myTooltips.getDefaults();

                    myTooltips.open({
                        template: attrs.myTooltips,
                        className: attrs.myTooltipsClass || defaults.className,
                        controller: attrs.myTooltipsController,
                        controllerAs: attrs.myTooltipsControllerAs,
                        bindToController: attrs.myTooltipsBindToController,
                        scope: myTooltipsScope,
                        data: attrs.myTooltipsData,
                        showClose: attrs.myTooltipsShowClose === 'false' ? false : (attrs.myTooltipsShowClose === 'true' ? true : defaults.showClose),
                        closeByDocument: attrs.myTooltipsCloseByDocument === 'false' ? false : (attrs.myTooltipsCloseByDocument === 'true' ? true : defaults.closeByDocument),
                        closeByEscape: attrs.myTooltipsCloseByEscape === 'false' ? false : (attrs.myTooltipsCloseByEscape === 'true' ? true : defaults.closeByEscape),
                        overlay: attrs.myTooltipsOverlay === 'false' ? false : (attrs.myTooltipsOverlay === 'true' ? true : defaults.overlay),
                        preCloseCallback: attrs.myTooltipsPreCloseCallback || defaults.preCloseCallback,
                        top:attrs.top,
                        left:attrs.left
                    });
                });
            }
        };
    }]);

    return m;
}));