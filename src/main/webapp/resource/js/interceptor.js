// Session 注入(请求拦截器)
app.factory('httpInterceptor', 
    [       '$q','$injector','$log','$cookieStore', 
    function($q,  $injector,  $log,    $cookieStore) {  
    var rootScope = $injector.get('$rootScope');
    var httpInterceptor = {
        request: function(config) { 
            //config.requestTimestamp = new Date().getTime();
            if (rootScope.user) {  
                //$log.info(rootScope.user.token);
                config.headers['token'] = rootScope.user.token;
                config.headers['Content-Type'] = "application/json;charset=utf-8" ;
                config.headers['language'] = "zh-cn";
            }
            return config;
        },  
        requestError : function(config){
            return $q.reject(config);  
        },
        response: function(config) { 
            // console.log(config.data);
            if(config.data.Code&&config.data.Code=='202'){
                //执行推出 清空缓存 
                var status = config.status;
                rootScope.user = null;
                $cookieStore.remove('user');  
                //$jsession.setSession(null); //广播 并且退出
                window.location.href="index.html";
            }else if(config.data.Code&&config.data.Code=='2002'){
                //rootScope.$state.go("admin.405page");
            }


            return config;
        },
        //访问响应异常 控制跳转 并且提示
        responseError:function (config) {
            // debugger;
            var status = config.status;
            var state = $injector.get('$rootScope').$state.current.name;  
            rootScope.stateBeforLogin = state; 
            $log.error("请求异常:"+ status); 
            rootScope.$state.go(status.toString()); 
            return $q.reject(config);
        }
    };
    return httpInterceptor;
}]);


app.config(['$httpProvider', function($httpProvider) {  
    $httpProvider.interceptors.push('httpInterceptor');
}]);