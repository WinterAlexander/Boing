#ifndef BOING_EDGE_H
#define BOING_EDGE_H

#include "vec2.h"
#include "manifold.h"

namespace boing {
	class edge;
	class body;
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
	vec2 normal;
	scalar_t length;
	vec2 offset;

	edge(const boing::body& body, vec2 normal, scalar_t length, vec2 offset = vec2::ZERO);

public:
	bool collision(const vec2& displ, const edge& other, manifold& manifold) const;

	vec2 get_position() const;

	const vec2& get_offset() const;
	void set_offset(vec2 offset);

	const vec2& get_normal() const;
	void set_normal(vec2 normal);

	scalar_t get_length() const;
	void set_length(scalar_t length);

private:
	static scalar_t contact_surface(const vec2& posA, scalar_t hLenA, const vec2& posB, scalar_t hLenB, const vec2& normal);
};


#endif //BOING_EDGE_H
