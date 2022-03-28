package iot.android.client.model.device.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RGBAStripData extends AbstractData {

    private Integer red = 0;

    private Integer green = 0;

    private Integer blue = 0;

    private Integer alfa = 0;

}
