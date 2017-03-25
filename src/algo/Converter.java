package algo;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Converter {
	
	public Map <String, String> hexToIntMap = new HashMap<String, String>();
	private final static int  MASK = 0xff; 
	
	public Converter(){
		
		hexToIntMap.put("0", "0");
		hexToIntMap.put("1", "1");
		hexToIntMap.put("2", "2");
		hexToIntMap.put("3", "3");
		hexToIntMap.put("4", "4");
		hexToIntMap.put("5", "5");
		hexToIntMap.put("6", "6");
		hexToIntMap.put("7", "7");
		hexToIntMap.put("8", "8");
		hexToIntMap.put("9", "9");
		hexToIntMap.put("A", "10");
		hexToIntMap.put("a", "10");
		hexToIntMap.put("b", "11");
		hexToIntMap.put("B", "11");
		hexToIntMap.put("c", "12");
		hexToIntMap.put("C", "12");
		hexToIntMap.put("d", "13");
		hexToIntMap.put("D", "13");
		hexToIntMap.put("e", "14");
		hexToIntMap.put("E", "14");
		hexToIntMap.put("f", "15");
		hexToIntMap.put("F", "15");
	}
	
	
	
	public byte[] StringToByteArray(String str){
		
		byte[] d = new byte[(str.length()/2)];
		byte a,b,c;
		for(int i = 0; i < (str.length()); i = i + 2){
			String subString1 = str.substring(i, i+1);
			String subString2 = str.substring(i+1, i+2);
			//System.out.println("Substring1 is: "+subString1+" Substring2 is: "+subString2);
			subString1 = hexToIntMap.get(subString1);
			subString2 = hexToIntMap.get(subString2);
			//System.out.println("Substring1 is: "+subString1+" Substring2 is: "+subString2);
			//a = (byte)Integer.parseInt(subString1, 10);
			//a = (byte)(a<<4);
			//b = (byte)Integer.parseInt(subString2, 10);
			//c = (byte)(a + b);
			d[i/2] = (byte) ((Integer.parseInt(subString1, 10)<<4)+ Integer.parseInt(subString2, 10));
			//System.out.println("a is: "+a+" b is: "+b+" c is "+c);
			//d[i/2] = c; 
		}
		
		return d;
		
		
		
	}
	
	public int[] bytestoWords(byte[] input,int p) {
		int[] output = new int[p];
		for (int i = 0; i < output.length; i++)
			output[i] = 0;

		for (int i = 0, index = 0; i < p; i++)
			output[i] = ((input[index++] & Converter.MASK)) | ((input[index++] & Converter.MASK) << 8) | ((input[index++] & Converter.MASK) << 16) | ((input[index++] & Converter.MASK) << 24);
		
		return output;
	}

	public List<Integer> bytesToInt(byte[] input, int length) {
		//System.out.println("Length if input inside bytesToIny is: "+input.length);
		//System.out.println("Printing input inside "+length);
		for(int i = 0; i< input.length;i++){
			//System.out.print(input[i]+" ");
		}
		//System.out.println();
		List<Integer> intData = new ArrayList<Integer>();
		
		
		//Byte b;
		int bInt;
		int index = 0;
		//bInt = ByteBuffer.wrap(input).asIntBuffer().array();
		
		for(int i=0;i<length;i++){
			//System.out.println(index);
			bInt = ((input[index++]&Converter.MASK))| ((input[index++]&Converter.MASK)<<8) | ((input[index++]&Converter.MASK)<<16)|((input[index++]&Converter.MASK)<<24);
			intData.add(bInt);
			//System.out.print(" "+bInt);
			
		}
		//System.out.println("After byte to Int: ");
		//System.out.println(intData);
		
		
		
		System.out.println();
		return intData;
	}

	public byte[] intToByte(List<Integer> tempOutput, int length) {
		// TODO Auto-generated method stub
		byte[] byteData = new byte[length];
		for(int i = 0; i < length; i++){
			byteData[i] = (byte)((tempOutput.get(i/4) >>> (i%4)*8) & Converter.MASK);
		}
		return byteData;
	}

	public String byteToHex(byte[] input) {
		
		StringBuffer output = new StringBuffer(input.length * 2);
		for(byte b: input){
			output.append(String.format("%02x", b & Converter.MASK));
		}
			
		return output.toString();
	}

	
	
	
		

}
