package com.racetacticbattle.game.MenuModels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.racetacticbattle.game.Helpers.Common;
import com.racetacticbattle.game.Screens.ScreenType;


public class MenuInput extends TextField {

    private final int buttonId;
    float buttonContainerWidth = Common.WORLD_WIDTH * 0.9f/2;
    float buttonContainerHeight;


    public MenuInput(Skin skin, Boolean password, int i, int j, ScreenType mainMenuScreen, String textInput) {
        super("", skin);
        int boxDivider = 1;
        float positionX;

        float positionY;
        switch (mainMenuScreen){
            case LOGIN:
                boxDivider= 3;
                buttonContainerHeight = Common.WORLD_HEIGHT * 0.9f;
                break;
            case REGISTER:
                boxDivider= 7;
                buttonContainerHeight = Common.WORLD_HEIGHT * 0.9f;
                break;
        }
        if(i<2){
            positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.95f;
        }else{
            positionX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.95f/2;
        }
        positionY = buttonContainerHeight - buttonContainerHeight / boxDivider * j;
        setPosition(positionX,  positionY);

        setSize(buttonContainerWidth, buttonContainerHeight / 6);
//        setColor(Color.GRAY);
        setMessageText(textInput);
        setPasswordCharacter('*');
        setPasswordMode(password);
        this.buttonId = i;

    }

}
