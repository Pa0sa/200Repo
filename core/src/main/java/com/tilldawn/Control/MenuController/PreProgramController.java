package com.tilldawn.Control.MenuController;

import com.badlogic.gdx.Gdx;
import com.tilldawn.Main;
import com.tilldawn.Model.App;
import com.tilldawn.Model.User;
import com.tilldawn.View.PreGameMenus.LoginMenu;
import com.tilldawn.View.PreGameMenus.MainMenu;
import com.tilldawn.View.PreGameMenus.PreProgram;
import com.tilldawn.View.PreGameMenus.RegisterMenu;
import com.tilldawn.Model.AssetManager;

import static com.tilldawn.Main.getMain;


public class PreProgramController {

    private PreProgram view;

    public void setView(PreProgram view) {
        this.view = view;
    }

    public void handleMainMenuButtons() {
        if (view != null) {
            if (view.getRegisterButton().isPressed()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new RegisterMenu(new RegisterMenuController(), AssetManager.getAssetManager().getSkin()));

            } else if (view.getLoginButton().isPressed()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new LoginMenu(new LoginMenuController(), AssetManager.getAssetManager().getSkin()));
            }
            else if (view.getSkipButton().isPressed()) {
                int rand  = (int) (Math.random() * 14) + 1;

                User user = new User("Guest" , " " ,"avatars/(" + rand +").png" , " " , " " , 0 , 0 , 0);
                App.setCurrentUser(user);
                getMain().getScreen().dispose();
                getMain().setScreen(new MainMenu( new MainMenuController(),AssetManager.getAssetManager().getSkin()));


            } else if (view.getExitButton().isPressed()) {
                if(!App.getCurrentUser().getUserName().equals("Guest")) {
                    App.saveUsers();
                }
                Main.getMain().getScreen().dispose();
                Gdx.app.exit();
            }

        }
    }
}
