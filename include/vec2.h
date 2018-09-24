#ifndef BOING_VEC2_H
#define BOING_VEC2_H

#include "describable.h"

namespace boing {
    class vec2;
}

/**
 * A 2d vector
 *
 * Created on 2018-09-21.
 *
 * Alexander Winter
 */
class boing::vec2 : public boing::describable {
public:
	const int x, y;

    /**
     * Constructs a vec2 from x and y components
     * @param x x component of the vector
     * @param y y component of the vector
     */
    vec2(int x, int y)
        : x(x), y(y) {

    }

    /**
     * Computes the dot product between this vector and specified vector
     * @param vec vector to compute dot product with
     * @return dot product of this and specified vector
     */
    int dot(const vec2& vec) const;

	std::ostream& describe(std::ostream& stream) const;
};


#endif //BOING_VEC2_H
