

#include <gtest/gtest.h>
#include <body.h>
#include <world.h>

using namespace boing;

TEST(edge_test, forward_collision)
{
	body bodyA(vec2(0, 0), 0, vec2::ZERO);
	edge edgeA(bodyA, vec2(1, 0), 10, vec2::ZERO);
	bodyA.edges.push_back(edgeA);

	body bodyB(vec2(5, 0), 0, vec2::ZERO);
	edge edgeB(bodyB, vec2(-1, 0), 10, vec2::ZERO);
	bodyB.edges.push_back(edgeB);

	manifold m;
	ASSERT_TRUE(edgeA.collision(vec2(10, 0), edgeB, m));
	ASSERT_EQ(10, m.surface);
	ASSERT_EQ(vec2(5, 0), m.penetration);
}


TEST(edge_test, offset_collision)
{
	body bodyA(vec2(0, 4), 0, vec2::ZERO);
	edge edgeA(bodyA, vec2(1, 0), 10, vec2::ZERO);
	bodyA.edges.push_back(edgeA);

	body bodyB(vec2(5, -4), 0, vec2::ZERO);
	edge edgeB(bodyB, vec2(-1, 0), 10, vec2::ZERO);
	bodyB.edges.push_back(edgeB);

	manifold m;
	ASSERT_TRUE(edgeA.collision(vec2(10, 0), edgeB, m));
	ASSERT_EQ(2, m.surface);
	ASSERT_EQ(vec2(5, 0), m.penetration);
}


TEST(edge_test, brush_no_collision)
{
	body bodyA(vec2(0, 5), 0, vec2::ZERO);
	edge edgeA(bodyA, vec2(1, 0), 10, vec2::ZERO);
	bodyA.edges.push_back(edgeA);

	body bodyB(vec2(5, -5), 0, vec2::ZERO);
	edge edgeB(bodyB, vec2(-1, 0), 10, vec2::ZERO);
	bodyB.edges.push_back(edgeB);

	manifold m;
	ASSERT_FALSE(edgeA.collision(vec2(10, 0), edgeB, m));
}