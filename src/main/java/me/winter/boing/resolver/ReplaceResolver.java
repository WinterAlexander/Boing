package me.winter.boing.resolver;

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

		float ratio = resolveWeights(collision);

		if(ratio != 1)
			replace((DynamicBody)collision.colliderA.getBody(), -collision.normal.x, -collision.normal.y, (1f - ratio) * collision.penetration);

		if(ratio != 0)
			replace((DynamicBody)collision.colliderB.getBody(), collision.normal.x, collision.normal.y, ratio * collision.penetration);
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

	private float resolveWeights(Collision collision)
	{
		if(!(collision.colliderA.getBody() instanceof DynamicBody))
			return 1f;

		if(!(collision.colliderB.getBody() instanceof DynamicBody))
			return 0f;

		float weightA = getWeight((DynamicBody)collision.colliderA.getBody(), -collision.normal.x, -collision.normal.y);
		float weightB = getWeight((DynamicBody)collision.colliderB.getBody(), collision.normal.x, collision.normal.y);

		if(capWeight)
			return weightA > weightB ? 1 : 0;

		return weightRatio(weightA, weightB);

	}

	private float getWeight(DynamicBody dynamic, float nx, float ny)
	{
		float weight = dynamic.getWeight();

		for(Collision collision : dynamic.getCollisions())
		{
			if(collision.normal.dot(nx, ny) == 1f)
			{
				if(!(collision.colliderB.getBody() instanceof DynamicBody))
					return POSITIVE_INFINITY;

				weight += getWeight((DynamicBody)collision.colliderB.getBody(), nx, ny);
			}
		}

		return weight;
	}
}
