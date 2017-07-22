package me.winter.boing.resolver;

import com.badlogic.gdx.utils.ObjectSet;
import me.winter.boing.Collision;
import me.winter.boing.DynamicBody;
import me.winter.boing.World;

import static java.lang.Float.POSITIVE_INFINITY;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-07-22.
 */
public class CappedWeightResolver implements WeightResolver
{
	private final ObjectSet<DynamicBody> alreadyChecked = new ObjectSet<>();

	@Override
	public void resolveWeight(Collision collision, World world)
	{
		if(!(collision.colliderA.getBody() instanceof DynamicBody))
		{
			collision.weightRatio = 1f;
			return;
		}

		if(!(collision.colliderB.getBody() instanceof DynamicBody))
		{
			collision.weightRatio = 0f;
			return;
		}

		alreadyChecked.clear();
		float weightA = getWeight(world, (DynamicBody)collision.colliderA.getBody(), -collision.normal.x, -collision.normal.y);

		alreadyChecked.clear();
		float weightB = getWeight(world, (DynamicBody)collision.colliderB.getBody(), collision.normal.x, collision.normal.y);

		collision.weightRatio = weightA > weightB ? 1 : 0;

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

				if(alreadyChecked.contains((DynamicBody)collision.colliderB.getBody()))
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
