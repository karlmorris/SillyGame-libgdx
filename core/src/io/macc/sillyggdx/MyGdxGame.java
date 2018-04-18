package io.macc.sillyggdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img, player;

	int x,y, myX, myY, mySize, canvasHeight, canvasWidth;

	int xDirection; // 0 - left, 1 - right
	int yDirection; // 0 - up, 1 - down

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		player = new Texture("bluesquare.jpg");
		mySize = Math.max(player.getHeight(), player.getWidth());
		canvasHeight = Gdx.graphics.getHeight();
		canvasWidth = Gdx.graphics.getWidth();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		updateEnemyXandY();
		batch.begin();
		batch.draw(img, x, y);
		batch.draw(player, myX, myY);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public void updateMyLocation(float[] values) {
		int multiplier = 1;

		if (values[2] > 0) {
			if (myY + mySize - (int) (Math.abs(values[2]) * multiplier) > 0)
				myY = myY + (int) (Math.abs(values[2]) * multiplier);
		} else {
			if (myY - (int) (Math.abs(values[2]) * multiplier) + mySize < canvasHeight)
				myY = myY - (int) (Math.abs(values[2]) * multiplier);
		}

		if (values[1] > 0) {
			if (myX - (int) (Math.abs(values[1]) * multiplier) - mySize > 0)
				myX = myX - (int) (Math.abs(values[1]) * multiplier);
		} else {
			if (myX + (int) (Math.abs(values[1]) * multiplier) + mySize < canvasWidth)
				myX = myX + (int) (Math.abs(values[1]) * multiplier);
		}
	}

	private void updateEnemyXandY() {

		final int movePixels = 5;

		if (xDirection == 0) {
			if ((x - movePixels) > 0) x -= movePixels ;
			else xDirection = 1;
		} else {
			if ((x + movePixels) + img.getWidth() < canvasWidth) x += movePixels;
			else xDirection = 0;
		}

		if (yDirection == 0) {
			if ((y - movePixels) > 0) y -= movePixels;
			else yDirection = 1;
		} else {
			if ((y + movePixels) + img.getHeight() < canvasHeight) y += movePixels;
			else yDirection = 0;
		}
	}
}
