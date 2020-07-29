package com.xphwv.netty.serializable;

import com.alibaba.fastjson.JSON;

import java.io.*;

/**
 * Created by xupan on 2018/4/10
 */
public class IOSerializable {
    public static void main(String[] args) throws Exception {
//        write();
        read();

    }
    private static void  write() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("/usr/local/work/workspace/github/data1.dat"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        JavaBean bean = new JavaBean();
        bean.setId(1);
        bean.setName("aaa");
        System.out.println(JSON.toJSONString(bean));
        objectOutputStream.writeObject(bean);
        fileOutputStream.flush();
        fileOutputStream.close();
        objectOutputStream.flush();
        objectOutputStream.close();
    }
    private static void  read() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File("/usr/local/work/workspace/github/data1.dat"));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        JavaBean1 bean= (JavaBean1) objectInputStream.readObject();
        System.out.println(JSON.toJSONString(bean));
        fileInputStream.close();
        objectInputStream.close();
    }
}
