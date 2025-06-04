package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.tilldawn.Main;
import com.tilldawn.Model.App;
import com.tilldawn.Model.AssetManager;
import com.tilldawn.Model.Player;

public class PlayerController {
    private Player player;

    static int wInt = 51;
    static int dInt = 32;
    static int aInt = 29;
    static int sInt = 47;
    static int rInt = 46;

    private boolean reload = false;
    private float reloadCounter = 0;

    public PlayerController(Player player){
        this.player = player;
    }

    public void update(){
        if (player.isHurt()) {
            player.setHurtOverlayTime(player.getHurtOverlayTime() + Gdx.graphics.getDeltaTime());

            Texture overlayFrame = player.getHurtAnimation()
                .getKeyFrame(player.getHurtOverlayTime(), true); // don't loop

            float x = player.getPlayerSprite().getX();
            float y = player.getPlayerSprite().getY();
            float width = player.getPlayerSprite().getWidth();
            float height = player.getPlayerSprite().getHeight();

            Main.getBatch().draw(overlayFrame, x, y, width, height);

            if (player.getHurtOverlayTime() > player.getHURT_DURATION()) {
                player.setHurt(false);
                player.setHurtOverlayTime(0f);
            }
        }
        player.getPlayerSprite().draw(Main.getBatch());
        player.cooldown();
        if (reload) {
            reload();
        }
        if (player.isPlayerIdle()) {
            idleAnimation();
        } else {
            runAnimation();
        }
        handlePlayerInput();

    }

    public void handlePlayerInput(){
        player.setPlayerIdle(true);
        if (Gdx.input.isKeyPressed(wInt)){
            App.getHeroOfChoice().setPosY(MathUtils.clamp(player.getPosY() - player.getSpeed(),-4375,  0));
            App.getPlayerController().getPlayer().getRect().setPosition(960 - player.getPosX(),500 - MathUtils.clamp(player.getPosY() - player.getSpeed(),-4375,  0) + 10);
            player.setPlayerIdle(false);
        }
        if (Gdx.input.isKeyPressed(dInt)){
            App.getHeroOfChoice().setPosX(MathUtils.clamp( player.getPosX() - player.getSpeed(),-5631,  0));
            App.getPlayerController().getPlayer().getRect().setPosition(960 -MathUtils.clamp( player.getPosX() - player.getSpeed(),-5631,  0) + 10 ,500 - player.getPosY());
            player.setPlayerIdle(false);
        }
        if (Gdx.input.isKeyPressed(sInt)){
            App.getHeroOfChoice().setPosY(MathUtils.clamp(player.getPosY() + player.getSpeed(),-4375,  0));
            App.getPlayerController().getPlayer().getRect().setPosition(960 -player.getPosX(),500 -MathUtils.clamp(player.getPosY() + player.getSpeed(),-4375,  0) - 10);
            player.setPlayerIdle(false);
        }
        if (Gdx.input.isKeyPressed(aInt)){
            App.getHeroOfChoice().setPosX(MathUtils.clamp( player.getPosX() + player.getSpeed(),-5631,  0));
            App.getPlayerController().getPlayer().getRect().setPosition(960 - MathUtils.clamp( player.getPosX() + player.getSpeed(),-5631,  0) - 10 ,500 - player.getPosY());
            player.getPlayerSprite().flip(true, false);
            player.setPlayerIdle(false);
        }
        if (Gdx.input.isKeyPressed(rInt)){
            reload = true;
        }
    }

    public static Music currentMusic;
    public static void playSFX(String path) {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal(path));
        currentMusic.setLooping(true);
        // Load saved volume
        Preferences prefs = Gdx.app.getPreferences("Settings");
        float volume = prefs.getFloat("sfxVolume", 0.5f);
        currentMusic.setVolume(volume);
        currentMusic.play();
        currentMusic.setLooping(false);
    }

    public void reload(){
        playSFX("SFX/reload.mp3");
        App.getWeaponOfChoice().setInGameSprite(AssetManager.getAssetManager().getSmgsSprite());
        reloadCounter += Gdx.graphics.getDeltaTime();
        if (reloadCounter >= App.getWeaponOfChoice().getReloadTime()) {
            App.getWeaponOfChoice().setRemainingAmmo(App.getWeaponOfChoice().getAmmo());
            App.getWeaponOfChoice().setInGameSprite(AssetManager.getAssetManager().getSmgSprite());
            reloadCounter = 0;
            reload = false;
        }
    }

    public void setReload(boolean reload) {
        this.reload = reload;
    }

    public void idleAnimation(){
        Animation<Texture> animation;
        animation = player.getIdleAnimation();
        player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));
        if (!animation.isAnimationFinished(player.getTime())) {
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        }
        else {
            player.setTime(0);
        }
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }
    public void runAnimation(){
        Animation<Texture> animation;
        animation = player.getRunAnimation();
        player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));
        if (!animation.isAnimationFinished(player.getTime())) {
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        }
        else {
            player.setTime(0);
        }
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public static int getwInt() {
        return wInt;
    }

    public static void setwInt(int wInt) {
        PlayerController.wInt = wInt;
    }

    public static int getdInt() {
        return dInt;
    }

    public static void setdInt(int dInt) {
        PlayerController.dInt = dInt;
    }

    public static int getaInt() {
        return aInt;
    }

    public static void setaInt(int aInt) {
        PlayerController.aInt = aInt;
    }

    public static int getsInt() {
        return sInt;
    }

    public static void setsInt(int sInt) {
        PlayerController.sInt = sInt;
    }

    public static int getrInt() {
        return rInt;
    }

    public static void setrInt(int rInt) {
        PlayerController.rInt = rInt;
    }
}
