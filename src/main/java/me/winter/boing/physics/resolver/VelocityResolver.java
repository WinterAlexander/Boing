package me.winter.boing.physics.resolver;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.DynamicSolid;
import me.winter.boing.physics.Collision;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class VelocityResolver extends CollisionResolver
{
	public VelocityResolver(float priority)
	{
		super(priority);
	}

	@Override
	public void resolve(Collision collision)
	{
		if(collision.colliderA.getSolid() instanceof DynamicSolid)
			reflect((DynamicSolid)collision.colliderA.getSolid(), collision.normalB);


		if(collision.colliderB.getSolid() instanceof DynamicSolid)
			reflect((DynamicSolid)collision.colliderB.getSolid(), collision.normalA);
	}

	private void reflect(DynamicSolid solid, Vector2 normal)
	{
		float x = solid.getVelocity().x; //to reflect
		float y = solid.getVelocity().y; //to reflect

		if(normal.y == 0)//means vertical tangent
		{
			solid.getVelocity().set(-x, y);
			return;
		}

		float a = -normal.x / normal.y;

		float d = (x + y * a) / (1 + a * a);

		solid.getVelocity().set(2 * d - x, 2 * d * a - y);
	}
}
