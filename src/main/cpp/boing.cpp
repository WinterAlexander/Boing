#include <iostream>
#include "vec2.h"
#include "describable.h"

int main(int argc, char** argv) {
	boing::vec2 vec(3, 1);
	boing::vec2 vec2(3, -4);

	std::cout << vec << std::endl;
	std::cout << vec2 << std::endl;

	return 0;
}
