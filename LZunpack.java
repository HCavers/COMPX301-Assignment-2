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
		int output;
		File input = new File("output.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(input));
			String Line = br.readLine();
			int output1 = 0b1010000011010000101001000011;
			int output2 = 0b1101000001;
			while(Line != null) {
				//output = Integer.parseInt(Line);
				output = readData(output1,counter);
				counter++;
				Line = br.readLine();
				
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	int iBits = bitsNeeded(counter);
	int mask = generateMask(iBits,32);
	int phrase = (line & mask) >>> (32 - iBits);
	line = line << iBits;
	int cBits = 8;
	mask = generateMask(8,32);
	int character = (line & mask) >>> (24 - iBits);
	line = line << 8;
	System.out.println("(" + phrase +  "," + character + ")");
	return line;
}

}