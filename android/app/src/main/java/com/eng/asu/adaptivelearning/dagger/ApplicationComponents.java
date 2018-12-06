package com.eng.asu.adaptivelearning.dagger;


import javax.inject.Singleton;

import dagger.Component;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponents {
}
