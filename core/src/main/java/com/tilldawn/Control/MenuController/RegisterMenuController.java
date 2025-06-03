package com.tilldawn.Control.MenuController;

import com.tilldawn.Model.App;
import com.tilldawn.Model.AssetManager;
import com.tilldawn.Model.User;
import com.tilldawn.View.PreGameMenus.PreProgram;
import com.tilldawn.View.PreGameMenus.RegisterMenu;

import static com.tilldawn.Main.getMain;

public class RegisterMenuController {

    private RegisterMenu view;

    public void setView(RegisterMenu view) {
        this.view = view;
    }

    public void handleMainMenuButtons() {
        if (view != null) {
            if (view.getConfirmButton().isPressed() && !view.isShowPasswordError() && !view.isShowUsernameError()) {
                int rand = (int)(Math.random() * 14) + 1;
                String avatar = "avatars/ (" + rand + ").png";
                App.getUsers().add(new User(view.getUserNameField().getText(), view.getPasswordField().getText()
                    , avatar , view.getSecurityQuestion().getSelected().toString(), view.getSecurityQuestionField().getText() , 0 , 0 , 0));

                getMain().getScreen().dispose();
                getMain().setScreen(new PreProgram( new PreProgramController(),AssetManager.getAssetManager().getSkin()));

            } else if (view.getBackButton().isPressed()){
                getMain().getScreen().dispose();
                getMain().setScreen(new PreProgram( new PreProgramController(),AssetManager.getAssetManager().getSkin()));
            }

        }
    }

}
