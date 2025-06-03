package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.awt.*;
import java.sql.Time;

public class User {

    private String userName;
    private String password;
    private String avatarPath;  // store file path instead of Texture
    private transient Texture avatar;  // transient: ignore during serialization
    private String selectedSecurityQuestion;
    private String securityQuestionAnswer;
    private int highScore;
    private int kills;
    private int timeSurvived;

    public User() {}

    public User(String userName, String password, String avatarPath,
                String selectedSecurityQuestion, String securityQuestionAnswer , int highScore , int kills, int timeSurvived) {
        this.userName = userName;
        this.password = password;
        this.avatarPath = avatarPath;
        this.selectedSecurityQuestion = selectedSecurityQuestion;
        this.securityQuestionAnswer = securityQuestionAnswer;
        this.highScore = highScore;
        this.kills = kills;
        this.timeSurvived = timeSurvived;
        loadAvatar();
    }

    // Load Texture from avatarPath
    public void loadAvatar() {
        if (avatarPath != null && !avatarPath.isEmpty()) {
            avatar = new Texture(Gdx.files.internal(avatarPath));

        }
    }

    public void setKills(int kills) {
        this.kills += kills;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getTimeSurvived() {
        return timeSurvived;
    }

    public void setTimeSurvived(int timeSurvived) {
        this.timeSurvived = timeSurvived;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }

    public String getSelectedSecurityQuestion() {
        return selectedSecurityQuestion;
    }

    public Texture getAvatar() {
        return avatar;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
        if (avatarPath != null && !avatarPath.isEmpty()) {
            avatar = new Texture(Gdx.files.internal(avatarPath));

        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getKills() {
        return kills;
    }

    public int getSurvivalTime() {
        return timeSurvived;
    }

    @Override
    public String toString() {
        return String.format("%-15s %-8d %-5d %-8d",
            userName , highScore, kills, timeSurvived);
    }
}
