package com.tilldawn.Control;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tilldawn.Main;

public class WorldController {
    private PlayerController playerController;
    private Texture backgroundTexture;
    private float backgroundX;
    private float backgroundY;


    public WorldController(PlayerController playerController) {
        this.backgroundTexture = new Texture("background.png");
        this.playerController = playerController;
    }

    public void update() {
        backgroundX = playerController.getPlayer().getPosX();
        backgroundY = playerController.getPlayer().getPosY();
        backgroundX = MathUtils.clamp(backgroundX,-5631,  0);
        backgroundY = MathUtils.clamp(backgroundY,-4375,  0);
        Main.getBatch().draw(backgroundTexture, backgroundX, backgroundY);
    }


}
