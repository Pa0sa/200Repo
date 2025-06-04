package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.tilldawn.Main;
import com.tilldawn.Model.App;
import com.tilldawn.Model.AssetManager;
import com.tilldawn.Model.Monster.*;
import com.tilldawn.View.GameView;

import static com.tilldawn.Main.playMusic;

public class MonsterSpawner {
    private Array<MonsterBullet> monsters = new Array<>();
    private Array<Tree> trees = new Array<>();
    private Array<TentacleMonster> tentacleMonsters = new Array<>();
    private Array<Eyebat> eyebats = new Array<>();
    private Array<XP> xps = new Array<>();

    private Array<MonsterBullet> monsterBullets = new Array<>();
    private ElderBoss elderBoss = null;
    private float elderBossSpeed = 1f;

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
    private boolean spawnElder = false;

    public MonsterSpawner() {
        spawnTrees();
        //spawnTentacleMonster();
        //eyebats.add(new Eyebat(0, 0));
                //elderBoss = new ElderBoss(0 , 0);
    }
    public void updateXPs() {
        for(XP xp : xps) {
            float treeX = MathUtils.clamp(App.getPlayerController().getPlayer().getPosX(),-5631,  0);
            float treeY = MathUtils.clamp(App.getPlayerController().getPlayer().getPosY(),-4375,  0);

            float x = xp.getPosX() + treeX;
            float y = xp.getPosY() + treeY;
            xp.getTreeSprite().setPosition(x, y);
            xp.getCollisionRect().set(x , y,xp.getTreeSprite().getWidth(),xp.getTreeSprite().getHeight());
            xp.getTreeSprite().draw(Main.getBatch());
        }
    }

    public void updateTrees() {
        for(Tree tree : trees) {
            float treeX = MathUtils.clamp(App.getPlayerController().getPlayer().getPosX(),-5631,  0);
            float treeY = MathUtils.clamp(App.getPlayerController().getPlayer().getPosY(),-4375,  0);

            float x = tree.getPosX() + treeX;
            float y = tree.getPosY() + treeY;
            tree.getTreeSprite().setPosition(x, y);
            updateTreeAnimation(tree);
            tree.getCollisionRect().set(x , y,tree.getTreeSprite().getWidth(),tree.getTreeSprite().getHeight());
            tree.getTreeSprite().draw(Main.getBatch());
        }
    }
    public void updateTreeAnimation(Tree tree){
        Animation<Texture> animation;
        animation = AssetManager.getAssetManager().getTree_frames();
        tree.getTreeSprite().setRegion(animation.getKeyFrame(tree.getTime()));
        if (!animation.isAnimationFinished(tree.getTime())) {
            tree.setTime(tree.getTime() + Gdx.graphics.getDeltaTime());
        }
        else {
            tree.setTime(0);
        }
        animation.setPlayMode(Animation.PlayMode.LOOP);
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

        for (int i = tentacleMonsters.size - 1; i >= 0; i--) {
            TentacleMonster tentacleMonster = tentacleMonsters.get(i);
            if (tentacleMonster.isDying()) {
                tentacleMonster.updateDeathTimer(Gdx.graphics.getDeltaTime());
                if (tentacleMonster.getDeathTimer() >= 0.15) {
                    // Drop XP and remove monster
                    xps.add(new XP(tentacleMonster.getPosX(), tentacleMonster.getPosY()));
                    tentacleMonsters.removeIndex(i);
                    continue;
                }

                // Set the frame from the death animation
                Texture frame = tentacleMonster.getDeathAnimation().getKeyFrame(tentacleMonster.getDeathTimer());
                tentacleMonster.getTentacle().setRegion(frame);

                // Still draw it (fading out or shrinking optional)
                tentacleMonster.getTentacle().draw(Main.getBatch());
                continue;
            } else {

                float playerX = App.getPlayerController().getPlayer().getPosX();
                float playerY = App.getPlayerController().getPlayer().getPosY();
                float monsterX = tentacleMonster.getPosX();
                float monsterY = tentacleMonster.getPosY();
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
                tentacleMonster.getCollisionRect2().set(tentacleMonster.getPosX(), tentacleMonster.getPosY()
                    , tentacleMonster.getTentacle().getWidth(), tentacleMonster.getTentacle().getHeight());

                updateTentaclesMonstersAnimation(tentacleMonster);
                tentacleMonster.getCollisionRect().set(drawX, drawY,
                    tentacleMonster.getTentacle().getWidth(),
                    tentacleMonster.getTentacle().getHeight());
                // 7. Draw
                tentacleMonster.getTentacle().draw(Main.getBatch());
            }
        }
    }


