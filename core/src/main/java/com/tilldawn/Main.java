package com.tilldawn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.tilldawn.Control.MenuController.PreProgramController;
import com.tilldawn.Model.App;
import com.tilldawn.Model.FileDropListener;
import com.tilldawn.View.Components.FileChooser;
import com.tilldawn.View.PreGameMenus.PreProgram;
import com.tilldawn.Model.AssetManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;
    private static ShaderProgram grayscaleShader;
    private static boolean useGrayscale = false;


    public static Music currentMusic;
    public static void playMusic(String path) {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }

        currentMusic = Gdx.audio.newMusic(Gdx.files.internal(path));
        currentMusic.setLooping(true);

        // Load saved volume
        Preferences prefs = Gdx.app.getPreferences("Settings");
        float volume = prefs.getFloat("musicVolume", 0.5f);
        currentMusic.setVolume(volume);

        currentMusic.play();
    }


    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();

        Pixmap pixmap = new Pixmap(Gdx.files.internal("cursor.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, 16, 16)); // (0, 0) is hotspot position
        pixmap.dispose(); // dispose after creating the cursor

        // Load shaders
        ShaderProgram.pedantic = false;
        grayscaleShader = new ShaderProgram(
            Gdx.files.internal("shaders/default.vs"),
            Gdx.files.internal("shaders/grayscale.fs")
        );
        if (!grayscaleShader.isCompiled()) {
            System.err.println("Shader compile error: " + grayscaleShader.getLog());
        }
        // Load saved preference or default false
        useGrayscale = Gdx.app.getPreferences("Settings").getBoolean("grayscale", false);
        updateShader();
        // Set your initial screen here
        //setScreen(new YourFirstScreen(this));

        playMusic("music/track1.mp3");
        App.loadUsers();
        getMain().setScreen(new PreProgram(new PreProgramController(),AssetManager.getAssetManager().getSkin()));
    }

    @Override
    public void render() {
        batch.setShader(useGrayscale ? grayscaleShader : null);  // Apply or remove grayscale every frame
        super.render();
    }

    public static void setGrayscale(boolean value) {
        useGrayscale = value;
        Gdx.app.getPreferences("Settings").putBoolean("grayscale", value).flush();
        updateShader();
    }

    public static boolean isGrayscale() {
        return useGrayscale;
    }

    public static ShaderProgram getGrayscaleShader() {
        return grayscaleShader;
    }

    private static void updateShader() {
        if (useGrayscale) {
            batch.setShader(grayscaleShader);
        } else {
            batch.setShader(null);
        }
    }


    @Override
    public void dispose() {
        batch.dispose();
        if(!App.getCurrentUser().getUserName().equals("Guest")) {
            App.saveUsers();
        }
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {return batch;}

    public static void setBatch(SpriteBatch batch) {Main.batch = batch; }



    private static FileDropListener fileDropListener;
    public void setFileDropListener(FileDropListener listener) {
        this.fileDropListener = listener;
    }
    public static FileDropListener getFileDropListener() {
        return fileDropListener;
    }


    private static FileChooser fileChooser;
    public static void setFileChooser(FileChooser chooser) {
        fileChooser = chooser;
    }
    public static FileChooser getFileChooser() {
        return fileChooser;
    }

}
