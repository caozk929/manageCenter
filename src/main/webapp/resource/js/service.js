/**
 * 定义 用户session factory
 */
app 
  .factory('$jsession', function ($rootScope,$cookieStore,$state) {
    var curuser;
    var curpermissions;
    return {
      setSession: function(_user,_permissions) { 
        curuser = _user; 
        curpermissions = _permissions;
        $rootScope.user = curuser;
        $cookieStore.put('user',curuser);

        var size = 2048;
        var count = 0;
        var _tmp = _permissions ;
        var size = 2048;
        var count = 0;
        var _tmp = _permissions ;
        if(_permissions != null&&_permissions!=''){//拆分_permissions
          // count = Math.ceil(_permissions.length/size);//向上取整,有小数就整数部分加1
          // for(var i=0; i<count; i++) {
          //   $cookieStore.put('permissions'+i,_permissions.substr(i*size,size));
          // }
          // $cookieStore.put('count',count);
          $cookieStore.put('permissions',_permissions);
          $rootScope.permissions = _permissions;
        } else {//退出时，删除cookie
            // var _count = $cookieStore.get('count');
            // for(var i=0;i<parseInt(_count);i++){
            //   $cookieStore.remove('permissions'+i);
            // }
            $cookieStore.remove('permissions');
        }
        $rootScope.$broadcast('jsessionChanged');
      },

      //控制界面的
      hasPermission: function (permission) { 
        //return true;
        if($rootScope.user.userType==3){//如果是商家主帐号拥有商家的所有权限
          return true;
        }
        //alert(permission);
        console.log(permission+","+$cookieStore.get('permissions'));
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
    };
  }) 

/**
 * 通用服务
 */
app.factory('commonService', function($rootScope,$q,$http,$translate,$jsession) {
  return {
    getCategoryById:function(id){
        var deferred = $q.defer();
        var promise = deferred.promise;  
        $http({
            method:'post',
            url: dataSource + '/categorys/getCategorys',
            data:{categoryId:id}
        })
        .success(function(data,status,headers,config){ 
          if(data.Code == '200'){ 
              deferred.resolve(data.Content);  
       }else{
          deferred.reject(data.Message);
        } 
          })
          .error(function(data,status,headers,config){ 
              deferred.reject(data);
          }); 
          promise.success = function (fn) {
              promise.then(fn);
              return promise;
          }
          promise.error = function (fn) {
              promise.then(null, fn);
              return promise;
          }
        return promise;   
    },
    getCategoryByParentId:function(id){
        var deferred = $q.defer();
        var promise = deferred.promise;  
        $http({
            method:'post',
            url: dataSource + '/categorys/getCategorysByParent',
            data:{parentId:id}
        })
        .success(function(data,status,headers,config){ 
          if(data.Code == '200'){ 
              deferred.resolve(data.Content);  
       }else{
          deferred.reject(data.Message);
        } 
          })
          .error(function(data,status,headers,config){ 
              deferred.reject(data);
          }); 
          promise.success = function (fn) {
              promise.then(fn);
              return promise;
          }
          promise.error = function (fn) {
              promise.then(null, fn);
              return promise;
          }
        return promise;   
    },
    getCategoryByBrother:function(id){
        var deferred = $q.defer();
        var promise = deferred.promise;  
        $http({
            method:'post',
            url: dataSource + '/categorys/getCategorysByBrother',
            data:{categoryId:id}
        })
        .success(function(data,status,headers,config){ 
          if(data.Code == '200'){ 
              deferred.resolve(data.Content);  
       }else{
          deferred.reject(data.Message);
        } 
          })
          .error(function(data,status,headers,config){ 
              deferred.reject(data);
          }); 
          promise.success = function (fn) {
              promise.then(fn);
              return promise;
          }
          promise.error = function (fn) {
              promise.then(null, fn);
              return promise;
          }
        return promise;   
    },
    getCategory:function(isAddAll){
        var deferred = $q.defer();
        var promise = deferred.promise;  
        $http({
            method:'post',
            url: dataSource + '/categorys/getcategorysbyparentid',
            data:{parentId:0}
        })
        .success(function(data,status,headers,config){ 
          if(data.Code == '200'){
            if(!isAddAll)
              deferred.resolve(data.Content);
            else{
              var list = new Array();
              list.push({id:-1,text:$translate.instant('allrecord')});
              for(i=0;i<data.Content.length;i++){
                list.push(data.Content[i]);
              } 
              deferred.resolve(list);
            }

       }else{
          deferred.reject(data.Message);
        } 
          })
          .error(function(data,status,headers,config){ 
              deferred.reject(data);
          }); 
          promise.success = function (fn) {
              promise.then(fn);
              return promise;
          }
          promise.error = function (fn) {
              promise.then(null, fn);
              return promise;
          }
        return promise;   
    },
    getOrganType:function(){
        var deferred = $q.defer();
        var promise = deferred.promise;  
        $http({
            method:'post',
            url: dataSource + '/apply/organ/getorgantype',
            data:{}
        })
        .success(function(data,status,headers,config){ 
          if(data.Code == '200'){
              deferred.resolve(data.Content); 
           }else{
              deferred.reject(data.Message);
            } 
          })
          .error(function(data,status,headers,config){ 
              deferred.reject(data);
          }); 
          promise.success = function (fn) {
              promise.then(fn);
              return promise;
          }
          promise.error = function (fn) {
              promise.then(null, fn);
              return promise;
          }
        return promise;   
    },
    getDictList:function(ids){
        var deferred = $q.defer();
        var promise = deferred.promise;  
        $http({
            method:'post',
            url: dataSource + '/setting/dict/getdictbydictids',
            data:{ids:ids}
        })
        .success(function(data,status,headers,config){ 
          if(data.Code == '200'){
              deferred.resolve(data.Content); 
           }else{
              deferred.reject(data.Message);
            } 
          })
          .error(function(data,status,headers,config){ 
              deferred.reject(data);
          }); 
          promise.success = function (fn) {
              promise.then(fn);
              return promise;
          }
          promise.error = function (fn) {
              promise.then(null, fn);
              return promise;
          }
        return promise;   
    },
    getAreaByDictId:function(id){
        var deferred = $q.defer();
        var promise = deferred.promise;  
        $http({
            method:'post',
            url: dataSource + '/setting/dict/getareabydictid',
            data:{id:id}
        })
        .success(function(data,status,headers,config){ 
          if(data.Code == '200'){
              deferred.resolve(data.Content); 
           }else{
              deferred.reject(data.Message);
            } 
          })
          .error(function(data,status,headers,config){ 
              deferred.reject(data);
          }); 
          promise.success = function (fn) {
              promise.then(fn);
              return promise;
          }
          promise.error = function (fn) {
              promise.then(null, fn);
              return promise;
          }
        return promise;   
    },
    getAreaByItemCode:function(id){
        var deferred = $q.defer();
        var promise = deferred.promise;  
        $http({
            method:'post',
            url: dataSource + '/region/list',
            data:{parentId:id}
        })
        .success(function(data,status,headers,config){ 
          if(data.Code == '200'){
              deferred.resolve(data.Content); 
           }else{
              deferred.reject(data.Message);
            } 
          })
          .error(function(data,status,headers,config){ 
              deferred.reject(data);
          }); 
          promise.success = function (fn) {
              promise.then(fn);
              return promise;
          }
          promise.error = function (fn) {
              promise.then(null, fn);
              return promise;
          }
        return promise;   
    },
    getNotify:function(){
        var deferred = $q.defer();
        var promise = deferred.promise;  
        $http({
            method:'post',
            url: dataSource + '/notice/getMyNotice',
            data:{}
        })
        .success(function(data,status,headers,config){ 
           deferred.resolve(data);
          })
          .error(function(data,status,headers,config){ 
              deferred.reject(data);
          }); 
          promise.success = function (fn) {
              promise.then(fn);
              return promise;
          }
          promise.error = function (fn) {
              promise.then(null, fn);
              return promise;
          }
        return promise;   
    },
    getNoticeList:function(){
        var deferred = $q.defer();
        var promise = deferred.promise;  
        $http({
            method:'post',
            url: dataSource + '/notice/getNoticeList2',
            data:{forshow:1}
        })
        .success(function(data,status,headers,config){ 
           deferred.resolve(data);
          })
          .error(function(data,status,headers,config){ 
              deferred.reject(data);
          }); 
          promise.success = function (fn) {
              promise.then(fn);
              return promise;
          }
          promise.error = function (fn) {
              promise.then(null, fn);
              return promise;
          }
        return promise;   
    },

    readPushMsg:function(msgType){
        var deferred = $q.defer();
        var promise = deferred.promise;  
        $http({
            method:'post',
            url: dataSource + '/notice/readPushMsg',
            data:{msgType:msgType}
        })
        .success(function(data,status,headers,config){ 
          if(data.Code == '200'){
              
           }else{
              //ngDialog.alert(data.Message);
            } 
          })
          .error(function(data,status,headers,config){ 
              deferred.reject(data);
          }); 
          promise.success = function (fn) {
              promise.then(fn);
              return promise;
          }
          promise.error = function (fn) {
              promise.then(null, fn);
              return promise;
          }
        return promise;   
    },
    loginOut:function(){
        var deferred = $q.defer();
        var promise = deferred.promise;  
        $http({
            method:'post',
            url: dataSource + '/users/loginout'
        })
        .success(function(data,status,headers,config){ 
          if(data.Code == '200'){
              $rootScope.user=null;
              $jsession.setSession(null,null); 
           }else{
              ngDialog.alert(data.Message);
            } 
          })
          .error(function(data,status,headers,config){ 
              deferred.reject(data);
          }); 
          promise.success = function (fn) {
              promise.then(fn);
              return promise;
          }
          promise.error = function (fn) {
              promise.then(null, fn);
              return promise;
          }
        return promise;   
    },

    /**
     * 获取类型列表
     */
    getValidCode : function(scope,http,log,tar) {  
      http({
          method:'get',
          url: dataSource+'/random?_='+Math.random(),
          headers: {"Accept":"text/plain"}
      })
      .success(function(result,status,headers,config){ 
        //alert(result);
        //$('#testVc').attr('src','data:image/jpg;base64,'+result);
        result = 'data:image/jpg;base64,'+ result; 
        eval('scope.'+tar+'=result');
        scope.token = headers('x-auth-token');
      })
      .error(function(result,status,headers,config){ 

      });
    },
           /**
     * 判断页面的code是否正确
     */
    checkValidCode : function(scope,http,log,code,ngDialog) { 
      http({
          method:'post',
          url: dataSource+'/random/check?code='+code,
          data: null,
          headers: {"x-auth-token":scope.token}
      })
      .success(function(result,status,headers,config){ 
        //alert(result.code);
        //alert(code);  
          if(result.code == '0000'){
             scope.vcRight();
            
          }else{ 
          var _dialog = ngDialog.open({ template: '<h4>请输入正确的验证码</h4>', plain: true,overlay: false}); }

             
          
      })
      .error(function(result,status,headers,config){
        
      });
    },
    getTypes : function(scope,http,log,tar,type ) {  
      http({
          method:'post',
          url: dataSource+'/api/options',
          data: {optionType: type}
      })
      .success(function(result,status,headers,config){ 
          if(result.code == '0000'){
            eval('scope.'+ tar + "=result.data");
          }else{
            log.info(result.msg);
          }

      })
      .error(function(result,status,headers,config){
        $.isLoading('hide'); 
      });
    },
    //单张图片服务
    uploadImgBase64 : function(scope,http,log,_imgBase64Str,tar ) { 
      _imgBase64Str = _imgBase64Str.replace('data:image/png;base64,','');
      $.isLoading({ text: "正在上传图片,请稍候......" }); 
      http({
          method:'post',
          url: dataSource+'/image/upload',
          data: _imgBase64Str
      })
      .success(function(result,status,headers,config){ 
        $.isLoading('hide'); 
          if(result.code == '0000'){
            eval('scope.'+ tar + "=result.data");
          } else {
            log.info(result.msg);
          }
      })
      .error(function(result,status,headers,config){
        $.isLoading('hide'); 
      });
    },
    //多张图片服务
    uploadImgBase64ForList : function(scope,http,log,_imgBase64Str,tar ) { 
      _imgBase64Str = _imgBase64Str.replace('data:image/png;base64,','');
      $.isLoading({ text: "正在上传图片,请稍候......" }); 
      http({
          method:'post',
          url: dataSource+'/image/upload',
          data: _imgBase64Str
      })
      .success(function(result,status,headers,config){ 
        $.isLoading('hide'); 
          if(result.code == '0000'){
            eval('scope.'+ tar + ".push(result.data)");
          }else{
            log.info(result.msg);
          }
      })
      .error(function(result,status,headers,config){
        $.isLoading('hide'); 
      });
    },
    

}});