package com.aircraftWar.GameDataDao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author 200110501符悦泽
 * @create 2022/4/17
 */
public class GameData implements Serializable {
    private static final long serialVersionUID =-166739026174189761L;
    private String playerID = null;
    private int score;
    private Date date;

    public static GameData gameData = new GameData(null,0,null);
    public GameData(String playerID, int score, Date date) {
        this.playerID = playerID;
        this.score = score;
        this.date = date;
    }

    public GameData getUserData() {
        gameData.date = this.date;
        gameData.playerID = this.playerID;
        gameData.score = this.score;

        return gameData;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        gameData.playerID = playerID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        SimpleDateFormat date = new SimpleDateFormat("yyyy--MM--dd HH：mm");
        return ("UserID:"+playerID+", Score:"+score+", Date:"+date.format(this.date));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameData gameData = (GameData) o;
        return score == gameData.score && Objects.equals(playerID, gameData.playerID) && Objects.equals(date, gameData.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerID, score, date);
    }
}
