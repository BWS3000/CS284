// ======================================================================== //
// Name: Brian Sampson														//
// Pledge: I pledge my honor that I have abided by the Stevens Honor System.//
// CS284 - HW Assignment 1													//
// ======================================================================== //

package cs284;
import java.lang.Math;
import java.io.*;
import java.util.*;

public class BinaryNumber {
	private String binaryNumber;
	private int[] data;
	
	//Constructor makes binary number with zeros only that is length long
	public BinaryNumber(int length) {
		if(length <= 0) {
			throw new IllegalArgumentException("Bad input");
		}
		
		this.binaryNumber = "";
		this.data = new int[length];
		
		for(int i = 0; i < length; i = i + 1) {
			this.binaryNumber = this.binaryNumber + "0";
		}
	}
	
	//Constructor that accepts the contents of binary number
	public BinaryNumber(String str) {
		
		//Checking for empty list
		if(str.equals("")) {
			throw new IllegalArgumentException("Bad input");
		}
		
		//Checking for anything other than 0's and 1's
		for(int i = 0; i < str.length(); i = i + 1) {
			//If there is not a 0 or 1 in the selected substring, throw IllegalArgumentException
			if(str.charAt(i) != '0' && str.charAt(i) != '1') {
				throw new IllegalArgumentException("Bad input");
			}
		}
		
		//If everything checks out, create BinaryNumber object
		this.binaryNumber = str;
		this.data = new int[this.binaryNumber.length()];
		
		String holdReverse = "";
		
		for(int x = str.length() - 1; x >= 0; x = x -1) { //Reverses String
			holdReverse = holdReverse + str.substring(x, x + 1);
		}
		
		for(int i = 0 ; i < str.length(); i = i + 1) { //Turns string into array
			this.data[i] = Integer.parseInt(holdReverse.substring(i, i + 1));
		}
	}
	
	//Returns the length of BinaryNumber
	public int length() {
		return this.binaryNumber.length();
	}
	
	//Returns digit of int index of BinaryNumber
	public int getDigit(int index) {
		//Check if bounds are OK...
		if(index < 0 || index > this.length() - 1) {
			throw new IndexOutOfBoundsException("Out of bounds input");
		}
		
			
			//If all bounds are OK, check if charAt index is "0" or "1" and return 0/1
			if(this.data[index] == 1) {
				return 1;
			} else if(this.data[index] == 0){
				return 0;
			}
			return -1;
	}
	
	//Returns the decimal value of BinaryNumber
	public int toInt() {
		
		int val = 0;
		int currentPower = this.length() - 1;
		
		//Traverses through the binaryNumber with its corresponding (2**i) and adds it common variable until completion
		for(int i = 0 ; i < this.length() ; i = i + 1) {
			if(Integer.parseInt(this.binaryNumber.substring(i, i + 1)) == 1) {
				val = (int)(val + Math.pow(2, currentPower));
			}
			currentPower = currentPower - 1;
		}
		return val;
	}
	
	public void bitShift(int direction, int amount) {
		if(amount == 0) { throw new IllegalArgumentException("amount != 0");}
		if((amount < 0) || (direction != 1 && direction != -1)) { throw new IllegalArgumentException("Bad input"); } //Check bad input
		
		String oldStr = this.binaryNumber;
		//When shifting right
		if(direction == 1) { 
			if(this.binaryNumber.length() - amount < 0) { throw new IllegalArgumentException("Bad input");}
				this.binaryNumber = oldStr.substring(0, oldStr.length() - amount);
		}
		
		//When shifting left
		if(direction == -1) {
			String ext = "";
			for(int i = 0; i < amount; i = i + 1) {
				ext = ext + "0";
			}
			
			//Replace string
			this.binaryNumber = oldStr + ext;
			
			//Replace array data
			
			
			int[] replaceData = new int[this.binaryNumber.length()];
			
			String holdReverse = "";
			
			for(int x = this.length() - 1; x >= 0; x = x -1) { //Reverses String
				holdReverse = holdReverse + this.binaryNumber.substring(x, x + 1);
			}
			
			
			for(int i = 0 ; i < this.binaryNumber.length(); i = i + 1) {
				replaceData[i] = Integer.parseInt(holdReverse.substring(i, i + 1));
			}
			//Final replacement of array data
			this.data = replaceData;
			
		}
		
	}
		//bitWise or method
		public static BinaryNumber bwor(BinaryNumber bn1, BinaryNumber bn2) {
			
			//Validate lengths
			if(bn1.length() != bn2.length()) {
				throw new IllegalArgumentException("Bad input");
			}
			
			String newBin = "";
			
			//Traverse both binaryNumbers to get final result
			for(int i = 0; i < bn1.length(); i = i + 1) {
				if(Integer.parseInt(bn1.binaryNumber.substring(i, i + 1)) + Integer.parseInt(bn2.binaryNumber.substring(i, i + 1)) == 1 || Integer.parseInt(bn1.binaryNumber.substring(i, i + 1)) + Integer.parseInt(bn2.binaryNumber.substring(i, i + 1)) == 2) {
					newBin = newBin + "1";
				} else {
					newBin = newBin + "0";
				}
			}
			
			return new BinaryNumber(newBin);
		}
		
