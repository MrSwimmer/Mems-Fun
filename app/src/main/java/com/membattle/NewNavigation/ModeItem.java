package com.membattle.NewNavigation;

/**
 * Created by Севастьян on 12.11.2017.
 */

class ModeItem {
    String Title;
    int Image, Time, Type, Color;
    /*Типы игры
            0 - бесконечный баттл
            1 - турнирка
    */
    public ModeItem(int image, String title, int time, int type, int color) {
        Title = title;
        Image = image;
        Time = time;
        Type = type;
        Color = color;
    }
}
