package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.Model.Monster.Eyebat;
import com.tilldawn.Model.Monster.MonsterBullet;
import com.tilldawn.Model.Monster.TentacleMonster;
import com.tilldawn.Model.Monster.Tree;
import com.tilldawn.View.GameView;

import java.util.ArrayList;


public class GameController {
    private GameView view;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;
    private MonsterSpawner monsterSpawner;

    private int remaining = App.getTimeOfChoice();
    private float counter = 0;
    private float delta = Gdx.graphics.getDeltaTime();


    public void setView(GameView view) {
        this.view = view;
        playerController = new PlayerController(App.getHeroOfChoice());
        worldController = new WorldController(playerController);
        weaponController = new WeaponController(App.getWeaponOfChoice());
        monsterSpawner = new MonsterSpawner();
        App.setWorldController(worldController);
        App.setPlayerController(playerController);
        App.setWeaponController(weaponController);
    }

    public void updateGame() {
        if (view != null) {
            worldController.update();
            playerController.update();
            weaponController.update();
            monsterSpawner.updateTrees();
            monsterSpawner.updateTentaclesMonsters();
            monsterSpawner.updateEyebats();
            monsterSpawner.updateEyebatsBullets();
            monsterSpawner.spawnTentacleMonster(delta);
            monsterSpawner.spawnEyebat(delta);
            treeLogic();
            treeLogicDealingDamage();
            tentacleLogic();
            tentacleLogicDealingDamage();
            eyebatLogic();
            eyebatLogicDealingDamage();
            eyebatBulletLogicDealingDamage();
            updateTime();
        }
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }

    public void updateTime(){
        if (view != null) {
            counter += Gdx.graphics.getDeltaTime();
            if (counter >= 1){
                remaining -= 1;
                counter = 0;
            }
            view.getTimeRemaining().setText(String.valueOf(remaining/60) + " : " + String.valueOf(remaining%60));
            view.getRemainingAmmo().setText("Ammo : " + String.valueOf(App.getWeaponOfChoice().getRemainingAmmo()));
            view.getKillCount().setText("Kills : " + String.valueOf(App.getCurrentUser().getKills()));
            App.setTimePassed(App.getTimeOfChoice() - remaining);
        }
    }

    public void treeLogic() {
        Array<Tree> t = monsterSpawner.getTrees();
        ArrayList<Bullet> b = weaponController.getBullets();
        Player p = playerController.getPlayer();
        for (int j = b.size() - 1; j >= 0; j--) {
            Bullet bullet = b.get(j);
            Rectangle bulletRect = bullet.getRect(); // make sure this is up-to-date
            for (int i = t.size - 1; i >= 0; i--) {
                Tree tree = t.get(i);
                if (tree.getCollisionRect().overlaps(bulletRect)) {
                    b.remove(j);
                    break; // bullet is gone, stop checking trees
                }
            }
        }
        monsterSpawner.setTrees(t);
    }

