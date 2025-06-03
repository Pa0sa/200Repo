package com.tilldawn.View.PreGameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MenuController.MainMenuController;
import com.tilldawn.Control.PlayerController;
import com.tilldawn.Main;
import com.tilldawn.Model.App;
import com.tilldawn.Model.AssetManager;

import java.util.HashMap;
import java.util.Map;

import static com.tilldawn.Main.getMain;

public class SettingsMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private Slider musicSlider, sfxSlider;
    private SelectBox<String> musicSelectBox;
    private CheckBox autoReloadCheckBox, grayscaleCheckBox;
    private TextButton changeControlsButton, backButton;


    private Music currentMusic;

    public SettingsMenu() {

        stage = new Stage(new ScreenViewport());
        skin = AssetManager.getAssetManager().getSkin();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(15);
        stage.addActor(table);

// Music Volume
        table.add(new Label("Music Volume", skin)).left();
        musicSlider = new Slider(0f, 1f, 0.01f, false, skin);
        Preferences prefs = Gdx.app.getPreferences("Settings");
        float savedVolume = prefs.getFloat("musicVolume", 0.5f);
        musicSlider.setValue(savedVolume);
        if (Main.currentMusic != null) {
            Main.currentMusic.setVolume(savedVolume);
        }
        musicSlider.addListener(event -> {
            if (event instanceof ChangeListener.ChangeEvent) {
                float volume = musicSlider.getValue();
                if (Main.currentMusic != null) {
                    Main.currentMusic.setVolume(volume);
                }
                prefs.putFloat("musicVolume", volume);
                prefs.flush();
                return true; // Event handled
            }
            return false;
        });
        table.add(musicSlider).width(200).row();


        // SFX Volume
        table.add(new Label("SFX Volume", skin)).left();
        sfxSlider = new Slider(0f, 1f, 0.01f, false, skin);
        sfxSlider.setValue(0.5f); // Default
        // Add SFX test if needed
        table.add(sfxSlider).width(200).row();

        // Background Music Selection
        table.add(new Label("Background Music", skin)).left();
        musicSelectBox = new SelectBox<>(skin);
        musicSelectBox.setItems("Track1", "Track2", "Track3");
        musicSelectBox.addListener(event -> {
            if (event instanceof ChangeListener.ChangeEvent) {
                String selected = musicSelectBox.getSelected();
                Main.playMusic("music/" + selected + ".mp3");
            }
            return false;
        });
        table.add(musicSelectBox).width(200).row();

        // Auto Reload Toggle
        autoReloadCheckBox = new CheckBox("Auto-Reload", skin);
        autoReloadCheckBox.setChecked(App.isAutoReload());
        autoReloadCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean checked = autoReloadCheckBox.isChecked();
                App.setAutoReload(checked);
            }
        });
        table.add(autoReloadCheckBox).colspan(2).left().row();


        // Grayscale Toggle
        grayscaleCheckBox = new CheckBox("Scary Mode", skin);
        grayscaleCheckBox.setChecked(false);
        grayscaleCheckBox.setChecked(((Main) Gdx.app.getApplicationListener()).isGrayscale());
        grayscaleCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean checked = grayscaleCheckBox.isChecked();
                ((Main) Gdx.app.getApplicationListener()).setGrayscale(checked);
            }
        });
        table.add(grayscaleCheckBox).colspan(2).left().row();


        // Change Keybindings
        changeControlsButton = new TextButton("Change Controllers", skin);
        changeControlsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                // نمایش منوی جدید یا وارد کردن کلید
                Gdx.app.log("SettingsMenu", "Change controls clicked");
                showKeybindWindow();
                // implement separate keybinding screen if needed
            }
        });
        table.add(changeControlsButton).colspan(2).width(250).row();

        // Back Button
        backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                getMain().getScreen().dispose();
                getMain().setScreen(new MainMenu(new MainMenuController(), AssetManager.getAssetManager().getSkin()));
            }
        });
        backButton.setPosition(30, 30);
        stage.addActor(backButton);
    }

    public boolean isAutoReloadEnabled() {
        return autoReloadCheckBox.isChecked();
    }

    public boolean isGrayscaleEnabled() {
        return grayscaleCheckBox.isChecked();
    }

    public float getMusicVolume() {
        return musicSlider.getValue();
    }

    public float getSfxVolume() {
        return sfxSlider.getValue();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.1f, 1);
        Main.getBatch().begin();
        Batch batch = stage.getBatch();
        if (((Main) Gdx.app.getApplicationListener()).isGrayscale()) {
            batch.setShader(((Main) Gdx.app.getApplicationListener()).getGrayscaleShader());
        } else {
            batch.setShader(null);
        }
        stage.act(delta);
        stage.draw();
        batch.setShader(null);
        Main.getBatch().end();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
        if (currentMusic != null) currentMusic.dispose();
    }

    public Slider getSfxSlider() {
        return sfxSlider;
    }

    public Slider getMusicSlider() {
        return musicSlider;
    }


    private final String[] actions = {"Move Up", "Move Down", "Move Right", "Move Left", "Reload"};
    private final String[] actionKeys = {"moveUp", "moveDown", "moveRight", "moveLeft", "reload"};
    private static final Map<String, Integer> currentKeyBindings = new HashMap<>();

    private void loadKeyBindings() {
        Preferences prefs = Gdx.app.getPreferences("Controls");
        for (int i = 0; i < actionKeys.length; i++) {
            int defaultKey;
            switch (actionKeys[i]) {
                case "moveUp":
                    defaultKey = PlayerController.getwInt();
                    PlayerController.setwInt(defaultKey);
                    break;
                case "moveDown":
                    defaultKey = PlayerController.getsInt();
                    PlayerController.setsInt(defaultKey);
                    break;
                case "moveRight":
                    defaultKey = PlayerController.getdInt();
                    PlayerController.setdInt(defaultKey);
                    break;
                case "moveLeft":
                    defaultKey = PlayerController.getaInt();
                    PlayerController.setaInt(defaultKey);
                    break;
                case "reload":
                    defaultKey = PlayerController.getrInt();
                    PlayerController.setrInt(defaultKey);
                    break;
                default:
                    defaultKey = Input.Keys.UNKNOWN;
                    break;
            }
            currentKeyBindings.put(actionKeys[i], prefs.getInteger(actionKeys[i], defaultKey));
        }
    }

    private void showKeybindWindow() {
        loadKeyBindings();
        applyKeyBindingsToPlayerController();
        final Window window = new Window("", skin); // empty title here
        final Label titleLabel = new Label("Change Controls", skin);
        window.getTitleLabel().setText(titleLabel.getText());

        window.setSize(400, 300);
        window.setPosition(
            (stage.getWidth() - window.getWidth()) / 2,
            (stage.getHeight() - window.getHeight()) / 2
        );
        window.setModal(true);
        window.setMovable(false);

        final Label[] keyLabels = new Label[actions.length];

        for (int i = 0; i < actions.length; i++) {
            final int index = i;
            String action = actions[i];
            String keyPref = actionKeys[i];
            int keycode = currentKeyBindings.get(keyPref);
            String keyName = Input.Keys.toString(keycode);

            Label actionLabel = new Label(action + ": ", skin);
            Label keyLabel = new Label(keyName, skin);
            keyLabels[i] = keyLabel;

            TextButton changeButton = new TextButton("Change", skin);

            changeButton.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    window.getTitleLabel().setText("Press a key for " + action);
                    stage.setKeyboardFocus(window);
                    window.addListener(new InputListener() {
                        public boolean keyDown(InputEvent event, int keycode) {
                            keyLabel.setText(Input.Keys.toString(keycode));
                            currentKeyBindings.put(keyPref, keycode);

                            Preferences prefs = Gdx.app.getPreferences("Controls");
                            prefs.putInteger(keyPref, keycode);
                            prefs.flush();

                            window.getTitleLabel().setText("Change Controls");
                            window.clearListeners(); // Remove this InputListener after one use
                            return true;
                        }
                    });
                }
            });

            window.add(actionLabel).left().pad(5);
            window.add(keyLabel).pad(5);
            window.add(changeButton).pad(5).row();
        }

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                applyKeyBindingsToPlayerController();
                window.remove();
            }
        });
        window.add(closeButton).colspan(3).padTop(10).center().row();

        stage.addActor(window);
    }
    private void applyKeyBindingsToPlayerController() {
        PlayerController.setwInt(currentKeyBindings.getOrDefault("moveUp", PlayerController.getwInt()));
        PlayerController.setsInt(currentKeyBindings.getOrDefault("moveDown", PlayerController.getsInt()));
        PlayerController.setdInt(currentKeyBindings.getOrDefault("moveRight", PlayerController.getdInt()));
        PlayerController.setaInt(currentKeyBindings.getOrDefault("moveLeft", PlayerController.getaInt()));
        PlayerController.setrInt(currentKeyBindings.getOrDefault("reload", PlayerController.getrInt()));
    }

    public static Map<String, Integer> getCurrentKeyBindings() {
        return currentKeyBindings;
    }
}

