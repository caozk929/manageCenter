
//配置对话框
// app.config(['ngDialogProvider', function (ngDialogProvider) {
//     ngDialogProvider.setDefaults({
//         className: 'ngdialog-theme-default',
//         plain: false,
//         showClose: true,
//         closeByDocument: true,
//         closeByEscape: true,
//         appendTo: false,
//         preCloseCallback: function () {
//             //console.log('default pre-close callback');
//         }
//     });
// }])
// .config(function($sceProvider){
//   $sceProvider.enabled(false);
// });


/**
 * 工具
 * @type {Object}
 */
var helper = {};
helper.cssHelper = function(){
  // var winHeight=$(window).height();  //获取浏览器窗口高度
  // winHeight = winHeight<700?700:winHeight;
  // var headHeight=$(".header").outerHeight(true);    //获取头部全部高度
  // var footerHeight=$(".footer").outerHeight(true);  //获取尾部全部高度
  // var minHeight= winHeight-headHeight-footerHeight; //内容的最小高度等于浏览器高度-头部高度-尾部高度
  // minHeight = minHeight<700?700:minHeight;
  // $(".minHeight").css("min-height",minHeight);
  // //帐户信息side、main最小高度
  // $(".side,.main").css("min-height",minHeight-20);
  // var mainH=$(".side").siblings(".main").height();
  // $(".side").height(mainH);
  
}

helper.setLeftMenuOn = function(id){
  $(".leftmenu .subitems").removeClass("on");
  $("#"+id).addClass("on");
}

helper.initParseDictItems = function(scope){
  scope.getDictItems = function(dictList,dictId){ 
    var items;
    for (var i = 0; i < dictList.length; i++) {
      if(dictList[i].dict !=null && dictList[i].dict.dictId === dictId){
        items = dictList[i].dictItem;
      }
    };
    return items;
  };

  scope.$watch('dictList', function (newVal, oldVal) {
      if (newVal !== oldVal) {
        scope.afterGetDictList();
      }
  }, true);
}

helper.initParseDictItems2 = function(scope){
  scope.getDictItems = function(dictList,dictId){ 
    var items;
    for (var i = 0; i < dictList.length; i++) {
      if(dictList[i].dict.dictId === dictId){
        items = dictList[i].dictItem;
      }
    };
    return items;
  }; 
}

/**
 * 全局配置 scope 的ng-grid属性
 * 
 * @type {Object}
 */
 /*
var pagerHelper = {};
pagerHelper.init = function(scope){
  scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    };
  scope.totalServerItems = 0;
  scope.pagingOptions = { 
      pageSize: 20,
      currentPage: 1,
      pageSizes:[10,20,50]
  };

  scope.setPagingData = function(data, page, pageSize){ 
      var pagedData = data.Content; 
      scope.myData = pagedData;
      scope.totalServerItems =  data.RecordCount;
      if (!scope.$$phase) {
          scope.$apply();
      }
  };
  
  scope.$watch('pagingOptions', function (newVal, oldVal) {
      if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
        scope.getPagedDataAsync();
      }
  }, true);

  scope.$watch('filterOptions', function (newVal, oldVal) {
      if (newVal !== oldVal) {
        scope.getPagedDataAsync();
      }
  }, true);

  scope.gridOptions = {
      data: 'myData',
      rowHeight: 38,
      footerRowHeight :44,
      headerRowHeight :38,
      enablePaging: true,
      showFooter: true,
      totalServerItems: 'totalServerItems',
      pagingOptions: scope.pagingOptions,
      filterOptions: scope.filterOptions,
      multiSelect: false,
      enableSorting: false,
      enableColumnResize: true,
      i18n:'en' };

}
*/

//=====分页=======

/**
 * 全局配置 scope 的ng-grid属性
 * 
 * @type {Object}
 */
