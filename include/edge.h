#ifndef BOING_EDGE_H
#define BOING_EDGE_H

#include "vec2.h"
#include "body.h"
#include "manifold.h"

namespace boing {
	class edge;
}

using boing::body;
using boing::vec2;

/**
 * Edge of a body.
 *
 * Created on 2018-09-30.
 *
 * @author Alexander Winter
 */
class boing::edge {
private:
	const body& body;
	vec2 offset, normal;
	scalar_t length;

	edge(const body& body, vec2 normal, scalar_t length, vec2 offset = vec2::ZERO)
		: body(body), normal(normal), length(length), offset(offset) {}

public:
	bool collision(const edge& other, manifold& manifold) const;

	vec2 getPosition() const;

	const vec2& getOffset() const;
	void setOffset(vec2 offset);

	const vec2& getNormal() const;
	void setNormal(vec2 normal);

	scalar_t getLength() const;
	void setLength(scalar_t length);

private:
	static scalar_t contactSurface(vec2 posA, scalar_t hLenA, vec2 posB, scalar_t hLenB, vec2 normal);
};


#endif //BOING_EDGE_H
