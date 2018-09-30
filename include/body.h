
#ifndef BOING_BODY_H
#define BOING_BODY_H

#include "vec2.h"
#include "vector"

namespace boing {
	class body;
	class edge;
}

using boing::edge;

class boing::body {
private:
	std::vector<edge> edges;

public:
	const vec2& get_position() const;
};

#endif //BOING_BODY_H
