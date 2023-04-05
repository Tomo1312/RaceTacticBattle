package com.racetacticbattle.game.MenuModels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.racetacticbattle.game.Helpers.Common;

public class MenuDialog  {
    Skin skin;
    Dialog dialog;
    public MenuDialog(String title) {
        skin = new Skin(Gdx.files.internal("Screens/cloud/cloud-form-ui.json"));
        dialog = new Dialog(title, skin);
    }

    public void showText(String s, Stage stage) {
        dialog.text(s);
        dialog.show(stage);
    }

    public void clear() {
        dialog.clear();
        dialog.remove();
    }
}
