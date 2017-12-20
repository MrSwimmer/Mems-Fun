package com.membattle.Sups;

/**
 * Created by Севастьян on 22.11.2017.
 */
public class RequestToGame {
    public int user_id, game_id, mem_id;
    public RequestToGame(int user_id, int game_id, int mem_id) {
        this.user_id = user_id;
        this.game_id = game_id;
        this.mem_id = mem_id;
    }
}