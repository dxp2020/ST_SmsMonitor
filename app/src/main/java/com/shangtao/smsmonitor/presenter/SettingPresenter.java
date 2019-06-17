package com.shangtao.smsmonitor.presenter;

public class SettingPresenter extends CommonPresenter<SettingPresenter.SettingView> {

    public interface SettingView{

    }

    public SettingPresenter(SettingPresenter.SettingView view) {
        attachView(view);
    }
}
