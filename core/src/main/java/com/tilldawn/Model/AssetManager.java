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


    private final String Shana_idle0 = "1/Idle_0.png";
    private final String Shana_idle1 = "1/Idle_1.png";
    private final String Shana_idle2 = "1/Idle_2.png";
    private final String Shana_idle3 = "1/Idle_3.png";
    private final String Shana_idle4 = "1/Idle_4.png";
    private final String Shana_idle5 = "1/Idle_5.png";
    private final Texture Shana_idle0_tex = new Texture(Shana_idle0);
    private final Texture Shana_idle1_tex = new Texture(Shana_idle1);
    private final Texture Shana_idle2_tex = new Texture(Shana_idle2);
    private final Texture Shana_idle3_tex = new Texture(Shana_idle3);
    private final Texture Shana_idle4_tex = new Texture(Shana_idle4);
    private final Texture Shana_idle5_tex = new Texture(Shana_idle5);
    private final Animation<Texture> Shana_idle_frames = new Animation<>(0.1f, Shana_idle0_tex, Shana_idle1_tex, Shana_idle2_tex, Shana_idle3_tex, Shana_idle4_tex, Shana_idle5_tex);
    private final String Shana_Run0 = "1/Running/1/Run_0 #8756.png";
    private final String Shana_Run1 = "1/Running/1/Run_1 #8772.png";
    private final String Shana_Run2 = "1/Running/1/Run_2.png";
    private final String Shana_Run3 = "1/Running/1/Run_3.png";
    private final Texture Shana_Run0_tex = new Texture(Shana_Run0);
    private final Texture Shana_Run1_tex = new Texture(Shana_Run1);
    private final Texture Shana_Run2_tex = new Texture(Shana_Run2);
    private final Texture Shana_Run3_tex = new Texture(Shana_Run3);
    private final Animation<Texture> ShanaRun_frames = new Animation<>(0.1f, Shana_Run0_tex, Shana_Run1_tex, Shana_Run2_tex, Shana_Run3_tex);

    private final String Diamond_idle0 = "1/Diamond (1).png";
    private final String Diamond_idle1 = "1/Diamond (2).png";
    private final String Diamond_idle2 = "1/Diamond (3).png";
    private final String Diamond_idle3 = "1/Diamond (4).png";
    private final String Diamond_idle4 = "1/Diamond (5).png";
    private final String Diamond_idle5 = "1/Diamond (6).png";
    private final Texture Diamond_idle0_tex = new Texture(Diamond_idle0);
    private final Texture Diamond_idle1_tex = new Texture(Diamond_idle1);
    private final Texture Diamond_idle2_tex = new Texture(Diamond_idle2);
    private final Texture Diamond_idle3_tex = new Texture(Diamond_idle3);
    private final Texture Diamond_idle4_tex = new Texture(Diamond_idle4);
    private final Texture Diamond_idle5_tex = new Texture(Diamond_idle5);
    private final Animation<Texture> Diamond_idle_frames = new Animation<>(0.1f, Diamond_idle0_tex, Diamond_idle1_tex, Diamond_idle2_tex, Diamond_idle3_tex, Diamond_idle4_tex, Diamond_idle5_tex);
    private final String Diamond_Run0 = "1/Running/Diamond/Run (1).png";
    private final String Diamond_Run1 = "1/Running/Diamond/Run (2).png";
    private final String Diamond_Run2 = "1/Running/Diamond/Run (3).png";
    private final String Diamond_Run3 = "1/Running/Diamond/Run (4).png";
    private final Texture Diamond_Run0_tex = new Texture(Diamond_Run0);
    private final Texture Diamond_Run1_tex = new Texture(Diamond_Run1);
    private final Texture Diamond_Run2_tex = new Texture(Diamond_Run2);
    private final Texture Diamond_Run3_tex = new Texture(Diamond_Run3);
    private final Animation<Texture> DiamondRun_frames = new Animation<>(0.1f, Diamond_Run0_tex, Diamond_Run1_tex, Diamond_Run2_tex, Diamond_Run3_tex);

    private final String Scarlett_idle0 = "1/Scarlett (1).png";
    private final String Scarlett_idle1 = "1/Scarlett (2).png";
    private final String Scarlett_idle2 = "1/Scarlett (3).png";
    private final String Scarlett_idle3 = "1/Scarlett (4).png";
    private final String Scarlett_idle4 = "1/Scarlett (5).png";
    private final String Scarlett_idle5 = "1/Scarlett (6).png";
    private final Texture Scarlett_idle0_tex = new Texture(Scarlett_idle0);
    private final Texture Scarlett_idle1_tex = new Texture(Scarlett_idle1);
    private final Texture Scarlett_idle2_tex = new Texture(Scarlett_idle2);
    private final Texture Scarlett_idle3_tex = new Texture(Scarlett_idle3);
    private final Texture Scarlett_idle4_tex = new Texture(Scarlett_idle4);
    private final Texture Scarlett_idle5_tex = new Texture(Scarlett_idle5);
    private final Animation<Texture> Scarlett_idle_frames = new Animation<>(0.1f, Scarlett_idle0_tex, Scarlett_idle1_tex, Scarlett_idle2_tex, Scarlett_idle3_tex, Scarlett_idle4_tex, Scarlett_idle5_tex);
    private final String Scarlett_Run0 = "1/Running/Scarlett/Run (1).png";
    private final String Scarlett_Run1 = "1/Running/Scarlett/Run (2).png";
    private final String Scarlett_Run2 = "1/Running/Scarlett/Run (3).png";
    private final String Scarlett_Run3 = "1/Running/Scarlett/Run (4).png";
    private final Texture Scarlett_Run0_tex = new Texture(Scarlett_Run0);
    private final Texture Scarlett_Run1_tex = new Texture(Scarlett_Run1);
    private final Texture Scarlett_Run2_tex = new Texture(Scarlett_Run2);
    private final Texture Scarlett_Run3_tex = new Texture(Scarlett_Run3);
    private final Animation<Texture> ScarlettRun_frames = new Animation<>(0.1f, Scarlett_Run0_tex, Scarlett_Run1_tex, Scarlett_Run2_tex, Scarlett_Run3_tex);

    private final String Lilith_idle0 = "1/Lilith (1).png";
    private final String Lilith_idle1 = "1/Lilith (2).png";
    private final String Lilith_idle2 = "1/Lilith (3).png";
    private final String Lilith_idle3 = "1/Lilith (4).png";
    private final String Lilith_idle4 = "1/Lilith (5).png";
    private final String Lilith_idle5 = "1/Lilith (6).png";
    private final Texture Lilith_idle0_tex = new Texture(Lilith_idle0);
    private final Texture Lilith_idle1_tex = new Texture(Lilith_idle1);
    private final Texture Lilith_idle2_tex = new Texture(Lilith_idle2);
    private final Texture Lilith_idle3_tex = new Texture(Lilith_idle3);
    private final Texture Lilith_idle4_tex = new Texture(Lilith_idle4);
    private final Texture Lilith_idle5_tex = new Texture(Lilith_idle5);
    private final Animation<Texture> Lilith_idle_frames = new Animation<>(0.1f, Lilith_idle0_tex, Lilith_idle1_tex, Lilith_idle2_tex, Lilith_idle3_tex, Lilith_idle4_tex, Lilith_idle5_tex);
    private final String Lilith_Run0 = "1/Running/Lilith/Run (1).png";
    private final String Lilith_Run1 = "1/Running/Lilith/Run (2).png";
    private final String Lilith_Run2 = "1/Running/Lilith/Run (3).png";
    private final String Lilith_Run3 = "1/Running/Lilith/Run (4).png";
    private final Texture Lilith_Run0_tex = new Texture(Lilith_Run0);
    private final Texture Lilith_Run1_tex = new Texture(Lilith_Run1);
    private final Texture Lilith_Run2_tex = new Texture(Lilith_Run2);
    private final Texture Lilith_Run3_tex = new Texture(Lilith_Run3);
    private final Animation<Texture> LilithRun_frames = new Animation<>(0.1f, Lilith_Run0_tex, Lilith_Run1_tex, Lilith_Run2_tex, Lilith_Run3_tex);

    private final String Dasher_idle0 = "1/Dasher (1).png";
    private final String Dasher_idle1 = "1/Dasher (2).png";
    private final String Dasher_idle2 = "1/Dasher (3).png";
    private final String Dasher_idle3 = "1/Dasher (4).png";
    private final String Dasher_idle4 = "1/Dasher (5).png";
    private final String Dasher_idle5 = "1/Dasher (6).png";
    private final Texture Dasher_idle0_tex = new Texture(Dasher_idle0);
    private final Texture Dasher_idle1_tex = new Texture(Dasher_idle1);
    private final Texture Dasher_idle2_tex = new Texture(Dasher_idle2);
    private final Texture Dasher_idle3_tex = new Texture(Dasher_idle3);
    private final Texture Dasher_idle4_tex = new Texture(Dasher_idle4);
    private final Texture Dasher_idle5_tex = new Texture(Dasher_idle5);
    private final Animation<Texture> Dasher_idle_frames = new Animation<>(0.1f, Dasher_idle0_tex, Dasher_idle1_tex, Dasher_idle2_tex, Dasher_idle3_tex, Dasher_idle4_tex, Dasher_idle5_tex);
    private final String Dasher_Run0 = "1/Running/Dasher/Run (1).png";
    private final String Dasher_Run1 = "1/Running/Dasher/Run (2).png";
    private final String Dasher_Run2 = "1/Running/Dasher/Run (3).png";
    private final String Dasher_Run3 = "1/Running/Dasher/Run (4).png";
    private final Texture Dasher_Run0_tex = new Texture(Dasher_Run0);
    private final Texture Dasher_Run1_tex = new Texture(Dasher_Run1);
    private final Texture Dasher_Run2_tex = new Texture(Dasher_Run2);
    private final Texture Dasher_Run3_tex = new Texture(Dasher_Run3);
    private final Animation<Texture> DasherRun_frames = new Animation<>(0.1f, Dasher_Run0_tex, Dasher_Run1_tex, Dasher_Run2_tex, Dasher_Run3_tex);

    private final String brainMonsterWalk_0 = "Monster/BrainMonster_0.png";
    private final String brainMonsterWalk_1 = "Monster/BrainMonster_1.png";
    private final String brainMonsterWalk_2 = "Monster/BrainMonster_2.png";
    private final String brainMonsterWalk_3 = "Monster/BrainMonster_3.png";
    private final Texture brainMonsterWalk_0_tex = new Texture(brainMonsterWalk_0);
    private final Texture brainMonsterWalk_1_tex = new Texture(brainMonsterWalk_1);
    private final Texture brainMonsterWalk_2_tex = new Texture(brainMonsterWalk_2);
    private final Texture brainMonsterWalk_3_tex = new Texture(brainMonsterWalk_3);
    private final Animation<Texture> brainMonsterWalk_frames = new Animation<>(0.2f, brainMonsterWalk_0_tex, brainMonsterWalk_1_tex, brainMonsterWalk_2_tex, brainMonsterWalk_3_tex);

    private final String brainMonsterDeath_0 = "Monster/Death/BrainMonster_0.png";
    private final String brainMonsterDeath_1 = "Monster/Death/BrainMonster_1.png";
    private final String brainMonsterDeath_2 = "Monster/Death/BrainMonster_2.png";
    private final String brainMonsterDeath_3 = "Monster/Death/BrainMonster_3.png";
    private final Texture brainMonsterDeath_0_tex = new Texture(brainMonsterDeath_0);
    private final Texture brainMonsterDeath_1_tex = new Texture(brainMonsterDeath_1);
    private final Texture brainMonsterDeath_2_tex = new Texture(brainMonsterDeath_2);
    private final Texture brainMonsterDeath_3_tex = new Texture(brainMonsterDeath_3);
    private final Animation<Texture> brainMonsterDeath_frames = new Animation<>(0.1f, brainMonsterDeath_0_tex, brainMonsterDeath_1_tex, brainMonsterDeath_2_tex, brainMonsterDeath_3_tex);


    private final String EyeBatWalk_0 = "Monster/T_EyeBat_0.png";
    private final String EyeBatWalk_1 = "Monster/T_EyeBat_1.png";
    private final String EyeBatWalk_2 = "Monster/T_EyeBat_2.png";
    private final String EyeBatWalk_3 = "Monster/T_EyeBat_3.png";
    private final Texture EyeBatWalk_0_tex = new Texture(EyeBatWalk_0);
    private final Texture EyeBatWalk_1_tex = new Texture(EyeBatWalk_1);
    private final Texture EyeBatWalk_2_tex = new Texture(EyeBatWalk_2);
    private final Texture EyeBatWalk_3_tex = new Texture(EyeBatWalk_3);
    private final Animation<Texture> EyeBatWalk_frames = new Animation<>(0.1f, EyeBatWalk_0_tex, EyeBatWalk_1_tex, EyeBatWalk_2_tex, EyeBatWalk_3_tex);

    private final String EyeBatDeath_0 = "Monster/Death/T_EyeBat_0.png";
    private final String EyeBatDeath_1 = "Monster/Death/T_EyeBat_1.png";
    private final String EyeBatDeath_2 = "Monster/Death/T_EyeBat_2.png";
    private final String EyeBatDeath_3 = "Monster/Death/T_EyeBat_3.png";
    private final Texture EyeBatDeath_0_tex = new Texture(EyeBatDeath_0);
    private final Texture EyeBatDeath_1_tex = new Texture(EyeBatDeath_1);
    private final Texture EyeBatDeath_2_tex = new Texture(EyeBatDeath_2);
    private final Texture EyeBatDeath_3_tex = new Texture(EyeBatDeath_3);
    private final Animation<Texture> EyeBatDeath_frames = new Animation<>(0.2f, EyeBatDeath_0_tex, EyeBatDeath_1_tex, EyeBatDeath_2_tex, EyeBatDeath_3_tex);


    private final String Tree_0 = "Monster/T_TreeMonster_0.png";
    private final String Tree_1 = "Monster/T_TreeMonster_1.png";
    private final String Tree_2 = "Monster/T_TreeMonster_2.png";
    private final String Tree_3 = "Monster/T_TreeMonster_3.png";
    private final String Tree_4 = "Monster/T_TreeMonster_4.png";
    private final Texture Tree_0_tex = new Texture(Tree_0);
    private final Texture Tree_1_tex = new Texture(Tree_1);
    private final Texture Tree_2_tex = new Texture(Tree_2);
    private final Texture Tree_3_tex = new Texture(Tree_3);
    private final Texture Tree_4_tex = new Texture(Tree_4);
    private final Animation<Texture> Tree_frames = new Animation<>(0.7f, Tree_0_tex, Tree_1_tex, Tree_2_tex, Tree_3_tex,Tree_4_tex);

    private final String ElderWalk_0 = "Monster/Elder/T_HasturBoss_0.png";
    private final String ElderWalk_1 = "Monster/Elder/T_HasturBoss_1.png";
    private final String ElderWalk_2 = "Monster/Elder/T_HasturBoss_2.png";
    private final String ElderWalk_3 = "Monster/Elder/T_HasturBoss_3.png";
    private final String ElderWalk_4 = "Monster/Elder/T_HasturBoss_4.png";
    private final String ElderWalk_5 = "Monster/Elder/T_HasturBoss_5.png";
    private final Texture ElderWalk_0_tex = new Texture(ElderWalk_0);
    private final Texture ElderWalk_1_tex = new Texture(ElderWalk_1);
    private final Texture ElderWalk_2_tex = new Texture(ElderWalk_2);
    private final Texture ElderWalk_3_tex = new Texture(ElderWalk_3);
    private final Texture ElderWalk_4_tex = new Texture(ElderWalk_4);
    private final Texture ElderWalk_5_tex = new Texture(ElderWalk_5);
    private final Animation<Texture> ElderWalk_frames = new Animation<>(0.2f, ElderWalk_0_tex, ElderWalk_1_tex, ElderWalk_2_tex, ElderWalk_3_tex ,ElderWalk_4_tex ,ElderWalk_5_tex);

    private final String ElderDash_0 = "Monster/Elder/T_HasturBossWindup_0.png";
    private final String ElderDash_1 = "Monster/Elder/T_HasturBossWindup_1.png";
    private final String ElderDash_2 = "Monster/Elder/T_HasturBossWindup_0.png";
    private final String ElderDash_3 = "Monster/Elder/T_HasturBossWindup_1.png";
    private final String ElderDash_4 = "Monster/Elder/T_HasturBossWindup_0.png";
    private final String ElderDash_5 = "Monster/Elder/T_HasturBossWindup_1.png";
    private final Texture ElderDash_0_tex = new Texture(ElderDash_0);
    private final Texture ElderDash_1_tex = new Texture(ElderDash_1);
    private final Texture ElderDash_2_tex = new Texture(ElderDash_2);
    private final Texture ElderDash_3_tex = new Texture(ElderDash_3);
    private final Texture ElderDash_4_tex = new Texture(ElderDash_4);
    private final Texture ElderDash_5_tex = new Texture(ElderDash_5);
    private final Animation<Texture> ElderDash_frames = new Animation<>(0.2f, ElderDash_0_tex, ElderDash_1_tex, ElderDash_2_tex, ElderDash_3_tex ,ElderDash_4_tex ,ElderDash_5_tex);


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

    private final String HurtAnimation_0 = "1/Hurt (1).png";
    private final String HurtAnimation_1 = "1/Hurt (2).png";
    private final String HurtAnimation_2 = "1/Hurt (3).png";
    private final String HurtAnimation_3 = "1/Hurt (4).png";
    private final String HurtAnimation_4 = "1/Hurt (5).png";
    private final String HurtAnimation_5 = "1/Hurt (6).png";
    private final Texture HurtAnimation0_tex = new Texture(HurtAnimation_0);
    private final Texture HurtAnimation1_tex = new Texture(HurtAnimation_1);
    private final Texture HurtAnimation2_tex = new Texture(HurtAnimation_2);
    private final Texture HurtAnimation3_tex = new Texture(HurtAnimation_3);
    private final Texture HurtAnimation4_tex = new Texture(HurtAnimation_4);
    private final Texture HurtAnimation5_tex = new Texture(HurtAnimation_5);
    private final Animation<Texture> HurtAnimation_frames = new Animation<>(0.1f, HurtAnimation0_tex, HurtAnimation1_tex, HurtAnimation2_tex, HurtAnimation3_tex, HurtAnimation4_tex, HurtAnimation5_tex);
    public Animation<Texture> getHurtAnimation_frames() {
        return HurtAnimation_frames;
    }

    private final String tree = "monsters/T_TreeMonster_2.png";
    private final Texture treeTexture = new Texture(tree);
    private final String tentacleMonster = "monsters/BrainMonster_3.png";
    private final Texture tentacleMonsterTexture = new Texture(tree);
    private final String eyebat = "monsters/EyeMonster_0.png";
    private final Texture eyebatTexture = new Texture(tree);
    private final String monsterBullet = "EyeMonsterProjecitle.png";
    private final Texture MonsterBulletTexture = new Texture(monsterBullet);
    private final String elder = "monsters/T_HasturBoss_0.png";
    private final Texture elderTexture = new Texture(elder);
    private final String xp = "T_ChargeUp_1.png";
    private final Texture xpTexture = new Texture(xp);


    private final String smg = "smg/SMGStill.png";
    private final Texture smgTexture = new Texture(smg);
    private final Sprite smgSprite = new Sprite(smgTexture);
    private final String smgs = "libgdx.png";
    private final Texture smgsTexture = new Texture(smgs);
    private final Sprite smgsSprite = new Sprite(smgsTexture);

    public Sprite getSmgSprite() {
        return smgSprite;
    }

    public String getXp() {
        return xp;
    }

    public Texture getXpTexture() {
        return xpTexture;
    }

    public Animation<Texture> getEyeBatDeath_frames() {
        return EyeBatDeath_frames;
    }

    public Sprite getSmgsSprite() {
        return smgsSprite;
    }

    private final String bullet = "bullet.png";

    public Animation<Texture> getBrainMonsterDeath_frames() {
        return brainMonsterDeath_frames;
    }

    public Animation<Texture> getElderDash_frames() {
        return ElderDash_frames;
    }

    public Animation<Texture> getShana_idle_frames() {
        return Shana_idle_frames;
    }

    public Animation<Texture> getBrainMonsterWalk_frames() {
        return brainMonsterWalk_frames;
    }

    public Animation<Texture> getEyeBatWalk_frames() {
        return EyeBatWalk_frames;
    }

    public Animation<Texture> getTree_frames() {
        return Tree_frames;
    }

    public Animation<Texture> getElderWalk_frames() {
        return ElderWalk_frames;
    }

    public Animation<Texture> getShanaRun_frames() {
        return ShanaRun_frames;
    }

    public Animation<Texture> getDiamondRun_frames() {
        return DiamondRun_frames;
    }

    public Animation<Texture> getDiamond_idle_frames() {
        return Diamond_idle_frames;
    }

    public Animation<Texture> getScarlett_idle_frames() {
        return Scarlett_idle_frames;
    }

    public Animation<Texture> getScarlettRun_frames() {
        return ScarlettRun_frames;
    }

    public Animation<Texture> getLilith_idle_frames() {
        return Lilith_idle_frames;
    }

    public Animation<Texture> getLilithRun_frames() {
        return LilithRun_frames;
    }

    public Animation<Texture> getDasher_idle_frames() {
        return Dasher_idle_frames;
    }

    public Animation<Texture> getDasherRun_frames() {
        return DasherRun_frames;
    }

    public Animation<Texture> getCharacter2_idle_animation() {
        return character2_idle_frames;
    }


    public String getCharacter1_idle0(){
        return character2_idle0;
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

    public Texture getElderTexture() {
        return elderTexture;
    }

    public String getElder() {
        return elder;
    }
















    public String getShana_idle0() {
        return Shana_idle0;
    }

    public String getShana_idle1() {
        return Shana_idle1;
    }

    public String getShana_idle2() {
        return Shana_idle2;
    }

    public String getShana_idle3() {
        return Shana_idle3;
    }

    public String getShana_idle4() {
        return Shana_idle4;
    }

    public String getShana_idle5() {
        return Shana_idle5;
    }

    public Texture getShana_idle0_tex() {
        return Shana_idle0_tex;
    }

    public Texture getShana_idle1_tex() {
        return Shana_idle1_tex;
    }

    public Texture getShana_idle2_tex() {
        return Shana_idle2_tex;
    }

    public Texture getShana_idle3_tex() {
        return Shana_idle3_tex;
    }

    public Texture getShana_idle4_tex() {
        return Shana_idle4_tex;
    }

    public Texture getShana_idle5_tex() {
        return Shana_idle5_tex;
    }

    public String getShana_Run0() {
        return Shana_Run0;
    }

    public String getShana_Run1() {
        return Shana_Run1;
    }

    public String getShana_Run2() {
        return Shana_Run2;
    }

    public String getShana_Run3() {
        return Shana_Run3;
    }

    public Texture getShana_Run0_tex() {
        return Shana_Run0_tex;
    }

    public Texture getShana_Run1_tex() {
        return Shana_Run1_tex;
    }

    public Texture getShana_Run2_tex() {
        return Shana_Run2_tex;
    }

    public Texture getShana_Run3_tex() {
        return Shana_Run3_tex;
    }

    public String getDiamond_idle0() {
        return Diamond_idle0;
    }

    public String getDiamond_idle1() {
        return Diamond_idle1;
    }

    public String getDiamond_idle2() {
        return Diamond_idle2;
    }

    public String getDiamond_idle3() {
        return Diamond_idle3;
    }

    public String getDiamond_idle4() {
        return Diamond_idle4;
    }

    public String getDiamond_idle5() {
        return Diamond_idle5;
    }

    public Texture getDiamond_idle0_tex() {
        return Diamond_idle0_tex;
    }

    public Texture getDiamond_idle1_tex() {
        return Diamond_idle1_tex;
    }

    public Texture getDiamond_idle2_tex() {
        return Diamond_idle2_tex;
    }

    public Texture getDiamond_idle3_tex() {
        return Diamond_idle3_tex;
    }

    public Texture getDiamond_idle4_tex() {
        return Diamond_idle4_tex;
    }

    public Texture getDiamond_idle5_tex() {
        return Diamond_idle5_tex;
    }

    public String getDiamond_Run0() {
        return Diamond_Run0;
    }

    public String getDiamond_Run1() {
        return Diamond_Run1;
    }

    public String getDiamond_Run2() {
        return Diamond_Run2;
    }

    public String getDiamond_Run3() {
        return Diamond_Run3;
    }

    public Texture getDiamond_Run0_tex() {
        return Diamond_Run0_tex;
    }

    public Texture getDiamond_Run1_tex() {
        return Diamond_Run1_tex;
    }

    public Texture getDiamond_Run2_tex() {
        return Diamond_Run2_tex;
    }

    public Texture getDiamond_Run3_tex() {
        return Diamond_Run3_tex;
    }

    public String getScarlett_idle0() {
        return Scarlett_idle0;
    }

    public String getScarlett_idle1() {
        return Scarlett_idle1;
    }

    public String getScarlett_idle2() {
        return Scarlett_idle2;
    }

    public String getScarlett_idle3() {
        return Scarlett_idle3;
    }

    public String getScarlett_idle4() {
        return Scarlett_idle4;
    }

    public String getScarlett_idle5() {
        return Scarlett_idle5;
    }

    public Texture getScarlett_idle0_tex() {
        return Scarlett_idle0_tex;
    }

    public Texture getScarlett_idle1_tex() {
        return Scarlett_idle1_tex;
    }

    public Texture getScarlett_idle2_tex() {
        return Scarlett_idle2_tex;
    }

    public Texture getScarlett_idle3_tex() {
        return Scarlett_idle3_tex;
    }

    public Texture getScarlett_idle4_tex() {
        return Scarlett_idle4_tex;
    }

    public Texture getScarlett_idle5_tex() {
        return Scarlett_idle5_tex;
    }

    public String getScarlett_Run0() {
        return Scarlett_Run0;
    }

    public String getScarlett_Run1() {
        return Scarlett_Run1;
    }

    public String getScarlett_Run2() {
        return Scarlett_Run2;
    }

    public String getScarlett_Run3() {
        return Scarlett_Run3;
    }

    public Texture getScarlett_Run0_tex() {
        return Scarlett_Run0_tex;
    }

    public Texture getScarlett_Run1_tex() {
        return Scarlett_Run1_tex;
    }

    public Texture getScarlett_Run2_tex() {
        return Scarlett_Run2_tex;
    }

    public Texture getScarlett_Run3_tex() {
        return Scarlett_Run3_tex;
    }

    public String getLilith_idle0() {
        return Lilith_idle0;
    }

    public String getLilith_idle1() {
        return Lilith_idle1;
    }

    public String getLilith_idle2() {
        return Lilith_idle2;
    }

    public String getLilith_idle3() {
        return Lilith_idle3;
    }

    public String getLilith_idle4() {
        return Lilith_idle4;
    }

    public String getLilith_idle5() {
        return Lilith_idle5;
    }

    public Texture getLilith_idle0_tex() {
        return Lilith_idle0_tex;
    }

    public Texture getLilith_idle1_tex() {
        return Lilith_idle1_tex;
    }

    public Texture getLilith_idle2_tex() {
        return Lilith_idle2_tex;
    }

    public Texture getLilith_idle3_tex() {
        return Lilith_idle3_tex;
    }

    public Texture getLilith_idle4_tex() {
        return Lilith_idle4_tex;
    }

    public Texture getLilith_idle5_tex() {
        return Lilith_idle5_tex;
    }

    public String getLilith_Run0() {
        return Lilith_Run0;
    }

    public String getLilith_Run1() {
        return Lilith_Run1;
    }

    public String getLilith_Run2() {
        return Lilith_Run2;
    }

    public String getLilith_Run3() {
        return Lilith_Run3;
    }

    public Texture getLilith_Run0_tex() {
        return Lilith_Run0_tex;
    }

    public Texture getLilith_Run1_tex() {
        return Lilith_Run1_tex;
    }

    public Texture getLilith_Run2_tex() {
        return Lilith_Run2_tex;
    }

    public Texture getLilith_Run3_tex() {
        return Lilith_Run3_tex;
    }

    public String getDasher_idle0() {
        return Dasher_idle0;
    }

    public String getDasher_idle1() {
        return Dasher_idle1;
    }

    public String getDasher_idle2() {
        return Dasher_idle2;
    }

    public String getDasher_idle3() {
        return Dasher_idle3;
    }

    public String getDasher_idle4() {
        return Dasher_idle4;
    }

    public String getDasher_idle5() {
        return Dasher_idle5;
    }

    public Texture getDasher_idle0_tex() {
        return Dasher_idle0_tex;
    }

    public Texture getDasher_idle1_tex() {
        return Dasher_idle1_tex;
    }

    public Texture getDasher_idle2_tex() {
        return Dasher_idle2_tex;
    }

    public Texture getDasher_idle3_tex() {
        return Dasher_idle3_tex;
    }

    public Texture getDasher_idle4_tex() {
        return Dasher_idle4_tex;
    }

    public Texture getDasher_idle5_tex() {
        return Dasher_idle5_tex;
    }

    public String getDasher_Run0() {
        return Dasher_Run0;
    }

    public String getDasher_Run1() {
        return Dasher_Run1;
    }

    public String getDasher_Run2() {
        return Dasher_Run2;
    }

    public String getDasher_Run3() {
        return Dasher_Run3;
    }

    public Texture getDasher_Run0_tex() {
        return Dasher_Run0_tex;
    }

    public Texture getDasher_Run1_tex() {
        return Dasher_Run1_tex;
    }

    public Texture getDasher_Run2_tex() {
        return Dasher_Run2_tex;
    }

    public Texture getDasher_Run3_tex() {
        return Dasher_Run3_tex;
    }

    public String getBrainMonsterWalk_0() {
        return brainMonsterWalk_0;
    }

    public String getBrainMonsterWalk_1() {
        return brainMonsterWalk_1;
    }

    public String getBrainMonsterWalk_2() {
        return brainMonsterWalk_2;
    }

    public String getBrainMonsterWalk_3() {
        return brainMonsterWalk_3;
    }

    public Texture getBrainMonsterWalk_0_tex() {
        return brainMonsterWalk_0_tex;
    }

    public Texture getBrainMonsterWalk_1_tex() {
        return brainMonsterWalk_1_tex;
    }

    public Texture getBrainMonsterWalk_2_tex() {
        return brainMonsterWalk_2_tex;
    }

    public Texture getBrainMonsterWalk_3_tex() {
        return brainMonsterWalk_3_tex;
    }

    public String getEyeBatWalk_0() {
        return EyeBatWalk_0;
    }

    public String getEyeBatWalk_1() {
        return EyeBatWalk_1;
    }

    public String getEyeBatWalk_2() {
        return EyeBatWalk_2;
    }

    public String getEyeBatWalk_3() {
        return EyeBatWalk_3;
    }

    public Texture getEyeBatWalk_0_tex() {
        return EyeBatWalk_0_tex;
    }

    public Texture getEyeBatWalk_1_tex() {
        return EyeBatWalk_1_tex;
    }

    public Texture getEyeBatWalk_2_tex() {
        return EyeBatWalk_2_tex;
    }

    public Texture getEyeBatWalk_3_tex() {
        return EyeBatWalk_3_tex;
    }

    public String getTree_0() {
        return Tree_0;
    }

    public String getTree_1() {
        return Tree_1;
    }

    public String getTree_2() {
        return Tree_2;
    }

    public String getTree_3() {
        return Tree_3;
    }

    public String getTree_4() {
        return Tree_4;
    }

    public Texture getTree_0_tex() {
        return Tree_0_tex;
    }

    public Texture getTree_1_tex() {
        return Tree_1_tex;
    }

    public Texture getTree_2_tex() {
        return Tree_2_tex;
    }

    public Texture getTree_3_tex() {
        return Tree_3_tex;
    }

    public Texture getTree_4_tex() {
        return Tree_4_tex;
    }

    public String getCharacter2_idle0() {
        return character2_idle0;
    }

    public String getCharacter2_idle1() {
        return character2_idle1;
    }

    public String getCharacter2_idle2() {
        return character2_idle2;
    }

    public String getCharacter2_idle3() {
        return character2_idle3;
    }

    public String getCharacter2_idle4() {
        return character2_idle4;
    }

    public String getCharacter2_idle5() {
        return character2_idle5;
    }

    public Texture getCharacter2_idle0_tex() {
        return character2_idle0_tex;
    }

    public Texture getCharacter2_idle1_tex() {
        return character2_idle1_tex;
    }

    public Texture getCharacter2_idle2_tex() {
        return character2_idle2_tex;
    }

    public Texture getCharacter2_idle3_tex() {
        return character2_idle3_tex;
    }

    public Texture getCharacter2_idle4_tex() {
        return character2_idle4_tex;
    }

    public Texture getCharacter2_idle5_tex() {
        return character2_idle5_tex;
    }

    public Animation<Texture> getCharacter2_idle_frames() {
        return character2_idle_frames;
    }

    public String getSmgs() {
        return smgs;
    }

    public Texture getSmgsTexture() {
        return smgsTexture;
    }
}
