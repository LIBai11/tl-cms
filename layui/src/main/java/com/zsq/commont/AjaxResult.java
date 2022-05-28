package com.zsq.commont;

import lombok.Data;

@Data
public class AjaxResult {
    private String msg;
    private Object obj;
    private Integer code;
}
