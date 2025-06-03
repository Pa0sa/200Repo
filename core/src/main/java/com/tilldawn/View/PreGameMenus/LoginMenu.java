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
import com.tilldawn.Control.MenuController.LoginMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.AssetManager;

public class LoginMenu implements Screen {

    private Stage stage;


    private TextButton confirmButton;
    private TextButton backButton;
    private TextButton backButton2;
    private TextButton forgotButton;

    private Label userNameTitle;
    private Label passwordTitle;

    private TextField userNameField;
    private TextField passwordField;

    private LoginMenuController controller;

    private TextButton submitButton;

    private TextField resetPasswordField;
    private SelectBox securityQuestion;
    private TextField securityQuestionField;
    private Label resetPasswordTitle;
    private Label passwordError;
    boolean showPasswordError = false;
    boolean resetPasswordMenu = false;

    private Texture logo = AssetManager.getAssetManager().getLogo();
    Image image = new Image(logo);

    public LoginMenu(LoginMenuController controller, Skin skin) {

        this.confirmButton = new TextButton("Confirm", skin);
        this.backButton = new TextButton("Back", skin);
        this.backButton2 = new TextButton("Back", skin);
        this.forgotButton = new TextButton("Reset\nPassword?", skin);
        this.submitButton = new TextButton("Submit", skin);

        this.userNameTitle = new Label("Enter Your Name", skin);
        this.passwordTitle = new Label("Enter Your Password", skin);

        this.userNameField = new TextField("", skin);
        this.passwordField = new TextField("", skin);
        this.resetPasswordField = new TextField("", skin);
        this.resetPasswordTitle = new Label("Enter Your New Password" , skin);

        this.securityQuestion = new SelectBox<>(skin);
        Array<String> hero = new Array<>();
        hero.add("What was your first car model?");
        hero.add("What was your first pet's name?");
        hero.add("Who is your favourite Avenger?");
        securityQuestion.setItems(hero);
        this.securityQuestionField = new TextField("", skin);
        this.passwordError = new Label("Invalid Password Format", skin);
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

        confirmButton.setWidth(200);
        confirmButton.setPosition(userNameField.getX()+(userNameField.getWidth() - confirmButton.getWidth())/2, 100);
        stage.addActor(confirmButton);

        forgotButton.setWidth(100);
        forgotButton.setPosition(userNameField.getX()+(userNameField.getWidth() - forgotButton.getWidth()), 100);
        stage.addActor(forgotButton);

        backButton.setPosition(20, 20);
        stage.addActor(backButton);

        backButton2.setPosition(20, 20);


        resetPasswordField.setWidth(700);
        resetPasswordField.setPosition(stage.getWidth() / 2 - passwordField.getWidth() / 2,
            userNameField.getY() - userNameField.getHeight() - passwordTitle.getHeight() - 10);


        submitButton.setWidth(200);
        submitButton.setPosition(userNameField.getX()+(userNameField.getWidth() - confirmButton.getWidth())/2, 100);

        securityQuestionField.setWidth(700);
        securityQuestionField.setPosition(stage.getWidth() / 2 - securityQuestionField.getWidth()/2 ,
            passwordField.getY() - passwordField.getHeight() - securityQuestion.getHeight());
        resetPasswordTitle.setPosition(passwordField.getX(), passwordField.getY() + passwordField.getHeight());
        securityQuestion.setWidth(700);
        securityQuestion.setPosition(securityQuestionField.getX(), securityQuestionField.getY() + securityQuestionField.getHeight() - 10);
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
        check();
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

    public Stage getStage() {
        return stage;
    }

    public TextButton getConfirmButton() {
        return confirmButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public TextButton getBackButton2() {
        return backButton2;
    }

    public TextField getUserNameField() {
        return userNameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextButton getForgotButton() {
        return forgotButton;
    }

    public TextButton getSubmitButton() {
        return submitButton;
    }

    public TextField getResetPasswordField() {
        return resetPasswordField;
    }

    public SelectBox getSecurityQuestion() {
        return securityQuestion;
    }

    public TextField getSecurityQuestionField() {
        return securityQuestionField;
    }

    public void changeStageToForget() {
        confirmButton.remove();
        passwordField.remove();
        forgotButton.remove();
        passwordTitle.remove();
        backButton.remove();
        stage.addActor(backButton2);
        stage.addActor(resetPasswordField);
        stage.addActor(submitButton);
        stage.addActor(securityQuestionField);
        stage.addActor(securityQuestion);
        stage.addActor(resetPasswordTitle);
        stage.addActor(passwordError);
        resetPasswordMenu = true;
    }
    public void changeStageNormal() {
        stage.addActor(confirmButton);
        stage.addActor(forgotButton);
        stage.addActor(userNameField);
        stage.addActor(passwordField);
        stage.addActor(passwordTitle);
        stage.addActor(backButton);
        resetPasswordField.remove();
        submitButton.remove();
        securityQuestionField.remove();
        securityQuestion.remove();
        resetPasswordTitle.remove();
        backButton2.remove();
        passwordError.remove();
        resetPasswordMenu = false;
    }
    public void check() {
        if(resetPasswordMenu) {
            if (!resetPasswordField.getText().matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%&*()_]).{8,}$") && !showPasswordError) {
                showPasswordError = true;
                stage.addActor(passwordError);
            }
            if (resetPasswordField.getText().matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%&*()_]).{8,}$") && showPasswordError) {
                showPasswordError = false;
                passwordError.remove();
            }
        }
    }

}
