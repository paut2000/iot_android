package iot.android.client.model.device.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DHTData extends AbstractData {

    private Double humidity;

    private Double temperature;

}
