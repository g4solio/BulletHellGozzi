package com.mygdx.game;

import javax.swing.ImageIcon;


public class Projectile extends ImageIcon{

    /*ImageIcon projectileImage = new ImageIcon(Gdx.files.internal("projectile.png"));*/

    public Projectile() {

        HellGame.hellGameInstance.projectileArray.add(this);
    }

}
