package example

// bad

type Range struct {
	begin int
	end int
}

func (rng *Range) overlapsWith(other *Range) bool {
	return (rng.begin >= other.begin && rng.begin < other.end) ||
		(rng.end > other.begin && rng.end <= other.end) ||
		(rng.begin <= other.begin && rng.end >= other.end)
}

// good

func (rng *Range) overlapsWith(other *Range) bool {
	if other.end <= rng.begin {
		return false  // They end before we begin
	}
	if other.begin >= rng.end {
		return false // They begin after we end
	}
	return true // Only possibility left: they overlap
}