package com.racetacticbattle.game.MenuModels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.racetacticbattle.game.Helpers.Common;
import com.racetacticbattle.game.Screens.ScreenType;


public class MenuButton extends TextButton {

    private final int buttonId;
    Rectangle boundingBox;
    float buttonContainerWidth = Common.WORLD_WIDTH * 0.9f;
    float buttonContainerHeight = Common.WORLD_HEIGHT * 0.9f;

    public MenuButton(Skin skin, int i, int j, String buttonTitle) {
        super(buttonTitle, skin);
        float positionX;
        float positionY = Common.WORLD_HEIGHT - buttonContainerHeight + buttonContainerHeight / 4;
        if (j < 2) {
            positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.95f;
        } else {
            positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.95f / 2;
        }
        this.boundingBox = new Rectangle(positionX, positionY, buttonContainerWidth / 2, buttonContainerHeight / 6);

        this.buttonId = i;

        setPosition(positionX,  positionY);
        setSize(buttonContainerWidth / 2, buttonContainerHeight / 6);


    }
    public int getButtonId() {
        return buttonId;
    }
}
