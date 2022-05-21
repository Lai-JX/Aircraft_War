package com.aircraftWar.Dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author 200110501符悦泽
 * @create 2022/4/17
 */
public class UserData implements Serializable {
    private static final long serialVersionUID =-166739026174189761L;
    private String playerID = null;
    private int score;
    private Date date;

    public static UserData userData = new UserData(null,0,null);
    public UserData(String playerID, int score, Date date) {
        this.playerID = playerID;
        this.score = score;
        this.date = date;
    }

    public UserData getUserData() {
        userData.date = this.date;
        userData.playerID = this.playerID;
        userData.score = this.score;

        return userData;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        userData.playerID = playerID;
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
        UserData userData = (UserData) o;
        return score == userData.score && Objects.equals(playerID, userData.playerID) && Objects.equals(date, userData.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerID, score, date);
    }
}
