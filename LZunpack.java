import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)
public class LZunpack {
	public static int buffAmount = 0;
	public static int line;
	public static void main(String[] args) {	
		int counter = 1;
		int output = 0;
		try {
			line = System.in.read();
			while(line != -1) {
				output = getNextPair(output,counter);
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

public static int getNextPair(int value, int counter) {
	int bitsNeeded = bitsNeeded(counter) + 8;
	
	try {
		
		while (buffAmount <= bitsNeeded) {
			if (line != -1) {
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


public static int readData(int value,int counter) {
	int iBits = bitsNeeded(counter);
	int mask = generateMask(iBits,32);
	int phrase = (value & mask) >>> (32 - iBits);
	value = value << iBits;
	int cBits = 8;
	mask = generateMask(8,32);
	int character = (value & mask)  >>> (24);
	char c = (char) character;
	value = value << 8;
	System.out.println(phrase + "," + character );
	return value;
}

}