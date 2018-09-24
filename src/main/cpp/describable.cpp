#include "describable.h"

using boing::describable;

std::ostream& operator<<(std::ostream& stream, const describable& describable) {
	return describable.describe(stream);
}