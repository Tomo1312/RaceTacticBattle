package com.racetacticbattle.game.MenuModels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.racetacticbattle.game.Helpers.Common;
import com.racetacticbattle.game.Screens.ScreenType;


public class MenuButton {

    private final int buttonId;
    Texture texture, pressed_texture;
    Rectangle boundingBox;
    float buttonContainerWidth = Common.WORLD_WIDTH * 0.9f;
    float buttonContainerHeight = Common.WORLD_HEIGHT * 0.9f;

    //font
    BitmapFont font;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    FreeTypeFontGenerator fontGenerator;
    GlyphLayout layout;

    boolean pressed;
    ScreenType screenType;

    public MenuButton(Texture texture, Texture pressed_texture, int i, int j, String buttonTitle) {
        this.texture = texture;
        this.pressed_texture = pressed_texture;
        float positionX;
        float positionY = Common.WORLD_HEIGHT - buttonContainerHeight + buttonContainerHeight / 4;
        if(j<2){
            positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.95f;
        }else{
            positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.95f/2;
        }
        this.boundingBox = new Rectangle(positionX, positionY, buttonContainerWidth/2, buttonContainerHeight / 6);
        this.pressed = false;
        this.buttonId = i;

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Screens/marigoldwild.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 40;
        font = fontGenerator.generateFont(fontParameter);
        font.setColor(Color.BLACK);
        layout = new GlyphLayout(font, buttonTitle);
    }


    public void draw(SpriteBatch batch) {
        batch.draw(texture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        if (pressed)
            batch.draw(pressed_texture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        font.draw(batch, layout, boundingBox.x + boundingBox.width / 2 - layout.width / 2, boundingBox.y + boundingBox.height / 2 + layout.height / 2);

    }

    public void isPressed(float touchPositionX, float touchPositionY) {
        if (this.boundingBox.contains(touchPositionX, touchPositionY)) {
            pressed = true;
        }
    }

    public int getButtonId() {
        return buttonId;
    }

    public boolean isPressedUp(float touchPositionX, float touchPositionY) {
        if (pressed) {
            pressed = false;
            return true;
        }
        return false;
    }

    public void isDragged(float touchPositionX, float touchPositionY) {
        if (!this.boundingBox.contains(touchPositionX, touchPositionY) && pressed) {
            pressed = false;
        } else if (this.boundingBox.contains(touchPositionX, touchPositionY)) {
            pressed = true;
        }
    }
}
