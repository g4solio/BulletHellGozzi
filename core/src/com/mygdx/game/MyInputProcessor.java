package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by j.zanti on 29/04/2017.
 */
public class MyInputProcessor implements InputProcessor {
    public boolean keyDown (int keycode) {
        return false;
    }

    public boolean keyUp (int keycode) {
        return false;
    }

    public boolean keyTyped (char character) {
        return false;
    }

    public boolean touchDown (int x, int y, int pointer, int button) {

        HellGame.hellGameInstance.camera.unproject(new Vector3(x,y,0));
        HellGame.hellGameInstance.bucket.x = x - 64 / 2;
        HellGame.hellGameInstance.bucket.y = y - 64 / 2;
        return true;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    public boolean mouseMoved (int x, int y) {
        return false;
    }

    public boolean scrolled (int amount) {
        return false;
    }
}