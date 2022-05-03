package iot.android.client.model.device.data;

import androidx.annotation.NonNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractData implements Cloneable {

    private Date datetime;

    @NonNull
    @NotNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
