package com.zjht.manager.common.dto;

/**
 * 处理结果成功dto
 */
public class ResultSuccessDto extends ResultDto<Object>{

    public ResultSuccessDto(){
        super.setStatus(ApiConstants.SUCCESS);
    }

    public ResultSuccessDto(String msg){
        super.setMsg(msg);
        super.setStatus(ApiConstants.SUCCESS);
    }
}
