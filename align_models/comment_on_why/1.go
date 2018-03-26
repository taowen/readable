package example

// bad

/** The name. */
var name string
/** The version. */
var version string
/** The licenceName. */
var licenceName string
/** The version. */
var info string

// bad

type Node struct{

}

// Find the Node in the given subtree, with the given name, using the given depth.
func FindNodeInSubtree(subtree Node*, name string, depth int) Node* {
	// ....
}

// good

// Impose a reasonable limit - no human can read that much anyway.
const MAX_RSS_SUBSCRIPTIONS = 1000

// Runtime is O(number_tags * average_tag_depth),
// so watch out for badly nested inputs.
func FixBrokenHtml(html string) string {
	// ...
}