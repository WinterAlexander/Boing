package me.winter.boing.testimpl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.Body;
import me.winter.boing.Collision;
import me.winter.boing.UpdatableBody;
import me.winter.boing.impl.BodyImpl;
import me.winter.boing.impl.DynamicBodyImpl;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import static me.winter.boing.testimpl.PlayerImpl.IsKeyPressed.aPressed;
import static me.winter.boing.testimpl.PlayerImpl.IsKeyPressed.dPressed;
import static me.winter.boing.testimpl.PlayerImpl.IsKeyPressed.jumpPressed;
import static me.winter.boing.util.VectorUtil.DOWN;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-13.
 */
public class PlayerImpl extends DynamicBodyImpl implements UpdatableBody
{
	protected boolean onGround;

	@Override
	public void update(float delta)
	{
		getVelocity().x *= 0.9f;


		if(aPressed)
			getVelocity().add(onGround ? -30 : -20, 0);
		else if(dPressed)
			getVelocity().add(onGround ? 30 : 20, 0);

		if(onGround)
		{
			if(getVelocity().y < 0)
				getVelocity().y = 0;
			if(jumpPressed)
				getVelocity().add(0, 200);
		}

		getVelocity().y -= 5;

		onGround = false;
	}

	@Override
	public void notifyCollision(Collision collision)
	{
		if(collision.normal.dot(DOWN) > 0.7 && collision.impactVelA.dot(DOWN) > 0.7)
			onGround = true;
	}

	@Override
	public float getWeight()
	{
		return 10_000f;
	}

	public static class IsKeyPressed
	{
		public static boolean aPressed = false, dPressed = false, jumpPressed = false;

		static
		{
			KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(ke -> {
				switch (ke.getID())
				{
					case KeyEvent.KEY_PRESSED:
						if (ke.getKeyCode() == KeyEvent.VK_A)
							aPressed = true;

						if (ke.getKeyCode() == KeyEvent.VK_D)
							dPressed = true;

						if(ke.getKeyCode() == KeyEvent.VK_W)
							jumpPressed = true;

						break;

					case KeyEvent.KEY_RELEASED:
						if (ke.getKeyCode() == KeyEvent.VK_A)
							aPressed = false;

						if (ke.getKeyCode() == KeyEvent.VK_D)
							dPressed = false;

						if(ke.getKeyCode() == KeyEvent.VK_W)
							jumpPressed = false;

						break;
				}
				return false;
			});
		}
	}
}
