package com.tilldawn.View.PreGameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MenuController.MainMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.App;
import com.tilldawn.Model.AssetManager;

public class MainMenu implements Screen {


    private static Stage stage;

    private MainMenuController controller;

    private TextButton settingButton;
    private TextButton profileButton;
    private TextButton TalentButton;
    private TextButton scoreBoardButton;
    private TextButton preGameButton;
    private TextButton resumeButton;
    private TextButton exitButton;
    private TextButton logoutButton;

    private Texture logo = AssetManager.getAssetManager().getLogo();
    Image image = new Image(logo);
    Image avatarIMG;

    private Label usernameLabel;
    private Label highScoreLabel;

    public MainMenu(MainMenuController controller, Skin skin) {
        this.stage = new Stage();

        this.settingButton = new TextButton("Settings", skin);
        this.profileButton = new TextButton("Profile", skin);
        this.TalentButton = new TextButton("Talents", skin);
        this.scoreBoardButton = new TextButton("Score Board", skin);
        this.preGameButton = new TextButton("Start", skin);
        this.resumeButton = new TextButton("Resume", skin);
        this.exitButton = new TextButton("Exit", skin);
        this.logoutButton = new TextButton("Logout", skin);

        this.usernameLabel = new Label(App.getCurrentUser().getUserName(), skin);
        this.highScoreLabel = new Label(String.valueOf(App.getCurrentUser().getScore()) , skin);

//        NinePatch avatar = new NinePatch(App.getCurrentUser().getAvatar() , 40, 40, 40, 40);
//        Image avatarIMG = new Image(new NinePatchDrawable(avatar));


        this.controller = controller;
        controller.setView(this);
    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        image.setHeight(700);
        image.setWidth(700);
        image.setPosition(stage.getWidth() / 2 - image.getWidth() / 2, stage.getHeight() / 2 - image.getHeight() / 2 + 200);

        avatarIMG =  new Image(App.getCurrentUser().getAvatar());
        avatarIMG.setWidth(80);
        avatarIMG.setHeight(80);
        avatarIMG.setPosition(20 , stage.getHeight() - avatarIMG.getHeight() - 20);
        stage.addActor(avatarIMG);

        usernameLabel.setPosition(110 , stage.getHeight() - 80 + 20 - usernameLabel.getHeight()/2);
        usernameLabel.setFontScale(2f);
        highScoreLabel.setPosition(120 + usernameLabel.getWidth()*2 , stage.getHeight() - 80 + 20 - highScoreLabel.getHeight()/2);
        highScoreLabel.setFontScale(2f);

        preGameButton.setWidth(200);
        preGameButton.setPosition(stage.getWidth() / 2 - preGameButton.getWidth() / 2, 320);
        stage.addActor(preGameButton);

        resumeButton.setWidth(200);
        resumeButton.setPosition(stage.getWidth() / 2 - preGameButton.getWidth() / 2, 280);

        profileButton.setWidth(200);
        profileButton.setPosition(stage.getWidth() / 2 - preGameButton.getWidth() / 2, 240);

        scoreBoardButton.setWidth(200);
        scoreBoardButton.setPosition(stage.getWidth() / 2 - preGameButton.getWidth() / 2, 200);

        TalentButton.setWidth(200);
        TalentButton.setPosition(stage.getWidth() / 2 - preGameButton.getWidth() / 2, 160);

        settingButton.setWidth(200);
        settingButton.setPosition(stage.getWidth() / 2 - preGameButton.getWidth() / 2, 120);

        logoutButton.setWidth(200);
        logoutButton.setPosition(stage.getWidth() / 2 - preGameButton.getWidth() / 2, 80);

        exitButton.setPosition(20, 20);
        stage.addActor(exitButton);

        stage.addActor(resumeButton);
        stage.addActor(scoreBoardButton);
        stage.addActor(profileButton);
        stage.addActor(TalentButton);
        stage.addActor(settingButton);
        stage.addActor(logoutButton);


        stage.addActor(usernameLabel);
        stage.addActor(highScoreLabel);
        stage.addActor(image);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.1f, 1);
        Main.getBatch().begin();
        Main.getBatch().end();

        Batch batch = stage.getBatch();
        if (((Main) Gdx.app.getApplicationListener()).isGrayscale()) {
            batch.setShader(((Main) Gdx.app.getApplicationListener()).getGrayscaleShader());
        } else {
            batch.setShader(null);
        }
        stage.act(delta);
        stage.draw();
        batch.setShader(null);

        controller.handleMainMenuButtons();
        updatePhoto();
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


    public TextButton getLogoutButton() {
        return logoutButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    public TextButton getResumeButton() {
        return resumeButton;
    }

    public TextButton getPreGameButton() {
        return preGameButton;
    }

    public TextButton getTalentButton() {
        return TalentButton;
    }

    public TextButton getScoreBoardButton() {
        return scoreBoardButton;
    }

    public TextButton getSettingButton() {
        return settingButton;
    }

    public TextButton getProfileButton() {
        return profileButton;
    }

    public static void updatePhoto(){
        Image newAvatarIMG =  new Image(App.getCurrentUser().getAvatar());
        newAvatarIMG.setWidth(80);
        newAvatarIMG.setHeight(80);
        newAvatarIMG.setPosition(20 , stage.getHeight() - 100);
        stage.addActor(newAvatarIMG);
    }
}
