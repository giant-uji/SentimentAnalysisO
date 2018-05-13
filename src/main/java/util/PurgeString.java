package util;

import java.util.Arrays;
import java.util.List;

public class PurgeString {
    private static List<String> punctuationSymbols = Arrays.asList(",", ".", "\"", "!", "?");
    private static List<String> determinants = Arrays.asList("the", "a", "some", "i", "i'm", "to");

    public static String removePunctuationSymbols(String input) {
        String output = new String(input);
        for(String symbol: punctuationSymbols) {
            output = output.replace(symbol, "");
        }
        return output;
    }

    public static String removeDeterminants(String input) {
        String output = new String(input);
        for(String symbol: determinants) {
            output = output.replace(symbol, "");
        }
        return output;
    }
}
