package com.tilldawn.View.PreGameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MenuController.MainMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.App;
import com.tilldawn.Model.AssetManager;
import com.tilldawn.Model.User;

import java.util.ArrayList;
import java.util.Comparator;

import static com.tilldawn.Main.getMain;

public class LeaderBoardMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private ArrayList<User> users;
    private Table leaderboardTable;
    private TextButton backButton;
    private TextButton sortByNameButton, sortByScoreButton, sortByKillsButton, sortByTimeButton;
    private static final Color GOLD_COLOR = Color.GOLD;
    private static final Color SILVER_COLOR = Color.LIGHT_GRAY;
    private static final Color BRONZE_COLOR = new Color(0.8f, 0.5f, 0.2f, 1); // Custom bronze
    private static final Color CURRENT_PLAYER_COLOR = Color.SKY;
    private static final Color DEFAULT_COLOR = Color.WHITE;

    public LeaderBoardMenu(ArrayList<User> users) {
        this.users = new ArrayList<>(users); // Create a copy of the original list
        this.stage = new Stage(new ScreenViewport());
        this.skin = AssetManager.getAssetManager().getSkin(); // Or your custom skin

        createUI();
    }

    private void createUI() {
        leaderboardTable = new Table(skin);
        leaderboardTable.setFillParent(true);
        leaderboardTable.top().padTop(200);

        // Header row
        leaderboardTable.add("Username").width(200).padRight(20);
        leaderboardTable.add("Score").width(100).padRight(20);
        leaderboardTable.add("Kills").width(100).padRight(20);
        leaderboardTable.add("Time").width(100);
        leaderboardTable.row();

        // Add users to the table
        refreshLeaderboard();

        // Create sort buttons
        Table buttonTable = new Table(skin);
        sortByNameButton = new TextButton("Sort by Name", skin);
        sortByScoreButton = new TextButton("Sort by Score", skin);
        sortByKillsButton = new TextButton("Sort by Kills", skin);
        sortByTimeButton = new TextButton("Sort by Time", skin);

        buttonTable.add(sortByNameButton).pad(5);
        buttonTable.add(sortByScoreButton).pad(5);
        buttonTable.add(sortByKillsButton).pad(5);
        buttonTable.add(sortByTimeButton).pad(5);

        // Back button
        backButton = new TextButton("Back", skin);

        // Main layout
        Table mainTable = new Table(skin);
        mainTable.setFillParent(true);
        mainTable.add(leaderboardTable).expand().fill().top().row();
        mainTable.add(buttonTable).padBottom(20).row();

        backButton.setPosition(30,30);
        stage.addActor(backButton);

        stage.addActor(mainTable);

        // Add button listeners
        addButtonListeners();
    }

    private void refreshLeaderboard() {
        leaderboardTable.clearChildren();

        // Re-add header row
        leaderboardTable.add("Rank").width(50).padRight(10);
        leaderboardTable.add("Username").width(200).padRight(20);
        leaderboardTable.add("Score").width(100).padRight(20);
        leaderboardTable.add("Kills").width(100).padRight(20);
        leaderboardTable.add("Time").width(100);
        leaderboardTable.row();

        // Add users with ranking and coloring
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            int rank = i + 1;

            // Determine the color based on rank and current player status
            Color rowColor = DEFAULT_COLOR;

            if (App.getCurrentUser().equals(user)) {
                rowColor = CURRENT_PLAYER_COLOR;
            } else if (rank == 1) {
                rowColor = GOLD_COLOR;
            } else if (rank == 2) {
                rowColor = SILVER_COLOR;
            } else if (rank == 3) {
                rowColor = BRONZE_COLOR;
            }

            // Create a label style with the appropriate color
            Label.LabelStyle labelStyle = new Label.LabelStyle(skin.get(Label.LabelStyle.class));
            labelStyle.fontColor = rowColor;

            // Add rank
            leaderboardTable.add(new Label(String.valueOf(rank), labelStyle)).width(50).padRight(10);

            // Add user data
            leaderboardTable.add(new Label(user.getUserName(), labelStyle)).left().width(200).padRight(20);
            leaderboardTable.add(new Label(String.valueOf(user.getScore()), labelStyle)).width(100).padRight(20);
            leaderboardTable.add(new Label(String.valueOf(user.getKills()), labelStyle)).width(100).padRight(20);
            leaderboardTable.add(new Label(String.valueOf(user.getSurvivalTime()), labelStyle)).width(100);
            leaderboardTable.row();
        }
    }

    private void addButtonListeners() {
        sortByNameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                users.sort(Comparator.comparing(User::getUserName));
                refreshLeaderboard();
            }
        });

        sortByScoreButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                users.sort(Comparator.comparingInt(User::getScore).reversed());
                refreshLeaderboard();
            }
        });

        sortByKillsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                users.sort(Comparator.comparingInt(User::getKills).reversed());
                refreshLeaderboard();
            }
        });

        sortByTimeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                users.sort(Comparator.comparingInt(User::getSurvivalTime).reversed());
                refreshLeaderboard();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getMain().getScreen().dispose();
                getMain().setScreen(new MainMenu( new MainMenuController(),AssetManager.getAssetManager().getSkin()));
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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
    }

    // Other required Screen methods...
    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}
