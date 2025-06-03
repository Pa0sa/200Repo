package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetManager {
    private static AssetManager assetManager;
    private final Skin skin = new Skin(Gdx.files.internal("skin/tracer-ui.json"));
    private final Texture logo = new Texture(Gdx.files.internal("images/T_20Logo.png"));


    public static AssetManager getAssetManager() {
        if (assetManager == null) {
            assetManager = new AssetManager();
        }
        return assetManager;
    }

    public Skin getSkin() {
        return skin;
    }
    public Texture getLogo() {
        return logo;
    }


    private final String character1_idle0 = "1/Idle_0.png";
    private final String character1_idle1 = "1/Idle_1.png";
    private final String character1_idle2 = "1/Idle_2.png";
    private final String character1_idle3 = "1/Idle_3.png";
    private final String character1_idle4 = "1/Idle_4.png";
    private final String character1_idle5 = "1/Idle_5.png";
    private final Texture character1_idle0_tex = new Texture(character1_idle0);
    private final Texture character1_idle1_tex = new Texture(character1_idle1);
    private final Texture character1_idle2_tex = new Texture(character1_idle2);
    private final Texture character1_idle3_tex = new Texture(character1_idle3);
    private final Texture character1_idle4_tex = new Texture(character1_idle4);
    private final Texture character1_idle5_tex = new Texture(character1_idle5);
    private final Animation<Texture> character1_idle_frames = new Animation<>(0.1f, character1_idle0_tex, character1_idle1_tex, character1_idle2_tex, character1_idle3_tex, character1_idle4_tex, character1_idle5_tex);


    private final String character2_idle0 = "1/T_Raven_Idle_0.png";
    private final String character2_idle1 = "1/T_Raven_Idle_1.png";
    private final String character2_idle2 = "1/T_Raven_Idle_2.png";
    private final String character2_idle3 = "1/T_Raven_Idle_3.png";
    private final String character2_idle4 = "1/T_Raven_Idle_4.png";
    private final String character2_idle5 = "1/T_Raven_Idle_5.png";
    private final Texture character2_idle0_tex = new Texture(character2_idle0);
    private final Texture character2_idle1_tex = new Texture(character2_idle1);
    private final Texture character2_idle2_tex = new Texture(character2_idle2);
    private final Texture character2_idle3_tex = new Texture(character2_idle3);
    private final Texture character2_idle4_tex = new Texture(character2_idle4);
    private final Texture character2_idle5_tex = new Texture(character2_idle5);
    private final Animation<Texture> character2_idle_frames = new Animation<>(0.1f, character2_idle0_tex, character2_idle1_tex, character2_idle2_tex, character2_idle3_tex, character2_idle4_tex, character2_idle5_tex);


    private final String tree = "monsters/T_TreeMonster_2.png";
    private final Texture treeTexture = new Texture(tree);
    private final String tentacleMonster = "monsters/BrainMonster_3.png";
    private final Texture tentacleMonsterTexture = new Texture(tree);
    private final String eyebat = "monsters/EyeMonster_0.png";
    private final Texture eyebatTexture = new Texture(tree);
    private final String monsterBullet = "EyeMonsterProjecitle.png";
    private final Texture MonsterBulletTexture = new Texture(monsterBullet);


    private final String smg = "smg/SMGStill.png";
    private final Texture smgTexture = new Texture(smg);
    private final Sprite smgSprite = new Sprite(smgTexture);
    private final String smgs = "libgdx.png";
    private final Texture smgsTexture = new Texture(smgs);
    private final Sprite smgsSprite = new Sprite(smgsTexture);

    public Sprite getSmgSprite() {
        return smgSprite;
    }

    public Sprite getSmgsSprite() {
        return smgsSprite;
    }

    private final String bullet = "bullet.png";


    public Animation<Texture> getCharacter1_idle_animation() {
        return character1_idle_frames;
    }

    public Animation<Texture> getCharacter2_idle_animation() {
        return character2_idle_frames;
    }


    public String getCharacter1_idle0(){
        return character1_idle0;
    }

    public Texture getSmgTexture(){
        return smgTexture;
    }

    public String getSmg(){
        return smg;
    }

    public String getBullet(){
        return bullet;
    }

    public Texture getMonsterBulletTexture() {
        return MonsterBulletTexture;
    }

    public String getMonsterBullet() {
        return monsterBullet;
    }

    public Texture getTreeTexture() {
        return treeTexture;
    }

    public String getTree() {
        return tree;
    }

    public String getCharacter1_idle1() {
        return character1_idle1;
    }

    public Texture getEyebatTexture() {
        return eyebatTexture;
    }

    public String getEyebat() {
        return eyebat;
    }

    public Texture getTentacleMonsterTexture() {
        return tentacleMonsterTexture;
    }

    public String getTentacleMonster() {
        return tentacleMonster;
    }
}
