#ifndef BOING_DESCRIBABLE_H
#define BOING_DESCRIBABLE_H

#include <string>
#include <ostream>

namespace boing {
	class describable;

	std::ostream& operator<<(std::ostream& stream, const describable& describable);
}

/**
 * Something that can be described
 */
class boing::describable {
public:
	virtual ~describable() {}

	virtual std::ostream& describe(std::ostream& stream) const = 0;
};
#endif //BOING_DESCRIBABLE_H

