package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.GameController;
import com.tilldawn.Control.LoseGameController;
import com.tilldawn.Control.WinGameController;
import com.tilldawn.Main;
import com.tilldawn.Model.App;


public class WinGameMenu implements Screen {
    private Stage stage;
    private TextButton backButton;
    private WinGameController controller;
    private Label label;
    private Label username;
    private Label remaining;
    private Label score;
    private Label killCount;
    private GameController controller2;

    public WinGameMenu(GameController controller ,Skin skin) {
        this.stage = new Stage();
        this.controller = new WinGameController();
        this.controller2 = controller;
        this.label = new Label("YOU WON!", skin);
        this.username = new Label("Username : " + App.getCurrentUser().getUserName(), skin);
        this.remaining = new Label("Survived Time : " + String.valueOf((App.getTimePassed())/60) + " : " +
            String.valueOf((App.getTimePassed())%60) , skin);
        this.killCount = new Label("Kills : " + App.getCurrentUser().getKills() , skin);
        this.score = new Label("Score : " + (App.getTimePassed()*App.getCurrentUser().getKills()) , skin);
        this.backButton = new TextButton("Back" , skin);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        label.setPosition((stage.getWidth()-label.getWidth())/2, (stage.getHeight()-label.getHeight())/2);
        username.setPosition(label.getX(), label.getY() - username.getHeight() - 10);
        remaining.setPosition(label.getX(), username.getY() - remaining.getHeight() - 10);
        killCount.setPosition(label.getX(), remaining.getY() - killCount.getHeight() - 10);
        score.setPosition(label.getX(), killCount.getY() - score.getHeight() - 10);
        stage.addActor(label);
        stage.addActor(username);
        stage.addActor(remaining);
        stage.addActor(killCount);
        stage.addActor(score);
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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public GameController getController2() {
        return controller2;
    }

    public void setController2(GameController controller2) {
        this.controller2 = controller2;
    }

    public Label getKillCount() {
        return killCount;
    }

    public void setKillCount(Label killCount) {
        this.killCount = killCount;
    }

    public Label getScore() {
        return score;
    }

    public void setScore(Label score) {
        this.score = score;
    }

    public Label getUsername() {
        return username;
    }

    public void setUsername(Label username) {
        this.username = username;
    }

    public Label getRemaining() {
        return remaining;
    }

    public void setRemaining(Label remaining) {
        this.remaining = remaining;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public void setBackButton(TextButton backButton) {
        this.backButton = backButton;
    }
}
