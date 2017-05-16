package com.mygdx.game;


import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class HellGame extends ApplicationAdapter {
	public Sprite projectileTexture;
	public Sprite shipImage;
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public Rectangle spaceShip;
	public long lastDropTime;
	public static HellGame hellGameInstance;
	public Array<Projectile> projectileArray;
	private int SpawnInterval = 1000000000;
	@Override
	public void create() {
		hellGameInstance = this;

		// load the images for the droplet and the spaceShip, 64x64 pixels each

		projectileTexture = new Sprite(new Texture(Gdx.files.internal("Asteroid.png")));
		shipImage = new Sprite(new Texture(Gdx.files.internal("ship_0.png")));


		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);
		batch = new SpriteBatch();

		// create a Rectangle to logically represent the spaceShip
		spaceShip = new Rectangle();
		spaceShip.x = 480 / 2 - 64 / 2; // center the spaceShip horizontally
		spaceShip.y = 20; // bottom left corner of the spaceShip is 20 pixels above the bottom screen edge
		spaceShip.width = 64;
		spaceShip.height = 64;


		// create the raindrops array and spawn the first raindrop
		projectileArray = new Array<Projectile>();
		spawnRaindrop();
		Gdx.input.setInputProcessor(new GestureDetector(new MyInputProcessor()));

	}

	private void spawnRaindrop() {
		new Projectile(new Rectangle(MathUtils.random(0, 480-64),800,64,64), projectileTexture,270,MathUtils.random(150,250));
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render() {
		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();


		batch.setProjectionMatrix(camera.combined);


		batch.begin();
		batch.draw(shipImage, spaceShip.x, spaceShip.y,64,64);
		for(Projectile projectile: projectileArray) {
			batch.draw(projectileTexture, projectile.projectile.x, projectile.projectile.y);
		}
		batch.end();






		// make sure the spaceShip stays within the screen bounds
		if(spaceShip.x < 0) spaceShip.x = 0;
		if(spaceShip.x > 480 - 64) spaceShip.x = 480 - 64;
		if(spaceShip.y > 800 - 64) spaceShip.y = 800 - 64;
		if(spaceShip.y < 0) spaceShip.y = 0;
		if(TimeUtils.nanoTime() - lastDropTime > SpawnInterval)
		{
			spawnRaindrop();
			SpawnInterval -= 20;
		}


		Iterator<Projectile> iter = projectileArray.iterator();
		while(iter.hasNext()) {
			Projectile projectile = iter.next();

			projectile.projectile.y -= projectile.velocity * Gdx.graphics.getDeltaTime() * Math.round(Math.cos(projectile.degree));
			projectile.projectile.x += projectile.velocity * Gdx.graphics.getDeltaTime() * Math.round(Math.sin(projectile.degree));
			if(projectile.projectile.y + 64 < 0) iter.remove();
			if(projectile.projectile.overlaps(new Rectangle(spaceShip.x + spaceShip.getWidth()/4, spaceShip.y + spaceShip.getHeight()/4, spaceShip.getWidth()/2, spaceShip.getHeight()/2))) {

				Gdx.app.exit();
			}
		}
	}

	@Override
	public void dispose() {
		// dispose of all the native resources
		batch.dispose();
	}
}
