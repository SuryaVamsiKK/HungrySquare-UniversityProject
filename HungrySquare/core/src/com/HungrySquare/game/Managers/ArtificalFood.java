package com.HungrySquare.game.Managers;

import com.HungrySquare.game.HungrySquare;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class ArtificalFood {

    public Vector2 position;
    public Vector2 scale;
    public Vector2 target;
    private float speed;
    private Vector2 velocity;
    private Sprite wrap;
    private Polygon col;
    private float verts[];
    private boolean reached = false;
    private  Vector2 d;
    private Vector2 v;

    public ArtificalFood(Vector2 position, Vector2 scale, Vector2 target, float speed, Texture tex) {
        reinit(position, scale, target, speed, tex);
    }

    public void setTarget(Vector2 targ)
    {
       // System.out.println(targ.x +" : "+ targ.y);
        reached = false;
        velocity = setVelocity(position, targ, speed);
    }

    public void setSpeed(float spd)
    {
        speed = spd;
    }

    public void reinit(Vector2 position, Vector2 scale, Vector2 target, float speed, Texture tex) {

        d = new Vector2(0,0);
        v = new Vector2(0,0);
        this.scale = scale;
        this.target = target;
        this.speed = speed;
        this.position = position;
        velocity = setVelocity(position, target, speed);
        verts = new float[]{-scale.x/2,-scale.y/2,scale.x/2,-scale.y/2,scale.x/2,scale.y/2,-scale.x/2,scale.y/2};
        wrap = new Sprite(tex);
        col = new Polygon(verts);
        target.x = HungrySquare.WIDTH + wrap.getTexture().getWidth()*this.scale.x + 200f;
        target.y = MathUtils.random(-200f, HungrySquare.HEIGHT + 200f);
        setTarget(target);
    }

    public void Update()
    {
        wrap.setScale(scale.x, scale.y);
        wrap.setOrigin(0, 0);
        wrap.setPosition(position.x, position.y);

        col.setPosition(position.x + ((wrap.getTexture().getWidth()/2)*scale.x), position.y + ((wrap.getTexture().getHeight()/2)*scale.y));
        col.setOrigin(0, 0);
        col.setScale(wrap.getTexture().getWidth() - 300f, wrap.getTexture().getHeight() - 300f);
    }

    public Polygon getCollider()
    {
        return col;
    }

    public void setCollider(Polygon ply)
    {
        col = ply;
    }

    public void move() {

        if(distance(position, target) < 30) {
            reached = true;
        }
        else
        {
            position.x += velocity.x * Gdx.graphics.getDeltaTime();
            position.y += velocity.y * Gdx.graphics.getDeltaTime();
        }
        //System.out.println(reached +" : "+distance(position, target));
        //System.out.println(distance(position, target));
    }

    public void setPosition(Vector2 pose)
    {
        position = pose;
    }

    public Sprite getSprite()
    {
        return wrap;
    }

    public Vector2 setVelocity(Vector2 pos, Vector2 targ, float speed)
    {
        float angle;
        d.x = targ.x - pos.x;
        d.y = targ.y - pos.y;
        angle = MathUtils.atan2(d.y, d.x);
        v.x = MathUtils.cos(angle)*speed;
        v.y = MathUtils.sin(angle)*speed;
        return v;
    }

    public float distance(Vector2 pos, Vector2 targ)
    {
        float dist;
        d.x = targ.x - pos.x;
        d.y = targ.y - pos.y;
        dist = (float) Math.sqrt(
                Math.pow(d.x, 2) +
                        Math.pow(d.y, 2) );
        return dist;
    }

    public boolean isReached()
    {
        return reached;
    }

    public void addScale(Vector2 scl)
    {
        scale.x += scl.x;
        scale.y += scl.y;
        verts = new float[]{-scale.x/2,-scale.y/2,scale.x/2,-scale.y/2,scale.x/2,scale.y/2,-scale.x/2,scale.y/2};
        setCollider(new Polygon(verts));
    }

    public void setRawScale(Vector2 scl)
    {
        scale = scl;
        verts = new float[]{-scale.x/2,-scale.y/2,scale.x/2,-scale.y/2,scale.x/2,scale.y/2,-scale.x/2,scale.y/2};
        setCollider(new Polygon(verts));
    }

    public Vector2 getScale()
    {
        return scale;
    }
}
