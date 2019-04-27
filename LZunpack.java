// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)

import java.io.IOException;

public class LZunpack {
	
	public static int cBits = 8;
	public static int maxBits = 32;
	
	public static void main(String[] args) {	
		int counter = 1;
		int output = 0;
		int buffAmount = 0;
		int iBits;
		int tBits;
		try {
			int line = System.in.read();
			while(line != -1) {
				iBits = bitsNeeded(counter);
				tBits = iBits + cBits;
				while (buffAmount <= tBits) {
				if (line != -1) {
					output = readByte(line,output);
					buffAmount += cBits;
					line = System.in.read();
				}else break;
				}
				output = readData(output,iBits);
				buffAmount -= tBits;
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

	public static int generateMask(int length, int totalBits) {
		int offset = totalBits - length;
		int mask = 1 << length;
		mask--;
		mask <<= offset;
		return mask;
	}	

	public static int getNextPair(int value, int counter) {
		
	}

	public static int readByte(int data,int value) {
		int shift = (maxBits - cBits) - buffAmount;
		data = data << shift;
		value = value | data;
		return value;
	}

	public static int readData(int value,int iBits) {
		int mask = generateMask(iBits,maxBits);
		int phrase = (value & mask) >>> (maxBits - iBits);
		value <<= iBits;
		mask = generateMask(cBits,maxBits);
		int character = (value & mask)  >>> (maxBits - cBits);
		value <<= cBits;
		System.out.println(phrase + "," + character );
		return value;
	}
}