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
			int numOut = currUsedBits / cBits; // Calculates the number of bytes that can be outputted from the buffer
			for(int i = 0; i< numOut; i ++) { 
				outputBytes(value); // Outputs a byte
				value <<= cBits;	// Shifts the buffer by one byte length		
			}
			if (currUsedBits % cBits != 0) {
				outputBytes(value); // Outputs the remaining bits in the case that the bits in the buffer were not a multiple of 8
			}
		} catch (Exception e) {
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

	public static int encodeLine(String Line, int counter, int value) {
		String[] lineValues = Line.split(",");
		int index = Integer.parseInt(lineValues[0]);
		int character = Integer.parseInt(lineValues[1]);
		int iBits = bitsNeeded(counter);
		int availableBits = maxBits - currUsedBits;
		if(iBits > availableBits) {
			value = makeSpace(value);
			availableBits += doubleByte;
		}
		availableBits -= iBits;
		value = pack(value,index,availableBits);
		currUsedBits += iBits;
		if(cBits > availableBits) {
			value = makeSpace(value);
			availableBits += doubleByte;
		}
		availableBits -= cBits;
		value = pack(value,character,availableBits);
		currUsedBits += cBits;
		return value;
	}
	
	public static int pack(int value, int inputBits, int bitOffset) {
		int mask = generateMask(currUsedBits,maxBits);
		value = value & mask;
		inputBits <<= bitOffset;
		value = value | inputBits;
		return value;
	}
	
	public static int makeSpace(int value) {
		byte firstOut = (byte) ((value & generateMask(cBits,maxBits))>>> (maxBits - cBits)) ;
		byte secondOut = (byte) ((value &  0x00FF0000)>>> doubleByte);
		System.out.write(firstOut);
		System.out.write(secondOut);
		System.out.flush();
		currUsedBits = currUsedBits - doubleByte;
		value <<= doubleByte;
		return value;
	}

	public static void outputBytes(int value) {
		byte output = (byte) ((value & generateMask(cBits,maxBits))>>> (maxBits - cBits)) ;
		System.out.write(output);
		System.out.flush();
	}
}
