package me.winter.boing.physics.v2;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.physics.v2.response.CollisionResponse;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public interface DynamicSolid extends Solid
{
	Vector2 getMovement();
	Array<CollisionResponse> responses();
	boolean freshVel();
	void setVelFresh(boolean fresh);

	void crush();

	float getMass();
}
