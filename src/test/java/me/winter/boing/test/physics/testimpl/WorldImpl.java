package me.winter.boing.test.physics.testimpl;

import com.badlogic.gdx.utils.Array;
import me.winter.boing.physics.SimpleWorld;
import me.winter.boing.physics.DynamicBody;
import me.winter.boing.physics.Body;
import me.winter.boing.physics.resolver.CollisionResolver;
import me.winter.boing.physics.util.iterator.GDXArrayFilterIterator;
import me.winter.boing.physics.util.iterator.GDXArrayIndexIterator;
import me.winter.boing.physics.util.iterator.IndexIterator;
import me.winter.boing.physics.util.iterator.ReusableIterator;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class WorldImpl extends SimpleWorld
{
	private Array<Body> solids = new Array<>();

	private GDXArrayIndexIterator<Body> iterator = new GDXArrayIndexIterator<>(solids);
	private GDXArrayFilterIterator<DynamicBody> dynamics = new GDXArrayFilterIterator<>(solids, DynamicBody.class);

	public WorldImpl(CollisionResolver resolver)
	{
		super(resolver);
	}

	@Override
	protected IndexIterator<Body> getBodyIterator()
	{
		return iterator;
	}

	@Override
	protected ReusableIterator<DynamicBody> getDynamicIterator()
	{
		return dynamics;
	}

	public Array<Body> getSolids()
	{
		return solids;
	}
}