var pagerHelper = {};
pagerHelper.init = function(scope,compile){
  scope.recordCount = 0;
  scope.pagingOptions = { 
      pageSize: 20,
      pageIndex: 1      
  };

  
scope.pageData = {};

scope.setPagingData = function(data, page, pageSize){  
      var pagedData = data.Content; 
      scope.pageData = pagedData; 
      scope.recordCount =  data.RecordCount;
      var totalPage = parseInt(scope.recordCount/scope.pagingOptions.pageSize);
      if(scope.recordCount%scope.pagingOptions.pageSize>0)
      {
          totalPage++;
      }
      scope.pagerHtml = "";

      scope.pagerHtml += "<span style='float:left;'>共找到："+scope.recordCount +"条记录</span>";

      if(scope.pagingOptions.pageIndex!=1)
      {
        scope.pagerHtml += "<a class=\"PageItem\" href=\"javascript:void(0)\" ng-click=\"prePage("+scope.pagingOptions.pageIndex+")\">上一页</a>&nbsp;";
      }
      var i = scope.pagingOptions.pageIndex;
      var max = scope.pagingOptions.pageIndex+5;
      if(scope.pagingOptions.pageIndex>4){
        i = scope.pagingOptions.pageIndex-4;
        max = scope.pagingOptions.pageIndex+5;
      }else{
        i=1;
        max = 10;
      }
      for(i;i<=totalPage&& i<max;i++)
      {
        scope.pagerHtml += "<a href=\"javascript:void(0)\" "+ (i==scope.pagingOptions.pageIndex ? 'class="PageItem OnBtn"' : 'class="PageItem"') +" ng-click=\"gotoPage("+i+")\">"+i+"</a>&nbsp;";
      }
      
      if(scope.pagingOptions.pageIndex<totalPage)
      {
        scope.pagerHtml += "<a  class=\"PageItem\" href=\"javascript:void(0)\" ng-click=\"nextPage("+scope.pagingOptions.pageIndex+")\">下一页</a>";
      }
        
      var template = angular.element(scope.pagerHtml);

      var el = compile(template)(scope); 

      $(".pager").html(el);
      //if(data.RecordCount==0 && data.PageSize>0){
      if(data.RecordCount==0){
        scope.pagingOptions.nomatchdata=true;
        $(".pager").parent('tr').hide();
      }else{
        scope.pagingOptions.nomatchdata=false;
        $(".pager").parent('tr').show();
      }
      // if(data.RecordCount==0){
      //   $(".pager").parents('tr').hide();
      //   return;
      // }else{
      //   $(".pager").parents('tr').show();
      // }
  };
  
  scope.$watch('pagingOptions', function (newVal, oldVal) {
      if (newVal !== oldVal && newVal.pageIndex !== oldVal.pageIndex) {
        scope.getPagedDataAsync();
      }
  }, true);

  scope.prePage = function(pageIndex){
      
      if(pageIndex<=0)
        return;
      pageIndex--;
      scope.pagingOptions.pageIndex = pageIndex;
  };

  scope.nextPage = function(pageIndex,totalPage){
       if(pageIndex>=totalPage)
        return;
      pageIndex++;
      scope.pagingOptions.pageIndex = pageIndex;      
  };

  scope.gotoPage = function(pageIndex,totalPage){
      if(pageIndex>=totalPage)
        return;
      scope.pagingOptions.pageIndex = pageIndex;      
  };

}


//=====临时校验工具=====
//校验邮箱
var validUtil = {};
validUtil.check_mail = function(obj){
 var strm=obj;
 var regm = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;//验证Mail的正则表达式,^[a-zA-Z0-9_-]:开头必须为字母,下划线,数字,
 return strm.match(regm);
}
//校验 手机号
validUtil.check_tel = function(obj){
 var strm=obj;
 var regm=/^(?:13\d|15\d|18[123456789])-?\d{5}(\d{3}|\*{3})$/;//验证Mail的正则表达式,^[a-zA-Z0-9_-]:开头必须为字母,下划线,数字,
 return strm.match(regm);
}