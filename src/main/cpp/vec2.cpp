#include "vec2.h"
#include <sstream>
#include <vec2.h>


using boing::vec2;

const vec2 vec2::ZERO = vec2(0, 0);

boing::scalar_t vec2::dot(const vec2& vec) const {
    return x * vec.x + y * vec.y;
}

std::ostream& vec2::describe(std::ostream& stream) const {
	return stream << '(' << std::to_string(x) << ", " << std::to_string(y) << ')';
}

vec2 boing::vec2::operator+(const vec2& addend) const
{
	return vec2(x + addend.x, y + addend.y);
}

vec2 boing::vec2::operator-(const vec2& minuend) const
{
	return vec2(x - minuend.x, y - minuend.y);
}

vec2 boing::vec2::operator-() const
{
	return vec2(-x, -y);
}

vec2 boing::vec2::operator*(boing::scalar_t scalar) const
{
	return vec2(x * scalar, y * scalar);
}

vec2 boing::vec2::operator/(boing::scalar_t scalar) const
{
	return vec2(x / scalar, y / scalar);
}
