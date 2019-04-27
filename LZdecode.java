// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)

// Import libraries needed
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

class LZdecode
{
	// List to hold all past phrases
	static List<Node> list = new ArrayList<Node>();
	// Main method
	public static void main(String[] args)
	{
		// Try catch to handle errors
		try
		{
			// Call process method to process input
			process();
		}
		catch (IOException ex)
		{
			// Print exception stack trace to help with debugging
			ex.printStackTrace();
		}
	}
	
	// Processes input and outputs original string
	public static void process() throws IOException
	{	
		// Set up reader to read lines of input
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		// Loop as long as there is input
		String line = reader.readLine();
		while(line != null)
		{
			// Split the input on comma to get phrase number and byte
			String[] values = line.split(",");
			int index = Integer.parseInt(values[0]);
			int data = Integer.parseInt(values[1]);
			// Make a new node using input and add to list to keep track of it
			Node node = new Node(index, (byte)data);
			list.add(node);
			// Check if phrase number is '0'
			if(index != 0)
			{	
				// If not '0' call get output method to get next mismatched byte in pattern
				getOutput(index - 1);
			}
			// Write the nodes mismatched byte
			System.out.write(node.getValue());
			System.out.flush();
			// Get next line
			line = reader.readLine();
		}
	}
	
	// Writes mismatched byte from node at position index from list, recursively calling
	// itself if phrase number is not zero to output pattern of mismatched bytes
	public static void getOutput(int index)
	{
		// Get node from list
		Node node = list.get(index);
		// Check if phrase number is '0'
		if(node.getIndex() != 0)
		{
			// If its not '0' call get output method to get byte at the next position in pattern
			// (Value is decremented by one as list starts at zero not 1)
			getOutput(node.getIndex() - 1);
		}
		// Write the nodes mismatched byte
		System.out.write(node.getValue());
		System.out.flush();
	}
}