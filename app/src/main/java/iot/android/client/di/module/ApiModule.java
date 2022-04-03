package iot.android.client.di.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dagger.Module;
import dagger.Provides;
import iot.android.client.api.iapi.IActuatorApi;
import iot.android.client.api.iapi.IDataApi;
import iot.android.client.api.iapi.IHouseApi;
import iot.android.client.api.message.DeviceDataSampleMessage;
import iot.android.client.api.parser.AbstractDeviceJsonParser;
import iot.android.client.api.parser.DeviceDataJsonParser;
import iot.android.client.api.parser.DeviceDataSampleParser;
import iot.android.client.model.device.AbstractDevice;
import iot.android.client.model.device.data.AbstractData;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.inject.Singleton;
import java.text.SimpleDateFormat;

@Module
public class ApiModule {

    private static final String URL = "http://192.168.0.3:8080";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(
                        JacksonConverterFactory.create(
                                new ObjectMapper().registerModule(
                                        new SimpleModule()
                                                .addDeserializer(
                                                        AbstractDevice.class,
                                                        new AbstractDeviceJsonParser()
                                                ).addDeserializer(
                                                        DeviceDataSampleMessage.class,
                                                        new DeviceDataSampleParser()
                                                )
                                ).setDateFormat(new SimpleDateFormat(DATE_FORMAT))
                        )
                ).build();
    }

    @Provides
    @Singleton
    public IHouseApi provideHouseApi(Retrofit retrofit) {
        return retrofit.create(IHouseApi.class);
    }

    @Provides
    @Singleton
    public IDataApi provideDataApi(Retrofit retrofit) {
        return retrofit.create(IDataApi.class);
    }

    @Provides
    @Singleton
    public IActuatorApi provideActuatorApi(Retrofit retrofit) {
        return retrofit.create(IActuatorApi.class);
    }

}
