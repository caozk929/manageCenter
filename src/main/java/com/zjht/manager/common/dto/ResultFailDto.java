package com.zjht.manager.common.dto;

/**
 * 处理结果不通过dto
 */
public class ResultFailDto extends ResultDto<Object>{

    public ResultFailDto(){
        super.setStatus(ApiConstants.FAIL);
    }

    public ResultFailDto(String msg){
        super.setMsg(msg);
        super.setStatus(ApiConstants.FAIL);
    }

    public ResultFailDto(String status, String msg){
        super.setStatus(status);
        super.setMsg(msg);
        super.setStatus(ApiConstants.FAIL);
    }
}
