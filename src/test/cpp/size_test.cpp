
#include <gtest/gtest.h>
#include <boing.h>
#include <vec2.h>

using namespace boing;

TEST(size_test, vector_size_test)
{
	ASSERT_EQ(sizeof(scalar_t) * 2, sizeof(vec2));
}