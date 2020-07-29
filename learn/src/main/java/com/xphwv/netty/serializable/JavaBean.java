package com.xphwv.netty.serializable;

import java.io.Serializable;

/**
 * Created by xupan on 2018/4/10
 */
public class JavaBean implements Serializable{
//    private static final long serialVersionUID = -1869515685642466756L;
    //    private static final long serialVersionUID = -1869515685642466756L;
        private static final long serialVersionUID = 1L;
//        private static final long serialVersionUID = -1869515685642466756L;
    private int id;
    private String name;
    private String sex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
