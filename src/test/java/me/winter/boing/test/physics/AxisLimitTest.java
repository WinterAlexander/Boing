package me.winter.boing.test.physics;

import me.winter.boing.physics.IntVector;
import me.winter.boing.physics.limit.AxisLimit;
import me.winter.boing.physics.limit.Axis;
import me.winter.boing.physics.limit.LimitHolder;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-03-28.
 */
public class AxisLimitTest
{
	/**
	 * Test the success of a simple collision on X axis
	 */
	@Test
	public void testObviousCollisionXAxis()
	{
		MockLimitHolder holderA = new MockLimitHolder();

		holderA.position.x = 0;
		holderA.movement.x = 2000;

		MockLimitHolder holderB = new MockLimitHolder();

		holderB.position.x = 1000;
		holderB.movement.x = -5000;

		AxisLimit axisLimitA = new AxisLimit(holderA, Axis.X, false, 0, 0, 1);

		AxisLimit axisLimitB = new AxisLimit(holderB, Axis.X, true, 0, 0, 1);

		assertTrue(axisLimitA.collides(axisLimitB));
		assertTrue(axisLimitB.collides(axisLimitA));
	}

	/**
	 * Test the success of a simple collision on Y axis
	 */
	@Test
	public void testObviousCollisionYAxis()
	{
		MockLimitHolder holderA = new MockLimitHolder();

		holderA.position.y = 0;
		holderA.movement.y = 2000;

		MockLimitHolder holderB = new MockLimitHolder();

		holderB.position.y = 1000;
		holderB.movement.y = -5000;

		AxisLimit axisLimitA = new AxisLimit(holderA, Axis.Y, false, 0, 0, 1);

		AxisLimit axisLimitB = new AxisLimit(holderB, Axis.Y, true, 0, 0, 1);

		assertTrue(axisLimitA.collides(axisLimitB));
		assertTrue(axisLimitB.collides(axisLimitA));
	}

	@Test
	public void testIrregularCollision()
	{
		MockLimitHolder holderA = new MockLimitHolder();

		holderA.position.set(3423, 8907);
		holderA.movement.set(350, 200);

		MockLimitHolder holderB = new MockLimitHolder();

		holderB.position.set(4000, 8907);
		holderB.movement.set(-300, 0);

		AxisLimit axisLimitA = new AxisLimit(holderA, Axis.X, false, 0, 0, 1000);

		AxisLimit axisLimitB = new AxisLimit(holderB, Axis.X, true, 0, 0, 1000);

		assertTrue(axisLimitA.collides(axisLimitB));
		assertTrue(axisLimitB.collides(axisLimitA));
	}

	@Test
	public void testDiagonalCollision()
	{
		MockLimitHolder holderA = new MockLimitHolder();

		holderA.position.set(500, 500);

		MockLimitHolder holderB = new MockLimitHolder();

		holderB.position.set(3500, 3500);
		holderB.movement.set(-4500, -4000);

		AxisLimit topA = new AxisLimit(holderA, Axis.Y, false, -500, 500, 1000);

		AxisLimit bottomB = new AxisLimit(holderB, Axis.Y, true, -500, -500, 1000);

		assertTrue(topA.collides(bottomB));
		assertTrue(bottomB.collides(topA));
	}

	/**
	 * Test the success of a collision of X aligned limits making a cross by their movement
	 */
	@Test
	public void testCrossCollision()
	{
		MockLimitHolder holderA = new MockLimitHolder();
		holderA.position.set(0, 0);
		holderA.movement.set(2000, 2000);

		MockLimitHolder holderB = new MockLimitHolder();
		holderB.position.set(1500, 0);
		holderB.movement.set(-2000, 2000);

		AxisLimit axisLimitA = new AxisLimit(holderA, Axis.X, false, 0, -500, 1000);

		AxisLimit axisLimitB = new AxisLimit(holderB, Axis.X, true, 0, -500, 1000);

		assertTrue(axisLimitA.collides(axisLimitB));
		assertTrue(axisLimitB.collides(axisLimitA));
	}

