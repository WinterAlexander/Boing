


#include <body.h>

boing::body::body(vec2 position, weight_t weight, vec2 velocity)
                  : position(std::move(position)),
                  velocity(std::move(velocity)),
                  edges(),
                  weight(weight)
{}
