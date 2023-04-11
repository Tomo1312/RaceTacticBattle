package com.racetacticbattle.game.Helpers;

import pl.mk5.gdx.fireapp.GdxFIRAuth;

public class Common {
    public final static float WORLD_WIDTH = 640;
    public final static float WORLD_HEIGHT = 480;
    public final static float buttonContainerWidth = Common.WORLD_WIDTH * 0.9f;
    public final static float buttonContainerHeight = Common.WORLD_HEIGHT * 0.85f;
    public final static float buttonWidth = Common.buttonContainerWidth / 3f;
    public final static float buttonHeight = Common.buttonContainerHeight / 6;
    public final static String firebaseUId = GdxFIRAuth.inst().getCurrentUser().getUserInfo().getUid();
}
