package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.tilldawn.Control.GameController;
import com.tilldawn.Control.PlayerController;
import com.tilldawn.Control.WeaponController;
import com.tilldawn.Control.WorldController;

import java.util.ArrayList;
import java.util.Collection;

public class App {

    private static ArrayList<User> users = new ArrayList<>();

    private static User currentUser = null;

    private static Weapon weaponOfChoice = null;

    private static Player heroOfChoice = null;

    private static String timeOfChoice;
    private static int timePassed = 0;

    private static final Json json = new Json();

    private Game currentGame;

    private static boolean autoReload = true;

    private static GameController controller;
    private static WorldController worldController;
    private static PlayerController playerController;
    private static WeaponController weaponController;

    public static ArrayList<User> getUsers() {
        return users;
    }

    private static boolean playSfx = true;
    private static float sfxVolume = 0.5f;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        App.currentUser = currentUser;
    }



    // Save to local file
    public static void saveUsers() {
        FileHandle file = Gdx.files.local("users.json");
        file.writeString(json.toJson(users), false); // false = overwrite
    }


    public static void loadUsers() {
        try {
            FileHandle file = Gdx.files.local("users.json");

            if (!file.exists() || file.length() == 0) {
                users = new ArrayList<>();
                return;
            }

            String jsonText = file.readString();
            Array<User> userArray = (Array<User>) json.fromJson(Array.class, User.class, jsonText);

            users = new ArrayList<User>();
            for (User u : userArray) {
                users.add(u);
            }

            // Load textures after deserialization
            for (User user : users) {
                user.loadAvatar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentUser = users.getFirst();
    }

    public Game getCurrentGame() {
        return currentGame;
    }
    public void setCurrentGame(Game game) {
        currentGame = game;
    }

    public static int getTimeOfChoice() {
        if(timeOfChoice == "   2 Minutes"){
            return 2*60;
        } else if(timeOfChoice == "   5 Minutes"){
            return 5*60;
        } else if(timeOfChoice == "   10 Minutes"){
            return 10*60;
        } else if(timeOfChoice == "   20 Minutes"){
            return 20*60;
        }
        return 20*60;
    }


    public static int getTimePassed() {
        return timePassed;
    }

    public static void setTimePassed(int timePassed) {
        App.timePassed = timePassed;
    }

    public static void setTimeOfChoice(String timeOfChoice) {
        App.timeOfChoice = timeOfChoice;
    }

    public static Player getHeroOfChoice() {
        return heroOfChoice;
    }

    public static void setHeroOfChoice(Player heroOfChoice) {
        App.heroOfChoice = heroOfChoice;
    }

    public static Weapon getWeaponOfChoice() {
        return weaponOfChoice;
    }

    public static void setWeaponOfChoice(Weapon weaponOfChoice) {
        App.weaponOfChoice = weaponOfChoice;
    }

    public static void setUsers(ArrayList<User> users) {
        App.users = users;
    }


    public static GameController getController() {
        return controller;
    }

    public static void setController(GameController controller) {
        App.controller = controller;
    }

    public static WorldController getWorldController() {
        return worldController;
    }

    public static void setWorldController(WorldController worldController) {
        App.worldController = worldController;
    }

    public static PlayerController getPlayerController() {
        return playerController;
    }

    public static void setPlayerController(PlayerController playerController) {
        App.playerController = playerController;
    }

    public static boolean isPlaySfx() {
        return playSfx;
    }

    public static void setPlaySfx(boolean playSfx) {
        App.playSfx = playSfx;
    }

    public static float getSfxVolume() {
        return sfxVolume;
    }

    public static void setSfxVolume(float sfxVolume) {
        App.sfxVolume = sfxVolume;
    }

    public static WeaponController getWeaponController() {
        return weaponController;
    }

    public static void setWeaponController(WeaponController weaponController) {
        App.weaponController = weaponController;
    }

    public static boolean isAutoReload() {
        return autoReload;
    }

    public static void setAutoReload(boolean autosReload) {
        autoReload = autosReload;
    }
}
