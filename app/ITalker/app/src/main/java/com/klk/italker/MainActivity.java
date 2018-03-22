package com.klk.italker;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.klk.common.app.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IView{


    @BindView(R.id.edit_input)
    EditText editInput;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.tv_test)
    TextView tvTest;

    private Presenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new Presenter(this);
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        mPresenter.search();
    }

    @Override
    public String getInput() {
        String text = editInput.getText().toString();
        return text;
    }

    @Override
    public void setResult(String result) {
        tvTest .setText(result);
    }
}
