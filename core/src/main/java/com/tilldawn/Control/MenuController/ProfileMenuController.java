package com.tilldawn.Control.MenuController;

import com.badlogic.gdx.Gdx;
import com.tilldawn.Main;
import com.tilldawn.Model.App;
import com.tilldawn.Model.AssetManager;
import com.tilldawn.View.Components.FileChooser;
import com.tilldawn.View.PreGameMenus.MainMenu;
import com.tilldawn.View.PreGameMenus.PreProgram;
import com.tilldawn.View.PreGameMenus.ProfileMenu;

import static com.tilldawn.Main.getMain;

public class ProfileMenuController {

    private ProfileMenu view;

    public void setView(ProfileMenu view) {
        this.view = view;
    }

    public void handleProfileMenuButtons() {
        if (view != null) {
            if (view.getBackButton().isPressed()){
                getMain().getScreen().dispose();
                getMain().setScreen(new MainMenu( new MainMenuController(), AssetManager.getAssetManager().getSkin()));
            } else if(view.getAccountDeletionButton().isPressed()){
                if(!App.getCurrentUser().getUserName().equals("Guest")) {
                    App.getUsers().remove(App.getCurrentUser());
                    App.setCurrentUser(null);
                }
                getMain().getScreen().dispose();
                getMain().setScreen(new PreProgram( new PreProgramController(),AssetManager.getAssetManager().getSkin()));
            } else if (view.getChangeUsernameButton().isPressed() && !view.isShowUsernameError() && !view.getUserNameField().getText().isEmpty()) {
                App.getCurrentUser().setUserName(view.getUserNameField().getText());
            } else if (view.getChangePasswordButton().isPressed() && !view.isShowPasswordError()) {
                App.getCurrentUser().setPassword(view.getPasswordField().getText());
            } else if (view.getChangeAvatarButton().isPressed() && !view.isShowAvatarError()) {
                App.getCurrentUser().setAvatarPath(view.getAvatarField().getText());
            } else if (view.getChangeAvatarWindowButton().isPressed()) {
                FileChooser chooser = Main.getFileChooser();
                if (chooser != null) {
                    chooser.chooseFile(filePath -> {
                        System.out.println("Selected file: " + filePath);
                        // Run on LibGDX render thread to safely load texture
                        Gdx.app.postRunnable(() -> {
                            App.getCurrentUser().setAvatarPath(filePath);
                            App.getCurrentUser().loadAvatar(); // safe texture loading here
                            App.saveUsers(); // save if needed
                        });
                    });
                }
            }
        }
    }
}
