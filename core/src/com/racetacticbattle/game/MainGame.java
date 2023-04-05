package com.racetacticbattle.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.racetacticbattle.game.Helpers.Common;
import com.racetacticbattle.game.Screens.AbstractScreen;
import com.racetacticbattle.game.Screens.ScreenType;

import java.util.EnumMap;
import pl.mk5.gdx.fireapp.GdxFIRApp;

public class MainGame extends Game {
	private EnumMap<ScreenType, AbstractScreen> screenCache;

	//For touch x-y coordinates in 2D
	private StretchViewport viewport;

	//For 2D camera
	private OrthographicCamera camera2d;
	SpriteBatch batch2d;

	//For 3D camera
	private PerspectiveCamera camera3d;
	ModelBatch batch3d;
	@Override
	public void create () {
		GdxFIRApp.inst().configure();
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		camera2d = new OrthographicCamera();
		camera3d = new PerspectiveCamera();
		viewport = new StretchViewport(Common.WORLD_WIDTH, Common.WORLD_HEIGHT, camera2d);
		screenCache = new EnumMap<ScreenType, AbstractScreen>(ScreenType.class);
		batch2d = new SpriteBatch();
		batch3d = new ModelBatch();
		setScreen(ScreenType.LOGIN);
	}

	@Override
	public void render () {
		super.render();
	}

    public void setScreen(final ScreenType screenType) {
        final Screen screen = screenCache.get(screenType);
        if (screen == null) {
            try {
                final AbstractScreen newScreen = (AbstractScreen) ClassReflection.getConstructor(screenType.getScreenClass(), MainGame.class).newInstance(this);
                screenCache.put(screenType, newScreen);
                setScreen(newScreen);
            } catch (ReflectionException e) {
                e.printStackTrace();
            }
        } else {
            setScreen(screen);
        }
    }
	@Override
	public void dispose () {
		super.dispose();
	}

	public StretchViewport getViewport() {
		return viewport;
	}

	public OrthographicCamera getCamera2d() {
		return camera2d;
	}

	public SpriteBatch getBatch2d() {
		return batch2d;
	}

	public PerspectiveCamera getCamera3d() {
		return camera3d;
	}

	public ModelBatch getBatch3d() {
		return batch3d;
	}
}
