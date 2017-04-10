package me.winter.boing.physics.v2.response;

import com.badlogic.gdx.utils.Pool.Poolable;
import me.winter.boing.physics.v2.Solid;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public abstract class CollisionResponse implements Poolable
{
	public static final CollisionResponse NONE = new CollisionResponse(CollisionResponseType.NONE) {
		@Override
		public void apply(Solid solid) {}

	};

	private CollisionResponseType type;

	public CollisionResponse(CollisionResponseType type)
	{
		this.type = type;
	}


	public abstract void apply(Solid solid);

	@Override
	public void reset()
	{

	}

	public CollisionResponseType getType()
	{
		return type;
	}
}
