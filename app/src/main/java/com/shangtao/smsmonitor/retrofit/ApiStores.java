package com.shangtao.smsmonitor.retrofit;

import com.google.gson.JsonObject;
import com.shangtao.smsmonitor.model.CommonValues;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ApiStores {

  @GET(CommonValues.SERVICE_HOST +"/data/login")
  Observable<JsonObject> login(
          @QueryMap Map<String, Object> params
  );

  @GET(CommonValues.SERVICE_HOST +"/data/getSmsCode")
  Observable<JsonObject> getVerifyCode(
          @QueryMap Map<String, Object> params
  );


}
