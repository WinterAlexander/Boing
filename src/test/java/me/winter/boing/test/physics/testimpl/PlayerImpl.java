package me.winter.boing.test.physics.testimpl;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.UpdatableBody;
import me.winter.boing.physics.impl.BodyImpl;
import me.winter.boing.physics.shapes.Collider;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import static java.lang.Math.abs;
import static me.winter.boing.test.physics.testimpl.PlayerImpl.IsKeyPressed.aPressed;
import static me.winter.boing.test.physics.testimpl.PlayerImpl.IsKeyPressed.dPressed;
import static me.winter.boing.test.physics.testimpl.PlayerImpl.IsKeyPressed.jumpPressed;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-13.
 */
public class PlayerImpl extends BodyImpl implements UpdatableBody
{
	private boolean onGround;
	private Vector2 velocity = new Vector2(), movement = new Vector2(), lastReplacement = new Vector2();


	@Override
	public void update(float delta)
	{
		velocity.x *= 0.9f;
		velocity.y -= 5;


		if(aPressed)
			velocity.add(onGround ? -100 : -20, 0);
		else if(dPressed)
			velocity.add(onGround ? 100 : 20, 0);

		if(onGround)
		{
			if(velocity.y < 0)
				velocity.y = 0;
			if(jumpPressed)
				velocity.add(0, 200);
		}
		onGround = false;
	}

	@Override
	public void notifyCollision(Collision collision)
	{
		if(abs(collision.normalB.angle() - 90f) < 0.001f)
			onGround = true;
	}

	@Override
	public Vector2 getVelocity()
	{
		return velocity;
	}

	@Override
	public Vector2 getMovement()
	{
		return movement;
	}

	@Override
	public float getWeight(Collision collision)
	{
		return 1f;
	}

	public static class IsKeyPressed
	{
		public static boolean aPressed = false, dPressed = false, jumpPressed = false;

		static
		{
			KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
				@Override
				public boolean dispatchKeyEvent(KeyEvent ke) {
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
				}
			});
		}
	}

	@Override
	public Vector2 getCollisionShifing()
	{
		return lastReplacement;
	}
}
