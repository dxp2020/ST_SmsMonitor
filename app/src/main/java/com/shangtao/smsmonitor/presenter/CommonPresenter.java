package com.shangtao.smsmonitor.presenter;

import com.mula.retrofit.AppClient;
import com.mvp.presenter.MvpPresenter;
import com.shangtao.smsmonitor.retrofit.ApiStores;

public class CommonPresenter<V> extends MvpPresenter<V> {
    public ApiStores apiStores = AppClient.retrofitOther().create(ApiStores.class);

}