    public void updateTentaclesMonstersAnimation(TentacleMonster tentacleMonster){
        Animation<Texture> animation;
        animation = AssetManager.getAssetManager().getBrainMonsterWalk_frames();
        tentacleMonster.getTentacle().setRegion(animation.getKeyFrame(tentacleMonster.getTime()));
        if (!animation.isAnimationFinished(tentacleMonster.getTime())) {
            tentacleMonster.setTime(tentacleMonster.getTime() + Gdx.graphics.getDeltaTime());
        }
        else {
            tentacleMonster.setTime(0);
        }
        animation.setPlayMode(Animation.PlayMode.LOOP);
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
        for (int i = eyebats.size - 1; i >= 0; i--) {
            Eyebat eyebat = eyebats.get(i);
            if (eyebat.isDying()) {
                eyebat.updateDeathTimer(Gdx.graphics.getDeltaTime());
                if (eyebat.getDeathTimer() >= 0.15) {
                    // Drop XP and remove monster
                    xps.add(new XP(eyebat.getPosX(), eyebat.getPosY()));
                    eyebats.removeIndex(i);
                    continue;
                }

                // Set the frame from the death animation
                Texture frame = eyebat.getDeathAnimation().getKeyFrame(eyebat.getDeathTimer());
                eyebat.getTentacle().setRegion(frame);

                // Still draw it (fading out or shrinking optional)
                eyebat.getTentacle().draw(Main.getBatch());
                continue;
            } else {
                float playerX = App.getPlayerController().getPlayer().getPosX();
                float playerY = App.getPlayerController().getPlayer().getPosY();
                eyebat.cooldown();
                float monsterX = eyebat.getPosX();
                float monsterY = eyebat.getPosY();
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

                if (distance <= 600f) {
                    if (eyebat.isCanShoot()) {
                        eyebatShoot(monsterX, monsterY, eyebat);
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
                updateEyeBatsAnimation(eyebat);
                eyebat.getCollisionRect().set(drawX, drawY,
                    eyebat.getTentacle().getWidth(),
                    eyebat.getTentacle().getHeight());
                // 7. Draw
                eyebat.getTentacle().draw(Main.getBatch());
            }
        }
    }
    public void updateEyeBatsAnimation(Eyebat eyebat){
        Animation<Texture> animation;
        animation = AssetManager.getAssetManager().getEyeBatWalk_frames();
        eyebat.getTentacle().setRegion(animation.getKeyFrame(eyebat.getTime()));
        if (!animation.isAnimationFinished(eyebat.getTime())) {
            eyebat.setTime(eyebat.getTime() + Gdx.graphics.getDeltaTime());
        }
        else {
            eyebat.setTime(0);
        }
        animation.setPlayMode(Animation.PlayMode.LOOP);
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

    public void updateElder() {
        if(elderBoss == null && App.getTimeOfChoice()/2 < App.getTimePassed()){
            elderBoss = new ElderBoss(0 , 0);
        }
        if(elderBoss != null) {
                float playerX = App.getPlayerController().getPlayer().getPosX();
                float playerY = App.getPlayerController().getPlayer().getPosY();
            elderBoss.cooldown();
            elderBoss.dashDuration();
            elderBoss.dash();
            elderDashClock();
                float monsterX = elderBoss.getPosX();
                float monsterY = elderBoss.getPosY();
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
                float speed = elderBossSpeed * 400f * Gdx.graphics.getDeltaTime();
                // 4. Update position

                if(elderBossSpeed == 1f) {
                    if (distance > 600f) { // only move if far enough
                        monsterXReal += xVelocity * speed;
                        monsterYReal += yVelocity * speed;
                    }
                } else {
                    monsterXReal += xVelocity * speed;
                    monsterYReal += yVelocity * speed;
                }
                monsterX = -monsterXReal + 960;
                monsterY = -monsterYReal + 500;

                if (distance <= 600) {
                    if (elderBoss.isCanDash()) {
                        elderDash(monsterX, monsterY, elderBoss);
                    }
                }
            elderBoss.setPosX(monsterX);
            elderBoss.setPosY(monsterY);
                // 5. World translation for drawing
                float camOffsetX = MathUtils.clamp(playerX, -5631, 0);
                float camOffsetY = MathUtils.clamp(playerY, -4375, 0);
                float drawX = monsterX + camOffsetX;
                float drawY = monsterY + camOffsetY;
                // 6. Set sprite position and collision rect
            elderBoss.getTentacle().setPosition(drawX, drawY);
            elderBoss.getCollisionRect2().setPosition(elderBoss.getPosX(), elderBoss.getPosY());
            updateElderAnimation(elderBoss);
            elderBoss.getCollisionRect().set(drawX, drawY,
                    elderBoss.getTentacle().getWidth(),
                    elderBoss.getTentacle().getHeight());
                // 7. Draw
            elderBoss.getTentacle().draw(Main.getBatch());
        }
    }
    public void updateElderAnimation(ElderBoss elderBoss){
        if(!elderBoss.isDashing()) {
            Animation<Texture> animation;
            animation = AssetManager.getAssetManager().getElderWalk_frames();
            elderBoss.getTentacle().setRegion(animation.getKeyFrame(elderBoss.getTime()));
            if (!animation.isAnimationFinished(elderBoss.getTime())) {
                elderBoss.setTime(elderBoss.getTime() + Gdx.graphics.getDeltaTime());
            } else {
                elderBoss.setTime(0);
            }
            animation.setPlayMode(Animation.PlayMode.LOOP);
        } else if (elderBoss.isDashing()) {
            Animation<Texture> animation;
            animation = AssetManager.getAssetManager().getElderDash_frames();
            elderBoss.getTentacle().setRegion(animation.getKeyFrame(elderBoss.getTime()));
            if (!animation.isAnimationFinished(elderBoss.getTime())) {
                elderBoss.setTime(elderBoss.getTime() + Gdx.graphics.getDeltaTime());
            } else {
                elderBoss.setTime(0);
            }
            animation.setPlayMode(Animation.PlayMode.LOOP);
        }
    }

    public static Music currentMusic;
    public static void playSFX(String path) {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal(path));
        currentMusic.setLooping(true);
        // Load saved volume
        Preferences prefs = Gdx.app.getPreferences("Settings");
        float volume = prefs.getFloat("sfxVolume", 0.5f);
        currentMusic.setVolume(volume);
        currentMusic.play();
    }

    private boolean playSfx = true;
    public void spawnElder(float delta){;
        if(App.getTimeOfChoice()/2 < App.getTimePassed() && playSfx) {
            playSfx = false;
            playSFX("SFX/bossfight.mp3");
        }
    }


    public void elderDash(float elderBossX , float elderBossY , ElderBoss elderBoss){
        if(elderBoss.isCanDash()){
            elderBoss.setDashing(true);
            elderBoss.setCanDash(false);
        }
    }
    public void elderDashClock(){
        if(elderBoss.isDash()) {
            elderBossSpeed = 3.5f;
        } else if(!elderBoss.isDash()) {
            elderBossSpeed = 1f;
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

    public ElderBoss getElderBoss() {
        return elderBoss;
    }

    public float getElderBossSpeed() {
        return elderBossSpeed;
    }

    public void setElderBoss(ElderBoss elderBoss) {
        this.elderBoss = elderBoss;
    }

    public void setElderBossSpeed(float elderBossSpeed) {
        this.elderBossSpeed = elderBossSpeed;
    }

    public Array<XP> getXps() {
        return xps;
    }

    public void setXps(Array<XP> xps) {
        this.xps = xps;
    }

    public float getEyebatTimer() {
        return eyebatTimer;
    }

    public void setEyebatTimer(float eyebatTimer) {
        this.eyebatTimer = eyebatTimer;
    }

    public float getTentacleTimer() {
        return tentacleTimer;
    }

    public void setTentacleTimer(float tentacleTimer) {
        this.tentacleTimer = tentacleTimer;
    }

    public float getEyebatBulletpassedTime() {
        return eyebatBulletpassedTime;
    }

    public void setEyebatBulletpassedTime(float eyebatBulletpassedTime) {
        this.eyebatBulletpassedTime = eyebatBulletpassedTime;
    }

    public int getEyebatBulletpassedTime2() {
        return eyebatBulletpassedTime2;
    }

    public void setEyebatBulletpassedTime2(int eyebatBulletpassedTime2) {
        this.eyebatBulletpassedTime2 = eyebatBulletpassedTime2;
    }

    public float getEyebatBulletCounter() {
        return eyebatBulletCounter;
    }

    public void setEyebatBulletCounter(float eyebatBulletCounter) {
        this.eyebatBulletCounter = eyebatBulletCounter;
    }

    public int getEyebatpassedTime2() {
        return eyebatpassedTime2;
    }

    public void setEyebatpassedTime2(int eyebatpassedTime2) {
        this.eyebatpassedTime2 = eyebatpassedTime2;
    }

    public float getEyebatspassedTime() {
        return eyebatspassedTime;
    }

    public void setEyebatspassedTime(float eyebatspassedTime) {
        this.eyebatspassedTime = eyebatspassedTime;
    }

    public float getEyebatCounter() {
        return eyebatCounter;
    }

    public void setEyebatCounter(float eyebatCounter) {
        this.eyebatCounter = eyebatCounter;
    }

    public int getTentacleMonsterpassedTime2() {
        return tentacleMonsterpassedTime2;
    }

    public void setTentacleMonsterpassedTime2(int tentacleMonsterpassedTime2) {
        this.tentacleMonsterpassedTime2 = tentacleMonsterpassedTime2;
    }

    public float getTentacleMonsterpassedTime() {
        return tentacleMonsterpassedTime;
    }

    public void setTentacleMonsterpassedTime(float tentacleMonsterpassedTime) {
        this.tentacleMonsterpassedTime = tentacleMonsterpassedTime;
    }

    public float getCounter() {
        return counter;
    }

    public void setCounter(float counter) {
        this.counter = counter;
    }
}


