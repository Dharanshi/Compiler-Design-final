import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlphaBitLexer {
    public static void main(String[] args) {
        List<String> tokens = tokenize("functions50.txt");
        for (String token : tokens) {
            System.out.println("Token: " + token);
        }
    }

    public static List<String> tokenize(String filename) {
        List<String> tokens = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                tokens.addAll(tokenizeLine(line));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tokens;
    }

    private static List<String> tokenizeLine(String line) {
        List<String> tokens = new ArrayList<>();
        String[] words = line.split("\\s+");

        for (String word : words) {
            if (isKeyword(word)) {
                tokens.add("KEYWORD: " + word);
            } else if (isIdentifier(word)) {
                tokens.add("IDENTIFIER: " + word);
            } else if (isConstant(word)) {
                tokens.add("CONSTANT: " + word);
            } else if (isOperator(word)) {
                tokens.add("OPERATOR: " + word);
            }
            // Handle other token types based on the grammar rules
            // Add additional checks for different token types
        }

        return tokens;
    }

    private static boolean isKeyword(String token) {
        List<String> keywords = Arrays.asList(
            "share", "group", "constant", "nothing", "whole", "decimal", "choice", "word",
            "giveback", "check", "otherwise", "repeat", "until", "show", "get", "fresh",
            "Input", "readnext", "greet", "askName", "convertToMiles", "askAge", "findRemainder",
            "areSame", "areDifferent", "isBigger", "isSmaller", "whisperMessage", "shoutMessage",
            "currentDate", "currentTime", "daysUntilBirthday", "findMax", "findMin", "sumArray",
            "averageArray", "sortArray", "listFiles", "readFile", "writeToFile", "generateRandomNumber",
            "convertToBinary", "convertToHexadecimal", "calculateAreaOfCircle", "calculatePerimeterOfRectangle",
            "calculateAreaOfTriangle", "isLeapYear", "countVowels", "countWords", "convertCelsiusToFahrenheit",
            "convertFahrenheitToCelsius", "doubleNumber", "halfNumber", "isMultiple", "swap",
            "roundToNextWhole", "roundToPreviousWhole", "countUpTo", "countDownFrom"
        );
        return keywords.contains(token);
    }

    private static boolean isIdentifier(String token) {
        return token.matches("[a-zA-Z_][a-zA-Z_0-9]*");
    }

    private static boolean isConstant(String token) {
        return token.matches("-?\\d+(\\.\\d+)?");
    }

    private static boolean isOperator(String token) {
        return token.matches("[-+*/%<>=&|^!~.,;{}\\[\\]()#]+");
    }
}