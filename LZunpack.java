import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)
public class LZunpack {
	public static int buffAmount = 0;
	public static void main(String[] args) {	
		int counter = 1;
		int output = 0;
		try {
			int line = System.in.read();
			while(line != 0) {
				output = getNextPair(output,counter,line);
				counter++;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	public static int bitsNeeded(int counter) {
		   double bits = Math.log(counter)/Math.log(2.0);
		   return ((int)(bits)) + 1;
	}

public static int generateMask(int length, int maxBits) {
	int offset = maxBits - length;
	int mask = 1 << length;
	mask--;
	mask <<= offset;
	return mask;
}

public static int getNextPair(int value, int counter, int input) {
	int bitsNeeded = bitsNeeded(counter) + 8;
	
	try {
		int line = input;
		while (buffAmount < bitsNeeded) {
			if (line != 0) {
				value = readByte(line,value);
				line = System.in.read();
			}
			else break;
			
		}
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	value = readData(value,counter);
	buffAmount = buffAmount - bitsNeeded;
	return value;
	
}

public static int readByte(int data,int value) {
	int shift = 24 - buffAmount;
	data = data << shift;
	buffAmount = buffAmount + 8;
	value = value | data;
	return value;
}


public static int readData(int line,int counter) {
	int iBits = bitsNeeded(counter);
	int mask = generateMask(iBits,32);
	int phrase = (line & mask) >>> (32 - iBits);
	line = line << iBits;
	int cBits = 8;
	mask = generateMask(8,32);
	int character = (line & mask)  >>> (24);
	char c = (char) character;
	line = line << 8;
	System.out.println(phrase + "," + character );
	return line;
}

}