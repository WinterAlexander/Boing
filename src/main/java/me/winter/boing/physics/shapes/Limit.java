package me.winter.boing.physics.shapes;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.Solid;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public class Limit extends AbstractShape
{
	public Vector2 normal;

	public Limit(Solid solid, float x, float y, Vector2 normal)
	{
		super(solid, x, y);
		this.normal = normal;
	}
}
