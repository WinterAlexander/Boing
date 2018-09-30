//
// Created by Alexander Winter on 2017-09-18.
//

#include <gtest/gtest.h>

TEST(trivial, trivial_one_equal_one)
{
    ASSERT_EQ(1, (3 < 4) == (bool)-1);
}