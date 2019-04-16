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
	static List<Node> list = new ArrayList<Node>();
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
			ex.printStackTrace();
		}
	}
	
	// Processes input and outputs original string
	public static void process() throws IOException
	{		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = reader.readLine();
		while(line != null)
		{
			String[] values = line.split(",");
			int index = Integer.parseInt(values[0]);
			int data = Integer.parseInt(values[1]);
			Node node = new Node(index, (byte)data);
			list.add(node);
			if(index != 0)
			{				
				getOutput(index - 1);
			}
			System.out.print((char)node.getValue());
			line = reader.readLine();
		}
		System.out.println();
	}
	
	public static void getOutput(int index)
	{
		Node node = list.get(index);
		if(node.getIndex() != 0)
		{
			getOutput(node.getIndex() - 1);
		}
		System.out.print((char)node.getValue());
	}
}