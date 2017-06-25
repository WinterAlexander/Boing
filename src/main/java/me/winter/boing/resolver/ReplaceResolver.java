package me.winter.boing.resolver;

import com.badlogic.gdx.utils.Array;
import me.winter.boing.Body;
import me.winter.boing.Collision;
import me.winter.boing.DynamicBody;
import me.winter.boing.World;

import static java.lang.Float.POSITIVE_INFINITY;

/**
 * CollisionResolver resolving collisions by replacing the position of the objects colliding
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
public class ReplaceResolver implements CollisionResolver
{
	private final Array<DynamicBody> alreadyChecked = new Array<>();

	@Override
	public boolean resolve(Collision collision, World world)
	{
		float pene = collision.penetration.getValue();
		float surface = collision.contactSurface.getValue();

		System.out.println((collision.normal.x != 0 ? "h" : "v") + collision.hashCode() + ": " + surface);

		if(pene <= 0 || surface <= 0) //corner glitch causes trouble here
			return false;

		float ratio = resolveWeights(collision, world);

		if(ratio != 1)
		{
			float amount = (1f - ratio) * pene;

			world.getState((DynamicBody)collision.colliderA.getBody()).shift(amount * -collision.normal.x, amount * -collision.normal.y);
		}

		if(ratio != 0)
		{
			float amount = ratio * pene;

			world.getState((DynamicBody)collision.colliderB.getBody()).shift(amount * collision.normal.x, amount * collision.normal.y);
		}

		return true;
	}

	private float resolveWeights(Collision collision, World world)
	{
		if(!(collision.colliderA.getBody() instanceof DynamicBody))
			return 1f;

		if(!(collision.colliderB.getBody() instanceof DynamicBody))
			return 0f;

		alreadyChecked.clear();
		float weightA = getWeight(world, (DynamicBody)collision.colliderA.getBody(), -collision.normal.x, -collision.normal.y);

		alreadyChecked.clear();
		float weightB = getWeight(world, (DynamicBody)collision.colliderB.getBody(), collision.normal.x, collision.normal.y);

		return weightA > weightB ? 1 : 0;

	}

	private float getWeight(World world, DynamicBody dynamic, float nx, float ny)
	{
		float weight = dynamic.getWeight();
		alreadyChecked.add(dynamic);

		for(Collision collision : world.getState(dynamic).getCollisions())
		{
			if(collision.normal.dot(nx, ny) == 1f)
			{
				if(!(collision.colliderB.getBody() instanceof DynamicBody))
					return POSITIVE_INFINITY;

				if(alreadyChecked.contains((DynamicBody)collision.colliderB.getBody(), true))
					continue;

				float w = getWeight(world, (DynamicBody)collision.colliderB.getBody(), nx, ny);

				if(w == POSITIVE_INFINITY)
					return w;
				weight += w;
			}
		}

		return weight;
	}
}
