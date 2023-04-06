package com.racetacticbattle.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.racetacticbattle.game.Inputs.CustomInputProcessor;
import com.racetacticbattle.game.MainGame;
import com.racetacticbattle.game.MenuModels.LoadingDialog;
import com.racetacticbattle.game.MenuModels.MenuButton;
import com.racetacticbattle.game.MenuModels.MenuDialog;
import com.racetacticbattle.game.MenuModels.MenuInput;

import java.util.ArrayList;

public abstract class LoginRegisterAbstractScreen extends AbstractScreen {

    Texture background, button, pressed_texture;

    ArrayList<MenuButton> menuButtons;
    ArrayList<MenuInput> menuInputs;
    //    TextField txtUsername;
    CustomInputProcessor customInputProcessor;
    InputMultiplexer inputMultiplexer;
    Skin skin;
    LoadingDialog loadingDialog;
    MenuDialog menuDialog;
    public LoginRegisterAbstractScreen(MainGame context) {
        super(context);

        inputMultiplexer = new InputMultiplexer();
        skin = new Skin(Gdx.files.internal("Screens/mySkin/moj-skin.json"));
        background = new Texture("Screens/mainManuBg.png");
        button = new Texture("Screens/button.png");
        pressed_texture = new Texture("Screens/pressedTexture.png");
        menuButtons = new ArrayList<>();
        menuInputs = new ArrayList<>();
        loadingDialog = new LoadingDialog();
        customInputProcessor = new CustomInputProcessor(context,viewport,menuButtons,menuInputs,stage, inputMultiplexer, loadingDialog);
    }
    abstract void handleInput();

    @Override
    public void dispose() {
        batch2d.dispose();
        stage.dispose();
    }
}
