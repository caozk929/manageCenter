var valid = {
	msg : {
		isUnsignedIntegerMsg : function(){
			return "请填写正整数";
		},
		isUnsignedNumericMsg : function(){
			return "请填写正整数或2位以内小数";
		},
		compareStrDateMsg : function(){
			return "开始时间应小于结束时间";
		}
	},
	isUnsignedInteger : function(a) {// 验证正整数
		var reg = /^\d+$/;
		return reg.test(a);
	},
	isUnsignedInteger4Json : function(param) {// 批量验证正整数，param为数组，格式[1,2]
		for(var i=0;i<param.length;i++){
			if(!valid.isUnsignedInteger(param[i])){
				return false;
			}
		}
		return true;
	},
	isUnsignedNumeric : function(a) {//验证正数
		var reg = /^\d+(\.\d{1,2})?$/;
		return reg.test(a);
	},
	getDateByStrDate : function(strDate) {
		var date = eval('new Date('
				+ strDate.replace(/\d+(?=-[^-]+$)/, function(a) {
					return parseInt(a, 10) - 1;
				}).match(/\d+/g) + ')');
		return date;
	},
	compareStrDate : function(startDate, endDate) {
		var sDate = valid.getDateByStrDate(startDate);
		var endDate = valid.getDateByStrDate(endDate);
		if(sDate.getTime() >= endDate.getTime()){
			return true;
		}else{
			return false;
		}
	} 
}