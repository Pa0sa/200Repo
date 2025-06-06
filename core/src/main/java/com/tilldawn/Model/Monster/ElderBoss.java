package com.tilldawn.Model.Monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.tilldawn.Model.AssetManager;

public class ElderBoss {
    private Texture texture;
    private boolean canDash;
    private boolean isDashing;
    private boolean dash;

    private Texture elderTexture;
    private Sprite tentacle ;
    private float posX ;
    private float posY ;
    private int health;
    private Rectangle collisionRect ;
    private Rectangle collisionRect2 ;
    private float time = 0;
    private float cooldownCounter = 0 ;
    private float cooldownPassed = 0 ;

    private float dashCooldownCounter = 0 ;
    private float DashCooldownPassed = 0 ;

    private float doingDashCooldownCounter = 0 ;
    private float doingDashCooldownPassed = 0 ;

    public ElderBoss(int posX, int posY) {

        this.texture = new Texture("monsters/T_HasturBoss_0.png");
        this.canDash = true;
        this.isDashing = false;
        this.dash = false;
        this.posX = posX;
        this.posY = posY;
        this.health = 400;

        elderTexture = new Texture(AssetManager.getAssetManager().getElder()); // Make sure this is loaded
        tentacle = new Sprite(elderTexture);

        // Set position first
        tentacle.setPosition(posX, posY);
        tentacle.setSize(tentacle.getWidth()*2, tentacle.getHeight()*2);


        // Update collision rect to match scaled dimensions
        collisionRect = new Rectangle(posX, posY, tentacle.getWidth(), tentacle.getHeight());
        collisionRect2 = new Rectangle(posX, posY, tentacle.getWidth(), tentacle.getHeight());
    }

    public void cooldown(){
        if(!canDash) {
            cooldownCounter += Gdx.graphics.getDeltaTime();
            if (cooldownCounter >= 1) {
                cooldownPassed += 1;
                cooldownCounter = 0;
            }
            if (cooldownPassed % 6 == 0) {
                canDash = true;
                cooldownPassed += 1;
            }
        }
    }
    public void dashDuration(){
        if(isDashing) {
            dashCooldownCounter += Gdx.graphics.getDeltaTime();
            if (dashCooldownCounter >= 1) {
                isDashing = false;
                dash = true;
                dashCooldownCounter = 0;
            }

        }
    }
    public void dash(){
        if(dash) {
            doingDashCooldownCounter += Gdx.graphics.getDeltaTime();
            if (doingDashCooldownCounter >= 0.5) {
                dash = false;
                doingDashCooldownCounter = 0;
            }
        }
    }

    public Rectangle getCollisionRect2() {
        return collisionRect2;
    }

    public void setCollisionRect2(Rectangle collisionRect2) {
        this.collisionRect2 = collisionRect2;
    }

    public void setCanDash(boolean canDash) {
        this.canDash = canDash;
    }

    public boolean isDash() {
        return dash;
    }

    public void setDash(boolean dash) {
        this.dash = dash;
    }

    public boolean isCanDash() {
        return canDash;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int damage) {
        health -= damage;
    }

    public Texture getElderTexture() {
        return elderTexture;
    }

    public void setElderTexture(Texture elderTexture) {
        this.elderTexture = elderTexture;
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

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public boolean isDashing() {
        return isDashing;
    }

    public void setDashing(boolean dashing) {
        isDashing = dashing;
    }
}
