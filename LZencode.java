// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)

// Import libraries needed 
import java.io.IOException;

class LZencode
{
	static Dictionary dic = new Dictionary();
	// Main method
	public static void main(String[] args)
	{
		// Try catch to handle errors 
		try
		{
			// Call process method to process input
			process();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	// Processes input and outputs LZ78 encoded data
	public static void process() throws IOException
	{
		// Set up the last index
		int lastIndex = 0;
		// Infinite loop
		while(true)
		{
			// Read in input from standard in
			int ch = System.in.read();
			// If data was read
			if(ch != -1)
			{
				// Check if dictionary contains byte
				int result = dic.contains((byte)ch);
				// If it doesn't
				if(result == 0)
				{
					// Print output
					System.out.println(lastIndex + "," +  ch);
					// Add new node to dictionary
					dic.addNode((byte)ch);
					// Reset position of dictionary
					dic.resetTrie();
					// Reset last index
					lastIndex = 0;
				}
				else
				{
					// Update last index
					lastIndex = result;
				}
			}
			else
			{
				// If there is no more data and there is still data to output 
				if(lastIndex != 0)
				{
					// Print the last bit of data
					System.out.println(lastIndex + "," +  0);
				}
				// Breaks out of loop
				break;
			}
		} 
	}
}