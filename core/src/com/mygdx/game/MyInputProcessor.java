package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


import javax.xml.ws.handler.LogicalHandler;

/**
 * Created by j.zanti on 29/04/2017.

public class MyInputProcessor implements InputProcessor {
    Vector2 firstTouch;
    boolean isTouched = false;
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
        Gdx.app.debug("TouchDown","TouchDown");
        HellGame.hellGameInstance.camera.unproject(new Vector3(x, y, 0));
        firstTouch.set(x - HellGame.hellGameInstance.bucket.x, y - HellGame.hellGameInstance.bucket.y);

        return true;

    }

    public boolean touchUp (int x, int y, int pointer, int button) {
        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
        Cursor.System("TouchDragged","TouchDragged");

        HellGame.hellGameInstance.bucket.x = (HellGame.hellGameInstance.bucket.x + firstTouch.x + x) - 64 / 2;
        HellGame.hellGameInstance.bucket.y = (HellGame.hellGameInstance.bucket.y + firstTouch.y + y) - 64 / 2;
        return true;
    }

    public boolean mouseMoved (int x, int y) {
        return false;
    }

    public boolean scrolled (int amount) {
        return false;
    }
}
 */
public class MyInputProcessor implements GestureDetector.GestureListener {

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {

        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        HellGame.hellGameInstance.bucket.x = (HellGame.hellGameInstance.bucket.x + deltaX) - 64 / 2;
        HellGame.hellGameInstance.bucket.y = (HellGame.hellGameInstance.bucket.y + deltaY) - 64 / 2;
        Gdx.app.log("meters",""+deltaY);

        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean zoom (float originalDistance, float currentDistance){

        return false;
    }

    @Override
    public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer){

        return false;
    }
    @Override
    public void pinchStop () {
    }
}