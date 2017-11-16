package com.membattle.NewNavigation;

/**
 * Created by Севастьян on 12.11.2017.
 */

class ModeItem {
    String Title;
    int Image;
    int Time;
    int Type;
    /*Типы игры
            0 - бесконечный баттл
            1 - турнирка
    */
    public ModeItem(int image, String title, int time, int type) {
        Title = title;
        Image = image;
        Time = time;
        Type = type;
    }
}
