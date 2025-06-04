package com.tilldawn.View.PreGameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MenuController.MainMenuController;
import com.tilldawn.Model.AssetManager;

import java.util.Map;

import static com.tilldawn.Main.getMain;

public class TalentMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private Table table;
    private Screen settingScreen;
    // داده‌های نمونه (در پروژه واقعی باید داده واقعی را بیاوری)
    private String[] heroes = {"Diamond: Has a lot of Health", "Dasher: Fastest among the 5", "Lilith: Average Speed and Health"};
    private Map<String, Integer> currentKeyBindings; // از تنظیمات خوانده شود
    private Map<String, String> cheats = Map.of(
        "O :", "Pass 1 minute",
        "K :", "Level up",
        "I :","Adds Health",
        "P :","Goes to Boss Fight",
        "M :", "Revives the Player after Death",
        "N :", "Infinite Ammo",
        "Space :" , "Auto Aim"
    );
    private String[] abilities = {"Vitality : Adds 1 to Maximum Health", "Damager : Increase Damage by 25% for 10 seconds",
        "ProCrease : Increase weapon projectiles by 1" , "AmoCrease : Increase weapon Max ammo by 1" , "Speedy : Doubles the player speed for 10 seconds" };

    public TalentMenu(Skin skin, Map<String, Integer> currentKeyBindings) {
        this.skin = skin;
        this.currentKeyBindings = currentKeyBindings;
        this.settingScreen = new SettingsMenu();
        stage = new Stage(new ScreenViewport());
        table = new Table(skin);
        table.setFillParent(true);
        stage.addActor(table);

        createUI();
    }

    private void createUI() {
        table.clear();
        // عنوان منو
        Label title = new Label("Hint Menu", skin, "title");
        table.add(title).colspan(2).center().pad(10);
        table.row();

        // بخش هیروها
        table.add(new Label("Heroes Info:", skin)).left().pad(5).colspan(2);
        table.row();
        for (String hero : heroes) {
            table.add(new Label(hero, skin)).left().colspan(2).padLeft(20);
            table.row();
        }

        // بخش کلیدهای بازی
        table.add(new Label("Current Key Bindings:", skin)).left().pad(5).colspan(2);
        table.row();
        if(currentKeyBindings.isEmpty()) {
            table.add(new Label("W" + ": ", skin)).left().padLeft(20);
            table.add(new Label("Move Up", skin)).left().padRight(20);
            table.row();
            table.add(new Label("S" + ": ", skin)).left().padLeft(20);
            table.add(new Label("Move Down", skin)).left().padRight(20);
            table.row();
            table.add(new Label("D" + ": ", skin)).left().padLeft(20);
            table.add(new Label("Move Right", skin)).left().padRight(20);
            table.row();
            table.add(new Label("A" + ": ", skin)).left().padLeft(20);
            table.add(new Label("Move Left", skin)).left().padRight(20);
            table.row();
            table.add(new Label("R" + ": ", skin)).left().padLeft(20);
            table.add(new Label("Reload", skin)).left().padRight(20);
            table.row();

        } else {
            for (Map.Entry<String, Integer> entry : currentKeyBindings.entrySet()) {
                String action = entry.getKey();
                String keyName = Input.Keys.toString(entry.getValue());
                table.add(new Label(action + ": ", skin)).left().padLeft(20);
                table.add(new Label(keyName, skin)).left().padRight(20);
                table.row();
            }
        }
        // بخش کدهای تقلب
        table.add(new Label("Cheat Codes:", skin)).left().pad(5).colspan(2);
        table.row();
        for (Map.Entry<String, String> cheat : cheats.entrySet()) {
            table.add(new Label(cheat.getKey(), skin)).left().padLeft(20);
            table.add(new Label(cheat.getValue(), skin)).left().padRight(20);
            table.row();
        }

        // بخش توانایی‌ها
        table.add(new Label("Abilities:", skin)).left().pad(5).colspan(2);
        table.row();
        for (String ability : abilities) {
            table.add(new Label(ability, skin)).left().colspan(2).padLeft(20);
            table.row();
        }

        // دکمه بازگشت
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getMain().getScreen().dispose();
                getMain().setScreen(new MainMenu( new MainMenuController(), AssetManager.getAssetManager().getSkin()));
            }
        });
        table.add(backButton).colspan(2).center().padTop(20);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.1f, 1);
        stage.act(delta);
        stage.draw();
    }

    // سایر متدهای Screen که می‌توانی خالی بگذاری
    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}
