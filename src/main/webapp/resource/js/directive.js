/**
 * 定义 指令 hasPermission
 */
app.directive('hasPermission', function($jsession) {
  return {
    link: function(scope, element, attrs) {
      if(!_.isString(attrs.hasPermission))
        throw "hasPermission value must be a string";
  
      var value = attrs.hasPermission.trim();
      var notPermissionFlag = value[0] === '!';
      if(notPermissionFlag) {
        value = value.slice(1).trim();
      }
  
      function toggleVisibilityBasedOnPermission() {
        var hasPermission = $jsession.hasPermission(value);
        if(hasPermission && !notPermissionFlag || !hasPermission && notPermissionFlag)
          element.show();
        else
          element.hide();
      }
      toggleVisibilityBasedOnPermission();
      scope.$on('jsessionChanged', toggleVisibilityBasedOnPermission);
    }
  };
});
 

app.directive("uploadify", function() {
    return {
        require: '?ngModel',
        restrict: 'A',
        link: function ($scope, element, attrs, ngModel) {
            var opts = angular.extend({}, $scope.$eval(attrs.uploadify));
            var thumbnailsize = '80x80';
            if(opts.thumbnailsize)thumbnailsize = opts.thumbnailsize
            element.uploadify({
                'fileObjName':'upfile',
                'formData'      : {'folder' : 'imgs/','thumbnailsize':thumbnailsize},
                'auto': opts.auto!=undefined?opts.auto:true,
                'swf': opts.swf || 'resource/js/uploadify/uploadify.swf',
                'uploader': opts.uploader || (dataSource+"/uploadifyController/uploadimg"),//图片上传方法
                'buttonText': opts.buttonText || '本地图片',
                'buttonClass':'inptext uploadbtn',
                'width': opts.width || 98,
                'height': opts.height || 34,
                'multi':opts.multi || true,
                'fileTypeDesc' : 'Image Files',
                'fileTypeExts' : opts.typeExts || '*.gif; *.jpg; *.png',
                'fileSizeLimit' : opts.maxSize || '10MB',
                'onUploadSuccess': function (file, d, response) {
                    if (ngModel) {
                        var result = $.parseJSON(d);
                        if (result.state == "SUCCESS") { 
                            $scope.$apply(function() {
                                var arr = new Array();
                                if(opts.multi == false){
                                  if(result.thumbnail === undefined)
                                    arr.push(result.url);
                                  else
                                    arr.push(result.thumbnail);
                                  ngModel.$setViewValue(arr[0]);
                                  return;
                                }

                                
                                if(ngModel.$modelValue===undefined || ngModel.$modelValue==null || ngModel.$modelValue == ''){}
                                else{
                                  for(img=0;img<ngModel.$modelValue.split(',').length;img++){
                                    arr.push(ngModel.$modelValue.split(',')[img]);
                                  }
                                }
                                if(result.thumbnail === undefined)
                                  arr.push(result.url);
                                else
                                  arr.push(result.thumbnail);
                                ngModel.$setViewValue(arr.join(','));
                            });
                        }
                    }
                },
                'onUploadError' : function(file, errorCode, errorMsg, errorString) { 
                },
                'onCancel' : function(file) { 
                }
            });
        }
    };
});


app.directive("uploadifyimg", function() {
    return {
        require: '?ngModel',
        restrict: 'A',
        link: function ($scope, element, attrs, ngModel) {
            var opts = angular.extend({}, $scope.$eval(attrs.uploadifyimg));
            var thumbnailsize = '80x80';
            if(opts.thumbnailsize)thumbnailsize = opts.thumbnailsize
            element.uploadify({
                'fileObjName':'upfile',
                'formData'      : {'folder' : 'imgs/','thumbnailsize':thumbnailsize},
                'auto': opts.auto!=undefined?opts.auto:true,
                'swf': opts.swf || 'resource/js/uploadify/uploadify.swf',
                'uploader': opts.uploader || (dataSource+"/uploadifyController/uploadImg2"),//图片上传方法
                'buttonText': opts.buttonText || '本地图片',
                'buttonClass':'inptext uploadbtn',
                'width': opts.width || 98,
                'height': opts.height || 34,
                'multi':opts.multi || true,
                'fileTypeDesc' : 'Image Files',
                'fileTypeExts' : opts.typeExts || '*.gif; *.jpg; *.png',
                'fileSizeLimit' : opts.maxSize || '10MB',
                'onUploadSuccess': function (file, d, response) {
                    if (ngModel) {
                        var result = $.parseJSON(d);
                        if (result.state == "SUCCESS") { 
                            $scope.$apply(function() { 
                                var arr = new Array();
                                if(opts.multi == false){
                                  arr.push(result);
                                  ngModel.$setViewValue(arr[0]);
                                  return;
                                }

                                
                                if(ngModel.$modelValue===undefined || ngModel.$modelValue==null || ngModel.$modelValue.length == 0){}
                                else{
                                  for(img=0;img<ngModel.$modelValue.length;img++){
                                    arr.push(ngModel.$modelValue[img]);
                                  }
                                }
                                if(result.thumbnail === undefined)
                                  arr.push(result);
                                else
                                  arr.push(result);
                                ngModel.$setViewValue(arr);
                            });
                        }
                    }
                },
                'onUploadError' : function(file, errorCode, errorMsg, errorString) { 
                },
                'onCancel' : function(file) { 
                }
            });
        }
    };
});

