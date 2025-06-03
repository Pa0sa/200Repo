package com.tilldawn.Model.Monster;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Model.AssetManager;

public class MonsterBullet {
    private Texture tentacleTexture ;
    private Sprite tentacle ;
    private float posX ;
    private float posY ;
    private float velX ;
    private float velY ;

    private Rectangle collisionRect ;
    private Rectangle collisionRect2 ;

    public MonsterBullet(float posX, float posY , float velX, float velY) {

        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;

        tentacleTexture = new Texture(AssetManager.getAssetManager().getMonsterBullet()); // Make sure this is loaded
        tentacle = new Sprite(tentacleTexture);

        // Set position first
        tentacle.setPosition(posX, posY);
        tentacle.setSize(tentacle.getWidth()*3, tentacle.getHeight()*3);


        // Update collision rect to match scaled dimensions
        collisionRect = new Rectangle(posX, posY, tentacle.getWidth(), tentacle.getHeight());
        collisionRect2 = new Rectangle(posX, posY, tentacle.getWidth(), tentacle.getHeight());
    }

    public Rectangle getCollisionRect2() {
        return collisionRect2;
    }

    public void setCollisionRect2(Rectangle collisionRect2) {
        this.collisionRect2 = collisionRect2;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public Texture getTentacleTexture() {
        return tentacleTexture;
    }

    public void setTentacleTexture(Texture tentacleTexture) {
        this.tentacleTexture = tentacleTexture;
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }

    public void setCollisionRect(Rectangle collisionRect) {
        this.collisionRect = collisionRect;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public Sprite getTentacle() {
        return tentacle;
    }

    public void setTentacle(Sprite tentacle) {
        this.tentacle = tentacle;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }
}
