package com.racetacticbattle.game.Inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.racetacticbattle.game.Helpers.Common;
import com.racetacticbattle.game.MainGame;
import com.racetacticbattle.game.MenuModels.LoadingDialog;
import com.racetacticbattle.game.Models.Player;
import com.racetacticbattle.game.Models.User;
import com.racetacticbattle.game.Screens.GameScreen;

import java.util.ArrayList;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;

public class GameInputProcessor extends CustomInputProcessor {
    ArrayList<ImageButton> gameImageButtons;
    GdxFIRDatabase gdxFIRDatabase;
    Player thisPlayer, opponentPlayer;
    User playerUser;

    GameScreen context;

    public GameInputProcessor(GameScreen context, ArrayList<ImageButton> gameImageButtons, Stage stage, InputMultiplexer inputMultiplexer,
                              LoadingDialog loadingDialog, Player thisPlayer, Player opponentPlayer, User playerUser) {
        super(stage, inputMultiplexer, loadingDialog);
        this.gameImageButtons = gameImageButtons;
        this.gdxFIRDatabase = GdxFIRDatabase.inst();
        this.thisPlayer = thisPlayer;
        this.opponentPlayer = opponentPlayer;
        this.playerUser = playerUser;
        this.context = context;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        Gdx.app.log("App", "Common.firebaseUId:");
        String[] races = {"dwarfs", "elfs", "humans", "orcs"};
        int i = 0;
        for (ImageButton imageButton : gameImageButtons) {
            if (imageButton.isPressed()) {
                gdxFIRDatabase.inReference("/Rooms/" + playerUser.getRoom() + "/" + playerUser.getUsername() + "/house")
                        .setValue(races[i]);
                context.setThisPlayerPickedHouse(true);
                break;
            }
            i++;
        }
        return super.touchUp(screenX, screenY, pointer, button);

    }

}
