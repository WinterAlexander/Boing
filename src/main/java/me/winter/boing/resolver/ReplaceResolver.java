package me.winter.boing.resolver;

import me.winter.boing.Collision;
import me.winter.boing.DynamicBody;
import me.winter.boing.World;

import static java.lang.Float.POSITIVE_INFINITY;
import static java.lang.Math.signum;

/**
 * CollisionResolver resolving collisions by replacing the objects colliding (if they are Dynamic)
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
public class ReplaceResolver implements CollisionResolver
{
	@Override
	public void resolve(Collision collision, World world)
	{
		if(collision.penetration == 0)
			return;

		DynamicBody toPush = resolveWeights(collision, world);

		if(toPush == null)
			return;

		float nx = toPush == collision.colliderA.getBody() ? -collision.normal.x : collision.normal.x;
		float ny = toPush == collision.colliderA.getBody() ? -collision.normal.y : collision.normal.y;

		replace(toPush, nx, ny, collision.penetration);
	}

	private void replace(DynamicBody solid, float nx, float ny, float pene)
	{
		float replaceX = nx * pene;
		float replaceY = ny * pene;

		solid.getPosition().sub(solid.getCollisionShifting());

		if(replaceX != 0f)
		{
			float dirX = signum(solid.getCollisionShifting().x);

			if(dirX != signum(replaceX))
				solid.getCollisionShifting().x += replaceX;
			else if(dirX == 0 || replaceX * dirX > solid.getCollisionShifting().x * dirX)
				solid.getCollisionShifting().x = replaceX;
		}

		if(replaceY != 0f)
		{
			float dirY = signum(solid.getCollisionShifting().y);

			if(dirY != signum(replaceY))
				solid.getCollisionShifting().y += replaceY;
			else if(dirY == 0 || replaceY * dirY > solid.getCollisionShifting().y * dirY)
				solid.getCollisionShifting().y = replaceY;
		}

		solid.getPosition().add(solid.getCollisionShifting());
	}

	private DynamicBody resolveWeights(Collision collision, World world)
	{
		if(!(collision.colliderA.getBody() instanceof DynamicBody))
		{
			if(!(collision.colliderB.getBody() instanceof DynamicBody))
				return null;

			return (DynamicBody)collision.colliderB.getBody();
		}
		else if(!(collision.colliderB.getBody() instanceof DynamicBody))
		{
			if(!(collision.colliderA.getBody() instanceof DynamicBody))
				return null;

			return (DynamicBody)collision.colliderA.getBody();
		}

		DynamicBody dynA = (DynamicBody)collision.colliderA.getBody();
		DynamicBody dynB = (DynamicBody)collision.colliderB.getBody();


		Collision swapped = world.getCollisionPool().obtain();

		float weightA = getWeight(world, dynA, -collision.normal.x, -collision.normal.y, swapped);
		float weightB = getWeight(world, dynB, collision.normal.x, collision.normal.y, swapped);

		world.getCollisionPool().free(swapped);

		if(weightA > weightB)
			return dynB;
		return dynA;
	}

	private float getWeight(World world, DynamicBody body, float nx, float ny, Collision tmpSwp)
	{
		float weight = body.getWeight();

		for(int i = 0; i < world.getCollisions().size; i++)
		{
			Collision collision = world.getCollisions().get(i);

			if(collision.colliderB.getBody() == body)
			{
				tmpSwp.setAsSwapped(collision);
				collision = tmpSwp;
			}

			if(collision.colliderA.getBody() == body)
			{
				if(collision.normal.dot(nx, ny) == 1f)
				{
					if(!(collision.colliderB.getBody() instanceof DynamicBody))
						return POSITIVE_INFINITY;

					weight += getWeight(world, (DynamicBody)collision.colliderB.getBody(), nx, ny, tmpSwp);
				}
			}
		}

		return weight;
	}
}
