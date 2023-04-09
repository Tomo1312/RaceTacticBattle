package com.racetacticbattle.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.racetacticbattle.game.Helpers.Common;
import com.racetacticbattle.game.MainGame;

public class LoginScreen extends LoginRegisterAbstractScreen {
    public boolean loggedIn = false;

    public LoginScreen(MainGame context) {
        super(context);
        int j = 1;
        float positionX;
        float positionY;
        int boxDivider = 7;
        customInputProcessor.setScreenType(ScreenType.LOGIN);
        customInputProcessor.setLoginContext(this);
        for (int i = 0; i < 2; i++) {
            String buttonTitle;
            if (i == 0) {
                buttonTitle = "Login";
            } else {
                buttonTitle = "Register";
            }
            if (j < 2) {
                positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.95f;
            } else {
                positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.95f / 2;
            }
            positionY = Common.WORLD_HEIGHT - Common.buttonContainerHeight + Common.buttonContainerHeight / 12;

            TextButton menuButton = new TextButton(buttonTitle, skin);
            menuButton.setPosition(positionX, positionY);
            menuButton.setSize(Common.buttonContainerWidth / 2, Common.buttonContainerHeight / 6);
            menuButtons.add(menuButton);
            stage.addActor(menuButton);
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
            TextField menuInputTmp = new TextField("", skin);
            positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.95f;
            positionY = Common.buttonContainerHeight - Common.buttonContainerHeight / boxDivider * j;
            menuInputTmp.setPosition(positionX, positionY);

            menuInputTmp.setSize(Common.buttonContainerWidth, Common.buttonContainerHeight / 6);
//        setColor(Color.GRAY);
            menuInputTmp.setMessageText(textInput);
            menuInputTmp.setPasswordCharacter('*');
            menuInputTmp.setPasswordMode(i == 1);
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
        batch2d.setColor(c.r, c.g, c.b, 0.5f);
        batch2d.draw(background, 0, 0, Common.WORLD_WIDTH, Common.WORLD_HEIGHT);
        handleInput();
        batch2d.end();
        stage.draw();
        stage.act();
        loadingDialog.draw(delta, batch2d);

        if (loggedIn)
            context.setScreen(ScreenType.MAIN_MENU);
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
