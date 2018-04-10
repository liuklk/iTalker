package com.klk.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/4/10  16:09
 */
@Database(name = AppDatabase.NAME ,version = AppDatabase.version)
public class AppDatabase {
    public static final String NAME = "AppDatabase";
    public static final int version = 1;
}
