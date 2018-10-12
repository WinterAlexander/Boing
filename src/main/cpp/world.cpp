
#include <world.h>
#include <algorithm>

using boing::world;
using std::abs;

void world::tick(float delta) {
	for(body& body : bodies)
		move(body, body.velocity * delta, body.weight);
}

bool world::move(body& bodyA, const vec2& displ, weight_t weight) {
	if(displ.is_zero())
		return false;

	manifold manifold;
	for(body& bodyB : bodies) {
		if(&bodyA == &bodyB)
			continue;

		if(weight > bodyB.weight) {
			for(const edge& edgeA : bodyA.edges)
				for(const edge& edgeB : bodyB.edges)
					if(edgeA.collision(displ, edgeB, manifold)) {
						if(move(bodyB, manifold.penetration, weight))
							goto end_loop;
						goto get_pushed;
					}
		}

		get_pushed:
		for(const edge& edgeA : bodyA.edges)
			for(const edge& edgeB : bodyB.edges)
				if(edgeA.collision(displ, edgeB, manifold)) {
					manifold.penetration = -manifold.penetration;

					if(abs(manifold.penetration.x) + abs(manifold.penetration.y) < 0)
						goto end_loop;

					manifold.penetration += displ;

					if(abs(manifold.penetration.x) + abs(manifold.penetration.y) > 0)
						move(bodyA, manifold.penetration, weight);
					return false;
				}

		end_loop:;
	}

	bodyA.position += displ;
	return true;
}

void boing::world::add_body(body&& body) {
	bodies.push_back(std::move(body));
	std::sort(bodies.begin(), bodies.end(), greater_weight);
}

const std::vector<body>& boing::world::get_bodies() const {
	return bodies;
}

bool world::greater_weight(const body& i, const body& j) {
	return i.weight > j.weight;
}
