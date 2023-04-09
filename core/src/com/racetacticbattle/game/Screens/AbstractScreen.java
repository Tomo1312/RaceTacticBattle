package com.racetacticbattle.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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

    Skin skin;
    Stage stage;
    public AbstractScreen(final MainGame context) {
        this.context = context;
        this.viewport = context.getViewport();
        this.camera2d = context.getCamera2d();
        this.camera3d = context.getCamera3d();
        this.batch2d = context.getBatch2d();
        this.batch3d = context.getBatch3d();
        this.stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("Screens/mySkin/moj-skin.json"));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera2d.update();
        camera3d.update();
    }

    @Override
    public void dispose() {
        batch2d.dispose();
        batch3d.dispose();
    }
}
