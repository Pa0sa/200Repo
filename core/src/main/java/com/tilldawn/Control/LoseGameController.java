package com.tilldawn.Control;

import com.tilldawn.Control.MenuController.MainMenuController;
import com.tilldawn.Model.App;
import com.tilldawn.Model.AssetManager;
import com.tilldawn.View.LoseGameMenu;
import com.tilldawn.View.PreGameMenus.MainMenu;

import static com.tilldawn.Main.getMain;

public class LoseGameController {

    private LoseGameMenu view;

    public void setView(LoseGameMenu view) {
        this.view = view;
    }


    public void handleMainMenuButtons() {
        if (view != null) {
            if(view.getBackButton().isChecked()){
                App.saveUsers();
                getMain().getScreen().dispose();
                getMain().setScreen(new MainMenu( new MainMenuController(), AssetManager.getAssetManager().getSkin()));
            }
        }
    }
}
