package iot.android.client.model.device.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractData {

    private Date datetime;

}
