package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
        player.getPlayerSprite().draw(Main.getBatch());
        player.cooldown();
        if(reload){
            reload();
        }
        if(player.isPlayerIdle()){
            idleAnimation();
        }

        handlePlayerInput();
    }

    public void handlePlayerInput(){
        if (Gdx.input.isKeyPressed(wInt)){
            App.getHeroOfChoice().setPosY(MathUtils.clamp(player.getPosY() - player.getSpeed(),-4375,  0));
            App.getPlayerController().getPlayer().getRect().setPosition(960 - player.getPosX(),500 - MathUtils.clamp(player.getPosY() - player.getSpeed(),-4375,  0) + 10);
        }
        if (Gdx.input.isKeyPressed(dInt)){
            App.getHeroOfChoice().setPosX(MathUtils.clamp( player.getPosX() - player.getSpeed(),-5631,  0));
            App.getPlayerController().getPlayer().getRect().setPosition(960 -MathUtils.clamp( player.getPosX() - player.getSpeed(),-5631,  0) + 10 ,500 - player.getPosY());
        }
        if (Gdx.input.isKeyPressed(sInt)){
            App.getHeroOfChoice().setPosY(MathUtils.clamp(player.getPosY() + player.getSpeed(),-4375,  0));
            App.getPlayerController().getPlayer().getRect().setPosition(960 -player.getPosX(),500 -MathUtils.clamp(player.getPosY() + player.getSpeed(),-4375,  0) - 10);
        }
        if (Gdx.input.isKeyPressed(aInt)){
            App.getHeroOfChoice().setPosX(MathUtils.clamp( player.getPosX() + player.getSpeed(),-5631,  0));
            App.getPlayerController().getPlayer().getRect().setPosition(960 - MathUtils.clamp( player.getPosX() + player.getSpeed(),-5631,  0) - 10 ,500 - player.getPosY());
            player.getPlayerSprite().flip(true, false);
        }
        if (Gdx.input.isKeyPressed(rInt)){
            reload = true;
        }
    }
    public void reload(){
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
        if(App.getHeroOfChoice().getName().equals("Diamond")){
            animation = AssetManager.getAssetManager().getCharacter2_idle_animation();
        } else {
            animation = AssetManager.getAssetManager().getCharacter1_idle_animation();
        }
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
