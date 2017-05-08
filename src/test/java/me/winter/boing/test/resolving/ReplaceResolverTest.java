package me.winter.boing.test.resolving;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.SimpleWorld;
import me.winter.boing.impl.DynamicBodyImpl;
import me.winter.boing.resolver.ReplaceResolver;
import me.winter.boing.shapes.Box;
import org.junit.Test;

import static me.winter.boing.test.util.VectorAssert.assertEquals;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-05-08.
 */
public class ReplaceResolverTest
{
	@Test
	public void simpleBoxBoxReplaceTest()
	{
		SimpleWorld world = new SimpleWorld(new ReplaceResolver());

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Box(solidImpl, 0, 0, 20, 20));
		solidImpl.getVelocity().set(15, 0);
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(10, 0);
		solidImpl2.addCollider(new Box(solidImpl2, 0, 0, 20, 20));
		world.add(solidImpl2);

		assertEquals(solidImpl.getPosition(), new Vector2(0, 0));
		assertEquals(solidImpl2.getPosition(), new Vector2(0, 0));
	}
}
