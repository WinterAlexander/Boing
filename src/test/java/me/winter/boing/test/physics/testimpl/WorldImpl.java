package me.winter.boing.test.physics.testimpl;

import com.badlogic.gdx.utils.Array;
import me.winter.boing.physics.AbstractWorld;
import me.winter.boing.physics.Solid;
import me.winter.boing.physics.resolver.CollisionResolver;
import me.winter.boing.physics.util.iterator.GDXArrayIndexIterator;
import me.winter.boing.physics.util.iterator.IndexIterator;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class WorldImpl extends AbstractWorld
{
	private Array<Solid> solids = new Array<>();

	private GDXArrayIndexIterator<Solid> iterator = new GDXArrayIndexIterator<>(solids);

	public WorldImpl(CollisionResolver resolver)
	{
		super(resolver);
	}

	@Override
	protected IndexIterator<Solid> getSolidIterator()
	{
		return iterator;
	}

	public Array<Solid> getSolids()
	{
		return solids;
	}
}
