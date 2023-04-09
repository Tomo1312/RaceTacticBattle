package com.racetacticbattle.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.racetacticbattle.game.Inputs.CustomInputProcessor;
import com.racetacticbattle.game.MainGame;
import com.racetacticbattle.game.MenuModels.LoadingDialog;
import com.racetacticbattle.game.MenuModels.MenuDialog;

import java.util.ArrayList;

public abstract class LoginRegisterAbstractScreen extends AbstractScreen {

    Texture background, button, pressed_texture;

    ArrayList<TextButton> menuButtons;
    ArrayList<TextField> menuInputs;
    //    TextField txtUsername;
    CustomInputProcessor customInputProcessor;
    InputMultiplexer inputMultiplexer;
    LoadingDialog loadingDialog;

    public LoginRegisterAbstractScreen(MainGame context) {
        super(context);

        inputMultiplexer = new InputMultiplexer();
        background = new Texture("Screens/mainManuBg.png");
        button = new Texture("Screens/button.png");
        pressed_texture = new Texture("Screens/pressedTexture.png");
        menuButtons = new ArrayList<>();
        menuInputs = new ArrayList<>();
        loadingDialog = new LoadingDialog();
        customInputProcessor = new CustomInputProcessor(context, viewport, menuButtons, menuInputs, stage, inputMultiplexer, loadingDialog);
    }

    abstract void handleInput();

}
