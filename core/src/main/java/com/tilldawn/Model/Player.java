package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;
import java.util.ArrayList;

public class Player {
    private Texture playerTexture = new Texture(AssetManager.getAssetManager().getCharacter1_idle0());
    private Sprite playerSprite = new Sprite(playerTexture);

    private float posX = 0;
    private float posY = 0;

    private Rectangle rect ;

    private float time = 0;
    private float speed;
    private boolean canBeAttacked = false;
    private float cooldownCounter = 0;

    private String name;
    private Texture avatar;
    private Texture icon;

    private String description;
    private int health;
    private int damage = 0;

    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = false;

    private ArrayList<Ability> abilities = new ArrayList<Ability>();


    public Player(String name, Texture avatar, String description, int health, float speed , Texture icon){
        this.name = name;
        this.avatar = avatar;
        this.description = description;
        this.health = health;
        this.speed = speed;
        this.icon = icon;
        playerSprite.setPosition( (float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2 );
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);

        rect = new Rectangle((float)Gdx.graphics.getWidth()/2,(float) Gdx.graphics.getHeight()/2
            , (int)playerSprite.getWidth(), (int) playerSprite.getHeight());

    }
    public void cooldown(){
        if(!canBeAttacked) {
            cooldownCounter += Gdx.graphics.getDeltaTime();
            if (cooldownCounter >= 1) {
                cooldownCounter = 0;
                canBeAttacked = true;
            }
        }
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public void setPlayerTexture(Texture playerTexture) {
        this.playerTexture = playerTexture;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public void setPlayerSprite(Sprite playerSprite) {
        this.playerSprite = playerSprite;
    }

    public float getPosX() {
        return posX;
    }

    public Texture getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public float getSpeed() {
        return speed;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCanBeAttacked() {
        return canBeAttacked;
    }

    public com.badlogic.gdx.math.Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }


    public boolean isPlayerIdle() {
        return isPlayerIdle;
    }

    public void setPlayerIdle(boolean playerIdle) {
        isPlayerIdle = playerIdle;
    }

    public boolean isPlayerRunning() {
        return isPlayerRunning;
    }

    public void setPlayerRunning(boolean playerRunning) {
        isPlayerRunning = playerRunning;
    }

    public float getTime() {
        return time;
    }

    public Texture getAvatar() {
        return avatar;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage += damage;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<Ability> abilities) {
        this.abilities = abilities;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(Texture icon) {
        this.icon = icon;
    }

    public void setAvatar(Texture avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCanBeAttacked(boolean canBeAttacked) {
        this.canBeAttacked = canBeAttacked;
    }
}
