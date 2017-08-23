	jQuery.validator.addMethod("money",
		function(value, element) { 
			var moneyRegx =  /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,6})?$/; 
			return this.optional(element) || ( moneyRegx.test(value));}
		,"请输入合法金额");
	jQuery.validator.addMethod("n_money",
		function(value, element) { 
			var moneyRegx =  /^-(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,6})?$/; 
			return this.optional(element) || ( moneyRegx.test(value));}
		,"请输入合法金额,必须为负数");
	jQuery.validator.addMethod("rate",
		function(value, element) { 
			var regx =  /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,4})?$/; 
			return this.optional(element) || ( regx.test(value));}
		,"请输入合法汇率值");
	jQuery.validator.addMethod("percentage",
		function(value, element) { 
			var regx =  /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,4})?$/; 
			return this.optional(element) || ( regx.test(value));}
		,"请输入合法的比例值");
	jQuery.validator.addMethod("limitTh",
		function(value, element) { 
			if(!isNaN(value)&&value<=300){
			   return true;
			}else{return false;}
		}
		,"请输入少于300的数字");