package iot.android.client.ui.utils;

import iot.android.client.model.device.data.RGBAStripData;

public class ColorConverter {

    public static int RGBAtoInt(RGBAStripData data) {
        return (data.getAlfa() & 0xff) << 24 |
                (data.getRed() & 0xff) << 16 |
                (data.getGreen() & 0xff) << 8 |
                (data.getBlue() & 0xff);
    }

    public static RGBAStripData IntToRGBA(int color) {
        RGBAStripData data = new RGBAStripData();
        data.setAlfa((color >> 24) & 0xff);
        data.setRed((color >> 16) & 0xff);
        data.setGreen((color >> 8) & 0xff);
        data.setBlue((color) & 0xff);
        return data;
    }

}
