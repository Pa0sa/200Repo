package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Weapon {
    public String name;
    public Texture icon;

    public int damage;
    public int reloadTime;
    private int ammo;
    private int remainingAmmo;
    private Texture inGameTexture;
    private Texture texture ;
    private Sprite inGameSprite;
    private Sprite sprite ;


    public Weapon(String name, Texture icon, Texture inGameTexture, int damage, int reloadTime, int ammo) {
        this.name = name;
        this.icon = icon;
        this.damage = damage;
        this.reloadTime = reloadTime;
        this.ammo = ammo;
        this.remainingAmmo = ammo;
        this.texture = icon;
        this.inGameTexture = inGameTexture;
        inGameSprite = new Sprite(inGameTexture);



        sprite = new Sprite(texture);
        sprite.setX((float) Gdx.graphics.getWidth() / 2 );
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        sprite.setSize(50,50);
    }

    public String getName() {
        return name;
    }

    public Sprite getSmgSprite() {
        inGameSprite.setX((float) Gdx.graphics.getWidth() / 2 );
        inGameSprite.setY((float) Gdx.graphics.getHeight() / 2);
        inGameSprite.setSize(50,50);
        return inGameSprite;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo){
        this.ammo = ammo;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public int getRemainingAmmo() {
        return remainingAmmo;
    }

    public void setRemainingAmmo(int remainingAmmo) {
        this.remainingAmmo = remainingAmmo;
    }

    public Texture getIcon() {
        return icon;
    }

    public void setIcon(Texture icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Texture getInGameTexture() {
        return inGameTexture;
    }

    public void setInGameTexture(Texture inGameTexture) {
        this.inGameTexture = inGameTexture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getInGameSprite() {
        return inGameSprite;
    }

    public void setInGameSprite(Sprite inGameSprite) {
        this.inGameSprite = inGameSprite;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
