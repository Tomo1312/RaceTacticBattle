package com.racetacticbattle.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.racetacticbattle.game.Helpers.Common;
import com.racetacticbattle.game.MainGame;

public class RegisterScreen extends LoginRegisterAbstractScreen {
    public RegisterScreen(MainGame context) {
        super(context);
        customInputProcessor.setScreenType(ScreenType.REGISTER);
        int j = 1;
        int boxDivider = 7;
        for (int i = 0; i < 2; i++) {
            String buttonTitle;
            if (i == 0) {
                buttonTitle = "Register";
            } else {
                buttonTitle = "Login";
            }
            float positionX;
            if (j < 2) {
                positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.95f;
            } else {
                positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.95f / 2;
            }
            float positionY = Common.WORLD_HEIGHT - Common.buttonContainerHeight + Common.buttonContainerHeight / 12;

            TextButton menuButton = new TextButton(buttonTitle, skin);
            menuButton.setPosition(positionX, positionY);
            menuButton.setSize(Common.buttonContainerWidth / 2, Common.buttonContainerHeight / 6);
            menuButtons.add(menuButton);
            stage.addActor(menuButton);
            j += 2;
        }

        float positionX;
        float positionY;
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
            if (i < 2) {
                positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.95f;
            } else {
                positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.95f / 2;
            }
            positionY = Common.buttonContainerHeight - Common.buttonContainerHeight / boxDivider * j;

            TextField menuInputTmp = new TextField("", skin);
            menuInputTmp.setPosition(positionX, positionY);
            menuInputTmp.setSize(Common.buttonContainerWidth / 2, Common.buttonContainerHeight / 6);

            menuInputTmp.setMessageText(textInput);
            menuInputTmp.setPasswordCharacter('*');
            menuInputTmp.setPasswordMode(i == 3 || i == 2);
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
//        batch2d.setColor(c.r, c.g,c.b, 1);
        handleInput();
        batch2d.end();
        stage.draw();
        stage.act();
        loadingDialog.draw(delta, batch2d);
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
