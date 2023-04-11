package com.racetacticbattle.game.Inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.racetacticbattle.game.Helpers.Common;
import com.racetacticbattle.game.MainGame;
import com.racetacticbattle.game.MenuModels.LoadingDialog;
import com.racetacticbattle.game.Models.SearchingRoomPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.annotations.MapConversion;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.promises.ListenerPromise;

public class MainMenuInputProcessor extends CustomInputProcessor {

    ArrayList<ImageButton> menuImageButtons;
    ListenerPromise<String> listener;
    int initialDelay = 1000; // start after 1 seconds
    int period = 1000;        // repeat every 1 seconds
    int seconds = 0;
    Timer timer;
    TimerTask task;

    final SearchingRoomPlayer playerTmp;
    final SearchingRoomPlayer opponentPlayer;
    String lastOpponentPlayerID = "";

    public MainMenuInputProcessor(MainGame context, StretchViewport viewport, ArrayList<TextButton> menuButtons, ArrayList<ImageButton> menuImageButtons, Stage stage, InputMultiplexer inputMultiplexer, LoadingDialog loadingDialog) {
        super(context, viewport, menuButtons, stage, inputMultiplexer, loadingDialog);
        this.menuImageButtons = menuImageButtons;
        this.playerTmp = new SearchingRoomPlayer(Common.firebaseUId);
        this.opponentPlayer = new SearchingRoomPlayer();
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                seconds++;
                if (seconds == 5) {
                    seconds = 0;
                    startLookingForRoom();
                    GdxFIRDatabase.inst()
                            .inReference("/Rooms/" + opponentPlayer.getOwnUId() + "/opponentPlayerUId")
                            .setValue("none");
                }
            }
        };
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        Gdx.app.error("MyTag", "Touch up");
        if (menuButtons.get(0).isPressed()) {

        } else if (menuButtons.get(1).isPressed()) {

        } else if (menuButtons.get(2).isPressed()) {

        } else if (menuButtons.get(3).isPressed()) {

        } else if (menuButtons.get(4).isPressed()) {
            loadingDialog.showWithImageButtons(menuButtons, menuImageButtons);
            startLookingForRoom();

        }
        return false;
    }

    private void startLookingForRoom() {

        GdxFIRDatabase.inst()
                .inReference("/Rooms/" + playerTmp.getOwnUId())
                .setValue(playerTmp);
        GdxFIRDatabase.inst()
                .inReference("/Rooms")
                .readValue(ArrayList.class)
                .then(new Consumer<ArrayList<SearchingRoomPlayer>>() {
                    @MapConversion(SearchingRoomPlayer.class)
                    @Override
                    public void accept(ArrayList<SearchingRoomPlayer> opponentPlayerTmps) {
//                            if (opponentPlayerTmp.opponentPlayerUId.equals("none")) {
//                                GdxFIRDatabase.inst()
//                                        .inReference("/Rooms/" + opponentPlayerTmp.ownUId + "/opponentPlayerUId")
//                                        .setValue(playerTmp.ownUId);
//                            } else
//                        for (int i = 0; i < opponentPlayerTmps.size(); i++) {
//                            Gdx.app.log("App", "opponentPlayerTmps.get(" + i + "):" + opponentPlayerTmps.get(i));

//                            Gdx.app.log("App", "opponentPlayerTmps.getOpponentPlayerUId(" + i + "):" + opponentPlayerTmps.get(i).getOwnUId());

//                        }
                        for (SearchingRoomPlayer opponentPlayerTmp : opponentPlayerTmps) {
//                            if (opponentPlayerTmp.getOwnUId().equals(playerTmp.getOpponentPlayerUId()) && !opponentPlayerTmp.getOpponentPlayerUId().equals("none")) {
//                                Gdx.app.log("App", "Game Started: 1 ");
//                                listener.cancel();
//                                task.cancel();
//                                loadingDialog.clearWithImageButtons(menuButtons, menuImageButtons);
//                                break;
//                            } else
                            if (opponentPlayerTmp.getOpponentPlayerUId().equals("none") && !opponentPlayerTmp.getOwnUId().equals(playerTmp.getOwnUId())
                                    && !opponentPlayerTmp.getOwnUId().equals(lastOpponentPlayerID)) {
                                GdxFIRDatabase.inst()
                                        .inReference("/Rooms/" + opponentPlayerTmp.getOwnUId() + "/opponentPlayerUId")
                                        .setValue(playerTmp.getOwnUId());
                                opponentPlayer.setOwnUId(opponentPlayerTmp.getOwnUId());
                                opponentPlayer.setOpponentPlayerUId(opponentPlayerTmp.getOpponentPlayerUId());
                                lastOpponentPlayerID = opponentPlayerTmp.getOwnUId();
                                timer.schedule(task, initialDelay, period);
                                break;
                            }
                        }
                        startListener();
                    }
                });
    }

    private void startListener() {
        if (listener == null) {
            listener = GdxFIRDatabase.inst()
                    .inReference("/Rooms/" + playerTmp.getOwnUId() + "/opponentPlayerUId")
                    .onDataChange(String.class)
                    .thenListener(new Consumer<String>() {
                        @Override
                        public void accept(String opponentPlayerUId) {
                            if (opponentPlayerUId.equals("none")) {
                                Gdx.app.log("App", "new value: " + opponentPlayerUId);
                            } else if (opponentPlayerUId.contains(opponentPlayer.getOwnUId())) {
                                playerTmp.setOpponentPlayerUId(opponentPlayerUId);
                                if (!opponentPlayerUId.contains("dontsend")) {
                                    GdxFIRDatabase.inst()
                                            .inReference("/Rooms/" + opponentPlayerUId + "/opponentPlayerUId")
                                            .setValue(playerTmp.getOwnUId() + ",dontsend");
                                }
                                Gdx.app.log("App", "Game Started" + opponentPlayerUId);
                                listener.cancel();
                                task.cancel();
                                loadingDialog.clearWithImageButtons(menuButtons, menuImageButtons);
                                mainMenuScreen.setGoToRoom(true);
                            }

                        }
                    });
        }
    }
