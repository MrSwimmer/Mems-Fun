package com.membattle.NewNavigation;

/**
 * Created by Севастьян on 12.11.2017.
 */

class ModeItem {
    String Title, Description;
    int Image;
    int Time;

    public ModeItem(int image, String title, String description, int time) {
        Title = title;
        Description = description;
        Image = image;
        Time = time;
    }
}
