package com.racetacticbattle.game.Inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.racetacticbattle.game.MainGame;
import com.racetacticbattle.game.MenuModels.LoadingDialog;
import com.racetacticbattle.game.MenuModels.MenuDialog;
import com.racetacticbattle.game.Models.User;
import com.racetacticbattle.game.Screens.LoginRegisterAbstractScreen;
import com.racetacticbattle.game.Screens.LoginScreen;
import com.racetacticbattle.game.Screens.ScreenType;

import java.util.ArrayList;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.functional.BiConsumer;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class LoginRegisterInputProcess extends CustomInputProcessor {

    ArrayList<TextField> menuInputs;

    public LoginRegisterInputProcess(MainGame context, ArrayList<TextButton> menuButtons, ArrayList<TextField> menuInputs, Stage stage, InputMultiplexer inputMultiplexer, LoadingDialog loadingDialog) {
        super(context, menuButtons, stage, inputMultiplexer, loadingDialog);
        this.menuInputs = menuInputs;

    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        Vector2 touchPoint = new Vector2(Gdx.input.getX(), Gdx.input.getY());
//        touchPoint = viewport.unproject(touchPoint);
        switch (screenType) {
            case LOGIN:
                handleTouchForLogin();
                break;
            case REGISTER:
                handleTouchForRegister();
                break;
            default:
                break;
        }
        return false;
    }

    private void handleTouchForLogin() {
        if (menuButtons.get(0).isPressed()) {
            loadingDialog.show(menuButtons, menuInputs);
            if (!menuInputs.get(0).getText().equals("") && menuInputs.get(0).getText() != null &&
                    !menuInputs.get(1).getText().equals("") && menuInputs.get(1).getText() != null) {
                GdxFIRAuth.inst()
                        .signInWithEmailAndPassword(menuInputs.get(0).getText(), menuInputs.get(1).getText().toCharArray())
                        .then(new Consumer<GdxFirebaseUser>() {
                            @Override
                            public void accept(GdxFirebaseUser gdxFirebaseUser) {
                                loadingDialog.clear(menuButtons, menuInputs);
                                menuButtons.clear();
                                menuInputs.clear();
                                inputMultiplexer.clear();
                                stage.clear();
                                loginScreen.loggedIn = true;
                            }
                        }).fail(new BiConsumer<String, Throwable>() {
                            @Override
                            public void accept(String s, Throwable throwable) {

                                loadingDialog.clear(menuButtons, menuInputs);
                                menuDialog = new MenuDialog("Sign in failed");
                                menuDialog.showText(s, stage);
                            }
                        });
            } else {
                loadingDialog.clear(menuButtons, menuInputs);
                menuDialog = new MenuDialog("Error with login");
                menuDialog.showText("Inputs are empty", stage);
            }
        } else if (menuButtons.get(1).isPressed()) {
            context.setScreen(ScreenType.REGISTER);
        }
    }

    private void handleTouchForRegister() {

        if (menuButtons.get(0).isPressed()) {
            loadingDialog.show(menuButtons, menuInputs);
            if (!menuInputs.get(0).getText().equals("") && menuInputs.get(0).getText() != null &&
                    !menuInputs.get(1).getText().equals("") && menuInputs.get(1).getText() != null &&
                    !menuInputs.get(2).getText().equals("") && menuInputs.get(2).getText() != null &&
                    !menuInputs.get(3).getText().equals("") && menuInputs.get(3).getText() != null) {
                if (menuInputs.get(2).getText().equals(menuInputs.get(3).getText())) {
                    GdxFIRAuth.inst()
                            .createUserWithEmailAndPassword(menuInputs.get(1).getText(), menuInputs.get(2).getText().toCharArray())
                            .then(new Consumer<GdxFirebaseUser>() {
                                @Override
                                public void accept(GdxFirebaseUser gdxFirebaseUser) {
                                    User userTmp = new User(menuInputs.get(0).getText(), menuInputs.get(1).getText());
                                    GdxFIRDatabase.inst()
                                            .inReference("/Users/" + GdxFIRAuth.inst().getCurrentUser().getUserInfo().getUid() + "")
                                            .setValue(userTmp);
//                                                        gdxFirebaseUser.delete().subscribe();

                                    loadingDialog.clear(menuButtons, menuInputs);
//                                    menuDialog = new MenuDialog("Great success");
//                                    menuDialog.showText("Successfully registered!", stage);
                                }
                            })
                            .fail(new BiConsumer<String, Throwable>() {
                                @Override
                                public void accept(String s, Throwable throwable) {
                                    loadingDialog.clear(menuButtons, menuInputs);
                                    menuDialog = new MenuDialog("Error with Register");
                                    menuDialog.showText(s, stage);
                                    if (s.contains("The email address is already in use by another account")) {
//                                        GdxFIRAuth.inst().getCurrentUser().delete().subscribe();
                                    }
                                }
                            });
                } else {
                    loadingDialog.clear(menuButtons, menuInputs);
                    menuDialog = new MenuDialog("Error with Register");
                    menuDialog.showText("Passwords are not same", stage);
                }
            } else {
                loadingDialog.clear(menuButtons, menuInputs);
                menuDialog = new MenuDialog("Error with Register");
                menuDialog.showText("Inputs are empty", stage);
            }
        } else if (menuButtons.get(1).isPressed()) {
            context.setScreen(ScreenType.LOGIN);
        }
    }
}
