package com.racetacticbattle.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.racetacticbattle.game.Helpers.Common;
import com.racetacticbattle.game.MainGame;
import com.racetacticbattle.game.MenuModels.MenuButton;
import com.racetacticbattle.game.MenuModels.MenuDialog;
import com.racetacticbattle.game.MenuModels.MenuInput;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.functional.BiConsumer;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class LoginScreen extends LoginRegisterAbstractScreen {
    InputProcessor inputProcessor;
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
            MenuButton menuButton = new MenuButton(skin, i, j, buttonTitle);
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
            MenuInput menuInputTmp = new MenuInput(skin, i == 1 || i == 2, j, 1, ScreenType.LOGIN, textInput);
            menuInputs.add(menuInputTmp);
            stage.addActor(menuInputTmp);

            j += 2;

        }
        GdxFIRAuth.inst().signOut();
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
//        batch2d.setColor(c.r, c.g, c.b, 1);
        handleInput();
        batch2d.end();
        stage.draw();
        stage.act();
        loadingDialog.draw(delta, batch2d);
    }

    void handleInput() {
        inputProcessor = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (menuDialog != null) {
                    menuDialog.clear();
                }
                stage.unfocusAll();
                Gdx.input.setOnscreenKeyboardVisible(false);
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                handleTouchForLogin();
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                return false;
            }
        };
        inputMultiplexer.addProcessor(inputProcessor);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void handleTouchForLogin() {
        for (MenuButton menuButton : menuButtons) {
            if (menuButton.isPressed()) {
                switch (menuButton.getButtonId()) {
                    case 0:
                        loadingDialog.show(menuButtons, menuInputs);
                        if (!menuInputs.get(0).getText().equals("") && menuInputs.get(0).getText() != null &&
                                !menuInputs.get(1).getText().equals("") && menuInputs.get(1).getText() != null) {
                            GdxFIRAuth.inst()
                                    .signInWithEmailAndPassword(menuInputs.get(0).getText(), menuInputs.get(1).getText().toCharArray())
                                    .then(new Consumer<GdxFirebaseUser>() {
                                        @Override
                                        public void accept(GdxFirebaseUser gdxFirebaseUser) {
                                            loadingDialog.clear(menuButtons, menuInputs);
                                            menuButtons.clear();
                                            menuInputs.clear();
                                            inputMultiplexer.clear();
                                            context.setScreen(ScreenType.MAIN_MENU);
                                        }
                                    }).fail(new BiConsumer<String, Throwable>() {
                                        @Override
                                        public void accept(String s, Throwable throwable) {

                                            loadingDialog.clear(menuButtons, menuInputs);
                                            menuDialog = new MenuDialog("Sign in failed");
                                            menuDialog.showText(s, stage);
                                        }
                                    });
                        } else {
                            loadingDialog.clear(menuButtons, menuInputs);
                            menuDialog = new MenuDialog("Error with login");
                            menuDialog.showText("Inputs are empty", stage);
                        }
                        break;
                    case 1:
                        context.setScreen(ScreenType.REGISTER);
                        break;
                }
            }
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
        batch2d.dispose();
        stage.dispose();
    }
}
