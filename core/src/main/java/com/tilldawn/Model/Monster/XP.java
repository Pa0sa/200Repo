package com.tilldawn.Model.Monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Model.AssetManager;
import com.tilldawn.Model.CollisionRect;


public class XP {
    private Texture XPTexture ;
    private Sprite XPSprite ;
    private float posX ;
    private float posY ;
    private Rectangle collisionRect ;
    private Rectangle collisionRect2 ;
    private float time = 0;

    public XP(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;

        XPTexture = new Texture(AssetManager.getAssetManager().getXp()); // Make sure this is loaded
        XPSprite = new Sprite(XPTexture);

        // Set position first
        XPSprite.setPosition(posX, posY);

        // Scale visually (optional: setSize instead, based on image resolution)
        //treeSprite.setScale(3f); // OR: treeSprite.setSize(originalWidth * 3, originalHeight * 3);
        XPSprite.setSize(XPSprite.getWidth()*2, XPSprite.getHeight()*2);
        // Update collision rect to match scaled dimensions
//        float scaledWidth = treeSprite.getWidth() * treeSprite.getScaleX();
//        float scaledHeight = treeSprite.getHeight() * treeSprite.getScaleY();

        collisionRect = new Rectangle(posX, posY, XPSprite.getWidth(), XPSprite.getHeight());
        collisionRect2 = new Rectangle(posX, posY, XPSprite.getWidth(), XPSprite.getHeight());
    }

    public Rectangle getCollisionRect2() {
        return collisionRect2;
    }

    public void setCollisionRect2(Rectangle collisionRect2) {
        this.collisionRect2 = collisionRect2;
    }

    public void setCollisionRect(Rectangle collisionRect) {
        this.collisionRect = collisionRect;
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }

    public Sprite getTreeSprite() {
        return XPSprite;
    }

    public Texture getTreeTexture() {
        return XPTexture;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getPosX() {
        return posX;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }


}

