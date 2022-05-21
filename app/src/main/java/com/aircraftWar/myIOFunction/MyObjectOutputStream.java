package com.aircraftWar.myIOFunction;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @author 200110501符悦泽
 * @create 2022/4/17
 */
public class MyObjectOutputStream extends ObjectOutputStream {

    public MyObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        //重写读取头部信息方法：不写入头部信息
        super.reset();
    }
}
