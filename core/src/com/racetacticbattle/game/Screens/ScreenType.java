package com.racetacticbattle.game.Screens;

import com.badlogic.gdx.Screen;

public enum ScreenType {
    LOGIN(LoginScreen.class),
    REGISTER(RegisterScreen.class),
    MAIN_MENU(MainMenuScreen.class),
//    ROOM(RoomScreen.class),
    GAME(GameScreen.class);

    private final Class<? extends AbstractScreen> screenClass;

    ScreenType(final Class<? extends AbstractScreen> screenClass) {
        this.screenClass = screenClass;
    }

    public Class<? extends Screen> getScreenClass() {
        return screenClass;
    }


}
