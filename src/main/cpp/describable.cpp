#include "describable.h"

using boing::describable;

std::ostream& boing::operator<<(std::ostream& stream, const describable& describable) {
	describable.describe(stream);
	return stream;
}