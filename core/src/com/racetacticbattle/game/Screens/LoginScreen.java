package com.racetacticbattle.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.racetacticbattle.game.Helpers.Common;
import com.racetacticbattle.game.MainGame;
import com.racetacticbattle.game.MenuModels.MenuButton;
import com.racetacticbattle.game.MenuModels.MenuInput;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.functional.BiConsumer;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class LoginScreen extends LoginRegisterAbstractScreen {
    public LoginScreen(MainGame context) {
        super(context);
        int j = 1;
        customInputProcessor.setScreenType(ScreenType.LOGIN);
        for (int i = 0; i < 2; i++) {
            String buttonTitle;
            if (i == 0) {
                buttonTitle = "Login";
            } else {
                buttonTitle = "Register";
            }
            menuButtons.add(new MenuButton(button, pressed_texture, i, j, buttonTitle));
            j += 2;
        }
        j = 1;
        for (int i = 0; i < 2; i++) {
            String textInput;
            if (i == 0) {
                textInput = "email";
            } else {
                textInput = "Password";

            }
            MenuInput menuInputTmp = new MenuInput(skin, i == 1 || i == 2, j, 1, ScreenType.LOGIN, textInput);
            menuInputs.add(menuInputTmp);
            stage.addActor(menuInputTmp);

            j += 2;

        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch2d.begin();
        batch2d.setProjectionMatrix(camera2d.combined);
        Color c = batch2d.getColor();
        batch2d.setColor(c.r, c.g, c.b, .5f);
        batch2d.draw(background, 0, 0, Common.WORLD_WIDTH, Common.WORLD_HEIGHT);
        batch2d.setColor(c.r, c.g, c.b, 1);

        for (MenuButton menuButton : menuButtons) {
            menuButton.draw(batch2d);
        }
        handleInput();
        stage.draw();
        stage.act();
        batch2d.end();
    }

    void handleInput() {
        inputMultiplexer.addProcessor(customInputProcessor);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
