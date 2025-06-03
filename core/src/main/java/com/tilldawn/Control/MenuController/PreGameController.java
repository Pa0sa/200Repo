package com.tilldawn.Control.MenuController;

import com.tilldawn.Model.AssetManager;
import com.tilldawn.View.PreGameMenus.MainMenu;
import com.tilldawn.View.PreGameMenus.PreGameMenu;

import static com.tilldawn.Main.getMain;

public class PreGameController {

    private PreGameMenu view;

    public void setView(PreGameMenu view) {
        this.view = view;
    }

    public void handleMainMenuButtons() {
        if (view != null) {
            if(view.getBackButton().isPressed()) {
                getMain().getScreen().dispose();
                getMain().setScreen(new MainMenu( new MainMenuController(),AssetManager.getAssetManager().getSkin()));
            }
            else if(view.getStartButton().isPressed()) {
                //App.setCurrentGame(new Game(view.getSelectedCharacter(),view.getSelectedWeapon(),view.getSelectedTime()));
//                Main.getMain().getScreen().dispose();
//                Main.getMain().setScreen(new GameView(new GameController(), AssetManager.getAssetManager().getSkin()));
            }
        }
    }
}
