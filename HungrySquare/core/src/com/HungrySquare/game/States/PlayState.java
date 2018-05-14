package com.HungrySquare.game.States;

import com.HungrySquare.game.HungrySquare;
import com.HungrySquare.game.Managers.ArtificalFood;
import com.HungrySquare.game.Managers.GameStatemanager;
import com.HungrySquare.game.Managers.state;
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
import com.badlogic.gdx.utils.Array;

public class PlayState extends state{


    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("AGENCYB.TTF"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
    BitmapFont font1;

//    private ArtificalFood foods;
    private ArtificalFood player;
    ShapeRenderer spr;
    private boolean inputtaken = false;

    private static ArtificalFood[] foodL;

    public Vector2[] startPos;

    public static Vector2[] random;

    private boolean in = false;


    public PlayState(GameStatemanager gsm) {
        super(gsm);
        foodL = new ArtificalFood[1];
        random = new Vector2[3];
        spr = new ShapeRenderer();
        inputtaken = false;
        startPos = new Vector2[3];


        startPos[0] = new Vector2(-50,HungrySquare.HEIGHT);
        startPos[1] = new Vector2(+50,HungrySquare.HEIGHT/2);
        startPos[2] = new Vector2(-100,0);

        random[0] = new Vector2(0,0);
        random[1] = new Vector2(0,0);
        random[2] = new Vector2(0,0);

        player = new ArtificalFood(new Vector2(800,400), new Vector2(0.12f,0.12f), new Vector2(0, 0), 20f, new Texture("Yellow.jpg"));

    }

    public void init()
    {
        for(int i = 0; i < foodL.length; i++)
        {
            foodL[i] = new ArtificalFood(startPos[i], new Vector2(0.1f,0.1f), new Vector2(0,0), 200f, new Texture("Green.jpg"));
            random[i].x = HungrySquare.WIDTH + foodL[i].getSprite().getTexture().getWidth()*foodL[i].scale.x + 200f;
            random[i].y = MathUtils.random(-200f, HungrySquare.HEIGHT + 200f);
            foodL[i].setTarget(random[i]);
//            System.out.println(random.y);
        }
    }

    @Override
    protected void handleInput() {

        if(!Gdx.input.isTouched())
        {
            inputtaken = false;
        }

        if(Gdx.input.isTouched())
        {
            if(isTouching(player.position.x, player.position.y, player.getSprite().getTexture().getWidth()*player.scale.x, player.getSprite().getTexture().getHeight()*player.scale.y))
            {
                /*player.position.x = Gdx.input.getX() - (player.getSprite().getTexture().getWidth()/2) * player.scale.x;
                player.position.y = (HungrySquare.HEIGHT - Gdx.input.getY())+player.getSprite().getTexture().getHeight()/2;*/
                inputtaken = true;
            }
        }

    }

    @Override
    public void update(float dt) {

        if(in == false)
        {
            init();
            in = true;
        }

//        System.out.println(foods.position.x + " : " + foods.position.y);
//        System.out.println(random.x + " : " + random.y);

        player.Update();

        for(int i = 0; i < foodL.length; i++)
        {
            foodL[i].Update();
            foodL[i].move();
            if(foodL[i].isReached())
            {
                foodL[i].reinit(startPos[i], new Vector2(0.1f,0.1f), random[i], 200f, new Texture("Green.jpg"));
                random[i].x = HungrySquare.WIDTH + foodL[i].getSprite().getTexture().getWidth()*foodL[i].scale.x + 200f;
                random[i].y = MathUtils.random(-200f, HungrySquare.HEIGHT + 200f);
                float rad = HungrySquare.scls(player.scale.x, 0.2f);
                foodL[i].setRawScale(new Vector2(rad, rad));
            }

            if(Intersector.overlapConvexPolygons(foodL[i].getCollider(), player.getCollider()))
            {
                if(foodL[i].scale.x*foodL[i].scale.y > player.scale.x*player.scale.y)
                {

                }

                if(foodL[i].scale.x*foodL[i].scale.y < player.scale.x*player.scale.y)
                {
                    player.setScale(new Vector2(foodL[i].getScale().x/4, foodL[i].getScale().y/4));
                    foodL[i].reinit(startPos[i], new Vector2(0.1f,0.1f), random[i], 200f, new Texture("Green.jpg"));
                    random[i].x = HungrySquare.WIDTH + foodL[i].getSprite().getTexture().getWidth()*foodL[i].scale.x + 200f;
                    random[i].y = MathUtils.random(-200f, HungrySquare.HEIGHT + 200f);
                    float rad = HungrySquare.scls(player.scale.x, 0.2f);
                    foodL[i].setRawScale(new Vector2(rad, rad));
                }
            }
        }

        /*
        foods.Update();
        foods.move();
        if(foods.isReached())
        {
            foods.reinit(new Vector2(0,0), new Vector2(0.1f,0.1f), random, 200f, new Texture("Green.jpg"));
            random.x = HungrySquare.WIDTH + foods.getSprite().getTexture().getWidth()*foods.scale.x + 200f;
            random.y = MathUtils.random(-200f, HungrySquare.HEIGHT + 200f);
            float rad = HungrySquare.scls(player.scale.x, 0.2f);
            //System.out.println(rad);
            System.out.println(random.y);
            foods.setRawScale(new Vector2(rad, rad));
            foods.setTarget(random);

            if(Intersector.overlapConvexPolygons(foods.getCollider(), player.getCollider()))
             {
            if(foods.scale.x*foods.scale.y > player.scale.x*player.scale.y)
            {

            }

            if(foods.scale.x*foods.scale.y < player.scale.x*player.scale.y)
            {
                player.setScale(new Vector2(foods.getScale().x/4, foods.getScale().y/4));
                foods.reinit(new Vector2(0,0), new Vector2(0.1f,0.1f), random, 200f, new Texture("Green.jpg"));
                random.x = HungrySquare.WIDTH + foods.getSprite().getTexture().getWidth()*foods.scale.x + 200f;
                random.y = MathUtils.random(-200f, HungrySquare.HEIGHT + 200f);
                float rad = HungrySquare.scls(player.scale.x, 0.2f);
                System.out.println(random.y);
                foods.setRawScale(new Vector2(rad, rad));
                foods.setTarget(random);
            }
        }
        }*/

        if(inputtaken)
        {
            player.setPosition(new Vector2(Gdx.input.getX() - (player.getSprite().getTexture().getWidth()/2) * player.scale.x, (HungrySquare.HEIGHT - Gdx.input.getY()) - (player.getSprite().getTexture().getHeight()/2) * player.scale.y));
        }

        //System.out.println("" + HungrySquare.HEIGHT + " : " + Gdx.input.getY() + " : " + ((HungrySquare.HEIGHT ) - (Gdx.input.getY())) + " : " + player.position.y);

        handleInput();


    }

    @Override
    public void render(SpriteBatch sb) {

        sb.begin();
       /* font1.getData().setScale(1, 1);
        font1.setColor(Color.BLACK);
        font1.draw(sb, "Score : " + score, HungrySquare.WIDTH - 75f, HungrySquare.HEIGHT - 20f, 0, Align.center, true);*/
//        foods.getSprite().draw(sb);
        for(int i = 0; i < foodL.length; i++)
        {
            foodL[i].getSprite().draw(sb);
        }
        player.getSprite().draw(sb);
        sb.end();


        //regiondebugmmode
        spr.begin(ShapeRenderer.ShapeType.Line);
//        debugmode(Color.BLACK, foods.getCollider());
        for(int i = 0; i < foodL.length; i++)
        {
            debugmode(Color.BLACK, foodL[i].getCollider());
        }
        debugmode(Color.RED, player.getCollider());
        spr.end();
        //endregion

    }

    @Override
    public void dispose() {

//        foods.getSprite().getTexture().dispose();
        for(int i = 0; i < foodL.length; i++)
        {
           foodL[i].getSprite().getTexture().dispose();
        }
        player.getSprite().getTexture().dispose();

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
        touchy = HungrySquare.HEIGHT - Gdx.input.getY();
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

