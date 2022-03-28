package iot.android.client.api.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit2.Response;

import java.io.IOException;

public class ErrorUtils {

    public static ApiError parseError(Response<?> response) {
        ApiError error = null;
        try {
            error = new ObjectMapper().readerFor(ApiError.class).readValue(response.errorBody().string());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return error;
    }

}
