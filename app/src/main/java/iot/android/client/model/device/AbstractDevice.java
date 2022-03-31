package iot.android.client.model.device;

import androidx.annotation.NonNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import iot.android.client.model.device.data.AbstractData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractDevice implements Cloneable {

    protected String serialNumber;

    protected String type;

    protected boolean alive;

    protected AbstractData data;

    @JsonIgnore
    protected String name = "Default name";

    @NonNull
    @NotNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
