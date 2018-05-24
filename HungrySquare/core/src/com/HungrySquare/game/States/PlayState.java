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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class PlayState extends state{


    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("De Valencia (beta).otf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
    FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
    BitmapFont font1, font2;

//    private ArtificalFood foods;
    private ArtificalFood player;
    ShapeRenderer spr;
    private boolean inputtaken = false;

    public static ArtificalFood[] foodL;

    public static Vector2[] startPos;

    public static Vector2[] random;

    private boolean in = false;
    private boolean isresizing = false;
    private boolean isresizingstart = false;
    float[] sclax, sclay;
    private int Energy = 500;
    float sclx, scly;
    float score  = 0;
    private float sizevalue = 1f;
    private String clr = "Black";


    private Texture scoretex,lifetex;

    public PlayState(GameStatemanager gsm) {
        super(gsm);
        parameter1.size = 30;
        parameter2.size = 60;
        font1 = generator.generateFont(parameter1);
        font2 = generator.generateFont(parameter2);
        generator.dispose();
        foodL = new ArtificalFood[12];
        random = new Vector2[12];
        spr = new ShapeRenderer();
        inputtaken = false;
        lifetex = new Texture("001-molecule-2.png");
        scoretex = new Texture("002-molecular-structure.png");
        startPos = new Vector2[12];

        startPos[0] = new Vector2(0,HungrySquare.HEIGHT/2);
        startPos[1] = new Vector2(0,500);
        startPos[2] = new Vector2(0,0);

        startPos[3] = new Vector2(HungrySquare.WIDTH,HungrySquare.HEIGHT/2);
        startPos[4] = new Vector2(HungrySquare.WIDTH,HungrySquare.HEIGHT);
        startPos[5] = new Vector2(HungrySquare.WIDTH,0);

        startPos[6] = new Vector2(HungrySquare.WIDTH/2,HungrySquare.HEIGHT);
        startPos[7] = new Vector2(200,HungrySquare.HEIGHT);
        startPos[8] = new Vector2(HungrySquare.WIDTH-200,HungrySquare.HEIGHT);

        startPos[9] = new Vector2(HungrySquare.WIDTH/2,0);
        startPos[10] = new Vector2(HungrySquare.WIDTH,0);
        startPos[11] = new Vector2(0,0);
        

        random[0] = new Vector2(0,0);
        random[1] = new Vector2(0,0);
        random[2] = new Vector2(0,0);
        random[3] = new Vector2(0,0);
        random[4] = new Vector2(0,0);
        random[5] = new Vector2(0,0);
        random[6] = new Vector2(0,0);
        random[7] = new Vector2(0,0);
        random[8] = new Vector2(0,0);
        random[9] = new Vector2(0,0);
        random[10] = new Vector2(0,0);
        random[11] = new Vector2(0,0);


        //cam.setToOrtho(false, HungrySquare.WIDTH, HungrySquare.HEIGHT);
        //cam.zoom = 1f;

        radm();
        player = new ArtificalFood(new Vector2(800,400), new Vector2(0.12f,0.12f), new Vector2(0, 0), 20f, new Texture("Whiteshd.png"));

    }

    public void init()
    {
        for(int i = 0; i < foodL.length; i++)
        {
            foodL[i] = new ArtificalFood(startPos[i], new Vector2(0.1f,0.1f ), new Vector2(0,0), 200f, new Texture(clr + ".jpg"));
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
                inputtaken = true;
            }
        }

    }

    @Override
    public void update(float dt) {

        //cam.update();
        player.Update();

        if ((player.getScale().x >= 0.3 || player.getScale().y >= 0.3) && isresizing == false)
        {
            isresizing = true;
            isresizingstart = true;
        }

        //regionResizing
        if(isresizing == true)
        {
            if(isresizingstart == true)
            {
                sclx = player.getScale().x;
                scly = player.getScale().y;
                sclax = new float[12];
                sclay = new float[12];

                for (int i = 0; i < foodL.length; i++)
                {
                    sclax[i] = foodL[i].getScale().x;
                    sclay[i] = foodL[i].getScale().y;
                }
                isresizingstart = false;
            }

            sclx -=  0.0035f;
            scly -=  0.0035f;

            for (int i = 0; i < foodL.length; i++)
            {
                sclax[i] -= 0.0045f;
                sclay[i] -= 0.0045f;
            }
            player.setRawScale(new Vector2(sclx , scly));
            for (int i = 0; i < foodL.length; i++)
            {
                foodL[i].setRawScale(new Vector2(sclax[i], sclay[i]));
            }

            if(sclx < 0.18f || scly < 0.18f)
            {
                isresizing = false;
            }
        }
        //endregion

        if(isresizing == false) {

            for (int i = 0; i < foodL.length; i++) {
                foodL[i].move();
                if (foodL[i].isReached()) {

                    startPos[0] = new Vector2(0,HungrySquare.HEIGHT/2);
                    startPos[1] = new Vector2(0,500);
                    startPos[2] = new Vector2(0,0);

                    startPos[3] = new Vector2(HungrySquare.WIDTH,HungrySquare.HEIGHT/2);
                    startPos[4] = new Vector2(HungrySquare.WIDTH,HungrySquare.HEIGHT);
                    startPos[5] = new Vector2(HungrySquare.WIDTH,0);

                    startPos[6] = new Vector2(HungrySquare.WIDTH/2,HungrySquare.HEIGHT);
                    startPos[7] = new Vector2(200,HungrySquare.HEIGHT);
                    startPos[8] = new Vector2(0,HungrySquare.HEIGHT);

                    startPos[9] = new Vector2(HungrySquare.WIDTH/2,50);
                    startPos[10] = new Vector2(HungrySquare.WIDTH,50);
                    startPos[11] = new Vector2(0,50);
                    radm();
                    //System.out.println(random[i]);
                    foodL[i].reinit(startPos[i], new Vector2(0.1f, 0.1f), random[i], 200f, new Texture(clr + ".jpg"));
                    float rad = HungrySquare.scls(player.scale.x, 0.8f);
                    foodL[i].setRawScale(new Vector2(rad, rad));
                }

                if (Intersector.overlapConvexPolygons(foodL[i].getCollider(), player.getCollider())) {
                    if (foodL[i].scale.x * foodL[i].scale.y > player.scale.x * player.scale.y)
                    {
                        Energy--;
                    }

                    if (foodL[i].scale.x * foodL[i].scale.y < player.scale.x * player.scale.y) {

                        //System.out.println(player.getScale());
                        startPos[0] = new Vector2(0,HungrySquare.HEIGHT/2);
                        startPos[1] = new Vector2(0,500);
                        startPos[2] = new Vector2(0,0);

                        startPos[3] = new Vector2(HungrySquare.WIDTH,HungrySquare.HEIGHT/2);
                        startPos[4] = new Vector2(HungrySquare.WIDTH,HungrySquare.HEIGHT);
                        startPos[5] = new Vector2(HungrySquare.WIDTH,0);

                        startPos[6] = new Vector2(HungrySquare.WIDTH/2,HungrySquare.HEIGHT);
                        startPos[7] = new Vector2(200,HungrySquare.HEIGHT);
                        startPos[8] = new Vector2(HungrySquare.WIDTH-200,HungrySquare.HEIGHT);

                        startPos[9] = new Vector2(HungrySquare.WIDTH/2,0);
                        startPos[10] = new Vector2(HungrySquare.WIDTH,0);
                        startPos[11] = new Vector2(0,0);
                        player.addScale(new Vector2(foodL[i].getScale().x / 4, (foodL[i].getScale().y / 4)));
                        score += (foodL[i].getScale().x / 4) * 100;
                        radm();
                        //System.out.println(random[i]);
                        foodL[i].reinit(startPos[i], new Vector2(0.1f, 0.1f), random[i], 200f, new Texture(clr + ".jpg"));
                        float rad = HungrySquare.scls(player.scale.x, 0.8f);
                        foodL[i].setRawScale(new Vector2(rad, rad));
                    }
                }
            }

            //regionSingle Food
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
                player.addScale(new Vector2(foods.getScale().x/4, foods.getScale().y/4));
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
            //endregion

            if (inputtaken) {
                player.setPosition(new Vector2(Gdx.input.getX() - (player.getSprite().getTexture().getWidth() / 2) * player.scale.x, (HungrySquare.HEIGHT - Gdx.input.getY()) - (player.getSprite().getTexture().getHeight() / 2) * player.scale.y));
            }

            handleInput();
        }

        if(Energy == 0)
        {
            //GameOver
            gsm.set(new GameOver(gsm, score));
            dispose();
        }

        for (int i = 0; i < foodL.length; i++)
        {
            foodL[i].Update();
        }

    }

    @Override
    public void render(SpriteBatch sb) {

        if (in == false) {
            //System.out.println("Isiniting");
            init();
            in = true;
        }
        //sb.setProjectionMatrix(cam.combined);
        sb.begin();

        //regionSingle Food
       /* font1.getData().addScale(1, 1);
        font1.setColor(Color.BLACK);
        font1.draw(sb, "Score : " + score, HungrySquare.WIDTH - 75f, HungrySquare.HEIGHT - 20f, 0, Align.center, true);*/
//      foods.getSprite().draw(sb);
        //endregion


        font1.getData().setScale(1, 1);
        font1.setColor(Color.BLACK);
        sb.draw(scoretex, HungrySquare.WIDTH-140, HungrySquare.HEIGHT-92, 40, 40);
        sb.draw(lifetex, HungrySquare.WIDTH-140, HungrySquare.HEIGHT-192, 40, 40);
        font1.draw(sb, "" + (int)score,  HungrySquare.WIDTH-80, HungrySquare.HEIGHT - 57);
        font1.setColor(Color.GRAY);
        font1.draw(sb, "" + Energy, HungrySquare.WIDTH-80, HungrySquare.HEIGHT - 157);
       if(isresizing == true)
        {
            font2.setColor(Color.BLACK);
            font2.draw(sb, "Making the World Bigger", HungrySquare.WIDTH/2, HungrySquare.HEIGHT - 300, 0, Align.center, true);
        }


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
            //debugmode(Color.BLACK, foodL[i].getCollider());
        }
        //debugmode(Color.BLACK, player.getCollider());
        for(int i = 0; i < foodL.length; i++)
        {
            //spr.line(foodL[i].position, foodL[i].target);
        }
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
        scoretex.dispose();
        lifetex.dispose();
        player.getSprite().getTexture().dispose();
        spr.dispose();
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

    public void radm()
    {
        for(int i = 0; i < 3; i++)
        {
            random[i] = new Vector2(HungrySquare.WIDTH + 20, MathUtils.random(0f, HungrySquare.HEIGHT));
        }


        for(int i = 3; i < 6; i++)
        {
            random[i] = new Vector2(-50, MathUtils.random(0f, HungrySquare.HEIGHT));
        }


        for(int i = 6; i < 9; i++)
        {
            random[i] = new Vector2(MathUtils.random(0f, HungrySquare.WIDTH), -50);
        }


        for(int i = 9; i < 12; i++)
        {
            random[i] = new Vector2(MathUtils.random(0f, HungrySquare.WIDTH), HungrySquare.HEIGHT);
        }
    }
}

