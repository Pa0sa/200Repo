package com.tilldawn.Control.MenuController;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.tilldawn.Model.App;
import com.tilldawn.Model.AssetManager;
import com.tilldawn.Model.User;
import com.tilldawn.View.PreGameMenus.LoginMenu;
import com.tilldawn.View.PreGameMenus.MainMenu;
import com.tilldawn.View.PreGameMenus.PreProgram;

import static com.tilldawn.Main.getMain;

public class LoginMenuController {

    private LoginMenu view;

    public void setView(LoginMenu view) {
        this.view = view;
    }

    public void handleMainMenuButtons() {
        if (view != null) {

            if (view.getConfirmButton().isPressed()) {

                Label passwordError = new Label("HE HE" , AssetManager.getAssetManager().getSkin());
                passwordError.setPosition(100, 100);
                //Texture avatar = new Texture("avatar.png");

                for(User user: App.getUsers()) {

                    if(user.getUserName().equals(view.getUserNameField().getText()) && user.getPassword().equals(view.getPasswordField().getText())) {
                        App.setCurrentUser(user);
                        getMain().getScreen().dispose();
                        getMain().setScreen(new MainMenu( new MainMenuController(),AssetManager.getAssetManager().getSkin()));
                    } else if(user.getUserName().equals(view.getUserNameField().getText()) && !user.getPassword().equals(view.getPasswordField().getText())){
                        view.getStage().addActor(passwordError);
                    }
                }

            } else if(view.getForgotButton().isPressed()) {
                view.changeStageToForget();
            } else if (view.getSubmitButton().isPressed()) {

                for(User user: App.getUsers()) {
                    if(user.getUserName().equals(view.getUserNameField().getText())) {
                        if(user.getSecurityQuestionAnswer().equals(view.getSecurityQuestionField().getText())
                        && user.getSelectedSecurityQuestion().equals(view.getSecurityQuestion().getSelected().toString())) {
                            if(view.getResetPasswordField().getText().matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%&*()_]).{8,}$")) {
                                user.setPassword(view.getResetPasswordField().getText());
                                view.changeStageNormal();
                            } else {

                            }
                        }
                    }
                }
            }

            else if (view.getBackButton().isPressed()){
                getMain().getScreen().dispose();
                getMain().setScreen(new PreProgram( new PreProgramController(),AssetManager.getAssetManager().getSkin()));
            } else if (view.getBackButton2().isPressed()){
                view.changeStageNormal();
            }

        }
    }
}
