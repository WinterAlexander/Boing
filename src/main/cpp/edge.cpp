#include <utility>
#include <edge.h>
#include "body.h"

using std::min;
using std::max;

boing::edge::edge(const boing::body& body, vec2 normal, scalar_t length, vec2 offset)
		: body(body), normal(std::move(normal)), length(length), offset(std::move(offset)) {}

bool boing::edge::collision(const vec2& displ, const edge& other, manifold& manifold) const
{
	if(normal != -other.normal)
		return false;

	vec2 posA = get_position();
	vec2 posB = other.get_position();

	if(posA * normal > posB * normal)
		return false;

	scalar_t pene = (posA - posB + displ) * normal;

	if(pene <= 0)
		return false;

	scalar_t t = (posA - posB) * normal / (-displ * normal);

	vec2 cA = posA + t * displ;

	scalar_t surface = contact_surface(cA, length / 2, posB, other.length / 2, normal);

	if(surface < 0 || (!surface && ((displ.x < displ.y) == (normal.y > normal.x))))
		return false;

	manifold.penetration = normal * pene;
	manifold.surface = surface;
	return true;
}

vec2 boing::edge::get_position() const
{
	return body.position + offset;
}

const vec2& boing::edge::get_offset() const
{
	return offset;
}

void boing::edge::set_offset(vec2 offset)
{
	this->offset = offset;
}

const vec2& boing::edge::get_normal() const
{
	return normal;
}

void boing::edge::set_normal(vec2 normal)
{
	this->normal = normal;
}

boing::scalar_t boing::edge::get_length() const
{
	return length;
}

void boing::edge::set_length(boing::scalar_t length)
{
	this->length = length;
}

boing::scalar_t boing::edge::contact_surface(const vec2& posA,
												scalar_t hLenA,
                                                const vec2& posB,
												scalar_t hLenB,
                                                const vec2& normal)
{
	scalar_t maxA = normal.y * (posA.x + hLenA) + normal.x * (posA.y + hLenA);
	scalar_t minA = normal.y * (posA.x - hLenA) + normal.x * (posA.y - hLenA);
	scalar_t maxB = normal.y * (posB.x + hLenB) + normal.x * (posB.y + hLenB);
	scalar_t minB = normal.y * (posB.x - hLenB) + normal.x * (posB.y - hLenB);

	return min(max(maxA, minA), max(maxB, minB))
	       - max(min(maxA, minA), min(maxB, minB));
}
