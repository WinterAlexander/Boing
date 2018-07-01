package me.winter.boing.testimpl;

import me.winter.boing.UpdatableBody;
import me.winter.boing.impl.DynamicBodyImpl;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import static me.winter.boing.testimpl.FlyingPlayerImpl.IsKeyPressed.aPressed;
import static me.winter.boing.testimpl.FlyingPlayerImpl.IsKeyPressed.dPressed;
import static me.winter.boing.testimpl.FlyingPlayerImpl.IsKeyPressed.sPressed;
import static me.winter.boing.testimpl.FlyingPlayerImpl.IsKeyPressed.wPressed;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-05-22.
 */
public class FlyingPlayerImpl extends DynamicBodyImpl implements UpdatableBody
{
	@Override
	public void update(float delta)
	{
		getVelocity().scl(0.9f);


		if(aPressed)
			getVelocity().add(-20, 0);
		if(dPressed)
			getVelocity().add(20, 0);
		if(wPressed)
			getVelocity().add(0, 20);
		if(sPressed)
			getVelocity().add(0, -20);
	}

	@Override
	public float getWeight()
	{
		return 100f;
	}

	public static class IsKeyPressed
	{
		public static boolean aPressed = false, dPressed = false, wPressed = false, sPressed = false;

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
							wPressed = true;

						if(ke.getKeyCode() == KeyEvent.VK_S)
							sPressed = true;

						break;

					case KeyEvent.KEY_RELEASED:
						if (ke.getKeyCode() == KeyEvent.VK_A)
							aPressed = false;

						if (ke.getKeyCode() == KeyEvent.VK_D)
							dPressed = false;

						if(ke.getKeyCode() == KeyEvent.VK_W)
							wPressed = false;

						if(ke.getKeyCode() == KeyEvent.VK_S)
							sPressed = false;

						break;
				}
				return false;
			});
		}
	}
}
