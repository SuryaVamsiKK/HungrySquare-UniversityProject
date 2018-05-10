package com.HugrySquare.game;

import com.HugrySquare.game.Managers.GameStatemanager;
import com.HugrySquare.game.States.PlayState;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HugrySquare extends ApplicationAdapter {

	public static final int WIDTH = 607;
	public static final int HEIGHT = 1080;

	private GameStatemanager gsm;
	private SpriteBatch batch;

	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		gsm = new GameStatemanager();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		gsm.push(new PlayState(gsm));
	}

	@Override
	public void render ()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
