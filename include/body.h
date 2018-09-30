
#ifndef BOING_BODY_H
#define BOING_BODY_H

#include "edge.h"
#include "vector"

namespace boing {
	class body;
}

using boing::edge;

class boing::body {
private:
	std::vector<edge> edges;

public:
	const vec2& getPosition() const;
};

#endif //BOING_BODY_H
