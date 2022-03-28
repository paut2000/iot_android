package iot.android.client.callback;

import retrofit2.Call;
import retrofit2.Callback;

public abstract class AbstractCallbackDefaultOnFailure<T> implements Callback<T> {
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        System.out.println(t.getMessage());
    }
}
