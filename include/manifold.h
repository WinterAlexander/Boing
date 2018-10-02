#include <utility>

#ifndef BOING_MANIFOLD_H
#define BOING_MANIFOLD_H

#include "boing.h"
#include "vec2.h"

namespace boing {
	class manifold;
}

using boing::vec2;

struct boing::manifold {
	vec2 penetration;
	scalar_t surface;

	manifold() : penetration(), surface() {}
};

#endif //BOING_MANIFOLD_H
