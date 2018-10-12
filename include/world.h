
#ifndef BOING_WORLD_H
#define BOING_WORLD_H

#include "body.h"

namespace boing {
	class world;
}

using boing::body;
using boing::vec2;

class boing::world {
public:
	std::vector<body> bodies;

	void tick(float delta);

	bool move(body& body, const vec2& displ, weight_t weight);

private:
	static bool greater_weight(const body&, const body&);
};

#endif //BOING_WORLD_H
