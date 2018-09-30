#include "vec2.h"
#include <sstream>
#include <vec2.h>
#include <cmath>


using boing::vec2;

const vec2 vec2::ZERO = vec2(0, 0);

boing::scalar_t boing::vec2::length() {
	return scalar_t(sqrt(x * x + y * y));
}

boing::scalar_t boing::vec2::length_squared()
{
	return x * x + y * y;
}

void vec2::describe(std::ostream& stream) const {
	stream << '(' << std::to_string(x) << ", " << std::to_string(y) << ')';
}

vec2 boing::vec2::operator+(const vec2& addend) const {
	return vec2(x + addend.x, y + addend.y);
}

vec2 boing::vec2::operator-(const vec2& minuend) const {
	return vec2(x - minuend.x, y - minuend.y);
}

vec2 boing::vec2::operator-() const {
	return vec2(-x, -y);
}

vec2 boing::vec2::operator*(boing::scalar_t scalar) const {
	return vec2(x * scalar, y * scalar);
}

vec2 boing::vec2::operator/(boing::scalar_t scalar) const {
	return vec2(x / scalar, y / scalar);
}

boing::scalar_t vec2::operator*(const vec2& vec) const {
	return x * vec.x + y * vec.y;
}

bool boing::vec2::operator==(const vec2& other) const {
	return x == other.x && y == other.y;
}

bool boing::vec2::operator!=(const vec2& other) const {
	return !(*this == other);
}

vec2& boing::vec2::operator+=(const vec2& addend) {
	x += addend.x;
	y += addend.y;
	return *this;
}

vec2& boing::vec2::operator-=(const vec2& minuend) {
	x -= minuend.x;
	y -= minuend.y;
	return *this;
}

vec2& boing::vec2::operator*=(boing::scalar_t scalar) {
	x *= scalar;
	y *= scalar;
	return *this;
}

vec2& boing::vec2::operator/=(boing::scalar_t scalar) {
	x /= scalar;
	y /= scalar;
	return *this;
}

vec2 boing::operator*(boing::scalar_t scalar, const vec2& vector) {
	return vector * scalar;
}

vec2 boing::operator/(boing::scalar_t scalar, const vec2& vector) {
	return vector / scalar;
}
