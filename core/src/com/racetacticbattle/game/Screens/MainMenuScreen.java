package com.racetacticbattle.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.racetacticbattle.game.Helpers.Common;
import com.racetacticbattle.game.MainGame;
import com.racetacticbattle.game.MenuModels.MainMenuInfo;

import java.util.ArrayList;

public class MainMenuScreen extends AbstractScreen {
    ArrayList<TextButton> menuButtons;
    ArrayList<ImageButton> infoButtons;
    ArrayList<TextField> menuInputs;
    MainMenuInfo mainMenuInfo;

    public MainMenuScreen(MainGame context) {
        super(context);
        //		GdxFIRAuth.inst().signOut();
        skin = new Skin(Gdx.files.internal("Screens/mySkin/MainMenuGame.json"));
        setupButtons();
        mainMenuInfo = new MainMenuInfo(skin);

        infoButtons = new ArrayList<>();
        mainMenuInfo.generateButtons(infoButtons, stage);
    }

    private void setupButtons() {
        menuButtons = new ArrayList<>();
        menuInputs = new ArrayList<>();
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
        }
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


        InputMultiplexer inputMultiplexer = new InputMultiplexer();
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

    @Override
    public void dispose() {
        stage.dispose();
    }
}
