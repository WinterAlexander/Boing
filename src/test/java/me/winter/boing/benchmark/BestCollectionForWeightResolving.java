package me.winter.boing.benchmark;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import org.junit.Test;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-07-12.
 */
public class BestCollectionForWeightResolving
{

	@Test
	public void singleCollisionTest()
	{
		Array<TestCollision> collisions = new Array<>();

		Object a = "A", b = "B";

		collisions.add(new TestCollision(a, b));

		execute(collisions, a);
	}

	@Test
	public void linearTripleCollisionTest()
	{
		Array<TestCollision> collisions = new Array<>();

		Object a = "A", b = "B", c = "C", d = "D";

		collisions.add(new TestCollision(a, b));
		collisions.add(new TestCollision(b, c));
		collisions.add(new TestCollision(c, d));

		execute(collisions, a);
	}

	@Test
	public void clusteredTripleCollisionTest()
	{
		Array<TestCollision> collisions = new Array<>();

		Object a = "A", b = "B", c = "C";

		collisions.add(new TestCollision(a, b));
		collisions.add(new TestCollision(b, c));
		collisions.add(new TestCollision(a, c));

		execute(collisions, a);
	}

	@Test
	public void clusteredMessCollisionTest()
	{

	}

	private void execute(Array<TestCollision> collisions, Object startObject)
	{
		TestCollection array = new ArrayWrapper(new Array());
		TestCollection set = new ObjectSetWrapper(new ObjectSet<>());

		long start, end;

		for(int i = 0; i < 100; i++)
		{
			array.clear();
			recursiveExample(collisions, array, startObject);
		}

		array.clear();
		start = System.nanoTime();
		recursiveExample(collisions, array, startObject);
		end = System.nanoTime();

		System.out.println("Array: " + (end - start) + "ns");

		for(int i = 0; i < 100; i++)
		{
			set.clear();
			recursiveExample(collisions, set, startObject);
		}

		set.clear();
		start = System.nanoTime();
		recursiveExample(collisions, set, startObject);
		end = System.nanoTime();


		System.out.println("Object set: " + (end - start) + "ns");
	}

	private int recursiveExample(Array<TestCollision> collisions, TestCollection collection, Object object)
	{
		collection.add(object);
		int w = 0;

		for(int i = 0; i < collisions.size; i++)
		{
			TestCollision collision = collisions.get(i);
			if(object == collision.objectA)
			{
				if(collection.contains(collision.objectB))
					continue;

				w += recursiveExample(collisions, collection, collision.objectB);
			}
			else if(object == collision.objectB)
			{
				if(collection.contains(collision.objectA))
					continue;

				w += recursiveExample(collisions, collection, collision.objectA);
			}
		}

		return w;
	}

	private static class TestCollision
	{
		public TestCollision(Object objectA, Object objectB)
		{
			this.objectA = objectA;
			this.objectB = objectB;
		}

		public final Object objectA, objectB;
	}

	private interface TestCollection
	{
		boolean contains(Object o);
		void add(Object o);
		void clear();
	}

	private static class ArrayWrapper implements TestCollection
	{
		private Array array;

		public ArrayWrapper(Array array)
		{
			this.array = array;
		}

		@Override
		public boolean contains(Object o)
		{
			return array.contains(o, true);
		}

		@Override
		public void add(Object o)
		{
			array.add(o);
		}

		@Override
		public void clear()
		{
			array.clear();
		}
	}

	private static class ObjectSetWrapper implements TestCollection
	{
		private ObjectSet set;

		public ObjectSetWrapper(ObjectSet set)
		{
			this.set = set;
		}

		@Override
		public boolean contains(Object o)
		{
			return set.contains(o);
		}

		@Override
		public void add(Object o)
		{
			set.add(o);
		}

		@Override
		public void clear()
		{
			set.clear();
		}
	}
}
