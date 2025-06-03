package com.tilldawn.View.PreGameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MenuController.PreProgramController;
import com.tilldawn.Main;
import com.tilldawn.Model.AssetManager;
import com.tilldawn.View.Components.FontUtil;

public class PreProgram implements Screen {


    private Stage stage;

    private TextButton registerButton;
    private TextButton loginButton;
    private TextButton exitButton;
    private TextButton skipButton;

    private Texture logo;
    private Image image;

    private PreProgramController controller;



    public PreProgram(PreProgramController controller, Skin skin) {
        BitmapFont customFont = FontUtil.generateFont("Fonts/ChevyRay - Lantern.ttf", 16 ); // or .otf
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(skin.get(TextButton.TextButtonStyle.class));
        buttonStyle.font = customFont;


        registerButton = new TextButton("Register", buttonStyle);
        loginButton = new TextButton("Login", buttonStyle);
        exitButton = new TextButton("Exit", buttonStyle);
        skipButton = new TextButton("Skip", buttonStyle);

        logo = AssetManager.getAssetManager().getLogo();
        image = new Image(logo);

        this.controller = controller;
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        image.setHeight(700);
        image.setWidth(700);
        image.setPosition(stage.getWidth() / 2 - image.getWidth() / 2, stage.getHeight() / 2 - image.getHeight() / 2 +200);
        stage.addActor(image);

        registerButton.setWidth(200);
        registerButton.setPosition(image.getX(), 150);
        stage.addActor(registerButton);

        loginButton.setWidth(200);
        loginButton.setPosition(image.getX()+image.getWidth()-loginButton.getWidth(), 150);
        stage.addActor(loginButton);

        exitButton.setPosition(20, 20);
        stage.addActor(exitButton);

        skipButton.setWidth(200);
        skipButton.setPosition(image.getX()+image.getWidth()-skipButton.getWidth(), 150 - skipButton.getHeight() - 10);
        stage.addActor(skipButton);

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



    public Button getLoginButton() {
        return loginButton;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public Button getSkipButton() {
        return skipButton;
    }

    public Stage getStage() {
        return stage;
    }
}
