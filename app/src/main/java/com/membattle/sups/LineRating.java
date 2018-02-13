package com.membattle.sups;

/**
 * Created by Севастьян on 20.11.2017.
 */

public class LineRating {
    public String user;
    public int coins;
    int pos;

    public LineRating(String user, int coins, int pos) {
        this.user = user;
        this.coins = coins;
        this.pos = pos;
    }
}
