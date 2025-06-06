package com.tilldawn.Model.Monster;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.tilldawn.Model.AssetManager;

public class TentacleMonster {
    private Texture tentacleTexture ;
    private Sprite tentacle ;
    private float posX ;
    private float posY ;
    private int health;
    private Rectangle collisionRect ;
    private Rectangle collisionRect2 ;
    private float time = 0;

    private boolean dying = false;
    private float deathTimer = 0f;
    private static final float DEATH_DURATION = 0.5f; // seconds for animation
    private Animation<Texture> deathAnimation;
    private TextureRegion currentFrame; // for drawing

    public TentacleMonster(int posX, int posY) {

        this.posX = posX;
        this.posY = posY;
        this.health = 25;

        tentacleTexture = new Texture(AssetManager.getAssetManager().getTentacleMonster()); // Make sure this is loaded
        deathAnimation = AssetManager.getAssetManager().getBrainMonsterDeath_frames();

        tentacle = new Sprite(tentacleTexture);

        // Set position first
        tentacle.setPosition(posX, posY);
        tentacle.setSize(tentacle.getWidth()*2, tentacle.getHeight()*2);


        // Update collision rect to match scaled dimensions
        collisionRect = new Rectangle(posX, posY, tentacle.getWidth(), tentacle.getHeight());
        collisionRect2 = new Rectangle(posX, posY, tentacle.getWidth(), tentacle.getHeight());
    }

    public Rectangle getCollisionRect2() {
        return collisionRect2;
    }

    public boolean isDying() { return dying; }
    public void setDying(boolean dying) {
        this.dying = dying;
        this.deathTimer = 0f;
    }


    public void updateDeathTimer(float delta) {
        deathTimer += delta;
    }

    public float getDeathTimer() {
        return deathTimer;
    }


    public void setDeathTimer(float deathTimer) {
        this.deathTimer = deathTimer;
    }

    public Animation<Texture> getDeathAnimation() {
        return deathAnimation;
    }

    public void setDeathAnimation(Animation<Texture> deathAnimation) {
        this.deathAnimation = deathAnimation;
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(TextureRegion currentFrame) {
        this.currentFrame = currentFrame;
    }

    public void setCollisionRect2(Rectangle collisionRect2) {
        this.collisionRect2 = collisionRect2;
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

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}

