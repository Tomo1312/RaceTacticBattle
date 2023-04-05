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
import com.racetacticbattle.game.MenuModels.MenuButton;
import com.racetacticbattle.game.MenuModels.MenuInput;

import java.util.ArrayList;

public abstract class LoginRegisterAbstractScreen extends AbstractScreen {

    Texture background, button, pressed_texture;

    ArrayList<MenuButton> menuButtons;
    ArrayList<MenuInput> menuInputs;
    Stage stage;
    //    TextField txtUsername;
    CustomInputProcessor customInputProcessor;
    InputMultiplexer inputMultiplexer;

    Skin skin;
    public LoginRegisterAbstractScreen(MainGame context) {
        super(context);

        this.stage = new Stage(context.getViewport());
        inputMultiplexer = new InputMultiplexer();
        skin = new Skin(Gdx.files.internal("Screens/cloud/cloud-form-ui.json"));
        background = new Texture("Screens/mainManuBg.png");
        button = new Texture("Screens/button.png");
        pressed_texture = new Texture("Screens/pressedTexture.png");
        menuButtons = new ArrayList<>();
        menuInputs = new ArrayList<>();
        customInputProcessor = new CustomInputProcessor(context,viewport,menuButtons,menuInputs,stage);
    }
    abstract void handleInput();

    @Override
    public void dispose() {
        batch2d.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera2d.update();
    }
}
