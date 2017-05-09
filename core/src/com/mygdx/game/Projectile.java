package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier;
import com.badlogic.gdx.math.Rectangle;


public class Projectile{

    public Rectangle projectile;
    public Sprite texture;
    public float velocity;
    public float degree;


    public Projectile(Rectangle Dimension, Sprite Texture, float Degree, float Velocity) {
        projectile = Dimension;
        texture = Texture;
        degree = Degree;
        velocity = Velocity;
        HellGame.hellGameInstance.projectileArray.add(this);
    }

}
