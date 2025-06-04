package com.tilldawn.Control.MenuController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.tilldawn.Main;
import com.tilldawn.Model.App;
import com.tilldawn.Model.AssetManager;
import com.tilldawn.View.PreGameMenus.*;

import static com.tilldawn.Main.getMain;

public class MainMenuController {

    private MainMenu view;

    public void setView(MainMenu view) {
        this.view = view;
    }

    public void handleMainMenuButtons() {
        if (view != null) {
            if (view.getExitButton().isPressed()) {
                if(!App.getCurrentUser().getUserName().equals("Guest")) {
                    App.saveUsers();
                }
                Main.getMain().getScreen().dispose();
                Gdx.app.exit();
            } else if (view.getProfileButton().isPressed()) {
                getMain().getScreen().dispose();
                getMain().setScreen(new ProfileMenu( new ProfileMenuController(), AssetManager.getAssetManager().getSkin()));
            } else if (view.getPreGameButton().isPressed()) {
                getMain().getScreen().dispose();
                getMain().setScreen(new PreGameMenu(new PreGameController() , AssetManager.getAssetManager().getSkin()));
            } else if(view.getScoreBoardButton().isPressed()) {
                getMain().getScreen().dispose();
                getMain().setScreen(new LeaderBoardMenu(App.getUsers()));
            } else if(view.getSettingButton().isPressed()) {
                getMain().getScreen().dispose();
                getMain().setScreen(new SettingsMenu());
            } else if(view.getLogoutButton().isPressed()) {
                if(!App.getCurrentUser().getUserName().equals("Guest")) {
                    App.setCurrentUser(null);
                }
                getMain().getScreen().dispose();
                getMain().setScreen(new PreProgram( new PreProgramController(),AssetManager.getAssetManager().getSkin()));
            } else if (view.getTalentButton().isPressed()) {
                getMain().getScreen().dispose();
                getMain().setScreen(new TalentMenu(AssetManager.getAssetManager().getSkin(), SettingsMenu.getCurrentKeyBindings()));
            }
        }
    }
}
