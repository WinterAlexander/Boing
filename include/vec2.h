//
// A 2d vector
//
// Created on 2018-09-21.
//
// Alexander Winter
//

#ifndef BOING_VEC2_H
#define BOING_VEC2_H

namespace boing {
    class vec2;
}

class boing::vec2 {
private:
    int x, y;

public:
    vec2(int x, int y)
        : x(x), y(y) {

    }
};


#endif //BOING_VEC2_H
