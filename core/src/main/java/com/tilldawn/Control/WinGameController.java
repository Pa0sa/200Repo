package com.tilldawn.Control;

import com.tilldawn.Control.MenuController.MainMenuController;
import com.tilldawn.Model.App;
import com.tilldawn.Model.AssetManager;
import com.tilldawn.View.LoseGameMenu;
import com.tilldawn.View.PreGameMenus.MainMenu;
import com.tilldawn.View.WinGameMenu;

import static com.tilldawn.Main.getMain;

public class WinGameController {

    private WinGameMenu view;

    public void setView(WinGameMenu view) {
        this.view = view;
    }


    public void handleMainMenuButtons() {
        if (view != null) {
            if(view.getBackButton().isPressed()){

                App.saveUsers();
                getMain().getScreen().dispose();
                getMain().setScreen(new MainMenu( new MainMenuController(), AssetManager.getAssetManager().getSkin()));
            }
        }
    }
}
