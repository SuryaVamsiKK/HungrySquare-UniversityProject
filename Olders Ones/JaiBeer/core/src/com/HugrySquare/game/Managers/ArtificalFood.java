package com.HugrySquare.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import java.lang.annotation.Target;

public class ArtificalFood {

    public Vector2 position;
    public Vector2 scale;
    private Vector2 target;
    private float speed;
    private Vector2 velocity;
    private Sprite wrap;
    private Polygon col;
    private float verts[];
    private boolean reached = false;

    public ArtificalFood(Vector2 position, Vector2 scale, Vector2 target, float speed, Texture tex) {
        reinit(position, scale, target, speed, tex);
    }

    public void setTarget(Vector2 targ)
    {
        position.x = 0;
        position.y = 0;
        target = targ;
        reached = false;
        velocity = setVelocity(position, target, speed);
    }

    public void setSpeed(float spd)
    {
        speed = spd;
    }

    public void reinit(Vector2 position, Vector2 scale, Vector2 target, float speed, Texture tex) {
        this.position = position;
        this.scale = scale;
        this.target = target;
        this.speed = speed;
        reached = false;
        velocity = setVelocity(position, target, speed);
        verts = new float[]{0,0,scale.x,0,scale.x,scale.y,0,scale.y};
        wrap = new Sprite(tex);
        col = new Polygon(verts);
    }

    public void Update()
    {
        wrap.setScale(scale.x, scale.y);
        wrap.setOrigin(0, 0);
        wrap.setPosition(position.x, position.y);

        col.setPosition(position.x, position.y);
        col.setScale(wrap.getTexture().getWidth(), wrap.getTexture().getHeight());
        col.setOrigin(0, 0);
    }

    public Polygon getCollider()
    {
        return col;
    }

    public void setCollider(Polygon ply)
    {
        col = ply;
    }

    public void move()
    {

        if(distance(position, target) > 1f) {
            position.x += velocity.x * Gdx.graphics.getDeltaTime();
            position.y += velocity.y * Gdx.graphics.getDeltaTime();
        }
        else
        {
            reached = true;
        }

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
        Vector2 d = new Vector2();
        Vector2 v = new Vector2();
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
        Vector2 d = new Vector2();
        d.x = targ.x - pos.x;
        d.y = targ.y - pos.y;
        dist = (float)Math.sqrt((d.x * d.x) + (d.y * d.y));
        return dist;
    }

    public boolean isReached()
    {
        return reached;
    }
}
