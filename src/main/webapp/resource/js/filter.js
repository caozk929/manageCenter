app 
.filter('typeFilter',function($sce){
    /**
     * [description]
     * @param  {[type]} key  [关键字]
     * @param  {[type]} name [类型名称]
     * @param  {[type]} type [索引类型] (hash,index)
     */
    return function(key,name,type){
        if(key==null){return  $sce.trustAsHtml('<d class="txt_red">未处理</d>')};

        var tc = new typeCollection();
        var text = key; 

        if(type == 'hash'||type==null){
            text = tc.getBykey(name,key);
        }else if(type == 'index'){
            text = tc.getByIndex(name,key);
        } 
        return $sce.trustAsHtml(text);
    
    }
}).filter('auditTypeFilter',function($sce){ 
    return function(type,id){
        if(type == 'leagueMember'){
            return "#/admin/memberInfo/realNameAuth";
        }else if(type == 'commend'){
            return "#/admin/my/commend/edit?id="+id;
        }else if(type == 'punish'){
            return "#/admin/my/punish/edit?id="+id;
        }else if(type == 'certificate'){
            return "#/admin/my/cert/edit?id="+id;
        }
        return "";
    
    }
}).filter('df', function(){
    return function(value) {
        if("string" == typeof(value)) {
             return value.replace('-', ' 年 ').replace('-', ' 月 ') + " 日";
        } else {
            return value;
        } 

    }
})
.filter('dfm', function(){
    return function(value) {
        if("string" == typeof(value)) {
             return value.replace('-', ' 年 ') + " 月";
        } else {
            return value;
        } 

    }
})
.filter('rmb', function(){
    return function(value) {
        return '￥'+value; 
    }
})
.filter('trustHtml', function ($sce) {

        return function (input) {

            return $sce.trustAsHtml(input);

        }

    })


.filter('CardAuditStatusText',function($sce){ 
    return function(type){
        if(type == 'CARD_STATUS_NOT_VERIFY'){
            return "待审核";
        }else if(type == 'CARD_STATUS_VERIFY_FAIL'){
            return "审核失败";
        }else if(type == 'CARD_STATUS_VERIFY_OK'){
            return "通过审核";
        }else if(type == 'CARD_STATUS_USER_DELETE'){
            return "卡券被商户删除";
        }
        else if(type == 'CARD_STATUS_DISPATCH'){
            return "在公众平台投放过的卡券";
        }
        return "";
    
    }})


.filter('PluginDiscountStatusText',function($sce){ 
    return function(type){
        if(type == '0'){
            return "未启动";
        }else if(type == '1'){
            return "启动中";
        }else if(type == '2'){
            return "已结束";
        }
        return "";
    
    }})
.filter('CardUseRangeTypeText',function($sce){ 
    return function(type){
        if(type == 'ALL'){
            return "所有商品";
        }else if(type == 'SELECT'){
            return "选定商品";
        }
        return "";
    
    }})
.filter('ZhShopStatusText',function($sce){ 
    return function(type){
        if(type == '0'){
            return "删除";
        }else if(type == '1'){
            return "启用";
        }
        return "";
    
    }})
    .filter('ZhShopPosStatusText',function($sce){ 
    return function(type){
        if(type == '1'){
            return "在用";
        }else if(type == '2'){
            return "待用";
        }else if(type == '0'){
            return "停用";
        }
        return "";
    
    }})



 .filter('cut', function () {
        return function (value, wordwise, max, tail) {
            if (!value) return '';

            max = parseInt(max, 10);
            if (!max) return value;
            if (value.length <= max) return value;

            value = value.substr(0, max);
            if (wordwise) {
                var lastspace = value.lastIndexOf(' ');
                if (lastspace != -1) {
                    value = value.substr(0, lastspace);
                }
            }

            return value + (tail || ' …');
        };
    });
 