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

import java.util.Map;

public class TalentMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private Table table;

    // داده‌های نمونه (در پروژه واقعی باید داده واقعی را بیاوری)
    private String[] heroes = {"Hero1: Fast", "Hero2: Power", "Hero3: Defence"};
    private Map<String, Integer> currentKeyBindings; // از تنظیمات خوانده شود
    private Map<String, String> cheats = Map.of(
        "IDDQD", "God Mode: God Mode",
        "GIVEALL", "Getting All Weapons"
    );
    private String[] abilities = {"Fast Pace", "Multi Shot", "Accurate Shooting"};

    public TalentMenu(Skin skin, Map<String, Integer> currentKeyBindings) {
        this.skin = skin;
        this.currentKeyBindings = currentKeyBindings;

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
        for (Map.Entry<String, Integer> entry : currentKeyBindings.entrySet()) {
            String action = entry.getKey();
            String keyName = Input.Keys.toString(entry.getValue());
            table.add(new Label(action + ": ", skin)).left().padLeft(20);
            table.add(new Label(keyName, skin)).left().padRight(20);
            table.row();
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
                // اینجا باید به منوی قبلی برگردی
                // مثلا setScreen(mainMenu);
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
        ScreenUtils.clear(0, 0, 0, 1);
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
