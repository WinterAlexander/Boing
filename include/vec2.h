#ifndef BOING_VEC2_H
#define BOING_VEC2_H

#include "boing.h"
#include <ostream>

namespace boing {
    class vec2;

	vec2 operator*(scalar_t scalar, const vec2& vector);
	vec2 operator/(scalar_t scalar, const vec2& vector);

	vec2 operator*(float scalar, const vec2& vector);
	vec2 operator/(float scalar, const vec2& vector);

	std::ostream& operator<<(std::ostream& stream, const vec2& vec);
}

/**
 * A 2d vector
 *
 * Created on 2018-09-21.
 *
 * Alexander Winter
 */
class boing::vec2 {
public:
	static const vec2 ZERO;

	scalar_t x, y;

	/**
	 * Constructs a null vector
	 */
	vec2();

    /**
     * Constructs a vec2 from x and y components
     * @param x x component of the vector
     * @param y y component of the vector
     */
    vec2(scalar_t x, scalar_t y);

    /**
     * @return true if all components are zero, otherwise false
     */
    bool is_zero() const;

    /**
     * Adds this vector and the specified vector from their components and
     * returns the result
     * @param addend vector to add to this one
     * @return vector representing the sum of the 2 vectors
     */
	vec2 operator+(const vec2& addend) const;

	/**
	 * Substracts this vector from the specified vector from their components
	 * and returns the result
     * @param minuend vector to substract to this one
     * @return vector representing the difference of the 2 vectors
	 */
	vec2 operator-(const vec2& minuend) const;

	/**
	 * Computes the opposite of this vector by taking the opposite of each
	 * component
	 * @return the opposite of this vector
	 */
	vec2 operator-() const;

	/**
	 * Multiplies this vector by a scalar by multiplying each component
	 * @param scalar value to scale the vector with
	 * @return scaled vector
	 */
	vec2 operator*(scalar_t scalar) const;

	/**
	 * Divides this vector by a scalar by dividing each component
	 * @param scalar value to divide the vector with
	 * @return scaled vector
	 */
	vec2 operator/(scalar_t scalar) const;

	/**
	 * Computes the dot product between this vector and specified vector
	 * @param vec vector to compute dot product with
	 * @return dot product of this and specified vector
	 */
	scalar_t operator*(const vec2& vec) const;

	/**
	 * Checks the equality of this vector and the specified one. Equality
	 * between 2 vectors means that each of their component are equal.
	 * @param other vector to check for equality with
	 * @return true if vector are equals, otherwise false
	 */
	bool operator==(const vec2& other) const;
	bool operator!=(const vec2& other) const;

	vec2& operator+=(const vec2& addend);
	vec2& operator-=(const vec2& minuend);
	vec2& operator*=(scalar_t scalar);
	vec2& operator/=(scalar_t scalar);

	vec2 operator*(float scalar) const;
	vec2 operator/(float scalar) const;

	vec2& operator*=(float scalar);
	vec2& operator/=(float scalar);
};

#endif //BOING_VEC2_H
