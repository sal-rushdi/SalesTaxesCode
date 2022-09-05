/*
 *  The code was written and tested with Java JDK 16
* and the Eclipse IDE. the better way is to import the 
* project or to compile it correctly, keeping in mind the package name. 
 */

package com.saleTax.tax;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

// this is Class for calculating Tax and also importing and exporting things to add them to the bill
public class SaleTax {

	//private static final int saleTax = 10;
	//private static final int importedTax = 5;
	
	// create an ArrayList
	// we need to provide exception like books, medical, and food
	private static List<String> exception = new ArrayList<>();
	
	// create ArrayLists to add description and price
	private static List<String> test1 = new ArrayList<>();
	private static List<String> test2 = new ArrayList<>();
	private static List<String> test3 = new ArrayList<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// insert elements to the ArrayList exception
		exception.add("book");
		exception.add("food");
		exception.add("medical");
		
		// insert elements to the ArrayList test1
		test1.add("1 book at 12.49");
		test1.add("1 music CD at 14.99");
		test1.add("1 chocolate bar at 0.85");
		
		// insert elements to the ArrayList test2
		test2.add("1 imported box of chocolates at 10.00");
		test2.add("1 imported bottle of perfume at 47.50");
		
		// insert elements to the ArrayList test3
		test3.add("1 imported bottle of perfume at 27.99");
		test3.add("1 bottle of perfume at 18.99");
		test3.add("1 packet of headache pills at 9.75");
		test3.add("1 imported box of chocolates at 11.25");
		
		//calling the method calculate
		calculate(test1);
		System.out.println("");
		calculate(test2);
		System.out.println("");
		calculate(test3);
	}
	
	// this method calculate additional sales tax
	// applicable on all imported goods at a rate of 5%, with no exemptions. 
	// and at a rate of 10% on all goods,
	public static double calculatedTax(boolean isImported, boolean isException, double amount) {
		double imported = 0;
		double tax = 0;
		if (isImported) {
			imported += (amount * 5 / 100);
		}
		if (!isException) {
			tax += (amount * 10 / 100);
		}
		// Convert the double imported and double tax to BigDecimal
		BigDecimal importTax = new BigDecimal(tax + imported);
		
		BigDecimal roundValue = round(importTax, BigDecimal.valueOf(0.05), RoundingMode.UP);
		BigDecimal amountNew = BigDecimal.valueOf(amount);

		return roundValue(amountNew.add(roundValue));
	}
	
	/* this method used to get the amount of the input data and return it without the description */
	public static double getAmount(String text) {
		int textIndex = text.indexOf(" at");
		String amount = text.substring(textIndex + 1 + 2);
		//converted to a Double
		return Double.valueOf(amount);
	}
	
	/* this method used to get the description of the input data and return it without the amount */
	public static String getDescription(String text) {
		int textIndex = text.indexOf(" at");
		String description = text.substring(0, textIndex);
		return description + ":";
	}
	
	/** This method is used to check whether a item is imported or not. */
	public static boolean isImportedAvailable(String text) {
		int textIndex = text.indexOf("imported");
		if (textIndex == -1) {
			return false;
		} else {
			return true;
		}
	}
	
	/** This method is used to check if the item in the exempted list */
	public static boolean isExceptionAvailable(String text) {
		// Create an array of strings for the categories books, medical and food.
		String[] exempt = new String[] { "chocolate", "pills", "book" }; // check if the item in the exempted list

		for (String string : exempt) {
			if (text.indexOf(string) > 0) {
				return true;
			}

		}
		return false;
	}
	
	/* this method is for rounding values, save and change its status */
	public static double roundValue(BigDecimal amount) {
		DecimalFormat round = new DecimalFormat("#.##");
		return Double.valueOf(round.format(amount));
	}
	
	/* this method rounds values if value after more than two decimal point
	 * return the given value as a double in a specific format of 0.00 */
	public static double roundValue(double amount) {
		DecimalFormat round = new DecimalFormat("#.##");
		return Double.valueOf(round.format(amount));
	}
	
	/* this method used to calculate the taxes and add them to the total amount when there is tax,
	 *  is also used to print the sale Tax amount */
	public static void calculate(List<String> list) {
		double saleTax = 0;
		double total = 0;

		for (String string : list) {
			double amount = getAmount(string);
			boolean isImported = isImportedAvailable(string);
			boolean isExcept = isExceptionAvailable(string);
			String description = getDescription(string);
			double getCalculateTax = calculatedTax(isImported, isExcept, amount);
			total += getCalculateTax;
			saleTax += (getCalculateTax - amount);
			System.out.println(description + "" + getCalculateTax);

		}
		System.out.println("Sales Taxed :" + roundValue(saleTax));
		System.out.println("Total :" + roundValue(total));

	}
	
	/* if increase is null, nothing happened and return value.
	 * Else
	 * value divided by increase with additional some field and then multiply */
	public static BigDecimal round(BigDecimal v, BigDecimal increse, RoundingMode round) {
		if (increse.signum() == 0) {
			/* if increase is null, nothing happened and return value */
			return v;
		} else {
			BigDecimal divide = v.divide(increse, 0, round);
			BigDecimal result = divide.multiply(increse);
			result.setScale(2, RoundingMode.UNNECESSARY); // Two decimal places
			return result;
		}
	}

}