app.directive("fileuploadify", function() {
    return {
        require: '?ngModel',
        restrict: 'A',
        link: function ($scope, element, attrs, ngModel) {
            var opts = angular.extend({}, $scope.$eval(attrs.fileuploadify));
            element.uploadify({
                'fileObjName':'upfile',
                'formData'      : {'folder' : 'files/'},
                'auto': opts.auto!=undefined?opts.auto:true,
                'swf': opts.swf || 'resource/js/uploadify/uploadify.swf',
                'uploader': opts.uploader || (dataSource+"/uploadifyController/uploadfile"),
                'buttonText': opts.buttonText || '本地文件',
                'buttonClass':'inptext uploadbtn',
                'width': opts.width || 98,
                'height': opts.height || 34,
                'multi':opts.multi || true,
                'fileSizeLimit' :opts.maxSize || '10MB',
                'onUploadSuccess': function (file, d, response) {
                    if (ngModel) { 
                        var result =  $.parseJSON(d);
                        if (result.state == "SUCCESS") { 
                            $scope.$apply(function() {
                                var arr = new Array();
                                if(opts.multi == false){
                                  arr.push(result.filename+'|'+result.url); 
                                  ngModel.$setViewValue(arr[0]);
                                  return;
                                }
 
                                if(ngModel.$modelValue===undefined || ngModel.$modelValue==null || ngModel.$modelValue == ''){}
                                else{
                                  for(img=0;img<ngModel.$modelValue.split(',').length;img++){
                                    arr.push(result.filename+'|'+ngModel.$modelValue.split(',')[img]);
                                  }
                                }
                                arr.push(result.filename+'|'+result.url);
                                ngModel.$setViewValue(arr.join(','));
                            });
                        }
                    }
                },
                'onUploadError' : function(file, errorCode, errorMsg, errorString) {
                },
                'onCancel' : function(file) { 
                }
            });
        }
    };
});

app.directive("swipeflash", function() {
    return {
        require: '?ngModel',
        restrict: 'A',
        link: function ($scope, element, attrs, ngModel) {
          setTimeout(function(){
            var mySwiper = new Swiper($(element).find('.swiper-container'),{
            pagination: '.pagination',
            loop:true,
            grabCursor: true,
            paginationClickable: true,
            autoplay:3000
          });
          element.find('.arrow-left').on('click', function(e){
            e.preventDefault()
            mySwiper.swipePrev()
          })
          element.find('.arrow-right').on('click', function(e){
            e.preventDefault()
            mySwiper.swipeNext()
          });},1000);
        }
    };
});
 

app.directive("removeuploaditem", function() {
    return {
        require: '?ngModel',
        restrict: 'A',
        link: function ($scope, element, attrs, ngModel) {
            element.bind('click',function(){   
              if (ngModel && ngModel.$modelValue!= undefined && ngModel.$modelValue.length>0) {
                $scope.$apply(function() { 
                                var arr = new Array();
                                if(ngModel.$modelValue===undefined){}
                                else{
                                    for(img=0;img<ngModel.$modelValue.split(',').length;img++){
                                      if(ngModel.$modelValue.split(',')[img]!=attrs.path)
                                      arr.push(ngModel.$modelValue.split(',')[img]);
                                    } 
                                }
                                ngModel.$setViewValue(arr.join(','));
                            }); 
              }
            });
        }
    };
});

app.directive("removeimageitem", function() {
    return {
        require: '?ngModel',
        restrict: 'A',
        link: function ($scope, element, attrs, ngModel) {
            element.bind('click',function(){ 
               $scope.$apply(function() {
                  var item =  $scope.$eval(attrs.path);
                  var arr  = ngModel.$modelValue;
                  var idx = -1;
                  for(i=0;i<arr.length;i++){
                    if(arr[i].url == item.url){
                      idx = i;
                    }
                  }
                  if(idx!=-1){
                    arr.splice(idx,1)
                    ngModel.$setViewValue(arr); 
                  }
                });
             });
        }
    };
});

