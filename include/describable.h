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
	virtual ~describable() = default;

	/**
	 * Makes the object describe itself into the specified stream by writing
	 * a human readable representation of its content
	 * @param stream to write the description in
	 */
	virtual void describe(std::ostream& stream) const = 0;
};
#endif //BOING_DESCRIBABLE_H

