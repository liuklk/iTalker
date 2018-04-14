package com.klk.common;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/4/3  9:19
 */

public class Common {

    /**
     * 配置一些永恒不变的值
     */
    public interface Constance{
        // 手机号的正则,11位手机号
        String REGEX_MOBILE = "[1][3,4,5,7,8][0-9]{9}$";

        //服务器根路径
        String ROOT_URL = "http://192.168.42.135:8090/api/";
    }
}
