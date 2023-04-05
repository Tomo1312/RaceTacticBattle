package com.racetacticbattle.game.MenuModels;

import com.badlogic.gdx.Gdx;
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
    Rectangle boundingBox;
    float buttonContainerWidth = Common.WORLD_WIDTH * 0.9f/2;
    float buttonContainerHeight;

    //font
    BitmapFont font;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    FreeTypeFontGenerator fontGenerator;
    GlyphLayout layout;

    boolean pressed;
    ScreenType screenType;

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
        setMessageText(textInput);
        setPasswordCharacter('*');
        setPasswordMode(password);
        this.buttonId = i;
//        String str = new String();
//        switch (screenType) {
//            case SETTINGS:
//                switch (i) {
//                    case 1:
//                        str = "Change Account";
//                        break;
//                    case 3:
//                        str = "Language";
//                        break;
//                    default:
//                        str = "Back";
//
//
//                }
//                break;
//            case MAIN_MENU:
//                switch (i) {
//                    case 1:
//                        str = "Exit";
//                        break;
//                    case 3:
//                        str = "Settings";
//                        break;
//                    default:
//                        str = "Play";
//
//                }
//                break;
//        }

    }

}
