package com.aircraftWar.myIOFunction;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * @author 200110501符悦泽
 * @create 2022/4/17
 */
public class MyObjectInputStream extends ObjectInputStream {

    public MyObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected void readStreamHeader() throws IOException {
        //重写读取头部信息方法：什么也不做
    }
}