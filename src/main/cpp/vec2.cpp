#include "vec2.h"
#include <sstream>

using boing::vec2;

int vec2::dot(const vec2& vec) const {
    return x * vec.x + y * vec.y;
}

std::ostream& vec2::describe(std::ostream& stream) const {
	return stream << '(' << std::to_string(x) << ", " << std::to_string(y) << ')';
}