package com.sysgepecole.demo.Models;

public class FrenchNumberToWords {

	 private static final String[] units = {
		        "", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf", "dix",
		        "onze", "douze", "treize", "quatorze", "quinze", "seize"
		    };

		    public static String convert(long number) {
		        if (number == 0) return "zéro";
		        if (number < 0) return "moins " + convert(-number);

		        String words = "";

		        if (number / 1000000 > 0) {
		            words += convert(number / 1000000) + " million ";
		            number %= 1000000;
		        }

		        if (number / 1000 > 0) {
		            if (number / 1000 == 1) {
		                words += "mille ";
		            } else {
		                words += convert(number / 1000) + " mille ";
		            }
		            number %= 1000;
		        }

		        if (number / 100 > 0) {
		            if (number / 100 == 1) {
		                words += "cent ";
		            } else {
		                words += units[(int)(number / 100)] + " cent ";
		            }
		            number %= 100;
		        }

		        if (number > 0) {
		            if (number < 17) {
		                words += units[(int)number];
		            } else if (number < 20) {
		                words += "dix-" + units[(int)(number - 10)];
		            } else if (number < 100) {
		                int dizaine = (int)(number / 10);
		                int unite = (int)(number % 10);
		                if (dizaine == 7 || dizaine == 9) {
		                    words += units[dizaine - 1 * 10] + "-dix";
		                    if (unite > 0) words += "-" + units[unite];
		                } else {
		                    words += units[dizaine] + (unite > 0 ? "-" + units[unite] : "");
		                }
		            }
		        }

		        return words.trim();
		    }
}
