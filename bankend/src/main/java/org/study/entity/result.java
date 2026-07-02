package org.study.entity;

import lombok.Data;

@Data
public class result {

    private Integer code;
    private Object data;
    private String msg;

    public static result ok(Object data) {
        result r = new result();
        r.setCode(200);
        r.setData(data);
        return r;
    }

    public static result fail(Integer code, String msg) {
        result r = new result();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}
