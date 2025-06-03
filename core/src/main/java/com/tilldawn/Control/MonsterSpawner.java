package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.tilldawn.Main;
import com.tilldawn.Model.App;
import com.tilldawn.Model.AssetManager;
import com.tilldawn.Model.Monster.Eyebat;
import com.tilldawn.Model.Monster.MonsterBullet;
import com.tilldawn.Model.Monster.TentacleMonster;
import com.tilldawn.Model.Monster.Tree;

public class MonsterSpawner {
    private Array<MonsterBullet> monsters = new Array<>();
    private Array<Tree> trees = new Array<>();
    private Array<TentacleMonster> tentacleMonsters = new Array<>();
    private Array<Eyebat> eyebats = new Array<>();
    private Array<MonsterBullet> monsterBullets = new Array<>();
    private float counter = 0;
    private float tentacleMonsterpassedTime = 0;
    private int tentacleMonsterpassedTime2 = 0;
    private float eyebatCounter = 0;
    private float eyebatspassedTime = 0;
    private int eyebatpassedTime2 = 0;
    private float eyebatBulletCounter = 0;
    private float eyebatBulletpassedTime = 0;
    private int eyebatBulletpassedTime2 = 0;
    private float tentacleTimer = 0;
    private float eyebatTimer = 0;

    public MonsterSpawner() {
        spawnTrees();
        spawnTentacleMonster();
    }

    public void updateTrees() {
        for(Tree tree : trees) {
            float treeX = MathUtils.clamp(App.getPlayerController().getPlayer().getPosX(),-5631,  0);
            float treeY = MathUtils.clamp(App.getPlayerController().getPlayer().getPosY(),-4375,  0);

            float x = tree.getPosX() + treeX;
            float y = tree.getPosY() + treeY;
            tree.getTreeSprite().setPosition(x, y);
            tree.getCollisionRect().set(x , y,tree.getTreeSprite().getWidth(),tree.getTreeSprite().getHeight());
            tree.getTreeSprite().draw(Main.getBatch());
        }
    }



    public void spawnTrees(){
        int x = 1920;
        int y = 1000;
        for(int i = 0; i < 40; i++) {
            trees.add(new Tree(x, y));
            x = (int)(Math.random()*7551);
            y = (int)(Math.random()*5375);
        }
    }

