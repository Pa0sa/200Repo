package com.tilldawn.Model.Monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Model.AssetManager;

public class Eyebat {
    private Texture texture;
    private boolean canShoot;

    private Texture tentacleTexture ;
    private Sprite tentacle ;
    private float posX ;
    private float posY ;
    private int health;
    private Rectangle collisionRect ;
    private Rectangle collisionRect2 ;

    private float cooldownCounter = 0 ;
    private float cooldownPassed = 0 ;

    public Eyebat(int posX, int posY) {

        this.texture = new Texture("monsters/EyeMonster_0.png");
        this.canShoot = true;
        this.posX = posX;
        this.posY = posY;
        this.health = 50;

        tentacleTexture = new Texture(AssetManager.getAssetManager().getEyebat()); // Make sure this is loaded
        tentacle = new Sprite(tentacleTexture);

        // Set position first
        tentacle.setPosition(posX, posY);
        tentacle.setSize(tentacle.getWidth()*3, tentacle.getHeight()*3);


        // Update collision rect to match scaled dimensions
        collisionRect = new Rectangle(posX, posY, tentacle.getWidth(), tentacle.getHeight());
        collisionRect2 = new Rectangle(posX, posY, tentacle.getWidth(), tentacle.getHeight());
    }

    public void cooldown(){
        if(!canShoot) {
            cooldownCounter += Gdx.graphics.getDeltaTime();
            if (cooldownCounter >= 1) {
                cooldownPassed += 1;
                cooldownCounter = 0;
            }
            if (cooldownPassed % 5 == 0) {
                canShoot = true;
                cooldownPassed += 1;
            }
        }
    }

    public Rectangle getCollisionRect2() {
        return collisionRect2;
    }

    public void setCollisionRect2(Rectangle collisionRect2) {
        this.collisionRect2 = collisionRect2;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public boolean isCanShoot() {
        return canShoot;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int damage) {
        health -= damage;
    }

    public Texture getTentacleTexture() {
        return tentacleTexture;
    }

    public void setTentacleTexture(Texture tentacleTexture) {
        this.tentacleTexture = tentacleTexture;
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

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }

    public void setCollisionRect(Rectangle collisionRect) {
        this.collisionRect = collisionRect;
    }
}


