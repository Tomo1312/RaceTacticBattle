package com.racetacticbattle.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.racetacticbattle.game.Helpers.Common;
import com.racetacticbattle.game.MainGame;
import com.racetacticbattle.game.MenuModels.MenuButton;
import com.racetacticbattle.game.MenuModels.MenuInput;

public class RegisterScreen extends LoginRegisterAbstractScreen {
    public RegisterScreen(MainGame context) {
        super(context);
        customInputProcessor.setScreenType(ScreenType.REGISTER);
        int j = 1;
        for (int i = 0; i < 2; i++) {
            String buttonTitle;
            if (i == 0) {
                buttonTitle = "Register";
            } else {
                buttonTitle = "Login";
            }
            menuButtons.add(new MenuButton(button, pressed_texture, i, j, buttonTitle));
            j += 2;
        }
        j = 1;
        for (int i = 0; i < 4; i++) {
            String textInput;
            if (i == 0) {
                textInput = "Username";
            } else if (i == 1) {
                textInput = "e-mail";

            } else if (i == 2) {
                textInput = "Password";
            } else {
                textInput = " Repeat Password";
            }
            MenuInput menuInputTmp = new MenuInput(skin, i == 3 || i == 2, i, j, ScreenType.REGISTER, textInput);
            menuInputs.add(menuInputTmp);
            stage.addActor(menuInputTmp);
            if (i == 1) {
                j = 1;
            } else {
                j += 2;
            }
        }
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
        inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(customInputProcessor);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    @Override
    public void show() {

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
