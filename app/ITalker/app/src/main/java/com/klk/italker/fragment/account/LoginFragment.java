package com.klk.italker.fragment.account;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.klk.common.app.BasePresenterFragment;
import com.klk.factory.presenter.account.LoginContact;
import com.klk.factory.presenter.account.LoginPresenter;
import com.klk.italker.R;
import com.klk.italker.activity.MainActivity;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 登录的fragment
 */
public class LoginFragment extends BasePresenterFragment<LoginContact.IPresenter>
        implements LoginContact.IView {

    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.txt_go_register)
    TextView txtGoRegister;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.loading)
    Loading loading;
    private AccountTrigger accountTrigger;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }


    @Override
    protected LoginContact.IPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        accountTrigger = (AccountTrigger) context;
    }


    @Override
    public void showError(@StringRes int id) {
        super.showError(id);
        //进度条停止
        loading.stop();
        editPassword.setEnabled(true);
        editPhone.setEnabled(true);
        btnSubmit.setEnabled(true);


    }

    @Override
    public void showLoading() {
        super.showLoading();
        loading.start();

        editPassword.setEnabled(false);
        editPhone.setEnabled(false);
        btnSubmit.setEnabled(false);
    }
    @OnClick({R.id.txt_go_register, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_go_register:
                accountTrigger.triggerAccount();
                break;
            case R.id.btn_submit:
                showLoading();
                LoginPresenter presenter = (LoginPresenter) getPresenter();
                String phone = editPhone.getText().toString();
                String password = editPassword.getText().toString();

                presenter.login(phone,password);
                break;
        }
    }


    @Override
    public void loginSuccess() {
        //进入主界面
        MainActivity.show(getContext());
        //关闭账户界面
        getActivity().finish();
    }
}
