package com.tilldawn.View;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.Ability;
import com.tilldawn.Model.App;
import com.tilldawn.Model.AssetManager;
import com.tilldawn.View.Components.FontUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.tilldawn.Main.getMain;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private GameController controller;
    private TextButton resumeButton;
    private TextButton cheatButton;
    private TextButton abilitiesButton ;
    private TextButton giveUpButton;
    private TextButton toggleGrayscaleButton;
    private TextButton saveQuitButton;
    private boolean isPaused = false;
    private Window pauseWindow;

    private InputProcessor previousGameInput;

    float reloadCounter = 0;
    float speedyTimer = 0;
    float damagerTimer = 0;
    float deathTimer = 0;
    boolean countdeath = false;
    boolean countSpeedy = false;
    boolean countDamager = false;


    private Label timeRemaining;
    private Label remainingAmmo;
    private Label killCount;
    private Label levelCount;

    private boolean showLevelUpWindow = false;

    public GameView(GameController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);

    }

    @Override
    public void show() {
        pauseMenuBuilder();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);


        Main.getBatch().begin();
        inputHandler();
        controller.updateGame();
        Main.getBatch().end();


        if (showAbilityMenu && abilityStage != null) {
            abilityStage.act(Gdx.graphics.getDeltaTime());
            abilityStage.draw();
            return; // Skip rest of game rendering
        }

        if(countSpeedy){
            speedyTimer();
        }
        if(countDamager){
            damagerTimer();
        }
        if(countdeath){
            deathTimer();
        }

        if(controller.getPlayerController().getPlayer().getDamage() >= controller.getPlayerController().getPlayer().getHealth()){
            countdeath = true;
        }
        if(controller.getRemaining() <= 0){
            App.getCurrentUser().setTimeSurvived(App.getTimeOfChoice() - controller.getRemaining());
            App.getCurrentUser().setHighScore(App.getTimeOfChoice() - controller.getRemaining() * App.getCurrentUser().getKills());
            getMain().getScreen().dispose();
            getMain().setScreen(new WinGameMenu(controller,AssetManager.getAssetManager().getSkin()));
        }

        levelUp();
        // Set shader and draw UI
        Batch batch = stage.getBatch();
        batch.setShader(Main.isGrayscale() ? Main.getGrayscaleShader() : null);
        stage.act(delta);
        stage.draw();


    }

    public void deathTimer() {
        deathTimer += Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
            controller.getPlayerController().getPlayer().setDamage(-(controller.getPlayerController().getPlayer().getDamage()));
            deathTimer = 0;
            countdeath = false;
        }
        if(deathTimer > 3) {
            deathTimer = 0;
            App.getCurrentUser().setTimeSurvived(App.getTimeOfChoice() - controller.getRemaining());
            App.getCurrentUser().setHighScore(App.getTimeOfChoice() - controller.getRemaining() * App.getCurrentUser().getKills());
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new LoseGameMenu(controller,AssetManager.getAssetManager().getSkin()));
            countdeath = false;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
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
        currentMusic.setLooping(false);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(App.getWeaponOfChoice().getRemainingAmmo() > 0) {
            App.getWeaponOfChoice().setInGameSprite(AssetManager.getAssetManager().getSmgSprite());
            playSFX("SFX/gunshot-352466.mp3");
            controller.getWeaponController().handleWeaponShoot(screenX, screenY);
            App.getWeaponOfChoice().setRemainingAmmo(App.getWeaponOfChoice().getRemainingAmmo() - 1);
        } else {
            if(App.isAutoReload()){
                controller.getPlayerController().setReload(true);
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public Label getTimeRemaining() {
        return timeRemaining;
    }

    public Label getRemainingAmmo() {
        return remainingAmmo;
    }

    public Label getKillCount() {
        return killCount;
    }

    public Label getLevelCount() {
        return levelCount;
    }

    public void setLevelCount(Label levelCount) {
        this.levelCount = levelCount;
    }

    public void setKillCount(Label killCount) {
        this.killCount = killCount;
    }

    public void setRemainingAmmo(Label remainingAmmo) {
        this.remainingAmmo = remainingAmmo;
    }



    private boolean showAbilityMenu = false;
    private Stage abilityStage;

    public void showAbilitySelection() {
        isPaused = true;
        showAbilityMenu = true;
        abilityStage = new Stage();
        Gdx.input.setInputProcessor(abilityStage);
        Skin skin = AssetManager.getAssetManager().getSkin();
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        // Replace this with your own method
        List<Ability> abilities = pickRandomAbilities(3);
        for (Ability ability : abilities) {
            TextButton btn = new TextButton(ability.getName() + "\n" + ability.getDescription(), skin);
            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    applyAbility(ability);  // Your logic to apply the ability
                    showAbilityMenu = false;
                    abilityStage.dispose();
                    isPaused = false;
                    Gdx.input.setInputProcessor(previousGameInput); // restore your game input
                }
            });
            table.add(btn).width(300).height(100).pad(20);
            table.row();
        }
        abilityStage.addActor(table);
    }
    private final List<Ability> allAbilities = Arrays.asList(
        new Ability("Vitality", "Increase max Health by 1"),
        new Ability("Damager", " 10 seconds 25% Damage Increase"),
        new Ability("ProCrease", "Increase weapon Projectile by 1 "),
        new Ability("AmmoCrease", "Increase max Ammo by 5"),
        new Ability("Speedy", "Doubles Speed for 10 seconds")
    );

    private List<Ability> pickRandomAbilities(int count) {
        List<Ability> shuffled = new ArrayList<>(allAbilities);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, count);
    }
    private void applyAbility(Ability ability) {
        switch (ability.getName()) {
            case "Vitality":
                controller.getPlayerController().getPlayer().addAbility(allAbilities.get(0));
                controller.getPlayerController().getPlayer().setHealth(controller.getPlayerController().getPlayer().getHealth() + 1);
                break;
            case "Damager":
                controller.getPlayerController().getPlayer().addAbility(allAbilities.get(1));
                controller.getWeaponController().getWeapon().setDamage(controller.getWeaponController().getWeapon().getDamage() + (controller.getWeaponController().getWeapon().getDamage()/4));
                break;
            case "ProCrease":
                controller.getPlayerController().getPlayer().addAbility(allAbilities.get(2));
                break;
            case "AmmoCrease":
                controller.getPlayerController().getPlayer().addAbility(allAbilities.get(3));
                controller.getWeaponController().getWeapon().setAmmo(controller.getWeaponController().getWeapon().getAmmo() + 5);
                break;
            case "Speedy":
                controller.getPlayerController().getPlayer().addAbility(allAbilities.get(4));
                controller.getPlayerController().getPlayer().setSpeed(controller.getPlayerController().getPlayer().getSpeed() *2);
                countSpeedy = true;
                break;
        }
    }

    private void speedyTimer() {
        speedyTimer += Gdx.graphics.getDeltaTime();
        if(speedyTimer > 10) {
            speedyTimer = 0;
            controller.getPlayerController().getPlayer().setSpeed(controller.getPlayerController().getPlayer().getSpeed() /2);
            countSpeedy = false;
        }
    }
    private void damagerTimer() {
        damagerTimer += Gdx.graphics.getDeltaTime();
        if(speedyTimer > 10) {
            damagerTimer = 0;
            controller.getWeaponController().getWeapon().setDamage(controller.getWeaponController().getWeapon().getDamage() - (controller.getWeaponController().getWeapon().getDamage()/5));
            countDamager = false;
        }
    }


    private Array<String> cheatCodes = new Array<>(new String[] {
        "O : Pass 1 minute",
        "L : Level up",
        "I : Adds Health",
        "P : Goes to Boss Fight",
        "M : Revives the Player after Death",
        "N : Infinite Ammo",
        "Space : Auto Aim"
    });

    private Array<String> abilities = new Array<>();
    public void creatAbilityList(){
        abilities.clear();
        for(Ability ability : controller.getPlayerController().getPlayer().getAbilities()){
            abilities.add(ability.getName());
        }
    }

    private void showListDialog(String title, Array<String> items) {
        Skin skin = AssetManager.getAssetManager().getSkin();

        Dialog dialog = new Dialog(title, skin) {
            @Override
            protected void result(Object object) {
                if (object instanceof Boolean && (Boolean)object) {
                    this.hide();
                }
            }
        };


        // Create a List widget and set the items
        com.badlogic.gdx.scenes.scene2d.ui.List<String> list = new com.badlogic.gdx.scenes.scene2d.ui.List<>(skin);
        list.setItems(items.toArray(String.class));

        // Wrap it in a ScrollPane for scrolling if too many items
        ScrollPane scrollPane = new ScrollPane(list, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false); // Allow vertical scrolling only

        dialog.getContentTable().add(scrollPane).width(300).height(200);
        dialog.button("Close", true); // Add a close button
        dialog.key(Input.Keys.ESCAPE, true); // ESC closes dialog

        dialog.show(stage); // Show on your stage

    }


    public void inputHandler(){

        if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
            previousGameInput = Gdx.input.getInputProcessor();
            controller.getPlayerController().getPlayer().setXp(controller.getPlayerController().getPlayer().getXp() + (controller.getPlayerController().getPlayer().getLevel()*20));
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.O)){
            controller.setRemaining(controller.getRemaining() - 60);
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.L)){
            controller.getPlayerController().getPlayer().setLevel(controller.getPlayerController().getPlayer().getLevel() + 1);
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            controller.setRemaining(App.getTimeOfChoice()/2);
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.N)){
            controller.getWeaponController().getWeapon().setAmmo(Integer.MAX_VALUE);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            isPaused = !isPaused;
            pauseWindow.setVisible(isPaused);
            if(isPaused){
                Gdx.input.setInputProcessor(stage);
            } else {
                Gdx.input.setInputProcessor(this);
            }
        } else if(resumeButton.isChecked()){
            isPaused = !isPaused;
            pauseWindow.setVisible(isPaused);
            Gdx.input.setInputProcessor(this);
            resumeButton.setChecked(false);
        } else if(cheatButton.isChecked()){
            showListDialog("Cheat Codes", cheatCodes);
            cheatButton.setChecked(false);
        } else if (abilitiesButton.isChecked()){
            creatAbilityList();
            showListDialog("Abilities", abilities);
            abilitiesButton.setChecked(false);


        } else if (giveUpButton.isPressed()){
            App.getCurrentUser().setTimeSurvived(App.getTimeOfChoice() - controller.getRemaining());
            App.getCurrentUser().setHighScore(App.getTimeOfChoice() - controller.getRemaining() * App.getCurrentUser().getKills());
            getMain().getScreen().dispose();
            getMain().setScreen(new LoseGameMenu(controller,AssetManager.getAssetManager().getSkin()));
        } else if (toggleGrayscaleButton.isPressed()){
            Main.setGrayscale(!Main.isGrayscale());
        } else if (saveQuitButton.isPressed()){
            //todo Save logic
            Gdx.app.exit();
        }
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isShowLevelUpWindow() {
        return showLevelUpWindow;
    }

    public void setShowLevelUpWindow(boolean showLevelUpWindow) {
        this.showLevelUpWindow = showLevelUpWindow;
    }
    public void levelUp(){
        if(showLevelUpWindow){
            showLevelUpWindow = false;
            previousGameInput = Gdx.input.getInputProcessor();
            showAbilitySelection();
        }
    }

    public void pauseMenuBuilder(){
        BitmapFont customFont = FontUtil.generateFont("Fonts/ChevyRay - Lantern.ttf", 32 ); // or .otf
        Label.LabelStyle labelStyle = new Label.LabelStyle(AssetManager.getAssetManager().getSkin().get(Label.LabelStyle.class));
        labelStyle.font = customFont;
        BitmapFont customFont2 = FontUtil.generateFont("Fonts/ChevyRay - Lantern.ttf", 16 ); // or .otf
        Window.WindowStyle windowStyle = new Window.WindowStyle(AssetManager.getAssetManager().getSkin().get(Window.WindowStyle.class));
        customFont2.setColor(Color.RED);
        windowStyle.titleFont = customFont2;

        timeRemaining = new Label((String.valueOf(App.getTimeOfChoice()/60) + " : " + String.valueOf(App.getTimeOfChoice()%60))
            , labelStyle);
        stage = new Stage(new ScreenViewport());
        timeRemaining.setPosition(Gdx.graphics.getWidth() - timeRemaining.getWidth() - 50, Gdx.graphics.getHeight() - timeRemaining.getHeight() - 40);
        remainingAmmo = new Label("Ammo : " + String.valueOf(App.getWeaponOfChoice().getRemainingAmmo()), labelStyle);
        remainingAmmo.setPosition(Gdx.graphics.getWidth() - remainingAmmo.getWidth() - 75, Gdx.graphics.getHeight() - remainingAmmo.getHeight() - 40);
        killCount = new Label("Kills : " + String.valueOf(App.getCurrentUser().getKills()) , labelStyle);

        levelCount = new Label("Level : " + String.valueOf(controller.getPlayerController().getPlayer().getLevel()), labelStyle);

        killCount.setPosition(Gdx.graphics.getWidth() - killCount.getWidth() - 50, Gdx.graphics.getHeight() - killCount.getHeight() - 40);
        remainingAmmo.setPosition(timeRemaining.getX() - 40, timeRemaining.getY() - timeRemaining.getHeight() - 40);
        killCount.setPosition(remainingAmmo.getX(), remainingAmmo.getY() - remainingAmmo.getHeight() - 40);
        levelCount.setPosition(killCount.getX(), killCount.getY() - killCount.getHeight() - 40);
        stage.addActor(levelCount);
        stage.addActor(remainingAmmo);
        stage.addActor(killCount);
        stage.addActor(timeRemaining);


        Gdx.input.setInputProcessor(this);
        Skin skin = AssetManager.getAssetManager().getSkin();
        pauseWindow = new Window("Paused", windowStyle);
        pauseWindow.setSize(400, 400);
        pauseWindow.setPosition(
            (Gdx.graphics.getWidth() - pauseWindow.getWidth()) / 2,
            (Gdx.graphics.getHeight() - pauseWindow.getHeight()) / 2
        );
        pauseWindow.setVisible(false);
// Resume Button
        resumeButton = new TextButton("Resume", skin);
        cheatButton = new TextButton("Cheat Codes", skin);
        abilitiesButton = new TextButton("Show Abilities", skin);
        giveUpButton = new TextButton("Give Up", skin);
        toggleGrayscaleButton = new TextButton("Toggle Grayscale", skin);
        saveQuitButton = new TextButton("Save and Quit", skin);
// Add to window
        pauseWindow.add(resumeButton).width(200).pad(10).row();
        pauseWindow.add(cheatButton).width(200).pad(10).row();
        pauseWindow.add(abilitiesButton).width(200).pad(10).row();
        pauseWindow.add(giveUpButton).width(200).pad(10).row();
        pauseWindow.add(toggleGrayscaleButton).width(200).pad(10).row();
        pauseWindow.add(saveQuitButton).width(200).pad(10).row();
        stage.addActor(pauseWindow);

    }

}
