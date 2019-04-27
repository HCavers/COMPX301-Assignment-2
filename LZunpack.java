// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)

import java.io.IOException;

public class LZunpack {
	
	public static int cBits = 8;
	public static int buffAmount = 0;
	public static int line;
	public static int maxBits = 32;
	
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

	public static int generateMask(int length, int totalBits) {
		int offset = totalBits - length;
		int mask = 1 << length;
		mask--;
		mask <<= offset;
		return mask;
	}	

	public static int getNextPair(int value, int counter) {
		int iBits = bitsNeeded(counter);
		int bitsNeeded = iBits + cBits;
		try {
			while (buffAmount <= bitsNeeded) {
				if (line != -1) {
					value = readByte(line,value);
					line = System.in.read();
				}else break;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		value = readData(value,iBits);
		buffAmount = buffAmount - bitsNeeded;
		return value;
	}

	public static int readByte(int data,int value) {
		int shift = (maxBits - cBits) - buffAmount;
		data = data << shift;
		buffAmount = buffAmount + cBits;
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