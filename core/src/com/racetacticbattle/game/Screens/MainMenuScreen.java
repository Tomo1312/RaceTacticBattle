package com.racetacticbattle.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.racetacticbattle.game.MainGame;

public class MainMenuScreen extends AbstractScreen {
    public MainMenuScreen(MainGame context) {
        super(context);
        //		GdxFIRAuth.inst().signOut();
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
        batch2d.end();

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
