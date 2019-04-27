// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LZpack {
	
	public static int currUsedBits = 0; // Stores the amount of bits currently in the buffer
	public static int cBits = 8; // The amount of bits for a character (1 byte)
	public static int maxBits = 32; // The amount of bits in an Integer
	public static int doubleByte = 16; // the amount of bits for 2 bytes
	
	public static void main(String[] args) {	
		int value = 0; // Stores the buffer
		int counter = 1; // Variable that keeps track of the total phrases 
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // Creates buffered reader that reads Standard input
			String Line = br.readLine(); // Reads the first line of input
			while(Line != null) { // While there is still data to be read
				value = encodeLine(Line,counter,value); // Encodes the current line
				Line = br.readLine(); // Reads the next line
				counter++; // Increments the counter
			}
			br.close(); // Closes the buffer
			int numOut = currUsedBits / cBits; // Calculates the number of bytes that can be output from the buffer
			for(int i = 0; i< numOut; i ++) { 
				outputBytes(value); // Outputs a byte
				value <<= cBits;	// Shifts the buffer by one byte length		
			}
			if (currUsedBits % cBits != 0) {
				outputBytes(value); // Outputs the remaining bits in the case that the bits in the buffer were not a multiple of 8
			}
		} catch (Exception e) {
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

	public static int encodeLine(String Line, int counter, int value) { // Encodes a line and stores it in the buffer
		String[] lineValues = Line.split(","); // Splits the line into its phrase and mismatched byte
		int index = Integer.parseInt(lineValues[0]); // Stores the phrase in the Integer index
		int character = Integer.parseInt(lineValues[1]); // Stores the mismatched byte in the Integer character
		int iBits = bitsNeeded(counter); // Calculates the number of bits to encode the phrase
		int availableBits = maxBits - currUsedBits; // Calculates the available bits by subtracting the used bits from the max bits
		if(iBits > availableBits) { // Checks if the bits needed to code the phrase is greater than the available bits
			value = makeSpace(value); // If so create space by outputting two bytes 
			availableBits += doubleByte; // Increase the available bits by the two bytes of space
		}
		availableBits -= iBits; // Reduce available bits by the bits needed to code the phrase
		value = pack(value,index,availableBits); // Pack the phrase into the buffer
		currUsedBits += iBits; // Increase the currently used bits by the bits needed to code the phrase
		if(cBits > availableBits) { // Checks if the bits needed to code the character is greater than the available bits
			value = makeSpace(value); 
			availableBits += doubleByte;
		}
		availableBits -= cBits; 
		value = pack(value,character,availableBits);
		currUsedBits += cBits; // Packs the character into the buffer using the same process used to pack the phrase and modify the relevant variables
		return value;
	}
	
	public static int pack(int value, int inputBits, int bitOffset) {
		int mask = generateMask(currUsedBits,maxBits); // Generates the mask for the current amount of bits
		value = value & mask; // Clears any unused bits
		inputBits <<= bitOffset; // Shifts the inputBits into the correct position
		value = value | inputBits; // Copies the input bits into the buffer
		return value;
	}
	
	public static int makeSpace(int value) {
		byte firstOut = (byte) ((value & generateMask(cBits,maxBits))>>> (maxBits - cBits)) ; // Store the first 8 bits of the buffer in a byte 
		byte secondOut = (byte) ((value &  0x00FF0000)>>> doubleByte); // Store the second 8 bits of the buffer in a byte
		System.out.write(firstOut);
		System.out.write(secondOut); // Write both bytes out to standard out
		System.out.flush(); 
		currUsedBits = currUsedBits - doubleByte; // Reduces the currently used bits by the size of the two bytes
		value <<= doubleByte; // Shifts the remaining 16 bits into the left position
		return value;
	}

	public static void outputBytes(int value) { // Outputs the first 8 bits in the buffer to standard out
		byte output = (byte) ((value & generateMask(cBits,maxBits))>>> (maxBits - cBits)) ; 
		System.out.write(output);
		System.out.flush();
	}
}
