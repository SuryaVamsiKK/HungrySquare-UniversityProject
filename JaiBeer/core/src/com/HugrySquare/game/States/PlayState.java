package com.HugrySquare.game.States;

import com.HugrySquare.game.Managers.ArtificalFood;
import com.HugrySquare.game.Managers.GameStatemanager;
import com.HugrySquare.game.Managers.state;
import com.HugrySquare.game.HugrySquare;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class PlayState extends state{


    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("AGENCYB.TTF"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
    BitmapFont font1;

    private ArtificalFood fd;
    private ArtificalFood player;
    ShapeRenderer spr;

    public PlayState(GameStatemanager gsm) {
        super(gsm);
        spr = new ShapeRenderer();
        fd = new ArtificalFood(new Vector2(0,0), new Vector2(0.1f,0.1f), new Vector2(100, 100), 20f, new Texture("Green.jpg"));
        player = new ArtificalFood(new Vector2(0,0), new Vector2(0.2f,0.2f), new Vector2(0, 0), 20f, new Texture("Yellow.jpg"));
    }


    @Override
    protected void handleInput() {

        if(!Gdx.input.isTouched())
        {

        }

        if(Gdx.input.isTouched())
        {
            if(isTouching(player.position.x, player.position.y, player.getSprite().getTexture().getWidth()*player.scale.x, player.getSprite().getTexture().getHeight()*player.scale.y))
            {
                /*player.position.x = Gdx.input.getX() - (player.getSprite().getTexture().getWidth()/2) * player.scale.x;
                player.position.y = (HugrySquare.HEIGHT - Gdx.input.getY())+player.getSprite().getTexture().getHeight()/2;*/

                player.setPosition(new Vector2(Gdx.input.getX() - (player.getSprite().getTexture().getWidth()/2) * player.scale.x, (HugrySquare.HEIGHT - Gdx.input.getY()) - (player.getSprite().getTexture().getHeight()/2) * player.scale.y));
            }

            System.out.println("" + HugrySquare.HEIGHT + " : " + Gdx.input.getY() + " : " + ((HugrySquare.HEIGHT ) - (Gdx.input.getY())));
        }

    }

    @Override
    public void update(float dt) {

        fd.Update();
        fd.move();
        player.Update();
        if(fd.isReached())
        {
            fd.setTarget(new Vector2(10, 100));
        }


        handleInput();

        if(Intersector.overlapConvexPolygons(fd.getCollider(), player.getCollider()))
        {
            if(fd.scale.x*fd.scale.y > player.scale.x*player.scale.y)
            {
                System.out.println("LOose");
            }

            if(fd.scale.x*fd.scale.y < player.scale.x*player.scale.y)
            {
                System.out.println("++");
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.begin();
       /* font1.getData().setScale(1, 1);
        font1.setColor(Color.BLACK);
        font1.draw(sb, "Score : " + score, HugrySquare.WIDTH - 75f, HugrySquare.HEIGHT - 20f, 0, Align.center, true);*/
        fd.getSprite().draw(sb);
        player.getSprite().draw(sb);
        sb.end();


        //regiondebugmmode
        spr.begin(ShapeRenderer.ShapeType.Line);
        debugmode(Color.BLACK, fd.getCollider());
        debugmode(Color.RED, player.getCollider());
        spr.end();
        //endregion

    }

    @Override
    public void dispose() {

        fd.getSprite().getTexture().dispose();

    }

    public Polygon setCollider(float x, float y, float scalex, float scaley)
    {
        Polygon ply;
        float verts[];
        //verts = new float[]{x,-y ,x + scalex,y - scaley/2 ,x + scalex/2,y + scaley/2 ,x - scalex/2,scaley + scaley/2};
        verts = new float[]{x - scalex/2, y - scaley/2, x + scalex/2, y - scaley/2, x + scalex/2 ,y + scaley/2, x - scalex/2, y + scaley/2};
        ply = new Polygon(verts);
        return ply;
    }


    public void debugmode(Color clr, Polygon ply)
    {
        spr.setColor(clr);
        spr.polygon(ply.getTransformedVertices());
    }

    public static boolean isTouching(float bx, float by, float width, float height)
    {
        boolean truth = false;
        float touchx, touchy;
        touchx = Gdx.input.getX();
        touchy = HugrySquare.HEIGHT - Gdx.input.getY();
        if(touchx > bx && (touchx < bx + width))
        {
            if(touchy > by && touchy < by+(height))
            {
                truth = true;
            }
        }

        return truth;
    }
}

