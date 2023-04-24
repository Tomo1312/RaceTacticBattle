package com.racetacticbattle.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.racetacticbattle.game.Helpers.Common;
import com.racetacticbattle.game.Helpers.FirebaseHelper;
import com.racetacticbattle.game.Inputs.CustomInputProcessor;
import com.racetacticbattle.game.Inputs.GameInputProcessor;
import com.racetacticbattle.game.Inputs.LoginRegisterInputProcess;
import com.racetacticbattle.game.MainGame;
import com.racetacticbattle.game.MenuModels.LoadingDialog;
import com.racetacticbattle.game.Models.Player;
import com.racetacticbattle.game.Models.User;

import java.util.ArrayList;

import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.annotations.MapConversion;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class GameScreen extends AbstractScreen {

    boolean loading = true, opponentPickedHouse = false, thisPlayerPickedHouse = false, everyonePicked = false;

    GdxFIRDatabase gdxFIRDatabase;

    BitmapFont font;

    Player thisPlayer, opponentPlayer;
    User playerUser;
    ProgressBar progressBar;
    LoadingDialog loadingDialog;
    CustomInputProcessor customInputProcessor;

    float seconds = 0;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;

    ArrayList<ImageButton> pickHouseButtons;

    FirebaseHelper firebaseHelper;

    public GameScreen(MainGame context) {
        super(context);
        initFirebaseValues();
        initFont();
        progressBar = new ProgressBar(0, 5, 0.01f, false, skin);
        progressBar.setBounds(Common.WORLD_WIDTH / 2 - Common.WORLD_WIDTH * 0.6f / 2, Common.WORLD_HEIGHT / 2 - font.getCapHeight(), Common.WORLD_WIDTH * 0.6f, 50);
        stage.addActor(progressBar);
        loadingDialog = new LoadingDialog();
    }

    private void initFirebaseValues() {
        this.gdxFIRDatabase = GdxFIRDatabase.inst();
        gdxFIRDatabase.inReference("/Rooms/searchingForPlayer/" + Common.firebaseUId).removeValue();
        gdxFIRDatabase.inReference("/Users/" + Common.firebaseUId).readValue(User.class).then(new Consumer<User>() {
            @Override
            public void accept(User user) {
                playerUser = user;
                gdxFIRDatabase.inReference("/Rooms/" + user.getRoom()).readValue(ArrayList.class).then(new Consumer<ArrayList<Player>>() {
                    @MapConversion(Player.class)
                    @Override
                    public void accept(ArrayList<Player> players) {
                        for (Player playerTmp : players) {
                            if (playerTmp.getPlayerName().equals(playerUser.getUsername())) {
                                thisPlayer = playerTmp;
                            } else {
                                opponentPlayer = playerTmp;
                            }
                        }
                    }
                });
            }
        });

    }

    private void initFont() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Screens/mySkin/IMMORTAL.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 42;
        fontParameter.color = new Color(1, 1, 1, 1f);
        font = fontGenerator.generateFont(fontParameter);
//        font.getData().setScale(0.5f);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        batch2d.setProjectionMatrix(camera2d.combined);
        if (loading && thisPlayer != null && opponentPlayer != null && font != null)
            showLoadingBatch();
        else if (everyonePicked) {
            loadingDialog.setShowLoading(false);

        } else {
            loadingDialog.draw(delta, batch2d);
        }

        stage.draw();
        stage.act();
    }

    private void addPickHouse() {
        pickHouseButtons = new ArrayList<>();
        String[] races = {"dwarfs", "elfs", "humans", "orcs"};
        for (int i = 0; i < 4; i++) {
            ImageButton imageButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("Game/2d/House/" + races[i] + "Banner.png"))), new TextureRegionDrawable(new Texture(Gdx.files.internal("Game/2d/House/" + races[i] + "Banner.png"))));
            float imageButtonX = i % 2 == 0 ? 0 : Common.WORLD_WIDTH / 2;
            float imageButtonY = i > 1 ? 0 : Common.WORLD_HEIGHT / 2;
            imageButton.setPosition(imageButtonX, imageButtonY);

            imageButton.setSize(Common.WORLD_WIDTH / 2, Common.WORLD_HEIGHT / 2);
            pickHouseButtons.add(imageButton);
            stage.addActor(imageButton);
        }
        customInputProcessor = new GameInputProcessor(this, pickHouseButtons, stage, inputMultiplexer, loadingDialog, thisPlayer, opponentPlayer, playerUser);

        inputMultiplexer.addProcessor(customInputProcessor);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void showLoadingBatch() {
        batch2d.begin();
        font.draw(batch2d, thisPlayer.getPlayerName() + " VS " + opponentPlayer.getPlayerName(), 0, Common.WORLD_HEIGHT / 2 + font.getCapHeight(), Common.WORLD_WIDTH, Align.center, true);
        batch2d.end();
        seconds += 0.02f;
        progressBar.setValue(seconds);
        if (seconds >= 5) {
            loading = false;
            stage.clear();
            addPickHouse();
            firebaseHelper = new FirebaseHelper(this, thisPlayer, opponentPlayer, playerUser);
            firebaseHelper.addListenerFoHousePick();
        }
    }

    public boolean isEveryonePicked() {
        return everyonePicked;
    }

    public void setThisPlayerPickedHouse(boolean picked) {
        this.thisPlayerPickedHouse = picked;
        loadingDialog.setShowLoading(true);
        stage.clear();
        if (thisPlayerPickedHouse && opponentPickedHouse) {
            everyonePicked = true;
        }
    }

    public void setOpponentPlayerPickedHouse(boolean picked) {
        this.opponentPickedHouse = picked;
        Gdx.app.debug("GameScreen", "thisPlayerPickedHouse: " + thisPlayerPickedHouse + " opponentPickedHouse: " + opponentPickedHouse);
        if (thisPlayerPickedHouse && opponentPickedHouse)
            everyonePicked = true;
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

    }
}
