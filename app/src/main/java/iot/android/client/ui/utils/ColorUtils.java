package iot.android.client.ui.utils;

import android.graphics.Color;

import java.util.HashMap;

public class ColorUtils {

    static private HashMap<String, Integer> colors = new HashMap<>();

    static {
        colors.put("Тёмно-бордовый", Color.rgb(128,0,0));
        colors.put("Тёмно-красный", Color.rgb(139,0,0));
        colors.put("Огнеупорный кирпич", Color.rgb(178,34,34));
        colors.put("Красный", Color.rgb(255,0,0));
        colors.put("Лососевый", Color.rgb(250,128,114));
        colors.put("Томатный", Color.rgb(255,99,71));
        colors.put("Коралловый", Color.rgb(255,127,80));
        colors.put("Оранжево-красный", Color.rgb(255,69,0));
        colors.put("Шоколадный", Color.rgb(210,105,30));
        colors.put("Песочно-коричневый", Color.rgb(244,164,96));
        colors.put("Тёмно-оранжевый", Color.rgb(255,140,0));
        colors.put("Оранжевый", Color.rgb(255,165,0));
        colors.put("Тёмный золотарник", Color.rgb(184,134,11));
        colors.put("Золотарниковый", Color.rgb(218,165,32));
        colors.put("Золотой", Color.rgb(255,215,0));
        colors.put("Оливковый", Color.rgb(128,128,0));
        colors.put("Жёлтый", Color.rgb(255,255,0));
        colors.put("Жёлто-зелёный", Color.rgb(154,205,50));
        colors.put("Зелёно-жёлтый", Color.rgb(173,255,47));
        colors.put("Шартрёз (ликёр)", Color.rgb(127,255,0));
        colors.put("Зелёный газон", Color.rgb(124,252,0));
        colors.put("Зелёный", Color.rgb(0,128,0));
        colors.put("Лаймовый", Color.rgb(0,255,0));
        colors.put("Зелёный лайм", Color.rgb(50,205,50));
        colors.put("Весенне зелёный", Color.rgb(0,255,127));
        colors.put("Весенне-зелёный нейтральный", Color.rgb(0,250,154));
        colors.put("Бирюзовый", Color.rgb(64,224,208));
        colors.put("Светло-зелёное море", Color.rgb(32,178,70));
        colors.put("Бирюзовый нейтральный", Color.rgb(72,209,204));
        colors.put("Тёмный циан", Color.rgb(0,139,139));
        colors.put("Морская волна", Color.rgb(0,255,255));
        colors.put("Тёмно-бирюзовый", Color.rgb(0,206,209));
        colors.put("Небесно-голубой тёмный", Color.rgb(0,191,255));
        colors.put("Обманчивый синий", Color.rgb(30,144,255));
        colors.put("Королевский синий", Color.rgb(65,105,225));
        colors.put("Военно-морского флота", Color.rgb(0,0,128));
        colors.put("Тёмно-синий", Color.rgb(0,0,139));
        colors.put("Cиний нейтральный", Color.rgb(0,0,205));
        colors.put("Синий", Color.rgb(0,0,255));
        colors.put("Сине-фиолетовый", Color.rgb(138,43,226));
        colors.put("Тёмная орхидея", Color.rgb(153,50,204));
        colors.put("Тёмно-фиолетовый", Color.rgb(148,0,211));
        colors.put("Фиолетовый", Color.rgb(128,0,128));
        colors.put("Тёмный маджента", Color.rgb(139,0,139));
        colors.put("Маджента", Color.rgb(255,0,255));
        colors.put("Фиолетово-красный нейтральный", Color.rgb(199,21,133));
        colors.put("Насыщенный розовый", Color.rgb(255,20,147));
        colors.put("Ярко-розовый", Color.rgb(255,105,80));
        colors.put("Малиновый", Color.rgb(220,20,60));
        colors.put("Коричневый", Color.rgb(165,42,42));
        colors.put("Красный индийский", Color.rgb(205,92,92));
        colors.put("Розово-коричневый", Color.rgb(188,143,143));
        colors.put("Светло-коралловый", Color.rgb(240,128,128));
        colors.put("Снег", Color.rgb(255,250,250));
        colors.put("Туманная роза", Color.rgb(255,228,225));
        colors.put("Тёмный лосось", Color.rgb(233,150,122));
        colors.put("Светлый лосось", Color.rgb(255,160,122));
        colors.put("Охра", Color.rgb(160,82,45));
        colors.put("Морская ракушка", Color.rgb(255,245,238));
        colors.put("Седло Браун", Color.rgb(139,69,19));
        colors.put("Персиковая пудра", Color.rgb(255,218,185));
        colors.put("Перу", Color.rgb(205,133,63));
        colors.put("Текстильный", Color.rgb(250,240,230));
        colors.put("Бисквит", Color.rgb(255,228,196));
        colors.put("Плотное дерево", Color.rgb(222,184,135));
        colors.put("Загар", Color.rgb(210,180,140));
        colors.put("Античный белый", Color.rgb(255,235,215));
        colors.put("Белый навахо", Color.rgb(255,222,173));
        colors.put("Бланшированный миндаль", Color.rgb(255,235,205));
        colors.put("Побег папайи", Color.rgb(255,239,213));
        colors.put("Мокасиновый", Color.rgb(255,228,181));
        colors.put("Пшеничный", Color.rgb(245,222,179));
        colors.put("Старое кружево", Color.rgb(253,245,230));
        colors.put("Цветочный белый", Color.rgb(255,250,240));
        colors.put("Кукурузный волос", Color.rgb(255,248,220));
        colors.put("Хаки", Color.rgb(240,230,140));
        colors.put("Лимонный шифон", Color.rgb(255,250,205));
        colors.put("Бледный золотарник", Color.rgb(238,232,170));
        colors.put("Тёмный хаки", Color.rgb(189,183,107));
        colors.put("Бежевый", Color.rgb(245,245,220));
        colors.put("Светло-жёлтый золотарник", Color.rgb(250,250,210));
        colors.put("Светло-жёлтый", Color.rgb(255,255,224));
        colors.put("Слоновая кость", Color.rgb(255,255,240));
        colors.put("Тёмно-оливковый", Color.rgb(107,142,35));
        colors.put("Тёмно-оливковый зелёный", Color.rgb(85,107,47));
        colors.put("Тёмно-зелёное море", Color.rgb(143,188,143));
        colors.put("Тёмно-зелёный", Color.rgb(0,100,0));
        colors.put("Зелёный лесной", Color.rgb(34,139,34));
        colors.put("Светло-зелёный", Color.rgb(144,238,144));
        colors.put("Бледно-зелёный", Color.rgb(152,251,152));
        colors.put("Медвяная роса", Color.rgb(240,255,240));
        colors.put("Зелёное море", Color.rgb(46,139,87));
        colors.put("Зелёное море, нейтральный", Color.rgb(60,179,113));
        colors.put("Мятный крем", Color.rgb(245,255,250));
        colors.put("Аквамариновый нейтральный", Color.rgb(102,205,170));
        colors.put("Аквамариновый", Color.rgb(127,255,212));
        colors.put("Тёмный грифельно-серый", Color.rgb(47,79,79));
        colors.put("Бледно-бирюзовый", Color.rgb(179,238,238));
        colors.put("Светло-голубой", Color.rgb(224,255,255));
        colors.put("Лазурный", Color.rgb(245,255,255));
        colors.put("Серо-синий", Color.rgb(95,158,160));
        colors.put("Пыльный голубой", Color.rgb(176,224,230));
        colors.put("Светло-синий", Color.rgb(173,216,230));
        colors.put("Небесно-голубой", Color.rgb(135,206,235));
        colors.put("Небесно-голубой светлый", Color.rgb(135,206,250));
        colors.put("Синяя сталь", Color.rgb(70,130,180));
        colors.put("Синяя Элис", Color.rgb(240,248,255));
        colors.put("Серый шифер", Color.rgb(112,128,144));
        colors.put("Светло-серый шифер", Color.rgb(119,136,153));
        colors.put("Светло-стальной синий", Color.rgb(176,196,222));
        colors.put("Васильковый голубой", Color.rgb(100,149,237));
        colors.put("Лаванда", Color.rgb(230,230,250));
        colors.put("Призрачно-белый", Color.rgb(248,248,255));
        colors.put("Полуночный синий", Color.rgb(25,25,112));
        colors.put("Аспидно-синий", Color.rgb(106,90,205));
        colors.put("Тёмный аспидно-синий", Color.rgb(72,61,139));
        colors.put("Нейтральный аспидно-синий", Color.rgb(123,104,238));
        colors.put("Фиолетовый нейтральный", Color.rgb(147,112,219));
        colors.put("Индиго", Color.rgb(75,0,130));
        colors.put("Нейтральный цвет орхидеи", Color.rgb(186,85,211));
        colors.put("Слива светлая", Color.rgb(221,160,221));
        colors.put("Розово-фиолетовый", Color.rgb(238,130,238));
        colors.put("Чертополох", Color.rgb(216,191,216));
        colors.put("Орхидея", Color.rgb(218,112,214));
        colors.put("Розово-лавандовый", Color.rgb(255,240,245));
        colors.put("Лиловый", Color.rgb(219,112,147));
        colors.put("Розовый", Color.rgb(255,192,203));
        colors.put("Светло-розовый", Color.rgb(255,182,193));
        colors.put("Чёрный", Color.rgb(0,0,0));
        colors.put("Тускло-серый", Color.rgb(105,105,105));
        colors.put("Серый", Color.rgb(128,128,128));
        colors.put("Тёмно-серый", Color.rgb(169,169,169));
        colors.put("Серебряный", Color.rgb(192,192,192));
        colors.put("Светло-серый", Color.rgb(211,211,211));
        colors.put("Геинсборо", Color.rgb(220,220,220));
        colors.put("Белый дым", Color.rgb(245,245,245));
        colors.put("Белый", Color.rgb(255,255,255));
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
