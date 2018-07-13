package com.tommy.autocheckin.utils;

/**
 * Created by wojia on 2018/6/19.
 */

import java.io.Closeable;
import java.io.IOException;

public class CloseUtils {

    private CloseUtils() {
        throw new UnsupportedOperationException("do not instantiate");
    }

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}