package com.klk.italker;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/3/22  11:04
 */

public class Presenter implements IPresenter {

    private IView mView ;

    /**
     * 在presenter中通过构造函数获取设置view
     * @param view
     */
    public Presenter(IView view){
        this.mView = view ;
    }
    @Override
    public void search() {
        //从View获取数据
        String input = mView.getInput();
        //交给Model处理数据
        IUserService userService =new UserService();
        int dataHashcode = userService.getDataHashcode(input);

        String result = input + ":"+ dataHashcode;
        //数据处理之后，更新数据
        mView.setResult(result);
    }
}
