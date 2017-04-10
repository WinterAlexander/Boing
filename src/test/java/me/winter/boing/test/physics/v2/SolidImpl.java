package me.winter.boing.test.physics.v2;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.physics.v2.response.CollisionResponse;
import me.winter.boing.physics.v2.Solid;
import me.winter.boing.physics.v2.colliders.CircleCollider;
import me.winter.boing.physics.v2.colliders.Collider;

import java.awt.Graphics;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class SolidImpl implements Solid
{
	private Vector2 position = new Vector2(), velocity = new Vector2(), movement = new Vector2();

	private Array<Collider> colliders = new Array<>();
	private Array<CollisionResponse> responses = new Array<>();
	private boolean fresh;

	@Override
	public Vector2 getPosition()
	{
		return position;
	}

	@Override
	public Vector2 getVelocity()
	{
		return velocity;
	}

	@Override
	public Array<Collider> getColliders()
	{
		return colliders;
	}

	@Override
	public Vector2 getMovement()
	{
		return movement;
	}

	@Override
	public Array<CollisionResponse> responses()
	{
		return responses;
	}

	@Override
	public void crush()
	{

	}

	@Override
	public boolean freshVel()
	{
		return fresh;
	}

	@Override
	public void setVelFresh(boolean fresh)
	{
		this.fresh = fresh;
	}

	public void draw(Graphics g)
	{
		float r = ((CircleCollider)getColliders().get(0)).getRadius();
		g.drawOval((int)(getPosition().x - r), (int)(600 - getPosition().y - r), (int)r, (int)r);
	}
}
