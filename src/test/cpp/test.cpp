//
// Created by Alexander Winter on 2017-09-18.
//

#include <gtest/gtest.h>
#include <chrono>

TEST(trivial, trivial_one_equal_one)
{
    ASSERT_EQ(1, (3 < 4) == (bool)-1);
}

struct test_type {
	int a;
};

int dot_v(test_type t) {
	return t.a * 14;
}

int dot_r(const test_type& t) {
	return t.a * 14;
}

TEST(benchmark, small_struct_pass_by_value)
{
	{
		auto start = std::chrono::system_clock::now();
		unsigned long s;
		for(long i = 0; i < 1'000'000'000; i++)
		{
			test_type t;
			t.a = i % 7;
			s += dot_v(t);
		}
		auto end = std::chrono::system_clock::now();
		std::chrono::duration<double> elapsed_seconds = end-start;
		std::cout << "elapsed time: " << elapsed_seconds.count() << "s\n";
	}


	{
		auto start = std::chrono::system_clock::now();
		unsigned long s;
		for(long i = 0; i < 1'000'000'000; i++)
		{
			test_type t;
			t.a = i % 7;
			s += dot_r(t);
		}
		auto end = std::chrono::system_clock::now();
		std::chrono::duration<double> elapsed_seconds = end-start;
		std::cout << "elapsed time: " << elapsed_seconds.count() << "s\n";
	}
}