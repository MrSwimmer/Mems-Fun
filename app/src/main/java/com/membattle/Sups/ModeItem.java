package com.membattle.Sups;

/**
 * Created by Севастьян on 12.11.2017.
 */

public class ModeItem {
    public String Title;
    public int Image;
    public int Time;
    int Type;
    public int Color;
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
