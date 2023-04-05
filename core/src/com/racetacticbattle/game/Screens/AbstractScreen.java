package com.racetacticbattle.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.racetacticbattle.game.MainGame;

public abstract class AbstractScreen implements Screen {
    protected final MainGame context;

    //For touch x-y coordinates in 2D
    StretchViewport viewport;

    //For 2D camera
    OrthographicCamera camera2d;
    SpriteBatch batch2d;

    //For 3D camera
    PerspectiveCamera camera3d;
    ModelBatch batch3d;
    public AbstractScreen(final MainGame context) {
        this.context = context;
        this.viewport = context.getViewport();
        this.camera2d =context.getCamera2d();
        this.camera3d =context.getCamera3d();
        this.batch2d = context.getBatch2d();
        this.batch3d = context.getBatch3d();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
