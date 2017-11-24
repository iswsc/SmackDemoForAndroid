package com.iswsc.smackdemo.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jacen on 2017/11/24 17:58.
 * jacen@iswsc.com
 */

public enum MessageStatus {
    success(0, "消息发送成功"),
    sending(1, "消息发送中"),
    failed(2, "消息发送失败");
    private int id;
    private String desc;
    private static final Map<Integer, MessageStatus> code2DeptType;

    static {
        code2DeptType = new HashMap<Integer, MessageStatus>();

        for (MessageStatus deptType : MessageStatus.values()) {
            code2DeptType.put(deptType.getId(), deptType);
        }
    }

    MessageStatus(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public static MessageStatus fromCode(int id) {
        if (code2DeptType.get(id) == null) {
            return success;
        }
        return code2DeptType.get(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
