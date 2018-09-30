
#include <edge.h>

#include "edge.h"

using std::min;
using std::max;

bool boing::edge::collision(const edge& other, boing::manifold& manifold) const
{
	return false;
}

vec2 boing::edge::getPosition() const
{
	return body.getPosition() + offset;
}

const vec2& boing::edge::getOffset() const
{
	return offset;
}

void boing::edge::setOffset(vec2 offset)
{
	this->offset = offset;
}

const vec2& boing::edge::getNormal() const
{
	return normal;
}

void boing::edge::setNormal(vec2 normal)
{
	this->normal = normal;
}

boing::scalar_t boing::edge::getLength() const
{
	return length;
}

void boing::edge::setLength(boing::scalar_t length)
{
	this->length = length;
}

boing::scalar_t boing::edge::contactSurface(vec2 posA, boing::scalar_t hLenA, vec2 posB, boing::scalar_t hLenB, vec2 normal)
{
	scalar_t maxA = normal.y * (posA.x + hLenA) + normal.x * (posA.y + hLenA);
	scalar_t minA = normal.y * (posA.x - hLenA) + normal.x * (posA.y - hLenA);
	scalar_t maxB = normal.y * (posB.x + hLenB) + normal.x * (posB.y + hLenB);
	scalar_t minB = normal.y * (posB.x - hLenB) + normal.x * (posB.y - hLenB);

	return min(max(maxA, minA), max(maxB, minB))
	       - max(min(maxA, minA), min(maxB, minB));
}
