// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)

// Dictionary will be used to in LZ78 encoding
class Dictionary
{
	// The root node of the trie
	private Node _root;
	// The current node being looked at in the trie
	private Node _current;
	// Holds the next index for use when adding nodes
	private int _nextIndex;
	
	// Constructor to make a new dictionary
	public Dictionary()
	{
		// Set next index to 0
		_nextIndex = 0;
		// Use next index to create root node
		_root = new Node(_nextIndex, (byte)0);
		// Increase next index 
		_nextIndex++;
		// Set current node to root node
		_current = _root;
	}
	
	// Uses input byte and next index to add child to current node
	public void addNode(byte value)
	{
		// Add child to current node
		_current.addChild(_nextIndex, value);
		// Increment next index
		_nextIndex++;
	}
	
	// Searches current nodes children for input byte and if found
	// returns index of where byte was found and sets current node to 
	// node that was found otherwise returns 0
	public int contains(byte value)
	{
		// Check if the current nodes children contains input byte
		Node result = _current.childrenContain(value);
		// If null is returned then no child containing input byte exists
		if(result == null)
		{
			// Return 0 to say that a new node needs to be added here
			return 0;
		}
		else
		{
			// Get the index of the node that contains input byte
			int index = result.getIndex();
			// Change current node
			_current = result;
			// Return the index
			return index;
		}
	}
	
	// Sets current node back to root node
	public void resetTrie()
	{
		_current = _root;
	}
}