package com.racetacticbattle.game.MenuModels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.racetacticbattle.game.Helpers.Common;

import java.util.ArrayList;

public class MainMenuInfo {
    Texture background, leftInfo, rightInfo;
    Rectangle boundingBox;
    static final float boundingBoxX = Common.WORLD_WIDTH - Common.WORLD_WIDTH * 0.96f;
    static final float boundingBoxY = Common.WORLD_HEIGHT - Common.WORLD_HEIGHT * 0.85f + Common.buttonHeight;
    static final float boundingBoxWidth = Common.buttonWidth * 2;
    static final float boundingBoxHeight = Common.WORLD_HEIGHT * 0.8f - Common.buttonHeight;

    static final float infoLeftRightWidth = 20, infoLeftRightHeight = 30;
    static final float infoLeftY = boundingBoxY + boundingBoxHeight / 2 - infoLeftRightHeight / 2;
    static final float infoRightY = boundingBoxY + boundingBoxHeight / 2 + infoLeftRightHeight / 2;
    static final float infoRightX = boundingBoxX + boundingBoxWidth;
    static final float infoLeftX = boundingBoxX;

    Skin skin;

    public MainMenuInfo(Skin skin) {
        this.background = new Texture("Screens/Info.png");
        this.skin = skin;
        this.boundingBox = new Rectangle(boundingBoxX, boundingBoxY, boundingBoxWidth, boundingBoxHeight);
    }

    public void draw(SpriteBatch batch2d) {
        batch2d.draw(background, boundingBoxX, boundingBoxY, boundingBoxWidth, boundingBoxHeight);
//        batch2d.draw(leftInfo, infoLeftX, infoLeftRightY, infoLeftRightWidth, infoLeftRightHeight);
//        batch2d.draw(rightInfo, infoRightX, infoLeftRightY, infoLeftRightWidth, infoLeftRightHeight);
    }

    public void generateButtons(ArrayList<ImageButton> menuButtons, Stage stage) {
        ImageButton rightInfoButton = new ImageButton(skin);
        rightInfoButton.setPosition(infoRightX, infoRightY);
        rightInfoButton.setSize(infoLeftRightWidth, infoLeftRightHeight);
        rightInfoButton.setTransform(true);
        rightInfoButton.setRotation(180);
        menuButtons.add(rightInfoButton);
        stage.addActor(rightInfoButton);
        ImageButton leftInfoButton = new ImageButton(skin);
        leftInfoButton.setPosition(infoLeftX, infoLeftY);
        leftInfoButton.setSize(infoLeftRightWidth, infoLeftRightHeight);
        menuButtons.add(leftInfoButton);
        stage.addActor(leftInfoButton);
    }
}
