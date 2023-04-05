package com.racetacticbattle.game.Inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.racetacticbattle.game.MainGame;
import com.racetacticbattle.game.MenuModels.MenuButton;
import com.racetacticbattle.game.MenuModels.MenuDialog;
import com.racetacticbattle.game.MenuModels.MenuInput;
import com.racetacticbattle.game.Models.User;
import com.racetacticbattle.game.Screens.ScreenType;


import java.util.ArrayList;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.functional.BiConsumer;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class CustomInputProcessor implements InputProcessor {

    protected final MainGame context;
    StretchViewport viewport;
    ArrayList<MenuButton> menuButtons;
    ArrayList<MenuInput> menuInputs;
    Stage stage;
    ScreenType screenType;
    MenuDialog menuDialog;

    public CustomInputProcessor(MainGame context, StretchViewport viewport, ArrayList<MenuButton> menuButtons, ArrayList<MenuInput> menuInputs, Stage stage) {
        this.context = context;
        this.viewport = viewport;
        this.menuButtons = menuButtons;
        this.menuInputs = menuInputs;
        this.stage = stage;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 touchPoint = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        touchPoint = viewport.unproject(touchPoint);
        for (MenuButton menuButton : menuButtons) {
            menuButton.isPressed(touchPoint.x, touchPoint.y);
        }
        if (menuDialog != null) {
            menuDialog.clear();
        }
        stage.unfocusAll();
        Gdx.input.setOnscreenKeyboardVisible(false);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 touchPoint = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        touchPoint = viewport.unproject(touchPoint);

        switch (screenType){
            case LOGIN:
                handleTouchForLogin(touchPoint);
                break;
            case REGISTER:
                handleTouchForRegister(touchPoint);
                break;
            default:
                break;
        }
        return false;
    }

    private void handleTouchForLogin(Vector2 touchPoint) {
        for (MenuButton menuButton : menuButtons) {
            if (menuButton.isPressedUp(touchPoint.x, touchPoint.y)) {
                switch (menuButton.getButtonId()) {
                    case 0:
                        if (!menuInputs.get(0).getText().equals("") && menuInputs.get(0).getText() != null &&
                                !menuInputs.get(1).getText().equals("") && menuInputs.get(1).getText() != null) {
                            GdxFIRAuth.inst()
                                    .signInWithEmailAndPassword(menuInputs.get(0).getText(), menuInputs.get(1).getText().toCharArray())
                                    .then(new Consumer<GdxFirebaseUser>() {
                                        @Override
                                        public void accept(GdxFirebaseUser gdxFirebaseUser) {
                                            menuButtons.clear();
                                            menuInputs.clear();
                                            context.setScreen(ScreenType.MAIN_MENU);
                                        }
                                    }).fail(new BiConsumer<String, Throwable>() {
                                        @Override
                                        public void accept(String s, Throwable throwable) {
                                            menuDialog = new MenuDialog("Sign in failed");
                                            menuDialog.showText(s, stage);
                                        }
                                    });
                        } else {
                            menuDialog = new MenuDialog("Error with login");
                            menuDialog.showText("Inputs are empty", stage);
                        }
                        break;
                    case 1:
                        context.setScreen(ScreenType.REGISTER);
                        break;
                }
            }
        }
    }

    private void handleTouchForRegister(Vector2 touchPoint) {
        for (MenuButton menuButton : menuButtons) {
            if (menuButton.isPressedUp(touchPoint.x, touchPoint.y)) {
                switch (menuButton.getButtonId()) {
                    case 0:
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

                                                menuDialog = new MenuDialog("Great success");
                                                menuDialog.showText("Successfully registered!", stage);
                                            }
                                        })
                                        .fail(new BiConsumer<String, Throwable>() {
                                            @Override
                                            public void accept(String s, Throwable throwable) {
                                                menuDialog = new MenuDialog("Error with Register");
                                                menuDialog.showText(s, stage);
                                                if (s.contains("The email address is already in use by another account")) {
                                                    GdxFIRAuth.inst().getCurrentUser().delete().subscribe();
                                                }
                                            }
                                        });
                            } else {
                                menuDialog = new MenuDialog("Error with Register");
                                menuDialog.showText("Passwords are not same", stage);
                            }
                        } else {
                            menuDialog = new MenuDialog("Error with Register");
                            menuDialog.showText("Inputs are empty", stage);
                        }
                        break;
                    case 1:
                        context.setScreen(ScreenType.LOGIN);
                        break;
                }
            }
            ;
        }
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 touchPoint = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        touchPoint = viewport.unproject(touchPoint);
        for (MenuButton menuButton : menuButtons) {
            menuButton.isDragged(touchPoint.x, touchPoint.y);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void setScreenType(ScreenType screenType) {
        this.screenType= screenType;
    }
}


//                                GdxFIRAuth.inst().signOut()
//                                        .then(new Consumer<Void>() {
//                                            @Override
//                                            public void accept(Void o) {
//                                                Gdx.app.debug("BUTTON", "Sing out");
//                                            }
//                                        });
//                                // Sign out

//                                Gdx.app.exit();
