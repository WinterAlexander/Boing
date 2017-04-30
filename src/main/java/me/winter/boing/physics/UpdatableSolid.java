package me.winter.boing.physics;

/**
 * Represents a dynamic solid that can also update itself every frame.
 * Note that your system (entity component or hierarchical) should already
 * provide a way to update your game objects. Using your own way is recommended
 * over using this interface.
 * <p>
 * Created by Alexander Winter on 2017-04-30.
 */
public interface UpdatableSolid extends DynamicSolid
{
	void update(float delta);
}