	@Test
	public void testGroundNegativeY()
	{
		MockLimitHolder holderA = new MockLimitHolder();
		holderA.position.set(-5000, -5000);
		holderA.movement.set(0, -2000);

		MockLimitHolder holderB = new MockLimitHolder();
		holderB.position.set(-5000, -6000);
		holderB.movement.set(0, 0);

		AxisLimit axisLimitA = new AxisLimit(holderA, Axis.Y, true, 0, 0, 1000);

		AxisLimit axisLimitB = new AxisLimit(holderB, Axis.Y, false, 0, 0, 1000);

		assertTrue(axisLimitA.collides(axisLimitB));
		assertTrue(axisLimitB.collides(axisLimitA));
	}

	/**
	 * Test there's no collision between objects that go one over the other without moving on the Y axis
	 */
	@Test
	public void testNoCollisionSideToSide()
	{
		MockLimitHolder holderA = new MockLimitHolder();

		holderA.position.x = 0;
		holderA.movement.x = 2000;

		MockLimitHolder holderB = new MockLimitHolder();

		holderB.position.x = 1000;
		holderB.position.y = 1000;
		holderB.movement.x = -5000;

		AxisLimit axisLimitA = new AxisLimit(holderA, Axis.X, false, 0, 0, 1000);
		AxisLimit axisLimitB = new AxisLimit(holderB, Axis.X, true, 0, 0, 1000);

		assertFalse(axisLimitA.collides(axisLimitB));
		assertFalse(axisLimitB.collides(axisLimitA));
	}

	/**
	 * Test there's no collision between objects that go one over the other without moving on the Y axis
	 */
	@Test
	public void testNoCollisionOneBehindOther()
	{
		MockLimitHolder holderA = new MockLimitHolder();

		holderA.position.x = 0;
		holderA.movement.x = 2000;

		MockLimitHolder holderB = new MockLimitHolder();

		holderB.position.x = 0;
		holderB.movement.x = 2000;

		AxisLimit axisLimitA = new AxisLimit(holderA, Axis.X, false, 0, 0, 1000);
		AxisLimit axisLimitB = new AxisLimit(holderB, Axis.X, true, 0, 0, 1000);

		assertFalse(axisLimitA.collides(axisLimitB));
		assertFalse(axisLimitB.collides(axisLimitA));
	}

	/**
	 * Ensure you can't penetrate a box by it's corner
	 */
	@Test
	public void testCornerGlitchFixed()
	{
		MockLimitHolder holderA = new MockLimitHolder();

		holderA.position.set(500, 500);

		MockLimitHolder holderB = new MockLimitHolder();

		holderB.position.set(3500, 3500);
		holderB.movement.set(-4000, -4000);

		AxisLimit topA = new AxisLimit(holderA, Axis.Y, false, -500, 500, 1000);
		AxisLimit rightA = new AxisLimit(holderA, Axis.X, false, 500, -500, 1000);

		AxisLimit bottomB = new AxisLimit(holderB, Axis.Y, true, -500, -500, 1000);
		AxisLimit leftB = new AxisLimit(holderB, Axis.X, true, -500, -500, 1000);

		assertTrue(topA.collides(bottomB));
		assertTrue(bottomB.collides(topA));
		assertTrue(rightA.collides(leftB));
		assertTrue(leftB.collides(rightA));
	}

	private static class MockLimitHolder implements LimitHolder
	{
		IntVector position = new IntVector(), movement = new IntVector();

		@Override
		public IntVector getPosition()
		{
			return position;
		}

		@Override
		public IntVector getMovement()
		{
			return movement;
		}

		@Override
		public float weightFor(LimitHolder other)
		{
			return 0.5f;
		}
	}
}
