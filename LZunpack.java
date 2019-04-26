import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)
public class LZunpack {
	public static void main(String[] args) {	
		int counter = 1;
		int output = 0;
		int output1 = 0b10100000110100001010010000110000;
		int output2 = 0b1101000001;
//		File input = new File("output.txt");
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(input));
//			String Line = br.readLine();
//			int output1 = 0b1010000011010000101001000011;
//			int output2 = 0b1101000001;
//			while(Line != null) {
//				//output = Integer.parseInt(Line);
//				output = readData(output1,counter);
//				counter++;
//				Line = br.readLine();
//				
//			}
//			br.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		while(output1 != 0) {
			output1 = readData(output1,counter);
			counter++;
		}
		
	}


public static int bitsNeeded(int counter) {
	   double bits = Math.log(counter)/Math.log(2.0);
	   if(bits == 0) {
		   return 1;
	   }
	   return (int)(Math.ceil(bits));
}

public static int generateMask(int length, int maxBits) {
	int offset = maxBits - length;
	int mask = 1 << length;
	mask--;
	mask <<= offset;
	return mask;
}

public static int readData(int line,int counter) {
	System.out.println("VALUE BEFORE SHIFT = " + Integer.toBinaryString(line));
	int iBits = bitsNeeded(counter);
	System.out.println("READING " + iBits + " BITS");
	int mask = generateMask(iBits,32);
	//System.out.println(Integer.toBinaryString(mask));
	int phrase = (line & mask) >>> (32 - iBits);
	System.out.println(Integer.toBinaryString(phrase));
	line = line << iBits;
	System.out.println("VALUE AFTER SHIFT  = " + Integer.toBinaryString(line));
	int cBits = 8;
	System.out.println("READING " + cBits + " BITS");
	mask = generateMask(8,32);
	int character = (line & mask)  >>> (24);
	System.out.println(Integer.toBinaryString(character));
	char c = (char) character;
	line = line << 8;
	System.out.println("(" + phrase +  "," + c + ")");
	return line;
}

}