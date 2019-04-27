// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)

// Import libraries needed
import java.util.List;
import java.util.ArrayList;

// Nodes will be used in dictionary
class Node
{
	// The index of the node in the dictionary
	private int _index;
	// The value assigned to the list
	private byte _value;
	// A collection of Nodes that will act a children in the multi way trie
	private List<Node> _children;
	
	// Constructor to create new nodes
	public Node(int index, byte	value)
	{
		_index = index;
		_value = value;
		_children = new ArrayList<Node>();
	}
	
	// Allows read only access to value attribute
	public byte getValue()
	{
		return _value;
	}
	
	// Allows read only access to index attribute
	public int getIndex()
	{
		return _index;
	}
	
	// Creates a node and adds it to list of children
	public void addChild(int index, byte value)
	{
		Node node = new Node(index, value);
		_children.add(node);
	}
	
	// Returns the child that contains the input or returns null
	public Node childrenContain(byte value)
	{
		// For each child
		for(Node node : _children)
		{
			// If child matches value return it
			if(node.getValue() == value)
			{
				return node;
			}
		}
		// If no child was found to match return null
		return null;
	}
}