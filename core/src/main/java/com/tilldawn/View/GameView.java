package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    boolean countSpeedy = false;
    boolean countDamager = false;


    private Label timeRemaining;
    private Label remainingAmmo;
    private Label killCount;
    private Label levelCount;


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

        // Set shader and draw UI
        Batch batch = stage.getBatch();
        batch.setShader(Main.isGrayscale() ? Main.getGrayscaleShader() : null);
        stage.act(delta);
        stage.draw();


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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(App.getWeaponOfChoice().getRemainingAmmo() > 0) {
            App.getWeaponOfChoice().setInGameSprite(AssetManager.getAssetManager().getSmgSprite());
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

    public void inputHandler(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
            previousGameInput = Gdx.input.getInputProcessor();
            showAbilitySelection();
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
        } else if(cheatButton.isPressed()){
            //todo Show cheat code dialog or label
        } else if (abilitiesButton.isPressed()){
            //todo Show Ability dialog or label
            for (Ability ability : controller.getPlayerController().getPlayer().getAbilities()) {
                System.out.println(ability.getName());
            }
            System.out.println("________________________");
        } else if (giveUpButton.isPressed()){
            //todo ; lose screen
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
        remainingAmmo.setPosition(Gdx.graphics.getWidth() - remainingAmmo.getWidth() - 50, Gdx.graphics.getHeight() - remainingAmmo.getHeight() - 40);
        killCount = new Label("Kills : " + String.valueOf(App.getCurrentUser().getKills()) , labelStyle);

        levelCount = new Label("Level : " + String.valueOf(controller.getPlayerController().getPlayer().getLevel()), labelStyle);

        killCount.setPosition(Gdx.graphics.getWidth() - killCount.getWidth() - 50, Gdx.graphics.getHeight() - killCount.getHeight() - 40);
        remainingAmmo.setPosition(timeRemaining.getX(), timeRemaining.getY() - timeRemaining.getHeight() - 40);
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
