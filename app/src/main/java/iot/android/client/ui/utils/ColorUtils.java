package iot.android.client.ui.utils;

import android.graphics.Color;

import java.util.HashMap;

public class ColorUtils {

    static private HashMap<String, Integer> colors = new HashMap<>();

    static {
        colors.put("Выключено",                       Color.rgb(0,0,0));
        colors.put("Белый",                           Color.rgb(255,255,255));

        colors.put("Красный",                         Color.rgb(255,0,0));
        colors.put("Алый",                            Color.rgb(224,32,0));
        colors.put("Красное дерево",                  Color.rgb(192,64,0));
        colors.put("Коричневый",                      Color.rgb(160,96,0));
        colors.put("Оливковый",                       Color.rgb(128,128,0));
        colors.put("Яркий желто-зеленый",             Color.rgb(96,160,0));
        colors.put("Ирландский зеленый",              Color.rgb(64,192,0));
        colors.put("Ярко-зеленый",                    Color.rgb(32,224,0));
        colors.put("Лайм",                            Color.rgb(0,255,0));
        //colors.put("",                                Color.rgb(0,224,32));
        colors.put("Темный пастельно-зеленый",        Color.rgb(0,192,64));
        colors.put("Зеленый трилистник",              Color.rgb(0,160,96));
        colors.put("Сине-зелёный",                    Color.rgb(0,128,128));
        colors.put("Средний персидский синий",        Color.rgb(0,96,160));
        colors.put("Кобальтовый",                     Color.rgb(0,64,192));
        //colors.put("",                                Color.rgb(0,32,224));
        colors.put("Синий",                           Color.rgb(0,0,255));
        //colors.put("",                                Color.rgb(32,0,224));
        colors.put("Темный пурпурно-фиолетовый",      Color.rgb(64,0,192));
        //colors.put("Темный пурпурно-фиолетовый",      Color.rgb(96,0,160));
        colors.put("Пурпурный",                       Color.rgb(128,0,128));
        colors.put("Баклажановый",                    Color.rgb(160,0,96));
        colors.put("Яркий красный",                   Color.rgb(192,0,64));
        colors.put("Карминово-красный",               Color.rgb(224,0,32));
    }

    static public String getBestColorName(Color c) {
        float dist, diff = Float.MAX_VALUE;
        String colorName = "";
        Color extreme;

        for (String name : colors.keySet()) {
            extreme = Color.valueOf(colors.get(name));
            dist = Math.abs(c.red() - extreme.red())
                    + Math.abs(c.green() - extreme.green())
                    + Math.abs(c.blue() - extreme.blue());

            if (dist < diff) {
                diff = dist;
                colorName = name;
            }
        }

        return colorName;
    }

}
