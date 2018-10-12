#include "vec2.h"


using boing::vec2;

const vec2 vec2::ZERO = vec2();

boing::vec2::vec2() = default;

boing::vec2::vec2(boing::scalar_t x, boing::scalar_t y)
		: x(x), y(y) {

}

bool boing::vec2::is_zero() const {
	return !x && !y;
}

std::ostream& boing::operator<<(std::ostream& stream, const vec2& vec) {
	return stream << '(' << std::to_string(vec.x) << ", " << std::to_string(vec.y) << ')';
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

vec2 boing::vec2::operator*(float scalar) const {
	return vec2((scalar_t)(x * scalar), (scalar_t)(y * scalar));
}

vec2 boing::vec2::operator/(float scalar) const {
	return *this * (1.0f / scalar);
}

vec2 boing::operator*(float scalar, const vec2 &vector) {
	return vector * scalar;
}

vec2 boing::operator/(float scalar, const vec2 &vector) {
	return vector / scalar;
}

vec2 &boing::vec2::operator*=(float scalar) {
	x = (scalar_t)(x * scalar);
	y = (scalar_t)(y * scalar);
	return *this;
}

vec2 &boing::vec2::operator/=(float scalar) {
	x = (scalar_t)(x / scalar);
	y = (scalar_t)(y / scalar);
	return *this;
}
