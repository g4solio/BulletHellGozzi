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
	public Sprite dropImage;
	public Sprite shipImage;
	public Sound dropSound;
	public Music rainMusic;
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public Rectangle bucket;
	public Array<Rectangle> raindrops;
	public long lastDropTime;
	public static HellGame hellGameInstance;
	public Array<Projectile> projectileArray;
	@Override
	public void create() {
		hellGameInstance = this;

		// load the images for the droplet and the bucket, 64x64 pixels each

		dropImage = new Sprite(new Texture(Gdx.files.internal("droplet.png")));
		shipImage = new Sprite(new Texture(Gdx.files.internal("ship_0.png")));

		// load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		// start the playback of the background music immediately
		rainMusic.setLooping(true);
		rainMusic.play();

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		// create a Rectangle to logically represent the bucket
		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
		bucket.y = 20; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
		bucket.width = 64;
		bucket.height = 64;


		// create the raindrops array and spawn the first raindrop
		raindrops = new Array<Rectangle>();
		projectileArray = new Array<Projectile>();
		spawnRaindrop();
		Gdx.input.setInputProcessor(new GestureDetector(new MyInputProcessor()));

	}

	private void spawnRaindrop() {
		new Projectile(new Rectangle(MathUtils.random(0, 800-64),240,64,64),dropImage,90,200);
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

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the bucket and
		// all drops
		batch.begin();
		batch.draw(shipImage, bucket.x, bucket.y,64,64);
		for(Projectile projectile: projectileArray) {
			batch.draw(dropImage, projectile.projectile.x, projectile.projectile.y);
		}
		batch.end();

		// process user input





		// make sure the bucket stays within the screen bounds
		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800 - 64) bucket.x = 800 - 64;
		if(bucket.y > 480 - 64) bucket.y = 480 - 64;
		if(bucket.y < 0) bucket.y = 0;
		// check if we need to create a new raindrop
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we play back
		// a sound effect as well.
		Iterator<Projectile> iter = projectileArray.iterator();
		while(iter.hasNext()) {
			Projectile projectile = iter.next();

			projectile.projectile.y -= projectile.velocity * Gdx.graphics.getDeltaTime() * Math.sin(Math.toDegrees(projectile.degree));
			projectile.projectile.x += projectile.velocity * Gdx.graphics.getDeltaTime() * Math.cos(Math.toDegrees(projectile.degree));
			Gdx.app.log("Goccia",Math.cos(Math.toDegrees(projectile.degree)) + "");
			if(projectile.projectile.y + 64 < 0) iter.remove();
			if(projectile.projectile.overlaps(bucket)) {
				dropSound.play();
				iter.remove();
			}
		}
	}

	@Override
	public void dispose() {
		// dispose of all the native resources

		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}
}
