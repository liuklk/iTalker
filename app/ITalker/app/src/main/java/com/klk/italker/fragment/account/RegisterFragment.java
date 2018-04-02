package com.klk.italker.fragment.account;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.klk.common.app.BasePresenterFragment;
import com.klk.factory.presenter.account.RegisterContact;
import com.klk.factory.presenter.account.RegisterPresenter;
import com.klk.italker.R;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BasePresenterFragment<RegisterContact.IPresenter> implements RegisterContact.IView {

    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.txt_go_login)
    TextView txtGoLogin;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.loading)
    Loading loading;
    Unbinder unbinder;
    private AccountTrigger accountTrigger;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected RegisterContact.IPresenter initPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        accountTrigger = (AccountTrigger) context;

    }


    @Override
    public void registerSuccess() {

    }


    @OnClick({R.id.txt_go_login, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_go_login:
                accountTrigger.triggerAccount();
                break;
            case R.id.btn_submit:
                break;
        }
    }
}
