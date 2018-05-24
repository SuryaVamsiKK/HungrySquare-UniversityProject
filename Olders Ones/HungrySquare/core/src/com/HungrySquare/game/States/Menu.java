package com.HungrySquare.game.States;

import com.HungrySquare.game.HungrySquare;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.HungrySquare.game.Managers.GameStatemanager;
import com.HungrySquare.game.Managers.state;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;


public class Menu extends state {

    private Texture BG;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font15;

    public Menu(GameStatemanager gsm) {

        super(gsm);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("De Valencia (beta).otf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        font15 = generator.generateFont(parameter);
        BG = new Texture("White.jpg");}

    @Override
    public void handleInput() {

        if(Gdx.input.justTouched())
        {
            gsm.set(new PlayState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {

        handleInput();

    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.begin();
        sb.draw(BG, 0, 0, HungrySquare.WIDTH, HungrySquare.HEIGHT);
        font15.getData().setScale(1, 1);
        font15.setColor(Color.BLACK);
        font15.draw(sb, "Play", (HungrySquare.WIDTH/2), (HungrySquare.HEIGHT/2), 0, Align.center, true);
        sb.end();
    }

    @Override
    public void dispose() {

        BG.dispose();

    }
}
