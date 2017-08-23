//全局数据源
//var dataSource = 'http://localhost:8099/api';
//var dataSource = 'http://localhost:8099/testapi';
var dataSource = 'http://localhost:8099/pjw/api/';

var permissionList;
var curuser = {};
//加载
var app = angular.module('pujinwangApp', [
  'pujinwangApp.pub',
  'pujinwangApp.admin',
  'pujinwangApp.sale',
  'pujinwangApp.apply',
  'pujinwangApp.buy',
  'pujinwangApp.service',  
  'ui.router',
  'ngCookies',
  'ngDialog',
  'ngGrid' ,
  'w5c.validator',
  'nsPopover','pascalprecht.translate','TNotify'
]);

// run
app.run(
  [          '$rootScope', '$state', '$stateParams','$jsession','$log','$cookieStore','$location','$translate', 
    function ($rootScope,   $state,   $stateParams, $jsession, $log, $cookieStore,$location,$translate ) { 
      
      $rootScope.$state = $state;
      $rootScope.$stateParams = $stateParams;
      var lang = $cookieStore.get("language");
      if(lang) 
          $rootScope.language=lang;
      else
          $rootScope.language='cn';
      $translate.use($rootScope.language);

      $rootScope.showRawImg = function(url,size){
        showRawImg(url.replace(size,''));
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
        var _count = $cookieStore.get('count');
        for(var i=0;i<parseInt(_count);i++){
          _p += $cookieStore.get('permissions'+i);
        }
        $jsession.setSession(_u,_p);
        $rootScope.user = _u;
         
        if(_u.status==7)
        {
          //alert(_u.status+" "+_u.userType);
          if(_u.userType == 0)
          { 
            $state.go('apply.organ');
          }
          if(_u.userType == 1)
          {
                if(window.location.href.indexOf("admin.html")>=0)
                  { 
                      //window.location.href="admin.html"; 
                      $state.go('admin.audit.list'); 
                  }
                  if(window.location.href.indexOf("admin_auction.html")>=0)
                  { 
                      //window.location.href="admin.html"; 
                      $state.go('admin.acutionmall.add'); 
                  }
          }
          if(_u.userType == 2)
          {
            if(window.location.href.indexOf("index.html")>=0)
            {
                //$location.path("index_sale.html");
                window.location.href="index_sale.html";
                $state.go('sale.product.upload');
                //$state.go('sale.product.upload');
            }
            if(window.location.href.indexOf("index_sale.html")>=0)
            {
                $state.go('sale.product.upload');
            }
            if(window.location.href.indexOf("index_service.html")>=0)
            {
                $state.go('service.bankvoucher.list',{payType:1});
            }

            if(window.location.href.indexOf("index_buy.html")>=0)
            {
                //$location.path("index_sale.html");
                $state.go('buy.search.list');
                //$state.go('sale.product.upload');
            }
            
             
          }
          if(_u.userType == 3) //拍卖用户
          {
             //$location.path("index_apply.html");
          }
        } 
        else
        {
          if(_u.userType==3){
            $state.go('apply.paimai');
          }else{
            $state.go('apply.organ');
          }
        }

        //state.go('main.product.upload'); 
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
              
              if(_u.status==7)
              {
                if(_u.userType == 0)//普通用户
                {
                  $state.go('apply.organ');
                }
                if(_u.userType == 1)//中心管理员
                {
                  if(window.location.href.indexOf("admin.html")>=0)
                  { 
                      // window.location.href="admin.html"; 
                      
                      $state.go('admin.audit.list'); 
                  }
                  if(window.location.href.indexOf("admin_auction.html")>=0)
                  { 
                      //window.location.href="admin.html"; 
                      $state.go('admin.acutionmall.add'); 
                  }
                  // $state.go('admin.userlog'); 
                }
                if(_u.userType == 2)//卖家
                {
                  if(window.location.href.indexOf("index.html")>=0)
                  {
                      //$location.path("index_sale.html");
                      window.location.href="index_sale.html";
                      //$state.go('sale.product.upload');
                  }
                  $state.go('sale.product.upload'); 
                }
                if(_u.userType == 3) //拍卖用户
                {

                }
              }
              else
              {
                $state.go('apply.organ');
              }
            }   
      })
 
      //定义 前一状态 方便返回
      $rootScope.$on('$stateChangeSuccess', function(event, to, toParams, from, fromParams) {
            $rootScope.$previousState = from;
      });


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