		//bitWise and method
		public static BinaryNumber bwand(BinaryNumber bn1, BinaryNumber bn2) {
			if(bn1.length() != bn2.length()) {
				throw new IllegalArgumentException("Bad input");
			}
			
			String newBin = "";
			
			//Traverse both binaryNumbers to get final result
			for(int i = 0; i < bn1.length(); i = i + 1) {
				if(Integer.parseInt(bn1.binaryNumber.substring(i, i + 1)) + Integer.parseInt(bn2.binaryNumber.substring(i, i + 1)) == 2) {
					newBin = newBin + "1";
				} else {
					newBin = newBin + "0";
				}
		}
			return new BinaryNumber(newBin);
	}
		//toString
		public String toString() {
			return this.binaryNumber = this.binaryNumber + "";
		}
		
		//Add this.BinaryNumber to aBinaryNumber
		public void add(BinaryNumber aBN) {
				int intABN;
				int intThisBN;
				
				//Change string -> int by using Integer.parseInt()
				intThisBN = Integer.parseInt(this.binaryNumber);
				intABN = Integer.parseInt(aBN.binaryNumber);
			
				//Max binaryNumber length value
				final int INTEGER = 10000;
				
				int[] sum = new int[INTEGER]; //array that holds total with 0's prepended
				int[] carry = new int[INTEGER]; //Carry array
				int count = INTEGER - 1; //Keeps count

					//Traverses both integers while they still have contents
					while((intThisBN != 0 || intABN != 0) && count >= 0) {
						 if(intThisBN % 10 + intABN % 10 + carry[count] == 1) {
							sum[count] = 1;
							
						} else if(intThisBN % 10 + intABN % 10 + carry[count]== 2){
							carry[count - 1] = 1;
							sum[count] = 0;
							
						} else if(intThisBN % 10 + intABN % 10 + carry[count] == 3) {
							carry[count - 1] = 1;
							sum[count] = 1;
						}
						count = count - 1;
						intThisBN = intThisBN / 10;
						intABN = intABN / 10;
					
					}
					
					//Checks if last number in binaryNumber is a 1 to finalize result
					if(intThisBN == 0 && intABN == 0 && carry[count] == 1) {
						sum[count] = 1;
					}
					
					
					count = 0; //Count for counting the amount of 0's remaining
					for(int x = 0; x <= sum.length - 1; x = x + 1) {
						if(sum[x] == 0) {
							count = count + 1;
							} else if(sum[x] == 1) {
								break;
							}
					}
					
					int[] finalBN = new int[INTEGER - count]; //Creates new array to hold final result
					
					for(int y = 0; y < finalBN.length; y = y + 1) {
						finalBN[y] = sum[count + y];
					}
					
					//Re-assigning data array
					this.data = finalBN;
					
					if(this.data.length == 0) {
						this.data = new int[1];
					}
					
					//Creates new binaryNumber string
					String finalString = "";
					for(int z : finalBN) {
						finalString = finalString + z;
					}
					
					//Re-assigns new binaryNumber string (finalString)
					this.binaryNumber = finalString;
					
					if(finalString.equals("")) {
						this.binaryNumber = "0";
					}
		}
					
	
		
	
	
	//Driver class and testing
		
	public static void main(String[]args) {

		BinaryNumber one = new BinaryNumber("1010");
		BinaryNumber two = new BinaryNumber("1100");
		
		
		System.out.println(bwor(one, two).toString());
		System.out.println(bwand(one, two).toString());
		
	} 
	
}
