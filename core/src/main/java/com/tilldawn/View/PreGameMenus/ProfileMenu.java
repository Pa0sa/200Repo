package com.tilldawn.View.PreGameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MenuController.ProfileMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.App;
import com.tilldawn.Model.AssetManager;
import com.tilldawn.Model.User;

import java.io.File;

public class ProfileMenu implements Screen {
    private Stage stage;
    private TextButton backButton;


    private TextButton changeUsernameButton;
    private TextButton changePasswordButton;
    private TextButton changeAvatarButton;
    private TextButton changeAvatarWindowButton;

    private Label userNameTitle;
    private Label passwordTitle;
    private Label userNameError;
    private Label passwordError;
    private TextField userNameField;
    private TextField passwordField;

    private Label avatarTitle;
    private Label avatarError;
    private TextField avatarField;
    private Label avatarDragAndDrop;

    boolean showUsernameError = false;
    boolean showPasswordError = false;
    boolean showAvatarError = false;

    private Texture logo = AssetManager.getAssetManager().getLogo();
    Image image = new Image(logo);

    private ProfileMenuController controller;


    public ProfileMenu(ProfileMenuController controller, Skin skin) {

        ((Main) Gdx.app.getApplicationListener()).setFileDropListener(filePath -> {
            // Handle the dropped file here
            System.out.println("File dropped in ProfileMenu: " + filePath);
            App.getCurrentUser().setAvatarPath(filePath);

            Texture texture = new Texture(filePath);
            Image avatar = new Image(texture);
            avatar.setHeight(100);
            avatar.setWidth(100);
            avatar.setPosition(100 , 100);
            stage.addActor(avatar);

        });

        this.backButton = new TextButton("Back", skin);
        this.controller = controller;


        this.changeUsernameButton = new TextButton("Change Username", skin);
        this.changePasswordButton = new TextButton("Change Password", skin);
        this.changeAvatarButton = new TextButton("Change Avatar", skin);
        this.changeAvatarWindowButton = new TextButton("Select From Disk", skin);

        this.userNameTitle = new Label("Enter Your Name", skin);
        this.passwordTitle = new Label("Enter Your Password", skin);
        this.userNameError = new Label("Username Has Been Used", skin);
        this.passwordError = new Label("Password Is Too Simple", skin);
        this.userNameField = new TextField("", skin);
        this.passwordField = new TextField("", skin);
        this.avatarDragAndDrop = new Label("To Change Your Avatar Drag And Drop Here", skin);
        this.avatarTitle = new Label("Enter Your Avatar Path", skin);
        this.avatarError = new Label("Path Not Recognized", skin);
        this.avatarField = new TextField("", skin);

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

        userNameField.setWidth(500);
        userNameField.setPosition(stage.getWidth() / 2 - userNameField.getWidth() / 2 - 100, 320);
        stage.addActor(userNameField);
        userNameTitle.setPosition(userNameField.getX(), userNameField.getY() + userNameField.getHeight());
        stage.addActor(userNameTitle);

        passwordField.setWidth(500);
        passwordField.setPosition(stage.getWidth() / 2 - passwordField.getWidth() / 2 - 100,
            userNameField.getY() - userNameField.getHeight() - passwordTitle.getHeight() - 10);
        stage.addActor(passwordField);

        passwordTitle.setPosition(passwordField.getX(), passwordField.getY() + passwordField.getHeight());
        stage.addActor(passwordTitle);

        avatarField.setWidth(500);
        avatarField.setPosition(stage.getWidth() / 2 - avatarField.getWidth() / 2 - 100,
            passwordField.getY() - passwordField.getHeight() - avatarTitle.getHeight() - 10);
        stage.addActor(avatarField);
        avatarTitle.setPosition(avatarField.getX(), avatarField.getY() + avatarField.getHeight());
        stage.addActor(avatarTitle);

        changeUsernameButton.setWidth(200);
        changeUsernameButton.setHeight(userNameField.getHeight());
        changeUsernameButton.setPosition(userNameField.getX() + userNameField.getWidth(), userNameField.getY());
        changePasswordButton.setWidth(200);
        changePasswordButton.setHeight(passwordField.getHeight());
        changePasswordButton.setPosition(userNameField.getX() + userNameField.getWidth(), passwordField.getY());
        stage.addActor(changeUsernameButton);
        stage.addActor(changePasswordButton);

        changeAvatarButton.setWidth(200);
        changeAvatarButton.setHeight(passwordField.getHeight());
        changeAvatarButton.setPosition(userNameField.getX() + userNameField.getWidth(), passwordField.getY()- userNameField.getHeight() - passwordTitle.getHeight() - 10);
        stage.addActor(changeAvatarButton);
        changeAvatarWindowButton.setWidth(200);
        changeAvatarWindowButton.setHeight(passwordField.getHeight());


        avatarDragAndDrop.setPosition(userNameField.getX(),
            avatarField.getY() - passwordField.getHeight() - avatarTitle.getHeight() - 10);

        changeAvatarWindowButton.setPosition(userNameField.getX() + userNameField.getWidth(),
            avatarDragAndDrop.getY());

        stage.addActor(avatarDragAndDrop);
        stage.addActor(changeAvatarWindowButton);

        userNameError.setPosition(userNameField.getX() + userNameField.getWidth() - userNameError.getWidth() - 3, userNameField.getY() + 8);
        passwordError.setPosition(passwordField.getX() + passwordField.getWidth() - passwordError.getWidth() - 3, passwordField.getY() + 8);
        avatarError.setPosition(avatarField.getX() + avatarField.getWidth() - avatarError.getWidth() - 3, avatarField.getY() + 8);

        backButton.setPosition(20, 20);
        stage.addActor(backButton);
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
        controller.handleProfileMenuButtons();
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


    public TextButton getBackButton() {
        return backButton;
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

        try {
            File file = new File(avatarField.getText());
            if (!file.exists() || !file.canRead()) {
                throw new Exception("File not found or unreadable");
            }
            showAvatarError = false;
            avatarError.remove();
        } catch (Exception e) {
            showAvatarError = true;
            stage.addActor(avatarError);
        }
    }


    public TextButton getChangePasswordButton() {
        return changePasswordButton;
    }

    public TextButton getChangeUsernameButton() {
        return changeUsernameButton;
    }

    public TextButton getChangeAvatarButton() {
        return changeAvatarButton;
    }

    public TextButton getChangeAvatarWindowButton() {
        return changeAvatarWindowButton;
    }

    public Stage getStage() {
        return stage;
    }

    public boolean isShowPasswordError() {
        return showPasswordError;
    }

    public boolean isShowUsernameError() {
        return showUsernameError;
    }

    public boolean isShowAvatarError() {
        return showAvatarError;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextField getUserNameField() {
        return userNameField;
    }

    public TextField getAvatarField() {
        return avatarField;
    }
}
