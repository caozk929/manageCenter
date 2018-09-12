package com.zjht.manager.common.dto;

/**
 * 处理结果不通过dto
 */
public class LayuiResultFailDto extends LayuiResultDto<Object>{

    public LayuiResultFailDto(){
        super.setStatus(ApiConstants.FAIL);
    }

    public LayuiResultFailDto(String msg){
        super.setMsg(msg);
        super.setStatus(ApiConstants.FAIL);
    }

    public LayuiResultFailDto(String status, String msg){
        super.setStatus(status);
        super.setMsg(msg);
    }
}
