package me.winter.boing.test.physics.testimpl;

import com.badlogic.gdx.utils.Array;
import me.winter.boing.physics.SimpleWorld;
import me.winter.boing.physics.DynamicSolid;
import me.winter.boing.physics.Solid;
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
	private Array<Solid> solids = new Array<>();

	private GDXArrayIndexIterator<Solid> iterator = new GDXArrayIndexIterator<>(solids);
	private GDXArrayFilterIterator<DynamicSolid> dynamics = new GDXArrayFilterIterator<>(solids, DynamicSolid.class);

	public WorldImpl(CollisionResolver resolver)
	{
		super(resolver);
	}

	@Override
	protected IndexIterator<Solid> getSolidIterator()
	{
		return iterator;
	}

	@Override
	protected ReusableIterator<DynamicSolid> getDynamicIterator()
	{
		return dynamics;
	}

	public Array<Solid> getSolids()
	{
		return solids;
	}
}
