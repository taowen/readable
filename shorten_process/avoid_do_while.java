// bad

// Search through the list, starting at 'node', for the given 'name'.
// Don't consider more than 'max_length' nodes.
public boolean ListHasNode(Node node, String name, int max_length) {
 do {
 if (node.name().equals(name))
 return true;
 node = node.next();
 } while (node != null && --max_length > 0);
 return false;
}


// good

public boolean ListHasNode(Node node, String name, int max_length) {
 while (node != null && max_length-- > 0) {
 if (node.name().equals(name)) return true;
 node = node.next();
 }
 return false;
}
