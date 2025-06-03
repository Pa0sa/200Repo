package com.tilldawn.View.PreGameMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.GameController;
import com.tilldawn.Control.MenuController.PreGameController;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.View.GameView;

import java.util.ArrayList;

public class PreGameMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private Player selectedCharacter;
    private Weapon selectedWeapon;
    private TextButton backButton;
    private TextButton startButton;

    String selectedTime = null;
    Stack selectedTimeStack = null;

    private Image icon = null;
    private final Image primitiveIcon = new Image(new Texture(Gdx.files.internal("Heroes/T_Shana_Portrait.png")));

    Image logoImage = new Image(new Texture(Gdx.files.internal("blank.png")));

    private ArrayList<Player> characterList;
    private ArrayList<Weapon> weaponList;
    private ArrayList<String> timeList;

    private ImageButton selectedCharacterButton = null;
    private ImageButton selectedWeaponButton = null;

    PreGameController controller;
    public PreGameMenu(PreGameController controller , Skin skin) {
        stage = new Stage(new ScreenViewport());
        this.skin = skin;

        Gdx.input.setInputProcessor(stage);
        this.backButton = new TextButton("Back", skin);
        this.startButton = new TextButton("Start Game", skin);
        loadCharactersAndWeapons();

        buildUI();

        this.controller = controller;

        controller.setView(this);
    }

    private void buildUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        primitiveIcon.setHeight(primitiveIcon.getHeight()*2f);
        primitiveIcon.setWidth(primitiveIcon.getWidth()*2f);
        primitiveIcon.setPosition(stage.getWidth()-primitiveIcon.getWidth() - 700, stage.getHeight() - primitiveIcon.getHeight() - 390);
        stage.addActor(primitiveIcon);

        // Characters
        Table characterTable = new Table();
        for (Player character : characterList) {
            TextureRegionDrawable up = new TextureRegionDrawable(new TextureRegion(character.getAvatar()));
            ImageButton button = new ImageButton(up);

            Label name = new Label(character.getName(), skin);

            Image selectionFrame = new Image(new Texture("selection-border.png"));
            selectionFrame.setVisible(false); // Initially hidden

            Stack imageStack = new Stack();
            imageStack.add(button);
            imageStack.add(selectionFrame);

            Table singleCharacter = new Table();
            singleCharacter.add(imageStack).size(300, 300).row(); // Avatar with selection frame
            singleCharacter.add(name).padTop(5); // Name below image

            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    primitiveIcon.remove();
                    selectedCharacter = character;

                    // Deselect previous
                    if (selectedCharacterButton != null) {
                        ((Stack) selectedCharacterButton.getParent()).getChildren().get(1).setVisible(false);
                    }

                    if (icon != null) {
                        icon.remove();
                    }
                    icon = new Image(character.getIcon());
                    icon.setSize(icon.getWidth() * 2f, icon.getHeight() * 2f);
                    icon.setPosition(stage.getWidth() - icon.getWidth() - 700, stage.getHeight() - icon.getHeight() - 390);
                    stage.addActor(icon);

                    // Highlight new
                    selectedCharacterButton = button;
                    App.setHeroOfChoice(selectedCharacter);
                    selectionFrame.setVisible(true);
                }
            });

            characterTable.add(singleCharacter).pad(10);
        }

        // Weapons
        Table weaponTable = new Table();

        for (Weapon weapon : weaponList) {
            TextureRegionDrawable up = new TextureRegionDrawable(new TextureRegion(weapon.icon));
            ImageButton button = new ImageButton(up);

            Label name = new Label(weapon.getName(), skin);

            Image selectionFrame2 = new Image(new Texture("selection-border2.png"));
            selectionFrame2.setVisible(false); // Hide by default

            Stack imageStack = new Stack();
            imageStack.add(button);
            imageStack.add(selectionFrame2);

            Table singleWeapon = new Table();
            singleWeapon.add(imageStack).size(128, 128).row(); // weapon icon + border
            singleWeapon.add(name).padTop(5); // weapon name below

            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    selectedWeapon = weapon;

                    // Unselect previous
                    if (selectedWeaponButton != null) {
                        ((Stack) selectedWeaponButton.getParent()).getChildren().get(1).setVisible(false);
                    }

                    selectedWeaponButton = button;
                    App.setWeaponOfChoice(selectedWeapon);
                    selectionFrame2.setVisible(true); // Highlight
                }
            });

            weaponTable.add(singleWeapon).pad(10);
        }

        Table timeTable = new Table();
        for (String time : timeList) {
            Label label = new Label(time, skin);

            Image selectionFrame2 = new Image(new Texture("selection-border2.png"));
            selectionFrame2.setVisible(false); // Hide by default

            Stack stack = new Stack();
            stack.add(label);
            stack.add(selectionFrame2);

            label.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    // Deselect previous
                    if (selectedTimeStack != null) {
                        selectedTimeStack.getChildren().get(1).setVisible(false); // Hide old selection frame
                    }

                    // Set current as selected
                    selectedTime = time;
                    App.setTimeOfChoice(selectedTime);
                    selectedTimeStack = stack;
                    selectionFrame2.setVisible(true); // Show highlight
                }
            });

            timeTable.add(stack).pad(10).size(128, 64); // Size adjusted for text
        }

        Label heroTitle = new Label("Select Your Character", skin);
        heroTitle.setPosition( 60, stage.getHeight() - 50);
        stage.addActor(heroTitle);
        characterTable.setPosition(heroTitle.getX() + 750, heroTitle.getY() -  characterTable.getHeight() - 160);
        stage.addActor(characterTable);

        Label weaponTitle = new Label("Select Your Weapon", skin);
        weaponTitle.setPosition( 60, stage.getHeight() - 400);
        stage.addActor(weaponTitle);
        weaponTable.setPosition(weaponTitle.getX() + 190, weaponTitle.getY() -  weaponTable.getHeight() - 90);
        stage.addActor(weaponTable);

        Label timeTitle = new Label("Select Game Time", skin);
        timeTitle.setPosition( 60, stage.getHeight() - 630);
        stage.addActor(timeTitle);
        timeTable.setPosition(timeTitle.getX() + 270, timeTitle.getY() -  timeTable.getHeight() - 30); // Custom position (optional)
        stage.addActor(timeTable);


        startButton.setWidth(primitiveIcon.getWidth());
        startButton.setPosition(1720 - 200 , 100);

        logoImage.setHeight(logoImage.getHeight()*1.5f);
        logoImage.setWidth(logoImage.getWidth()*1.5f);
        logoImage.setPosition(60 , 70);
        stage.addActor(logoImage);

        backButton.setPosition(20, 20);
        stage.addActor(backButton);

        startButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (selectedCharacter != null && selectedWeapon != null) {
                    // Pass to game
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new GameView(new GameController(), AssetManager.getAssetManager().getSkin()));
                }
            }
        });

        stage.addActor(startButton);
    }

    private void loadCharactersAndWeapons() {
        characterList = new ArrayList<>();
        weaponList = new ArrayList<>();
        timeList = new ArrayList<>();

        characterList.add(new Player("Shana", new Texture("Heroes/Shana2.png"), "Fast, high crit rate", 4, 4f, new Texture("Heroes/T_Shana_Portrait.png")));
        characterList.add(new Player("Diamond", new Texture("Heroes/Diamond.png"), "Area control", 7, 1f, new Texture("Heroes/T_Diamond_Portrait.png")));
        characterList.add(new Player("Scarlett", new Texture("Heroes/Scarlett.png"), "Fast, high crit rate", 3, 5f, new Texture("Heroes/T_Scarlett_Portrait.png")));
        characterList.add(new Player("Lilith", new Texture("Heroes/Lilith.png"), "Area control", 5, 3f, new Texture("Heroes/T_Lilith_Portrait.png")));
        characterList.add(new Player("Dasher", new Texture("Heroes/Dasher3.png"), "Area control", 2, 10f, new Texture("Heroes/T_Dasher_Portrait.png")));

        weaponList.add(new Weapon("Revolver", new Texture("weapons/revolver.png"),AssetManager.getAssetManager().getSmgTexture() ,20, 1 , 6));
        weaponList.add(new Weapon("Shotgun", new Texture("weapons/shotgun.png"), AssetManager.getAssetManager().getSmgTexture(),10, 1 , 2));
        weaponList.add(new Weapon("SMGs Dual", new Texture("weapons/smgdual.png"), AssetManager.getAssetManager().getSmgTexture(),8, 2,24));

        timeList.add("   2 Minutes");
        timeList.add("   5 Minutes");
        timeList.add("   10 Minutes");
        timeList.add("   20 Minutes");
    }
    @Override
    public void show() {

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


    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }

    public Player getSelectedCharacter() {
        return selectedCharacter;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public ImageButton getSelectedWeaponButton() {
        return selectedWeaponButton;
    }

    public ImageButton getSelectedCharacterButton() {
        return selectedCharacterButton;
    }

    public TextButton getStartButton() {
        return startButton;
    }


}
