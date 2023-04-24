package com.racetacticbattle.game.Helpers;

import com.badlogic.gdx.Gdx;
import com.racetacticbattle.game.Models.Player;
import com.racetacticbattle.game.Models.User;
import com.racetacticbattle.game.Screens.GameScreen;

import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.annotations.MapConversion;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.promises.ListenerPromise;

public class FirebaseHelper {
    GameScreen context;
    GdxFIRDatabase gdxFIRDatabase;
    ListenerPromise<String> listener;
    Player thisPlayer, opponentPlayer;
    User playerUser;

    public FirebaseHelper(GameScreen context, Player thisPlayer, Player opponentPlayer, User playerUser) {
        this.gdxFIRDatabase = GdxFIRDatabase.inst();
        this.thisPlayer = thisPlayer;
        this.opponentPlayer = opponentPlayer;
        this.context = context;
        this.playerUser = playerUser;
    }

    public void addListenerFoHousePick() {
        listener = gdxFIRDatabase
                .inReference("/Rooms/" + playerUser.getRoom() + "/" + opponentPlayer.getPlayerName() + "/house/")
                .onDataChange(String.class)
                .thenListener(new Consumer<String>() {
                    @MapConversion(String.class)
                    @Override
                    public void accept(String s) {
                        Gdx.app.debug("FirebaseHelper", "String: " + s);
                        if (!s.equals("")) {
                            Gdx.app.debug("FirebaseHelper", "String: " + s);
                            context.setOpponentPlayerPickedHouse(true);
                            listener.cancel();
                        }
                    }
                });
    }
}
