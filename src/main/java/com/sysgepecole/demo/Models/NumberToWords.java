package com.sysgepecole.demo.Models;


public class NumberToWords  {

	private static final String[] UNITS = {
	        "", "UN", "DEUX", "TROIS", "QUATRE", "CINQ", "SIX", "SEPT", "HUIT", "NEUF"
	    };

	    private static final String[] TEENS = {
	        "DIX", "ONZE", "DOUZE", "TREIZE", "QUATORZE", "QUINZE", "SEIZE",
	        "DIX-SEPT", "DIX-HUIT", "DIX-NEUF"
	    };

	    private static final String[] TENS = {
	        "", "", "VINGT", "TRENTE", "QUARANTE", "CINQUANTE",
	        "SOIXANTE", "SOIXANTE-DIX", "QUATRE-VINGT", "QUATRE-VINGT-DIX"
	    };

	    public static String convert(long number) {
	        if (number == 0) return "ZERO";
	        if (number < 10) return UNITS[(int) number];
	        if (number < 20) return TEENS[(int) (number - 10)];
	        if (number < 100) {
	            int tens = (int) (number / 10);
	            int units = (int) (number % 10);
	            return TENS[tens] + (units > 0 ? "-" + UNITS[units] : "");
	        }
	        if (number < 1000) {
	            int hundreds = (int) (number / 100);
	            int remainder = (int) (number % 100);
	            return (hundreds == 1 ? "CENT" : UNITS[hundreds] + " CENT") + (remainder > 0 ? " " + convert(remainder) : "");
	        }
	        if (number < 1_000_000) {
	            long thousands = number / 1000;
	            long remainder = number % 1000;
	            return (thousands == 1 ? "MILLE" : convert(thousands) + " MILLE") + (remainder > 0 ? " " + convert(remainder) : "");
	        }

	        return String.valueOf(number);
	    }

	    public static String convert(double amount) {
	        long integerPart = (long) amount;
	        int decimalPart = (int) Math.round((amount - integerPart) * 100);
	        return convert(integerPart) + " FRANCS CONGOLAIS" + (decimalPart > 0 ? " ET " + convert(decimalPart) + " CENTIMES" : "");
	    }

}
