// Hunter Cavers (1288108)
// Sivaram Manoharan (1299026)

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LZpack {
	
	public static int currUsedBits = 0; // X length
	public static int byteLength = 8;
	public static int maxBits = 32;
	public static int twoByte = 16;
	
	public static void main(String[] args) {	
		int value = 0; 
		int counter = 1;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String Line = br.readLine();
			while(Line != null) {
				value = encodeLine(Line,counter,value);
				Line = br.readLine();
				counter++;
			}
			br.close();
			int numOut = currUsedBits / byteLength;
			for(int i = 0; i< numOut; i ++) {
				outputBytes(value);
				value <<= byteLength;			
			}
			if (currUsedBits % byteLength != 0) {
				outputBytes(value);
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
		if(iBits <= availableBits) { // if the bits needed to encode the Phrase number is less than the available bits encode the phrase number
			availableBits = availableBits - iBits;
			value = pack(value,index,availableBits);
			currUsedBits = currUsedBits + iBits;
				if(byteLength <= availableBits) {
					availableBits = availableBits - byteLength;
					value = pack(value,character,availableBits);
					currUsedBits = currUsedBits + byteLength;
					return value;
				}
				value = makeSpace(value);
				availableBits = availableBits + byteLength;
				value = pack(value,character,availableBits);
				currUsedBits = currUsedBits + byteLength;
				return value;
		}	
		value = makeSpace(value);
		availableBits = availableBits + twoByte - iBits;
		value = pack(value,index,availableBits);
		currUsedBits = currUsedBits + iBits;
		if(byteLength <= availableBits) {
			availableBits = availableBits - byteLength;
			value = pack(value,character,availableBits);
			currUsedBits = currUsedBits + byteLength;
			return value;
		}
		value = makeSpace(value);
		availableBits = availableBits + byteLength;
		value = pack(value,character,availableBits);
		currUsedBits = currUsedBits + byteLength;
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
		byte firstOut = (byte) ((value & generateMask(byteLength,maxBits))>>> (maxBits - byteLength)) ;
		byte secondOut = (byte) ((value &  0x00FF0000)>>> twoByte);
		System.out.write(firstOut);
		System.out.write(secondOut);
		System.out.flush();
		currUsedBits = currUsedBits - twoByte;
		value <<= twoByte;
		return value;
	}

	public static void outputBytes(int value) {
		byte output = (byte) ((value & generateMask(byteLength,maxBits))>>> (maxBits - byteLength)) ;
		System.out.write(output);
		System.out.flush();
	}
}
