package com.HungrySquare.game;

import com.HungrySquare.game.Managers.GameStatemanager;
import com.HungrySquare.game.States.PlayState;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class HungrySquare extends ApplicationAdapter {

	public static final int WIDTH = 1080;
	public static final int HEIGHT = 607;

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

	public static float scls(float ps, float probab)
	{
		float rs = 0, r;

		r = MathUtils.random(0,1);

		if(r < probab)
		{
			rs = MathUtils.random(0.07f, ps);
		}

		if(r > probab)
		{
			rs = MathUtils.random(ps, 0.5f);
		}

		return rs;
	}
}
