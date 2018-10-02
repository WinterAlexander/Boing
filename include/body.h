
#ifndef BOING_BODY_H
#define BOING_BODY_H

#include "vec2.h"
#include "edge.h"
#include "vector"

namespace boing {
	class body;
}

using boing::edge;

struct boing::body {
	vec2 position, velocity;
	std::vector<edge> edges;
	weight_t weight;

	body(vec2 position, vec2 velocity, const std::vector<edge>& edges, weight_t weight);
};

#endif //BOING_BODY_H
