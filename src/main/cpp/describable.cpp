#include "describable.h"

using boing::describable;

std::ostream& boing::operator<<(std::ostream& stream, const describable& describable) {
	return describable.describe(stream);
}