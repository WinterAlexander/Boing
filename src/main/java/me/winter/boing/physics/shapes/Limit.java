package me.winter.boing.physics.shapes;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.Solid;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public class Limit extends AbstractCollider
{
	public Vector2 normal;
	public float size;

	private final Vector2 p1 = new Vector2(), p2 = new Vector2();

	public Limit(Solid solid, float x, float y, Vector2 normal, float size)
	{
		super(solid, x, y);
		this.normal = normal;
		this.size = size;
	}

	public Vector2 getPoint1()
	{
		p1.set(normal).scl(size / 2).rotate90(-1).add(getAbsX(), getAbsY());
		return p1;
	}

	public Vector2 getPoint2()
	{
		p2.set(normal).scl(size / 2).rotate90(1).add(getAbsX(), getAbsY());
		return p2;
	}
}