    public void treeLogicDealingDamage() {
        Array<Tree> t = monsterSpawner.getTrees();
        Player p = playerController.getPlayer();
        for (int i = t.size - 1; i >= 0; i--) {
            Tree tree = t.get(i);
            if (tree.getCollisionRect2().overlaps(p.getRect())) {
                if(p.isCanBeAttacked()) {
                    p.setCanBeAttacked(false);
                    p.setDamage(1);
                }
            }
        }
    }
    public void eyebatLogic() {
        Array<Eyebat> t = monsterSpawner.getEyebats();
        ArrayList<Bullet> b = weaponController.getBullets();
        for (int j = b.size() - 1; j >= 0; j--) {
            Bullet bullet = b.get(j);
            Rectangle bulletRect = bullet.getRect(); // make sure this is up-to-date
            for (int i = t.size - 1; i >= 0; i--) {
                Eyebat tentacle = t.get(i);
                if (tentacle.getCollisionRect().overlaps(bulletRect)) {
                    tentacle.setHealth(App.getWeaponOfChoice().getDamage());
                    eyebatKnockBack(tentacle);
                    if(tentacle.getHealth() <= 0){
                        t.removeIndex(i);
                        App.getCurrentUser().setKills(1);
                    }
                    b.remove(j);
                    break; // bullet is gone, stop checking trees
                }
            }
        }
        monsterSpawner.setEyebats(t);
    }
    public void eyebatLogicDealingDamage() {
        Array<Eyebat> t = monsterSpawner.getEyebats();
        Player p = playerController.getPlayer();

        for (int i = t.size - 1; i >= 0; i--) {
            Eyebat eyebat = t.get(i);
            if (eyebat.getCollisionRect2().overlaps(p.getRect())) {
                if(p.isCanBeAttacked()) {
                    p.setCanBeAttacked(false);
                    p.setDamage(1);
                    eyebatKnockBack(eyebat);
                }
            }
        }
    }
    public void eyebatBulletLogicDealingDamage() {
        Array<MonsterBullet> t = monsterSpawner.getMonsterBullets();
        Player p = playerController.getPlayer();

        for (int i = t.size - 1; i >= 0; i--) {
            MonsterBullet eyebatBullet = t.get(i);
            if (eyebatBullet.getCollisionRect2().overlaps(p.getRect())) {
                if(p.isCanBeAttacked()) {
                    p.setCanBeAttacked(false);
                    p.setDamage(1);
                    t.removeIndex(i);
                }
            }
        }
    }
    public void eyebatKnockBack(Eyebat tentacleMonster) {
        for(int i = 0 ; i < 100 ; i++) {
            float monsterX = tentacleMonster.getPosX();
            float monsterY = tentacleMonster.getPosY();
            float monsterXReal = -monsterX + 960;
            float monsterYReal = -monsterY + 500;
            // 1. Calculate direction vector toward player
            float dx = App.getPlayerController().getPlayer().getPosX() - monsterXReal;
            float dy = App.getPlayerController().getPlayer().getPosY() - monsterYReal;
            // 2. Normalize it
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance == 0) distance = 1; // avoid division by zero
            float xVelocity = dx / distance;
            float yVelocity = dy / distance;
            // 3. Speed of movement (adjust this as needed)
            float speed = 0.1f;
            // 4. Update position
            monsterXReal -= xVelocity * speed;
            monsterYReal -= yVelocity * speed;
            monsterX = -monsterXReal + 960;
            monsterY = -monsterYReal + 500;
            tentacleMonster.setPosX((int) monsterX);
            tentacleMonster.setPosY((int) monsterY);
            // 5. World translation for drawing
            float camOffsetX = MathUtils.clamp(App.getPlayerController().getPlayer().getPosX(), -5631, 0);
            float camOffsetY = MathUtils.clamp(App.getPlayerController().getPlayer().getPosY(), -4375, 0);
            float drawX = monsterX + camOffsetX;
            float drawY = monsterY + camOffsetY;
            // 6. Set sprite position and collision rect
            tentacleMonster.getTentacle().setPosition(drawX, drawY);
            tentacleMonster.getCollisionRect().set(drawX, drawY,
                tentacleMonster.getTentacle().getWidth(),
                tentacleMonster.getTentacle().getHeight());
            // 7. Draw
            tentacleMonster.getTentacle().draw(Main.getBatch());
        }
    }

    public void tentacleLogic() {
        Array<TentacleMonster> t = monsterSpawner.getTentacleMonsters();
        ArrayList<Bullet> b = weaponController.getBullets();

        for (int j = b.size() - 1; j >= 0; j--) {
            Bullet bullet = b.get(j);
            Rectangle bulletRect = bullet.getRect(); // make sure this is up-to-date
            for (int i = t.size - 1; i >= 0; i--) {
                TentacleMonster tentacle = t.get(i);

                if (tentacle.getCollisionRect().overlaps(bulletRect)) {

                    tentacle.setHealth(App.getWeaponOfChoice().getDamage());

                    tentacleKnockBack(tentacle);

                    if(tentacle.getHealth() <= 0){
                        t.removeIndex(i);
                        App.getCurrentUser().setKills(1);
                    }
                    b.remove(j);
                    break; // bullet is gone, stop checking trees
                }
            }
        }
        monsterSpawner.setTentacleMonsters(t);
    }
    public void tentacleLogicDealingDamage() {
        Array<TentacleMonster> t = monsterSpawner.getTentacleMonsters();
        Player p = playerController.getPlayer();

        for (int i = t.size - 1; i >= 0; i--) {
            TentacleMonster tentacleMonster = t.get(i);
            if (tentacleMonster.getCollisionRect2().overlaps(p.getRect())) {
                if(p.isCanBeAttacked()) {
                    p.setCanBeAttacked(false);
                    p.setDamage(1);
                    tentacleKnockBack(tentacleMonster);
                }
            }
        }
    }
    public void tentacleKnockBack(TentacleMonster tentacleMonster) {
        for(int i = 0 ; i < 100 ; i++) {
            float monsterX = tentacleMonster.getPosX();
            float monsterY = tentacleMonster.getPosY();
            float monsterXReal = -monsterX + 960;
            float monsterYReal = -monsterY + 500;
            // 1. Calculate direction vector toward player
            float dx = App.getPlayerController().getPlayer().getPosX() - monsterXReal;
            float dy = App.getPlayerController().getPlayer().getPosY() - monsterYReal;
            // 2. Normalize it
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance == 0) distance = 1; // avoid division by zero
            float xVelocity = dx / distance;
            float yVelocity = dy / distance;
            // 3. Speed of movement (adjust this as needed)
            float speed = 0.1f;
            // 4. Update position
            monsterXReal -= xVelocity * speed;
            monsterYReal -= yVelocity * speed;
            monsterX = -monsterXReal + 960;
            monsterY = -monsterYReal + 500;
            tentacleMonster.setPosX((int) monsterX);
            tentacleMonster.setPosY((int) monsterY);
            // 5. World translation for drawing
            float camOffsetX = MathUtils.clamp(App.getPlayerController().getPlayer().getPosX(), -5631, 0);
            float camOffsetY = MathUtils.clamp(App.getPlayerController().getPlayer().getPosY(), -4375, 0);
            float drawX = monsterX + camOffsetX;
            float drawY = monsterY + camOffsetY;
            // 6. Set sprite position and collision rect
            tentacleMonster.getTentacle().setPosition(drawX, drawY);
            tentacleMonster.getCollisionRect().set(drawX, drawY,
                tentacleMonster.getTentacle().getWidth(),
                tentacleMonster.getTentacle().getHeight());
            // 7. Draw
            tentacleMonster.getTentacle().draw(Main.getBatch());
        }
    }
}
