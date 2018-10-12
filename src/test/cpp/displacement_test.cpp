
#include <vec2.h>
#include <gtest/gtest.h>
#include <body.h>
#include <world.h>

using namespace boing;

TEST(displacement_test, wall_crash)
{
	body bodyA(vec2(0, 0), 0, vec2(1, 0));
	edge edgeA(bodyA, vec2(1, 0), 10, vec2::ZERO);
	bodyA.edges.push_back(edgeA);

	body bodyB(vec2(5, 0), 2, vec2::ZERO);
	edge edgeB(bodyB, vec2(-1, 0), 10, vec2::ZERO);
	bodyB.edges.push_back(edgeB);

	world world;
	world.bodies.push_back(bodyA);
	world.bodies.push_back(bodyB);

	world.tick(10.0f);

	for(const body& body : world.bodies)
		ASSERT_EQ(vec2(5, 0), body.position);
}