app.directive("wdtree", function() {
    return {
        require: '?ngModel',
        restrict: 'A',
        link: function ($scope, element, attrs, ngModel) {
            element.bind('click',function(){   
              if (ngModel && ngModel.$modelValue!= undefined && ngModel.$modelValue.length>0) {
                $scope.$apply(function() {
                                var arr = new Array();
                                if(ngModel.$modelValue===undefined){}
                                else{
                                  for(img=0;img<ngModel.$modelValue.length;img++){
                                    if(ngModel.$modelValue[img]!=attrs.path)
                                    arr.push(ngModel.$modelValue[img]);
                                  }
                                }
                                ngModel.$setViewValue(arr);
                            }); 
              }
            });
        }
    };
});

app.directive("date", function() {
    var tomorrow=GetDate(1);
    var today=GetDate(0);
    return {
        require: '?ngModel',
        restrict: 'A',
        link: function ($scope, element, attrs, ngModel) {
            var opts =angular.extend({}, $scope.$eval(attrs.date));
            element.bind('focus',function(){   
               WdatePicker({
                'minDate':opts.minDate?(opts.minDate=='tomorrow'?tomorrow:opts.minDate):'1900-01-01',
                'maxDate':opts.maxDate?(opts.maxDate=='today'?today:opts.maxDate):'2999-01-01',
                'dateFmt':opts.dateFmt?opts.dateFmt:'yyyy-MM-dd',
                'onpicked':function(dp){ 
                    if (ngModel) { 
                      $scope.$apply(function() {
                      ngModel.$setViewValue(dp.cal.getNewDateStr()); 
                      });
                    }
                },
                oncleared:function(dp){
                  if (ngModel) { 
                      $scope.$apply(function() {
                      ngModel.$setViewValue(''); 
                      });
                    }
                }
               });
            });
        }
    };
});


function GetDate(AddDayCount) 
{ 
  var dd = new Date(); 
  dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期 
  var y = dd.getFullYear(); 
  var m = dd.getMonth()+1;//获取当前月份的日期 
  var d = dd.getDate(); 
  return y+"-"+m+"-"+d; 
} 

app.directive("dateminute", function() {
    return {
        require: '?ngModel',
        restrict: 'A',
        link: function ($scope, element, attrs, ngModel) {
            element.bind('click',function(){   
               WdatePicker({
                'onpicked':function(dp){ 
                    if (ngModel) { 
                      $scope.$apply(function() {
                      ngModel.$setViewValue(dp.cal.getNewDateStr()); 
                      });
                    }
                },
                dateFmt:'yyyy-MM-dd HH:mm'
               });
            });
        }
    };
});


app.directive("datesecond", function() {
    return {
        require: '?ngModel',
        restrict: 'A',
        link: function ($scope, element, attrs, ngModel) {
            element.bind('click',function(){   
               WdatePicker({
                'onpicked':function(dp){ 
                    if (ngModel) { 
                      $scope.$apply(function() {
                      ngModel.$setViewValue(dp.cal.getNewDateStr()); 
                      });
                    }
                },
                dateFmt:'yyyy-MM-dd HH:mm:ss',
                minDate:'%y-%M-#{%d}'
               });
            });
        }
    };
});
//批量发货
app.directive("fileuploadexcel", function(ngDialog,$cookieStore) {
	var cc = $cookieStore.get("user");
    return {
        require: '?ngModel',
        restrict: 'A',
        link: function ($scope, element, attrs, ngModel) {
            var opts = angular.extend({}, $scope.$eval(attrs.fileuploadexcel));
            var user = $cookieStore.get('user');
            element.uploadify({
                'fileObjName':'excelFile',
                'formData' : {'userId' : user.userId},
                'auto': opts.auto!=undefined?opts.auto:true,
                'swf': opts.swf || 'resource/js/uploadify/uploadify.swf',
                'uploader': opts.uploader || (dataSource+"/orders/bulkShipment"),//图片上传方法
                'buttonText': opts.buttonText || '本地图片',
                'buttonClass':'inptext uploadbtn',
                'width': opts.width || 98,
                'height': opts.height || 34,
                'multi':opts.multi || true,
                'fileTypeDesc' : 'Image Files',
                'fileTypeExts' : opts.typeExts || '*.xlsx; *.xls',
                'fileSizeLimit' : opts.maxSize || '10MB',
                'onUploadSuccess': function (file, d, response) {
                    if (ngModel) {
                        var result = $.parseJSON(d);
                        if (result.Code == "200") {
                        	var msgMap = result.Content;
                        	$scope.succSum=msgMap.succSum;
                        	$scope.failSum=msgMap.failSum;
                        	$scope.msgData=msgMap.msgData;
                        	$scope.incomplete=msgMap.incomplete;
                        	//弹出对话框
                            ngDialog.open({
                                closeByDocument:false,
                                template: 'views/pub/dialog_message.html',
                                className: 'ngDialog-custom-dialog ngDialog-custom-dialogU',
                                scope: $scope
                            });
                        } else {
                        	ngDialog.error(result.Message);
                        }
                    }
                },
                'onUploadError' : function(file, errorCode, errorMsg, errorString) { 
                },
                'onCancel' : function(file) { 
                }
            });
        }
    };
});