//
//    private void handleTouchForLogin() {
//        if (menuButtons.get(0).isPressed()) {
//            loadingDialog.show(menuButtons, menuImageButtons);
//            if (!menuInputs.get(0).getText().equals("") && menuImageButtons.get(0).getText() != null &&
//                    !menuInputs.get(1).getText().equals("") && menuInputs.get(1).getText() != null) {
//                GdxFIRAuth.inst()
//                        .signInWithEmailAndPassword(menuInputs.get(0).getText(), menuInputs.get(1).getText().toCharArray())
//                        .then(new Consumer<GdxFirebaseUser>() {
//                            @Override
//                            public void accept(GdxFirebaseUser gdxFirebaseUser) {
//                                loadingDialog.clear(menuButtons, menuInputs);
//                                menuButtons.clear();
//                                menuInputs.clear();
//                                inputMultiplexer.clear();
//                                stage.clear();
//                                loginScreen.loggedIn = true;
//                            }
//                        }).fail(new BiConsumer<String, Throwable>() {
//                            @Override
//                            public void accept(String s, Throwable throwable) {
//
//                                loadingDialog.clear(menuButtons, menuInputs);
//                                menuDialog = new MenuDialog("Sign in failed");
//                                menuDialog.showText(s, stage);
//                            }
//                        });
//            } else {
//                loadingDialog.clear(menuButtons, menuInputs);
//                menuDialog = new MenuDialog("Error with login");
//                menuDialog.showText("Inputs are empty", stage);
//            }
//        } else if (menuButtons.get(1).isPressed()) {
//            context.setScreen(ScreenType.REGISTER);
//        }
//    }
//
//    private void handleTouchForRegister() {
//
//        if (menuButtons.get(0).isPressed()) {
//            loadingDialog.show(menuButtons, menuInputs);
//            if (!menuInputs.get(0).getText().equals("") && menuInputs.get(0).getText() != null &&
//                    !menuInputs.get(1).getText().equals("") && menuInputs.get(1).getText() != null &&
//                    !menuInputs.get(2).getText().equals("") && menuInputs.get(2).getText() != null &&
//                    !menuInputs.get(3).getText().equals("") && menuInputs.get(3).getText() != null) {
//                if (menuInputs.get(2).getText().equals(menuInputs.get(3).getText())) {
//                    GdxFIRAuth.inst()
//                            .createUserWithEmailAndPassword(menuInputs.get(1).getText(), menuInputs.get(2).getText().toCharArray())
//                            .then(new Consumer<GdxFirebaseUser>() {
//                                @Override
//                                public void accept(GdxFirebaseUser gdxFirebaseUser) {
//                                    User userTmp = new User(menuInputs.get(0).getText(), menuInputs.get(1).getText());
//                                    GdxFIRDatabase.inst()
//                                            .inReference("/Users/" + GdxFIRAuth.inst().getCurrentUser().getUserInfo().getUid() + "")
//                                            .setValue(userTmp);
////                                                        gdxFirebaseUser.delete().subscribe();
//
//                                    loadingDialog.clear(menuButtons, menuInputs);
//                                    menuDialog = new MenuDialog("Great success");
//                                    menuDialog.showText("Successfully registered!", stage);
//                                }
//                            })
//                            .fail(new BiConsumer<String, Throwable>() {
//                                @Override
//                                public void accept(String s, Throwable throwable) {
//                                    loadingDialog.clear(menuButtons, menuInputs);
//                                    menuDialog = new MenuDialog("Error with Register");
//                                    menuDialog.showText(s, stage);
//                                    if (s.contains("The email address is already in use by another account")) {
//                                        GdxFIRAuth.inst().getCurrentUser().delete().subscribe();
//                                    }
//                                }
//                            });
//                } else {
//                    loadingDialog.clear(menuButtons, menuInputs);
//                    menuDialog = new MenuDialog("Error with Register");
//                    menuDialog.showText("Passwords are not same", stage);
//                }
//            } else {
//                loadingDialog.clear(menuButtons, menuInputs);
//                menuDialog = new MenuDialog("Error with Register");
//                menuDialog.showText("Inputs are empty", stage);
//            }
//        } else if (menuButtons.get(1).isPressed()) {
//            context.setScreen(ScreenType.LOGIN);
//        }
//    }
}


//                                GdxFIRAuth.inst().signOut()
//                                        .then(new Consumer<Void>() {
//                                            @Override
//                                            public void accept(Void o) {
//                                                Gdx.app.debug("BUTTON", "Sing out");
//                                            }
//                                        });
//                                // Sign out

//                                Gdx.app.exit();
