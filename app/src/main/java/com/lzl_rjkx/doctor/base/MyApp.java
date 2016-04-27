package com.lzl_rjkx.doctor.base;

import android.app.Application;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by lzl_os on 16/1/25.
 */
public class MyApp extends Application {

    private static MyApp app = new MyApp();
    private static DbManager.DaoConfig daoConfig;

    private String appToken;
    private String cid;

    public MyApp() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);

        daoConfig = new DbManager.DaoConfig()
                .setDbName("doctor_db.db") //设置数据库名称
                .setDbVersion(1).setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                    }
                });
    }

    public static DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    public static MyApp getInstance() {
        return app;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