    public void updateTentaclesMonsters() {

        for (TentacleMonster tentacleMonster : tentacleMonsters) {
        float playerX = App.getPlayerController().getPlayer().getPosX();
        float playerY = App.getPlayerController().getPlayer().getPosY();
            float monsterX = tentacleMonster.getPosX() ;
            float monsterY = tentacleMonster.getPosY() ;
            float monsterXReal = -monsterX + 960;
            float monsterYReal = -monsterY + 500;
            // 1. Calculate direction vector toward player
            float dx = playerX - monsterXReal;
            float dy = playerY - monsterYReal;
            // 2. Normalize it
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance == 0) distance = 1; // avoid division by zero
            float xVelocity = dx / distance;
            float yVelocity = dy / distance;
            // 3. Speed of movement (adjust this as needed)
            float speed = 400f * Gdx.graphics.getDeltaTime();
            // 4. Update position
            monsterXReal += xVelocity * speed;
            monsterYReal += yVelocity * speed;
            monsterX = -monsterXReal + 960;
            monsterY = -monsterYReal + 500;
            tentacleMonster.setPosX(monsterX);
            tentacleMonster.setPosY(monsterY);
            // 5. World translation for drawing
            float camOffsetX = MathUtils.clamp(playerX, -5631, 0);
            float camOffsetY = MathUtils.clamp(playerY, -4375, 0);
            float drawX = monsterX + camOffsetX;
            float drawY = monsterY + camOffsetY;
            // 6. Set sprite position and collision rect
            tentacleMonster.getTentacle().setPosition(drawX, drawY);
            tentacleMonster.getCollisionRect2().set(tentacleMonster.getPosX() , tentacleMonster.getPosY()
                ,tentacleMonster.getTentacle().getWidth(),tentacleMonster.getTentacle().getHeight());
            tentacleMonster.getCollisionRect().set(drawX, drawY,
                tentacleMonster.getTentacle().getWidth(),
                tentacleMonster.getTentacle().getHeight());
            // 7. Draw
            tentacleMonster.getTentacle().draw(Main.getBatch());
        }
    }
    public void spawnTentacleMonster(){
        tentacleMonsters.add(new TentacleMonster(0, 0));
    }
    public void spawnTentacleMonster(float delta){
        counter += delta;
        int x = (int)(Math.random()*7551);
        int y = (int)(Math.random()*5375);
        if (counter >= 1) {
            tentacleMonsterpassedTime += 1;
            tentacleMonsterpassedTime2 += 1;
            counter = 0;
        }
        if(tentacleMonsterpassedTime2 % 4 == 0){
            for(int i = 0; i <= tentacleMonsterpassedTime /30; i++) {
                int rand = (int)(Math.random()*4);
                if(rand == 0){
                    tentacleMonsters.add(new TentacleMonster(0 , y));
                } else if(rand == 1){
                    tentacleMonsters.add(new TentacleMonster(x, 0));
                } else if(rand == 2){
                    tentacleMonsters.add(new TentacleMonster(7551 - AssetManager.getAssetManager().getTentacleMonsterTexture().getWidth(), y));
                } else {
                    tentacleMonsters.add(new TentacleMonster(x, 5375 - AssetManager.getAssetManager().getTentacleMonsterTexture().getHeight()));
                }
            }
            tentacleMonsterpassedTime2 += 1;
        }
    }

    public void updateEyebats() {

        for (Eyebat eyebat : eyebats) {
        float playerX = App.getPlayerController().getPlayer().getPosX();
        float playerY = App.getPlayerController().getPlayer().getPosY();
            eyebat.cooldown();
            float monsterX = eyebat.getPosX() ;
            float monsterY = eyebat.getPosY() ;
            float monsterXReal = -monsterX + 960;
            float monsterYReal = -monsterY + 500;
            // 1. Calculate direction vector toward player
            float dx = playerX - monsterXReal;
            float dy = playerY - monsterYReal;
            // 2. Normalize it
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance == 0) distance = 1; // avoid division by zero
            float xVelocity = dx / distance;
            float yVelocity = dy / distance;
            // 3. Speed of movement (adjust this as needed)
            float speed = 400f * Gdx.graphics.getDeltaTime();
            // 4. Update position
            if (distance > 600f) { // only move if far enough
                monsterXReal += xVelocity * speed;
                monsterYReal += yVelocity * speed;
            }

            monsterX = -monsterXReal + 960;
            monsterY = -monsterYReal + 500;

            if(distance <= 600f){
                if(eyebat.isCanShoot()){
                    eyebatShoot(monsterX,monsterY , eyebat);
                }
            }
            eyebat.setPosX(monsterX);
            eyebat.setPosY(monsterY);
            // 5. World translation for drawing
            float camOffsetX = MathUtils.clamp(playerX, -5631, 0);
            float camOffsetY = MathUtils.clamp(playerY, -4375, 0);
            float drawX = monsterX + camOffsetX;
            float drawY = monsterY + camOffsetY;
            // 6. Set sprite position and collision rect
            eyebat.getTentacle().setPosition(drawX, drawY);
            eyebat.getCollisionRect2().setPosition(eyebat.getPosX(), eyebat.getPosY());
            eyebat.getCollisionRect().set(drawX, drawY,
                eyebat.getTentacle().getWidth(),
                eyebat.getTentacle().getHeight());
            // 7. Draw
            eyebat.getTentacle().draw(Main.getBatch());
        }
    }

    public void spawnEyebat(float delta){
        if(App.getTimeOfChoice()/4 < App.getTimePassed()) {
            eyebatCounter += delta;
            int x = (int) (Math.random() * 7551);
            int y = (int) (Math.random() * 5375);
            if (eyebatCounter >= 1) {
                eyebatspassedTime += 1;
                eyebatpassedTime2 += 1;
                eyebatCounter = 0;
            }
            if (eyebatpassedTime2 % 11 == 0) {
                int num = ((4*App.getTimePassed()) - App.getTimeOfChoice() + 30)/30;
                for (int i = 0; i <= num; i++) {
                    int rand = (int) (Math.random() * 4);
                    if (rand == 0) {
                        eyebats.add(new Eyebat(0, y));
                    } else if (rand == 1) {
                        eyebats.add(new Eyebat(x, 0));
                    } else if (rand == 2) {
                        eyebats.add(new Eyebat(7551 - AssetManager.getAssetManager().getTentacleMonsterTexture().getWidth(), y));
                    } else {
                        eyebats.add(new Eyebat(x, 5375 - AssetManager.getAssetManager().getTentacleMonsterTexture().getHeight()));
                    }
                }
                eyebatpassedTime2 += 1;
            }
        }
    }
    public void eyebatShoot(float eyebatX , float eyebatY , Eyebat eyebat){
        if(eyebat.isCanShoot()){
            monsterBullets.add(new MonsterBullet(eyebatX ,eyebatY , App.getPlayerController().getPlayer().getPosX(),App.getPlayerController().getPlayer().getPosY()));
            eyebat.setCanShoot(false);
        }
    }
    public void updateEyebatsBullets() {
        for(int i = monsterBullets.size - 1; i >= 0; i--) {
            MonsterBullet monsterBullet = monsterBullets.get(i);
            float playerX = monsterBullet.getVelX();
            float playerY = monsterBullet.getVelY();
            float monsterX = monsterBullet.getPosX() ;
            float monsterY = monsterBullet.getPosY() ;
            float monsterXReal = -monsterX + 960;
            float monsterYReal = -monsterY + 500;
            // 1. Calculate direction vector toward player
            float dx = playerX - monsterXReal;
            float dy = playerY - monsterYReal;
            // 2. Normalize it
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance == 0) distance = 1; // avoid division by zero
            float xVelocity = dx / distance;
            float yVelocity = dy / distance;
            // 3. Speed of movement (adjust this as needed)
            float speed = 400f * Gdx.graphics.getDeltaTime();
            // 4. Update position
            monsterXReal += xVelocity * speed;
            monsterYReal += yVelocity * speed;
            monsterBullet.setVelX(playerX + xVelocity * speed);
            monsterBullet.setVelY(playerY + yVelocity * speed);
            monsterX = -monsterXReal + 960;
            monsterY = -monsterYReal + 500;
            monsterBullet.setPosX(monsterX);
            monsterBullet.setPosY(monsterY);
            // 5. World translation for drawing
            float camOffsetX = App.getPlayerController().getPlayer().getPosX();
            float camOffsetY = App.getPlayerController().getPlayer().getPosY();
            float drawX = monsterX + camOffsetX;
            float drawY = monsterY + camOffsetY;
            // 6. Set sprite position and collision rect
            monsterBullet.getTentacle().setPosition(drawX, drawY);
            monsterBullet.getCollisionRect2().setPosition(monsterBullet.getPosX(), monsterBullet.getPosY());
            monsterBullet.getCollisionRect().set(drawX, drawY,
                monsterBullet.getTentacle().getWidth(),
                monsterBullet.getTentacle().getHeight());
            // 7. Draw
            monsterBullet.getTentacle().draw(Main.getBatch());

            if(monsterBullet.getTentacle().getX() > 3000 || monsterBullet.getTentacle().getX() < -1000 ||
                monsterBullet.getTentacle().getY() > 3000 || monsterBullet.getTentacle().getY() < -1000){
                monsterBullets.removeIndex(i);
            }
        }
    }
    public void setTrees(Array<Tree> trees) {
        this.trees = trees;
    }

    public Array<Tree> getTrees() {
        return trees;
    }

    public Array<MonsterBullet> getMonsters() {
        return monsters;
    }

    public void setMonsters(Array<MonsterBullet> monsters) {
        this.monsters = monsters;
    }

    public Array<TentacleMonster> getTentacleMonsters() {
        return tentacleMonsters;
    }

    public void setTentacleMonsters(Array<TentacleMonster> tentacleMonsters) {
        this.tentacleMonsters = tentacleMonsters;
    }

    public Array<Eyebat> getEyebats() {
        return eyebats;
    }

    public void setEyebats(Array<Eyebat> eyebats) {
        this.eyebats = eyebats;
    }

    public Array<MonsterBullet> getMonsterBullets() {
        return monsterBullets;
    }

    public void setMonsterBullets(Array<MonsterBullet> monsterBullets) {
        this.monsterBullets = monsterBullets;
    }
}


