package com.tilldawn.View.PreGameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MenuController.RegisterMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.App;
import com.tilldawn.Model.AssetManager;
import com.tilldawn.Model.User;

public class RegisterMenu implements Screen {


    private Stage stage;

    private TextButton confirmButton;
    private TextButton backButton;

    private Label userNameTitle;
    private Label passwordTitle;
    private Label userNameError;
    private Label passwordError;

    private SelectBox securityQuestion;

    private TextField userNameField;
    private TextField passwordField;

    private TextField securityQuestionField;

    private RegisterMenuController controller;

    private Texture logo = AssetManager.getAssetManager().getLogo();
    Image image = new Image(logo);

    boolean showUsernameError = false;
    boolean showPasswordError = false;


    public RegisterMenu(RegisterMenuController controller, Skin skin) {

        this.confirmButton = new TextButton("Confirm", skin);
        this.backButton = new TextButton("Back", skin);


        this.userNameTitle = new Label("Enter Your Name", skin);
        this.passwordTitle = new Label("Enter Your Password", skin);

        this.userNameError = new Label("Username Has Been Used", skin);
        this.passwordError = new Label("Password Is Too Simple", skin);

        this.securityQuestion = new SelectBox<>(skin);
        Array<String> hero = new Array<>();
        hero.add("What was your first car model?");
        hero.add("What was your first pet's name?");
        hero.add("Who is your favourite Avenger?");
        securityQuestion.setItems(hero);

        this.userNameField = new TextField("", skin);
        this.passwordField = new TextField("", skin);
        this.securityQuestionField = new TextField("", skin);

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
        stage.addActor(image);


        userNameField.setWidth(700);
        userNameField.setPosition(stage.getWidth() / 2 - userNameField.getWidth() / 2, 320);
        stage.addActor(userNameField);
        userNameTitle.setPosition(userNameField.getX(), userNameField.getY() + userNameField.getHeight());
        stage.addActor(userNameTitle);

        passwordField.setWidth(700);
        passwordField.setPosition(stage.getWidth() / 2 - passwordField.getWidth() / 2,
            userNameField.getY() - userNameField.getHeight() - passwordTitle.getHeight() - 10);
        stage.addActor(passwordField);

        passwordTitle.setPosition(passwordField.getX(), passwordField.getY() + passwordField.getHeight());
        stage.addActor(passwordTitle);

        securityQuestionField.setWidth(700);
        securityQuestionField.setPosition(stage.getWidth() / 2 - securityQuestionField.getWidth()/2 ,
            passwordField.getY() - passwordField.getHeight() - securityQuestion.getHeight());
        stage.addActor(securityQuestionField);

        securityQuestion.setWidth(700);
        securityQuestion.setPosition(securityQuestionField.getX(), securityQuestionField.getY() + securityQuestionField.getHeight() - 10);
        stage.addActor(securityQuestion);

        confirmButton.setWidth(200);
        confirmButton.setPosition(userNameField.getX()+(userNameField.getWidth() - confirmButton.getWidth())/2, 100);
        stage.addActor(confirmButton);

        backButton.setPosition(20, 20);
        stage.addActor(backButton);

        userNameError.setPosition(userNameField.getX() + userNameField.getWidth() - userNameError.getWidth() - 3, userNameField.getY() + 8);
        passwordError.setPosition(passwordField.getX() + passwordField.getWidth() - passwordError.getWidth() - 3, passwordField.getY() + 8);

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
        check();
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

    public void check() {
        boolean check = false;

        for (User user : App.getUsers()) {
            if (user.getUserName().equals(userNameField.getText())) {
                check = true;  // username found
            }
        }


        if (check && !showUsernameError) {
            showUsernameError = true;
            stage.addActor(userNameError);
        }
        if (!check && showUsernameError) {
            showUsernameError = false;
            userNameError.remove();
        }
        if (!passwordField.getText().matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%&*()_]).{8,}$") && !showPasswordError) {
            showPasswordError = true;
            stage.addActor(passwordError);
        }
        if (passwordField.getText().matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%&*()_]).{8,}$") && showPasswordError) {
            showPasswordError = false;
            passwordError.remove();
        }
    }


    public TextButton getBackButton() {
        return backButton;
    }

    public TextButton getConfirmButton() {
        return confirmButton;
    }

    public Stage getStage() {
        return stage;
    }

    public SelectBox getSecurityQuestion() {
        return securityQuestion;
    }

    public TextField getUserNameField() {
        return userNameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextField getSecurityQuestionField() {
        return securityQuestionField;
    }

    public boolean isShowUsernameError() {
        return showUsernameError;
    }

    public boolean isShowPasswordError() {
        return showPasswordError;
    }
}
