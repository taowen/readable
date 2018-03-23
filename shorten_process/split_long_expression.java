// bad 

return (begin >= other.begin && begin < other.end) ||
 (end > other.begin && end <= other.end) ||
 (begin <= other.begin && end >= other.end);
 
// good 

bool Range::OverlapsWith(Range other) {
 if (other.end <= begin) return false; // They end before we begin
 if (other.begin >= end) return false; // They begin after we end
 return true; // Only possibility left: they overlap
}
