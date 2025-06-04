package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Main;
import com.tilldawn.Model.Ability;
import com.tilldawn.Model.App;
import com.tilldawn.Model.Bullet;
import com.tilldawn.Model.Weapon;

import java.util.ArrayList;

import static com.tilldawn.Control.PlayerController.*;
import static com.tilldawn.Model.App.getPlayerController;

public class WeaponController {
    private Weapon weapon;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public WeaponController(Weapon weapon){
        this.weapon = weapon;
    }

    public void update(){
        weapon.getSmgSprite().draw(Main.getBatch());
        updateBullets();
    }

    public void handleWeaponRotation(int x, int y) {
        Sprite weaponSprite = weapon.getSmgSprite();

        float weaponCenterX = (float) Gdx.graphics.getWidth()/2;
        float weaponCenterY = (float) Gdx.graphics.getHeight()/2;

        float angle = (float) Math.atan2(y - weaponCenterY, x - weaponCenterX);

        weaponSprite.setRotation((float) (3.14 - angle * MathUtils.radiansToDegrees));
    }

    public void handleWeaponShoot(int x, int y){
        if(App.getWeaponOfChoice().getName().equals("Shotgun")){
            bullets.add(new Bullet(x + 40 , y + 40));
            bullets.add(new Bullet(x - 40, y - 40));
            bullets.add(new Bullet(x + 20, y + 20));
            bullets.add(new Bullet(x - 20, y - 20));
        } else {
            bullets.add(new Bullet(x, y));
            int k = 0;
            int u = 0;
            for(Ability ability : App.getPlayerController().getPlayer().getAbilities()){
                if(ability.getName().equals("ProCrease")){
                    k += 20;
                    u += 1;
                    if(u%2==0){
                        bullets.add(new Bullet(x + k , y + k));
                    }
                    if(u%2==1){
                        bullets.add(new Bullet(x - k , y + k));
                    }
                }
            }
        }
            //weapon.setRemainingAmmo(weapon.getAmmo() - 1);
    }

    public void updateBullets() {
        for(int i = bullets.size() - 1; i >= 0; i--){
            Bullet b = bullets.get(i);
            b.getSprite().draw(Main.getBatch());
            Vector2 direction = new Vector2(
                    Gdx.graphics.getWidth()/2f - b.getX(),
                    Gdx.graphics.getHeight()/2f - b.getY()
            ).nor();

            if (Gdx.input.isKeyPressed(wInt)){
                b.getSprite().setY(b.getSprite().getY() - (getPlayerController().getPlayer().getSpeed())/4);
            }
            if (Gdx.input.isKeyPressed(dInt)){
                b.getSprite().setX(b.getSprite().getX() - (getPlayerController().getPlayer().getSpeed())/4);
            }
            if (Gdx.input.isKeyPressed(sInt)){
                b.getSprite().setY(b.getSprite().getY() + (getPlayerController().getPlayer().getSpeed())/4);
            }
            if (Gdx.input.isKeyPressed(aInt)){
                b.getSprite().setX(b.getSprite().getX() + (getPlayerController().getPlayer().getSpeed())/4);
            }
            b.getSprite().setX(b.getSprite().getX() - direction.x * 5);
            b.getSprite().setY(b.getSprite().getY() + direction.y * 5);
            b.getRect().set(b.getSprite().getX() - direction.x * 5 ,b.getSprite().getY() + direction.y * 5,
                            b.getSprite().getWidth(), b.getSprite().getHeight());
            if(b.getSprite().getX() > 3000 || b.getSprite().getX() < -1000 || b.getSprite().getY() > 3000 || b.getSprite().getY() < -1000){
                bullets.remove(b);
            }
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
