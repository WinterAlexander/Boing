package me.winter.boing.resolver;

import me.winter.boing.Collision;
import me.winter.boing.DynamicBody;

import static java.lang.Math.abs;

/**
 * CollisionResolver resolving collisions by replacing the objects colliding (if they are Dynamic)
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
public class ReplaceResolver implements CollisionResolver
{
	@Override
	public void resolve(Collision collision)
	{
		float delta = collision.weightRatio * collision.penetration;

		StringBuilder debugBuilder = new StringBuilder();

		debugBuilder.append("Delta: " + delta + "\n");
		debugBuilder.append("Original pos A: " + collision.colliderA.getBody().getPosition().y + "\n");
		debugBuilder.append("Original pos B: " + collision.colliderB.getBody().getPosition().y + "\n");

		debugBuilder.append("Predicted pos A: " + (collision.colliderA.getBody().getPosition().y + collision.penetration - collision.normalB.y * delta - ((DynamicBody)collision.colliderA.getBody()).getCollisionShifing().y) + "\n");
		debugBuilder.append("Predicted pos B: " + (collision.colliderB.getBody().getPosition().y + collision.normalA.y * delta - ((DynamicBody)collision.colliderB.getBody()).getCollisionShifing().y) + "\n");

		if(collision.colliderA.getBody().getPosition().y + collision.penetration - collision.normalB.y * delta - ((DynamicBody)collision.colliderA.getBody()).getCollisionShifing().y != collision.colliderB.getBody().getPosition().y + collision.normalA.y * delta - ((DynamicBody)collision.colliderB.getBody()).getCollisionShifing().y)
		{
			System.out.println("before rip");
		}

		if(delta < collision.penetration)
		{
			DynamicBody solid = (DynamicBody)collision.colliderA.getBody();

			if(abs(collision.normalB.x * delta) > abs(solid.getCollisionShifing().x))
			{
				solid.getPosition().x = solid.getPosition().x + collision.penetration - collision.normalB.x * delta - solid.getCollisionShifing().x;
				solid.getCollisionShifing().x = collision.penetration - collision.normalB.x * delta;
			}

			if(abs(collision.normalB.y * delta) > abs(solid.getCollisionShifing().y))
			{
				solid.getPosition().y = solid.getPosition().y + collision.penetration - collision.normalB.y * delta - solid.getCollisionShifing().y;
				solid.getCollisionShifing().y = collision.penetration - collision.normalB.y * delta;
			}

			/*
			float replaceX = collision.normalB.x * delta;
			float replaceY = collision.normalB.y * delta;

			if(abs(replaceX) > abs(solid.getCollisionShifing().x))
			{
				solid.getPosition().x += collision.penetration - replaceX - solid.getCollisionShifing().x;
				solid.getCollisionShifing().x = collision.penetration - replaceX;
			}

			if(abs(replaceY) > abs(solid.getCollisionShifing().y))
			{
				solid.getPosition().y += collision.penetration - replaceY - solid.getCollisionShifing().y;
				solid.getCollisionShifing().y = collision.penetration - replaceY;
			}*/
		}
		else
		{
			System.out.println("rip common sense");
		}

		if(delta > 0)
		{
			DynamicBody solid = (DynamicBody)collision.colliderB.getBody();

			if(abs(collision.normalA.x * delta) > abs(solid.getCollisionShifing().x))
			{
				solid.getPosition().x = solid.getPosition().x + collision.normalA.x * delta - solid.getCollisionShifing().x;
				solid.getCollisionShifing().x = collision.normalA.x * delta;
			}

			if(abs(collision.normalA.y * delta) > abs(solid.getCollisionShifing().y))
			{
				solid.getPosition().y = solid.getPosition().y + collision.normalA.y * delta - solid.getCollisionShifing().y;
				solid.getCollisionShifing().y = collision.normalA.y * delta;
			}

			/*
			float replaceX = collision.normalA.x * delta;
			float replaceY = collision.normalA.y * delta;

			if(abs(replaceX) > abs(solid.getCollisionShifing().x))
			{
				solid.getPosition().x += replaceX - solid.getCollisionShifing().x;
				solid.getCollisionShifing().x = replaceX;
			}

			if(abs(replaceY) > abs(solid.getCollisionShifing().y))
			{
				solid.getPosition().y += replaceY - solid.getCollisionShifing().y;
				solid.getCollisionShifing().y = replaceY;
			}*/
		}
		else
		{
			System.out.println("rip common sense");
		}

		debugBuilder.append("Final pos A: " + collision.colliderA.getBody().getPosition().y + "\n");
		debugBuilder.append("Final pos B: " + collision.colliderB.getBody().getPosition().y + "\n");

		if(collision.colliderA.getBody().getPosition().y != collision.colliderB.getBody().getPosition().y)
		{
			System.out.println("after rip");
			System.out.println(debugBuilder.toString());
		}
	}

}
