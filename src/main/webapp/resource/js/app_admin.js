//全局数据源
//var dataSource = 'http://localhost:8099/api';
//var dataSource = 'http://localhost:8099/testapi';

// var dataSource = '/zshop/api/';
// var appname = "中经商城-电子商务平台";
// var permissionList;
// var version=new Date().getTime();//~(-new Date() / 36e5);
// var curuser = {};

// var reviewUrl = "/zshop/h5/detail.html?productId=";



var dataSource = '/admin/api/';
var appname = "鉴客商城-电子商务平台";
var permissionList;
var version=new Date().getTime();//~(-new Date() / 36e5);
var curuser = {};

var reviewUrl = "/detail.html?productId=";
var host = window.location.host;
var sketchReviewUrl="/sketch.html?id=";
var allSketchReviewUrl="http://"+host+"/sketch.html?id=";
var allReviewUrl="http://"+host+"/detail.html?productId=";
var productsReviewUrl="http://"+host+"/products.html?id=";



//加载
var app = angular.module('pujinwangApp', [
  'pujinwangApp.admin',
  'pujinwangApp.main',
  'ui.router',
  'ngCookies',
  'ngDialog',
  'w5c.validator',
  'treeControl',
  'nsPopover','pascalprecht.translate','TNotify','ngKeditor'
]);

// run
app.run(
  [          '$rootScope', '$state', '$stateParams','$jsession','$log','$cookieStore','$location','$translate', 
    function ($rootScope,   $state,   $stateParams, $jsession, $log, $cookieStore,$location,$translate ) { 
      
      $rootScope.appname=appname;
      $rootScope.$state = $state;
      $rootScope.$stateParams = $stateParams;
      $rootScope.uploadJson = dataSource+'/kindEditorController/doUpload';
      $rootScope.fileManagerJson = '../upload/file_manager_json.jsp';
      var lang = $cookieStore.get("language");
      if(lang) 
          $rootScope.language=lang;
      else
          $rootScope.language='cn';
      $translate.use($rootScope.language);
      // config
      $rootScope.app = {
        name: appname,
        version: '1.3.3',
        // for chart colors
        color: {
          primary: '#7266ba',
          info:    '#23b7e5',
          success: '#27c24c',
          warning: '#fad733',
          danger:  '#f05050',
          light:   '#e8eff0',
          dark:    '#3a3f51',
          black:   '#1c2b36'
        },
        settings: {
          themeID: 1,
          navbarHeaderColor: 'bg-black',
          navbarCollapseColor: 'bg-white-only',
          asideColor: 'bg-black',
          headerFixed: true,
          asideFixed: false,
          asideFolded: false,
          asideDock: false,
          container: false
        }
      }

      // //默认跳转状态
      var _u = $cookieStore.get('user'); 
      // if(_u){ 
      //   $rootScope.user = _u;
      //   $rootScope.isLogin=true;  
      // }  
      // $state.go('pub.login');
       

      if(_u!=null){
        var _p = "";
        if($cookieStore.get('permissions')){
          _p = $cookieStore.get('permissions');
        }

        $jsession.setSession(_u,_p);
        $rootScope.user = _u;
        $rootScope.permissions = _p;
        $state.go('admin.users.list'); 

      }else{
        //$log.info("u:"+_u);
        $rootScope.user = null;
        $cookieStore.remove('user');
        $state.go("pub.login")

        //$jsession.setSession(null); //广播 并且退出
      }
      /**
       * jsession 监听 
       */
      $rootScope.$on('jsessionChanged',   
      function(event){
          var state = event.targetScope.$state;
            if(!$rootScope.user ){
              event.preventDefault();// 取消默认跳转行为
              $state.go("pub.login");//跳转到登录界面
              //alert(1);
            }else{
              var _u = $rootScope.user;
              event.preventDefault();// 取消默认跳转行为
                           
              $state.go('admin.users.list');
            }   
      })
 
      //定义 前一状态 方便返回
      $rootScope.$on('$stateChangeSuccess', function(event, to, toParams, from, fromParams) {
            $rootScope.$previousState = from;
      });
      $rootScope.$on('$stateChangeStart', function() {
          if($('.uploadify').length>0){
            $('.uploadify').uploadify('destroy');
          }
      });


      $rootScope.hasPermission = function (permission) { 
       return true; 
        var flag = false;
        if($rootScope.permissions){
          var tmp = $rootScope.permissions.split(','); 
          var tmp2 = (permission+"").split(',');
          for(var ind=0;ind<tmp.length;ind++){
            for(var ind2 = 0;ind2<tmp2.length;ind2++){
              if(tmp[ind]==tmp2[ind2]){
                flag = true;
              }
            } 
          } 
        }else{ 
        }
        return flag; 
      } 

      
    }
  ]
);
 
//手动启动渲染
angular.element(document).ready(function() {
    angular.bootstrap(document, ['pujinwangApp']);
});
 
// 监听body的点击事件
angular.element('body').bind('click', function (eve) {

    //listenMydialog(eve);
    // 找到所有的popover实例
    var pops = angular.element(".ngdialog-content");

    if (pops.length == 0) {
        return;
    }

    var pop = angular.element(pops[0]);
    var popScope = pop.scope();
    if (!popScope) {
        return;
    }
    
    // 只响应元素自己产生的的popover，其余的不处理
    // if (element != popScope.triggerElement) {
    //     return;
    // }
    var target = eve.target;
    var isClickPop = pop.is(target) || pop.has(target).length > 0;
    // 点击的区域是popover的区域，不处理
    
    if (isClickPop) {
        return;
    }
    else
    {
      //ngDialog.closeAll({});
      $(".ngdialog").remove();
       angular.element('body').removeClass("ngdialog-open");
       angular.element('body').css('padding-right', '')
    }

    
    
});

