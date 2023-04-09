package com.racetacticbattle.game.MenuModels;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.racetacticbattle.game.Helpers.Common;

import java.util.ArrayList;

public class LoadingDialog {

    boolean showLoading;
    Texture glowBlock;
    Sprite glowSprite;
    private Vector2 center = new Vector2(Common.WORLD_WIDTH / 2, Common.WORLD_HEIGHT / 2);

    public LoadingDialog() {
        glowBlock = new Texture(Gdx.files.internal("LoadingCircle.png"));
        glowSprite = new Sprite(glowBlock);
        this.showLoading = false;
    }

    public void draw(float delta, SpriteBatch batch2d) {
        if (showLoading) {
            final float speed = 100f; // in degrees per second
            float angle = delta * speed;
            glowSprite.rotate(angle);
            glowSprite.setPosition(center.x - glowSprite.getWidth() / 2, center.y - glowSprite.getHeight() / 2);
            batch2d.begin();
            glowSprite.draw(batch2d, 100);
            batch2d.end();
        }
    }

    public void show(ArrayList<TextButton> menuButtons,
                     ArrayList<TextField> menuInputs) {
        showLoading = true;
        for (TextField menuInput : menuInputs)
            menuInput.setDisabled(true);
        for (TextButton menuButton : menuButtons)
            menuButton.setTouchable(Touchable.disabled);
    }

    public void clear(ArrayList<TextButton> menuButtons,
                      ArrayList<TextField> menuInputs) {
        showLoading = false;
        for (TextField menuInput : menuInputs)
            menuInput.setDisabled(false);
        for (TextButton menuButton : menuButtons)
            menuButton.setTouchable(Touchable.enabled);
    }
}
