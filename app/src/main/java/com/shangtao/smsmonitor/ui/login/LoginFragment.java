package com.shangtao.smsmonitor.ui.login;

import com.mula.base.fragment.BaseFragment;
import com.shangtao.smsmonitor.R;
import com.shangtao.smsmonitor.model.User;
import com.shangtao.smsmonitor.presenter.LoginPresenter;

public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginPresenter.LoginView {

    @Override
    public int getLayoutId() {
        return R.layout.layout_login;
    }

    @Override
    public void getVerifyCodeResult() {

    }

    @Override
    public void userLoginResult(User user) {

    }

}
