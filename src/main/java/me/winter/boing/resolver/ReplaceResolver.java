package me.winter.boing.resolver;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.Collision;
import me.winter.boing.DynamicBody;
import me.winter.boing.World;

import static java.lang.Float.POSITIVE_INFINITY;
import static java.lang.Math.signum;
import static me.winter.boing.util.VelocityUtil.weightRatio;

/**
 * CollisionResolver resolving collisions by replacing the position of the objects colliding
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
public class ReplaceResolver implements CollisionResolver
{
	private boolean capWeight;

	public ReplaceResolver()
	{
		this(true);
	}

	public ReplaceResolver(boolean capWeight)
	{
		this.capWeight = capWeight;
	}

	@Override
	public void resolve(Collision collision, World world)
	{
		if(collision.penetration == 0)
			return;

		float ratio = resolveWeights(collision, world);

		if(ratio != 1)
			replace((DynamicBody)collision.colliderA.getBody(),
					world.getState((DynamicBody)collision.colliderA.getBody()).getCollisionShifting(),
					-collision.normal.x,
					-collision.normal.y,
					(1f - ratio) * collision.penetration);

		if(ratio != 0)
			replace((DynamicBody)collision.colliderB.getBody(),
					world.getState((DynamicBody)collision.colliderB.getBody()).getCollisionShifting(),
					collision.normal.x,
					collision.normal.y,
					ratio * collision.penetration);
	}

	private void replace(DynamicBody solid, Vector2 collisionShifting, float nx, float ny, float pene)
	{
		float replaceX = nx * pene;
		float replaceY = ny * pene;

		solid.getPosition().sub(collisionShifting);

		if(replaceX != 0f)
		{
			float dirX = signum(collisionShifting.x);

			if(dirX != signum(replaceX))
				collisionShifting.x += replaceX;
			else if(dirX == 0 || replaceX * dirX > collisionShifting.x * dirX)
				collisionShifting.x = replaceX;
		}

		if(replaceY != 0f)
		{
			float dirY = signum(collisionShifting.y);

			if(dirY != signum(replaceY))
				collisionShifting.y += replaceY;
			else if(dirY == 0 || replaceY * dirY > collisionShifting.y * dirY)
				collisionShifting.y = replaceY;
		}

		solid.getPosition().add(collisionShifting);
	}

	private float resolveWeights(Collision collision, World world)
	{
		if(!(collision.colliderA.getBody() instanceof DynamicBody))
			return 1f;

		if(!(collision.colliderB.getBody() instanceof DynamicBody))
			return 0f;

		float weightA = getWeight(world, (DynamicBody)collision.colliderA.getBody(), -collision.normal.x, -collision.normal.y);
		float weightB = getWeight(world, (DynamicBody)collision.colliderB.getBody(), collision.normal.x, collision.normal.y);

		if(capWeight)
			return weightA > weightB ? 1 : 0;

		return weightRatio(weightA, weightB);

	}

	private float getWeight(World world, DynamicBody dynamic, float nx, float ny)
	{
		float weight = dynamic.getWeight();

		for(Collision collision : world.getState(dynamic).getCollisions())
		{
			if(collision.normal.dot(nx, ny) == 1f)
			{
				if(!(collision.colliderB.getBody() instanceof DynamicBody))
					return POSITIVE_INFINITY;

				weight += getWeight(world, (DynamicBody)collision.colliderB.getBody(), nx, ny);
			}
		}

		return weight;
	}
}
