package com.racetacticbattle.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.racetacticbattle.game.Helpers.Common;
import com.racetacticbattle.game.Inputs.CustomInputProcessor;
import com.racetacticbattle.game.Inputs.LoginRegisterInputProcess;
import com.racetacticbattle.game.Inputs.MainMenuInputProcessor;
import com.racetacticbattle.game.MainGame;
import com.racetacticbattle.game.MenuModels.LoadingDialog;
import com.racetacticbattle.game.MenuModels.MainMenuInfo;

import java.util.ArrayList;

public class MainMenuScreen extends AbstractScreen {
    ArrayList<TextButton> menuButtons;
    ArrayList<ImageButton> infoButtons;
    MainMenuInfo mainMenuInfo;
    MainMenuInputProcessor customInputProcessor;
    InputMultiplexer inputMultiplexer;
    LoadingDialog loadingDialog;
    boolean goToRoom;
    TextButton cancel;

    public MainMenuScreen(MainGame context) {
        super(context);
        //		GdxFIRAuth.inst().signOut();
        skin = new Skin(Gdx.files.internal("Screens/mySkin/MainMenuGame.json"));
        loadingDialog = new LoadingDialog();
        setupButtons();
        goToRoom = false;
    }

    private void setupButtons() {
        menuButtons = new ArrayList<>();
        float positionX;
        float positionY = Common.WORLD_HEIGHT - Common.WORLD_HEIGHT * 0.9f;
        String buttonTitle;
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    buttonTitle = "Exit";
                    positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.96f;
                    break;
                case 1:
                    buttonTitle = "Settings";
                    positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.96f + Common.buttonWidth;
                    break;
                case 2:
                    buttonTitle = "Collection";
                    positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.96f + Common.buttonWidth * 2;
                    break;
                case 3:
                    buttonTitle = "Vs Friend";
                    positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.96f + Common.buttonWidth * 2;
                    positionY = Common.WORLD_HEIGHT - Common.WORLD_HEIGHT * 0.9f + Common.buttonHeight;
                    break;
                default:
                    buttonTitle = "Vs Random";
                    positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.96f + Common.buttonWidth * 2;
                    positionY = Common.WORLD_HEIGHT - Common.WORLD_HEIGHT * 0.9f + Common.buttonHeight * 2;
                    break;
            }

            TextButton menuButton = new TextButton(buttonTitle, skin);
            menuButton.setPosition(positionX, positionY);
            menuButton.setSize(Common.buttonWidth, Common.buttonHeight);
            menuButtons.add(menuButton);
            stage.addActor(menuButton);

            infoButtons = new ArrayList<>();
            mainMenuInfo = new MainMenuInfo(skin);
            mainMenuInfo.generateButtons(infoButtons, stage);
        }
        customInputProcessor = new MainMenuInputProcessor(context, menuButtons, infoButtons, stage, inputMultiplexer, loadingDialog);
        customInputProcessor.setMainMenuScreenContext(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        batch2d.begin();
        batch2d.setProjectionMatrix(camera2d.combined);
        mainMenuInfo.draw(batch2d);
        batch2d.end();

        stage.draw();
        stage.act();
        loadingDialog.draw(delta, batch2d);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(customInputProcessor);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        if (goToRoom) {
            menuButtons.clear();
            infoButtons.clear();
            inputMultiplexer.clear();
            stage.clear();
            context.setScreen(ScreenType.GAME);
        }
    }

    public void showCancelButton() {
        cancel = new TextButton("CANCEL", skin);
        cancel.setPosition(Common.WORLD_WIDTH / 2 - Common.buttonWidth / 2, Common.WORLD_HEIGHT / 2 - Common.buttonHeight / 2);
        cancel.setSize(Common.buttonWidth, Common.buttonHeight);
        stage.addActor(cancel);
        customInputProcessor.setCancel(cancel);
    }

    public void deleteCancelButton() {
        if (cancel != null) {
            cancel.remove();
        }
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

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void setGoToRoom(boolean goToRoom) {
        this.goToRoom = goToRoom;
    }
}
