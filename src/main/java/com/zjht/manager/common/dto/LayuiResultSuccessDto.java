package com.zjht.manager.common.dto;

/**
 * 处理结果成功dto
 */
public class LayuiResultSuccessDto extends LayuiResultDto<Object>{

    public LayuiResultSuccessDto(String msg, Object data, long count) {
        super.setMsg(msg);
        super.setData(data);
        super.setCount(count);
        super.setStatus(ApiConstants.SUCCESS);
    }

    public LayuiResultSuccessDto() {
        super();
        super.setStatus(ApiConstants.SUCCESS);
    }

    public LayuiResultSuccessDto(String msg){
        if(msg == null){
            msg = ApiConstants.SUCCESS_MSG;
        }
        super.setMsg(msg);
        super.setStatus(ApiConstants.SUCCESS);
    }
}
