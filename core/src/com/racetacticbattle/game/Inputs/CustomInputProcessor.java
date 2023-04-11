package com.racetacticbattle.game.Inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.racetacticbattle.game.MainGame;
import com.racetacticbattle.game.MenuModels.LoadingDialog;
import com.racetacticbattle.game.MenuModels.MenuDialog;
import com.racetacticbattle.game.Models.User;
import com.racetacticbattle.game.Screens.LoginScreen;
import com.racetacticbattle.game.Screens.MainMenuScreen;
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
    ArrayList<TextButton> menuButtons;
    Stage stage;
    ScreenType screenType;
    MenuDialog menuDialog;
    InputMultiplexer inputMultiplexer;
    LoadingDialog loadingDialog;
    LoginScreen loginScreen;
    MainMenuScreen mainMenuScreen;

    public CustomInputProcessor(MainGame context, StretchViewport viewport, ArrayList<TextButton> menuButtons, Stage stage, InputMultiplexer inputMultiplexer, LoadingDialog loadingDialog) {
        this.context = context;
        this.viewport = viewport;
        this.menuButtons = menuButtons;
        this.stage = stage;
        this.inputMultiplexer = inputMultiplexer;
        this.loadingDialog = loadingDialog;
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
        if (menuDialog != null) {
            menuDialog.clear();
        }
        stage.unfocusAll();
        Gdx.input.setOnscreenKeyboardVisible(false);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
        this.screenType = screenType;
    }

    public void setLoginContext(LoginScreen loginScreen) {
        this.loginScreen = loginScreen;
    }

    public void setMainMenuScreenContext(MainMenuScreen mainMenuScreen) {
        this.mainMenuScreen = mainMenuScreen;
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
