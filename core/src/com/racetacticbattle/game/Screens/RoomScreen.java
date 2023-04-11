package com.racetacticbattle.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.racetacticbattle.game.Helpers.Common;
import com.racetacticbattle.game.MainGame;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;

public class RoomScreen extends AbstractScreen{
    public RoomScreen(MainGame context) {
        super(context);
        GdxFIRDatabase.inst()
                .inReference("/Rooms/" + Common.firebaseUId).removeValue();
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
