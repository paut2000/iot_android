package iot.android.client.model.device.data;

import androidx.annotation.NonNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DHTData extends AbstractData {

    private Double humidity;

    private Double temperature;

    @NonNull
    @NotNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        DHTData cloned = (DHTData) super.clone();
        cloned.setHumidity(humidity);
        cloned.setTemperature(temperature);
        return cloned;
    }

}
