# Boing
A simple 2D physics engine currently in development made with Java and LibGDX. You can follow its development on [Trello](https://trello.com/b/hOBzA1J5/boing).

### Features

The physic engine is designed to support Circle, AABB and Limit colliders. A limit is a part of a line (segment), 4 limits can make a rectangle in any rotation. This physic engine is not aiming to be realistic in anyway. The main reason is that realistic physic engines like Box2D gives non satisfactory results for 2D platformer games. As a game programmer, I wanted to have full control over my player movement and that's why I gave birth to this physic engine.

### Inspiration

My first physic engine was very weird because it had basically no source of inspiration. I made it only from the top of my head and it was working but I ran into giant issues. 

#### How it worked:

There was only Axis Aligned Limits and all collision detection was continuous but only took in consideration the movement of one entity at the time. The entities had to be sorted to decide which has the collision replacement priority and the replacement was simply a teleportation to the edge of the other limit by fixing the movement vector. 

The main problems were 
  - Impossibility for objects to push each other, no object were displaced farther than the original location of the frame
  - Really bad performance, using continuous collision detection for everything wasn't a good idea, especially with tiled terrain that does not ever enter in collision
  
#### Unity3D

After playing around a bit with Unity, I've learned a lot about the vocabulary of physic engines.
I've seen how objects are notified of collision events.
I've roughly understood how their collision handling worked and wanted to implement mine using the improvements I found into theirs. Of course my physic engine does not have all the features Unity's has but it is not meant to have them.

### LibGDX
Since games that will use this physic engine will most likely be on LibGDX, I wasn't scared to use it as a math library. I'm not using LibGDX collision detection but I'm using Vector and MathUtils classes. If you wish to use Boing without LibGDX, you can but you will have to replace the method calls and create a 2D Vector class. (Or just download LibGDX's one). In short, Boing doesn't require LibGDX to run but only needs it's utilities.

### SocialPlatformer

The purpose of this physic engine is mainly to be used with my currently developped game, SocialPlatformer. Unfortunately, SocialPlatformer is a private repository for security reasons. It requires a central server and a database to run and those must not be open to public.
