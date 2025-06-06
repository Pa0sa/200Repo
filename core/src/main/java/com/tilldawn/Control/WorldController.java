package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tilldawn.Main;

public class WorldController {
    private PlayerController playerController;
    private Texture backgroundTexture;
    private float backgroundX;
    private float backgroundY;
    private Texture borderTexture;
    private float borderX;
    private float borderY;
    private float borderNewX;
    private float borderNewY;

    private boolean worldBorder;
    private boolean shrinkWorldBorder;

    private float counter;
    private float passedTime;

    public WorldController(PlayerController playerController) {
        this.backgroundTexture = new Texture("background.png");
        this.borderTexture = new Texture("WorldBorder.png");
        this.playerController = playerController;
        this.worldBorder = false;
        this.shrinkWorldBorder = false;
    }

    public void update() {
        backgroundX = playerController.getPlayer().getPosX();
        backgroundY = playerController.getPlayer().getPosY();
        backgroundX = MathUtils.clamp(backgroundX,-5631,  0);
        backgroundY = MathUtils.clamp(backgroundY,-4375,  0);
        Main.getBatch().draw(backgroundTexture, backgroundX, backgroundY);


        worldBoarderGettingSmaller();
        if (worldBorder) {
            borderX = playerController.getPlayer().getPosX();
            borderY = playerController.getPlayer().getPosY();
            borderX = MathUtils.clamp(borderX,-5631,  0);
            borderY = MathUtils.clamp(borderY,-4375,  0);
            float width = borderTexture.getWidth() - borderNewX * 2;
            float height = borderTexture.getHeight() - borderNewY * 2;
            if (width < 1900 || height < 1400) {
                shrinkWorldBorder = false;
            }

            Main.getBatch().draw(borderTexture, borderX + borderNewX, borderY + borderNewY, width, height);

        }
    }

    public void worldBoarderGettingSmaller() {
        if(shrinkWorldBorder) {
            counter += Gdx.graphics.getDeltaTime();
            if (counter >= 0.1) {
                passedTime += 1;
                borderNewX += 10;
                borderNewY += 10;
                counter = 0;
            }
        }
    }

    public boolean isWorldBorder() {
        return worldBorder;
    }

    public void setWorldBorder(boolean worldBorder) {
        this.worldBorder = worldBorder;
    }


    public PlayerController getPlayerController() {
        return playerController;
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public float getBorderNewY() {
        return borderNewY;
    }

    public void setBorderNewY(float borderNewY) {
        this.borderNewY = borderNewY;
    }

    public float getBorderNewX() {
        return borderNewX;
    }

    public void setBorderNewX(float borderNewX) {
        this.borderNewX = borderNewX;
    }

    public float getBorderY() {
        return borderY;
    }

    public void setBorderY(float borderY) {
        this.borderY = borderY;
    }

    public Texture getBorderTexture() {
        return borderTexture;
    }

    public void setBorderTexture(Texture borderTexture) {
        this.borderTexture = borderTexture;
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public void setBackgroundTexture(Texture backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    public float getBackgroundX() {
        return backgroundX;
    }

    public void setBackgroundX(float backgroundX) {
        this.backgroundX = backgroundX;
    }

    public float getBackgroundY() {
        return backgroundY;
    }

    public void setBackgroundY(float backgroundY) {
        this.backgroundY = backgroundY;
    }

    public float getBorderX() {
        return borderX;
    }

    public void setBorderX(float borderX) {
        this.borderX = borderX;
    }

    public boolean isShrinkWorldBorder() {
        return shrinkWorldBorder;
    }

    public void setShrinkWorldBorder(boolean shrinkWorldBorder) {
        this.shrinkWorldBorder = shrinkWorldBorder;
    }

    public float getCounter() {
        return counter;
    }

    public void setCounter(float counter) {
        this.counter = counter;
    }

    public float getPassedTime() {
        return passedTime;
    }

    public void setPassedTime(float passedTime) {
        this.passedTime = passedTime;
    }
}
