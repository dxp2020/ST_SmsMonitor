package com.shangtao.smsmonitor.retrofit;

import com.google.gson.JsonObject;
import com.shangtao.smsmonitor.model.CommonValues;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ApiStores {

  // 获取版本更新信息
  @GET(CommonValues.SERVICE_HOST +"/api/mid/smsContent/save")
  Observable<JsonObject> uploadSms(
          @QueryMap Map<String, String> params
  );

}
