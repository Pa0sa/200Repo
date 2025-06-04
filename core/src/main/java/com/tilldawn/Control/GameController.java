package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.Model.Monster.*;
import com.tilldawn.View.GameView;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class GameController {
    private GameView view;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;
    private MonsterSpawner monsterSpawner;



    ShapeRenderer shapeRenderer = new ShapeRenderer();

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
            drawPlayerHealth();

            Main.getBatch().end();
            float f = (float) (playerController.getPlayer().getXp() - (20 * (playerController.getPlayer().getLevel() - 1))) / (20 * playerController.getPlayer().getLevel());
            drawProgressBar(f);
            Main.getBatch().begin();

            if(!view.isPaused()) {
                playerController.update();
                weaponController.update();
                monsterSpawner.updateXPs();
                monsterSpawner.updateTrees();
                monsterSpawner.updateTentaclesMonsters();
                monsterSpawner.updateEyebats();
                monsterSpawner.updateEyebatsBullets();
                monsterSpawner.spawnTentacleMonster(delta);
                monsterSpawner.spawnEyebat(delta);
                monsterSpawner.spawnElder(delta);
                monsterSpawner.updateElder();
                treeLogic();
                treeLogicDealingDamage();
                tentacleLogic();
                tentacleLogicDealingDamage();
                elderBossLogic();
                elderBossLogicDealingDamage();
                eyebatLogic();
                eyebatLogicDealingDamage();
                eyebatBulletLogicDealingDamage();

                gettingXP();

                worldBorder();
                updateTime();
            }
        }
    }



    private void drawPlayerHealth() {
        int maxHearts = App.getPlayerController().getPlayer().getHealth();
        int currentHealth = App.getPlayerController().getPlayer().getHealth() - App.getPlayerController().getPlayer().getDamage();


        Texture fullHeart = new Texture("heart_full.png");
        Texture emptyHeart = new Texture("heart_empty.png"); // optional

        for (int i = 0; i < maxHearts; i++) {
            float x = 20 + i * 40; // 40px spacing
            float y = Gdx.graphics.getHeight() - 50;

            if (i < currentHealth) {
                Main.getBatch().draw(fullHeart, x, y, 32, 32);
            } else {
                Main.getBatch().draw(emptyHeart, x, y, 32, 32);
            }
        }
    }
    public void drawProgressBar(float progress) {
        // Clamp progress between 0 and 1
        progress = MathUtils.clamp(progress, 0, 1);
        if(progress == 1){
            playerController.getPlayer().setLevel(playerController.getPlayer().getLevel() + 1);
            view.setShowLevelUpWindow(true);
        }

        float barX = 500;
        float barY = 960;
        float barWidth = 900;
        float barHeight = 20;
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // Draw background (gray)
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);
        // Draw progress (green)
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(barX, barY, barWidth * progress, barHeight);

        shapeRenderer.end();
    }
    public void gettingXP() {
        Array<XP> x = monsterSpawner.getXps();
        Player p = playerController.getPlayer();
        for (int i = x.size - 1; i >= 0; i--) {
            XP xp = x.get(i);
            if (xp.getCollisionRect2().overlaps(p.getRect())) {
                playerController.getPlayer().setXp(playerController.getPlayer().getXp() + 3);
                x.removeIndex(i);
            }
        }
        monsterSpawner.setXps(x);
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
            view.getLevelCount().setText("Level : " + String.valueOf(getPlayerController().getPlayer().getLevel()));
            App.setTimePassed(App.getTimeOfChoice() - remaining);
        }
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
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
                    p.setHurt(true);
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
                        tentacle.setDying(true); // Trigger death animation
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
                    p.setHurt(true);
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
                    p.setHurt(true);
                    t.removeIndex(i);
                }
            }
        }
    }
    public void eyebatKnockBack(Eyebat tentacleMonster) {
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
            float speed = 100f;
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
                    if (tentacle.getHealth() <= 0 && !tentacle.isDying()) {
                        tentacle.setDying(true); // Trigger death animation
                        App.getCurrentUser().setKills(1);
                    }
                    b.remove(j);
                    break;
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
                    p.setHurt(true);
                    tentacleKnockBack(tentacleMonster);
                }
            }
        }
    }
    public void tentacleKnockBack(TentacleMonster tentacleMonster) {
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
            float speed = 100f;
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
    public void elderBossLogic() {
        if(monsterSpawner.getElderBoss() != null) {
            ElderBoss e = monsterSpawner.getElderBoss();
            ArrayList<Bullet> b = weaponController.getBullets();
            for (int j = b.size() - 1; j >= 0; j--) {
                Bullet bullet = b.get(j);
                Rectangle bulletRect = bullet.getRect(); // make sure this is up-to-date

                if (e.getCollisionRect().overlaps(bulletRect)) {
                    e.setHealth(App.getWeaponOfChoice().getDamage());
                    if (e.getHealth() <= 0) {
                        monsterSpawner.getXps().add(new XP(e.getPosX(),e.getPosY()));
                        App.getCurrentUser().setKills(1);
                        e = null;
                    }
                    b.remove(j);
                    break; // bullet is gone, stop checking trees
                }
            }
            monsterSpawner.setElderBoss(e);
        }
    }
    public void elderBossLogicDealingDamage() {
        if(monsterSpawner.getElderBoss() != null) {
            ElderBoss e = monsterSpawner.getElderBoss();
            Player p = playerController.getPlayer();
            if (e.getCollisionRect2().overlaps(p.getRect())) {
                if (p.isCanBeAttacked()) {
                    p.setCanBeAttacked(false);
                    p.setDamage(1);
                    p.setHurt(true);
                    elderKnockBack(e);
                }
            }
        }
    }
    public void elderKnockBack(ElderBoss elderBoss) {
        float monsterX = elderBoss.getPosX();
        float monsterY = elderBoss.getPosY();
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
        float speed = 200f;
        // 4. Update position
        monsterXReal -= xVelocity * speed;
        monsterYReal -= yVelocity * speed;
        monsterX = -monsterXReal + 960;
        monsterY = -monsterYReal + 500;
        elderBoss.setPosX((int) monsterX);
        elderBoss.setPosY((int) monsterY);
        // 5. World translation for drawing
        float camOffsetX = MathUtils.clamp(App.getPlayerController().getPlayer().getPosX(), -5631, 0);
        float camOffsetY = MathUtils.clamp(App.getPlayerController().getPlayer().getPosY(), -4375, 0);
        float drawX = monsterX + camOffsetX;
        float drawY = monsterY + camOffsetY;
        // 6. Set sprite position and collision rect
        elderBoss.getTentacle().setPosition(drawX, drawY);
        elderBoss.getCollisionRect().set(drawX, drawY,
            elderBoss.getTentacle().getWidth(),
            elderBoss.getTentacle().getHeight());
        // 7. Draw
        elderBoss.getTentacle().draw(Main.getBatch());
    }

    public void worldBorder(){
        if(monsterSpawner.getElderBoss() != null && !worldController.isWorldBorder()) {
            worldController.setWorldBorder(true);
            worldController.setShrinkWorldBorder(true);
        } else if (monsterSpawner.getElderBoss() == null){
            worldController.setWorldBorder(false);
            worldController.setShrinkWorldBorder(false);
        }
        if(worldController.isWorldBorder()){
            float minX = -5631 + worldController.getBorderNewX() - 900;
            float maxX = 960 - worldController.getBorderNewX();
            float minY = -4375 + worldController.getBorderNewY() - 420;
            float maxY = 500 - worldController.getBorderNewY();

            playerController.getPlayer().setPosX(clamp(playerController.getPlayer().getPosX(), minX, maxX));
            playerController.getPlayer().setPosY(clamp(playerController.getPlayer().getPosY(), minY, maxY));
        }
    }

    public float clamp (float value, float min, float max) {
        if (value < min){
            if (playerController.getPlayer().isCanBeAttacked()) {
                playerController.getPlayer().setCanBeAttacked(false);
                playerController.getPlayer().setDamage(1);
            }
            return min;
        }
        if (value > max) {
            if (playerController.getPlayer().isCanBeAttacked()) {
                playerController.getPlayer().setCanBeAttacked(false);
                playerController.getPlayer().setDamage(1);
            }
            return max;
        }
        return value;
    }
}
