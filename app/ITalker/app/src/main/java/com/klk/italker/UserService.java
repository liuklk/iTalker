package com.klk.italker;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/3/22  11:11
 */

public class UserService implements IUserService {

    @Override
    public int getDataHashcode(String input) {
        return input.hashCode();
    }
}
