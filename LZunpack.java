// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)

import java.io.IOException;

public class LZunpack {
	
	public static int cBits = 8; // The amount of bits for a character (1 byte)
	public static int maxBits = 32; // The amount of bits in an Integer
	
	public static void main(String[] args) {	
		int counter = 1;  // Variable that keeps track of the total phrases 
		int output = 0; // Stores the buffer
		int buffAmount = 0; // Stores the amount of bits currently in the buffer
		int iBits; // Integer to hold the number of bits needed to code the phrase
		int tBits; // Integer to hold the number of bits to code the tuple
		try {
			int line = System.in.read(); // Read the next byte and store it in line
			while(line != -1) { // While there is data to be read
				iBits = bitsNeeded(counter); // Calculates the number of bits to encode the phrase
				tBits = iBits + cBits; // Calculates the number of bits needed to encode the tuple
				while (buffAmount <= tBits) { // While the number of bits in the buffer is less than the amount needed to code a tuple
				if (line != -1) {
					output = readByte(line,output,buffAmount); // Read the next byte of data and store it in the buffer
					buffAmount += cBits; // Increase the buffer amount by the number of bits in a single byte
					line = System.in.read(); // Read the next byte of data
				}else break; 
				}
				output = outputData(output,iBits); // Outputs one tuple of data
				buffAmount -= tBits; // Reduces the amount of bits stored in the buffer
				counter++; // Increments the number of phrases
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static int bitsNeeded(int counter) { // Takes in the number of phrases and returns the number of bits needed to encode
		double bits = Math.log(counter)/Math.log(2.0);
		return ((int)(bits)) + 1; // Returns log2(counter) rounded down and then incremented
	}

	public static int generateMask(int length, int totalBits) { // Creates an integer with the first X bits set where X is the length
		int offset = totalBits - length; // Calculates the difference between the length and total bits
		int mask = 1 << length; // Shifts 1 by the length
		mask--; // Subtracts 1 from the mask to set all the bits preceding the 1
		mask <<= offset; // Shifts the 1's up to the front
		return mask;
	}

	public static int readByte(int data,int value, int buffAmount) { 
		int shift = (maxBits - cBits) - buffAmount; // Calculates the number of bits to be shifted
		data = data << shift; // Shifts the data into position
		value = value | data; // Copies the data to the buffer
		return value;
	}

	public static int outputData(int value,int iBits) {
		int mask = generateMask(iBits,maxBits); // Generates a mask for the phrase
		int phrase = (value & mask) >>> (maxBits - iBits); // Stores the phrase in an Integer
		value <<= iBits; // Shifts the remaining bits of the buffer into position
		mask = generateMask(cBits,maxBits); 
		int character = (value & mask)  >>> (maxBits - cBits); 
		value <<= cBits; // Does the same steps for the mismatched byte
		System.out.println(phrase + "," + character ); // Outputs the phrase and mismatched byte
		return value;
	}
}