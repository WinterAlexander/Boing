#include <utility>


#include <body.h>

boing::body::body(vec2 position, vec2 velocity, const std::vector<edge>& edges,
                  weight_t weight)
                  : position(std::move(position)),
                  velocity(std::move(velocity)),
                  edges(edges),
                  weight(weight)
{}
