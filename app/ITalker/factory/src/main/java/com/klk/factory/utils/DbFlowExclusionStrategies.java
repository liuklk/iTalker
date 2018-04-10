package com.klk.factory.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

/**
 * @Des DbFlow的json解析的过滤器
 * @Auther Administrator
 * @date 2018/4/10  16:02
 */

public class DbFlowExclusionStrategies implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        //跳过的字段
        return f.getClass().equals(ModelAdapter.class);
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        //跳过的类
        return false;
    }
}
