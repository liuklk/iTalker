package com.klk.factory.net;

import android.text.format.DateFormat;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.klk.common.utils.HashUtil;
import com.klk.factory.Factory;

import java.io.File;
import java.util.Date;

/**
 * @Des  用于上传文件的工具类
 * @Auther Administrator
 * @date 2018/3/27  15:34
 */

public class UpLoadHelper {

    private static final String ENDPOINT = "oss-cn-huhehaote.aliyuncs.com";
    private static final String BUCKET_NAME = "klk-italker";
    private static OSS oss;

    /**
     * 获取oss
     * @return
     */
    public static OSS getOss(){

        if(oss==null){
            OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAIEBoUPZ5R7Hlc","nRoq72StWdhlr0YHfPR195Ls4yJSUF");
            //该配置类如果不设置，会有默认配置，具体可看该类
            ClientConfiguration conf = new ClientConfiguration();
            conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
            conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
            conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
            conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

            oss =new OSSClient(Factory.getApplication(), ENDPOINT, credentialProvider) ;
        }

        return oss;
    }

    /**
     * 上传文件的方法
     * @param objectKey
     * @param localPath
     * @return  服务器访问路径
     */
    private static String upload(String objectKey ,String localPath){
        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, objectKey, localPath);

        try {
            OSS mOss = getOss();
            PutObjectResult putResult = mOss.putObject(request);

            String url = mOss.presignPublicObjectURL(BUCKET_NAME, objectKey);
            return url ;

        } catch (Exception e) {
            // 本地异常如网络异常等
            e.printStackTrace();
            return null ;
        }
    }

    /**
     * 上传图片
     * @param localPath  本地路径
     * @return 服务器上访问路径
     */
    public static String upLoadImage(String localPath){
        String objectKey = getImageObjectKey(localPath);

        return upload(objectKey,localPath);
    }

    /**
     * 上传头像
     * @param localPath  本地路径
     * @return 服务器上访问路径
     */
    public static String upLoadPortrait(String localPath){
        String objectKey = getPortraitObjectKey(localPath);

        return upload(objectKey,localPath);
    } /**
     * 上传录音
     * @param localPath  本地路径
     * @return 服务器上访问路径
     */
    public static String upLoadAudio(String localPath){
        String objectKey = getAudioObjectKey(localPath);

        return upload(objectKey,localPath);
    }

    /**
     * 获取年月的字符串 ，按月进行管理文件
     * @return
     */
    private static String getDataFormart(){
        return DateFormat.format("yyyyMM",new Date()).toString();
    }

    /**
     * 获取图片ObjectKey
     * @param localPath
     * @return
     */
    private static String getImageObjectKey(String localPath) {
        String fileMd5 = HashUtil.getMD5String(new File(localPath));
        String dateString  = getDataFormart();
        return String.format("image/%s/%s.jpg",dateString,fileMd5);
    }/**
     * 获取头像ObjectKey
     * @param localPath
     * @return
     */
    private static String getPortraitObjectKey(String localPath) {
        String fileMd5 = HashUtil.getMD5String(new File(localPath));
        String dateString  = getDataFormart();
        return String.format("portrait/%s/%s.jpg",dateString,fileMd5);
    }/**
     * 获取录音ObjectKey
     * @param localPath
     * @return
     */
    private static String getAudioObjectKey(String localPath) {
        String fileMd5 = HashUtil.getMD5String(new File(localPath));
        String dateString  = getDataFormart();
        return String.format("image/%s/%s.jpg",dateString,fileMd5);
    }


}
