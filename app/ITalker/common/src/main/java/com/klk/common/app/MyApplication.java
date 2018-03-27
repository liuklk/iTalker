package com.klk.common.app;

import android.app.Application;
import android.os.SystemClock;
import android.widget.Toast;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.io.File;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/3/26  14:10
 */

public class MyApplication extends Application {

    private static MyApplication myApplication ;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this ;
    }

    /**
     * 获取application对象的方法
     * @return
     */
    public static MyApplication getInstance(){
        return myApplication ;
    }

    public static void Toast(final String msg){
        //保证Toast都在主线程进行
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                Toast.makeText(getInstance(),msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static void Toast(int msgId){
        Toast(getInstance().getString(msgId));
    }
    /**
     * 获取app 的缓存路径
     * @return
     */
    public static File getAppCacheDirFile(){
        return myApplication.getCacheDir();
    }
    /**
     * 获取头像的存储路径
     * @return
     */
    public static File getPortraitViewFile(){
        //创建头像放置的文件夹
        File dir = new File(getAppCacheDirFile(), "portraitView");
        dir.mkdir();

        //删除文件夹中所有文件
        File[] files = dir.listFiles();
        if(files.length>0){
            for (File f : files) {
                f.deleteOnExit();
            }
        }

        //用时间戳作为文件名
        long millis = SystemClock.uptimeMillis();
        File file = new File(dir, millis + "jpg");

        return file.getAbsoluteFile();
    }

    /**
     * 获取声音文件的本地地址
     *
     * @param isTmp 是否是缓存文件， True，每次返回的文件地址是一样的
     * @return 录音文件的地址
     */
    public static File getAudioTmpFile(boolean isTmp) {
        File dir = new File(getAppCacheDirFile(), "audio");
        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }

        // aar
        File path = new File(getAppCacheDirFile(), isTmp ? "tmp.mp3" : SystemClock.uptimeMillis() + ".mp3");
        return path.getAbsoluteFile();
    }
}
