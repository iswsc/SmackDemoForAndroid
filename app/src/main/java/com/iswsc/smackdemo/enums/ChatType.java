package com.iswsc.smackdemo.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jacen on 2017/10/20 18:45.
 * jacen@iswsc.com
 */

public enum ChatType {
    error(-1, -1,-1,"文本"),
    text(0, 1,2,"文本"),
    image(1,3,4, "图片"),
    audio(2, 5,6,"语音");
    private int id;
    private int left;
    private int right;
    private String desc;
    private static final Map<Integer, ChatType> code2DeptType;

    static {
        code2DeptType = new HashMap<Integer, ChatType>();

        for (ChatType deptType : ChatType.values()) {
            code2DeptType.put(deptType.getId(), deptType);
        }
    }

    ChatType(int id,int left,int right, String desc) {
        this.id = id;
        this.desc = desc;
        this.left = left;
        this.right = right;
    }

    public static ChatType fromCode(int id){
        if (code2DeptType.get(id) == null) {
            return error;
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

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }
}
