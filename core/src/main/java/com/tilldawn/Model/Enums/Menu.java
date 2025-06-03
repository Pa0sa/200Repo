package com.tilldawn.Model.Enums;

import com.badlogic.gdx.Screen;
import com.tilldawn.Control.MenuController.*;
import com.tilldawn.Model.AssetManager;
import com.tilldawn.View.PreGameMenus.*;

public enum Menu {
    MainMenu(new MainMenu( new MainMenuController(), AssetManager.getAssetManager().getSkin())),
    ProfileMenu(new ProfileMenu( new ProfileMenuController(), AssetManager.getAssetManager().getSkin())),
    RegisterMenu(new RegisterMenu(new RegisterMenuController(), AssetManager.getAssetManager().getSkin())),
    PreGameMenu(new PreProgram( new PreProgramController(),AssetManager.getAssetManager().getSkin())),
    LoginMenu(new LoginMenu(new LoginMenuController(), AssetManager.getAssetManager().getSkin()));

    private final Screen screen;

    Menu(Screen screen) {
        this.screen = screen;
    }

    public Screen getScreen() {
        return screen;
    }
}
