import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class TextIndex {


    public static double calculateIndex(String text) {
        int totalCharacters = text.length();


        HashMap<Character, Integer> letterCounts = new HashMap<>();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                letterCounts.put(ch, letterCounts.getOrDefault(ch, 0) + 1);
            }
        }

        double sum = 0.0;
        for (int count : letterCounts.values()) {
            sum += count * (count - 1);
        }
        double floor = (double) totalCharacters * (totalCharacters-1);

        return sum / floor;
    }

    public static void main(String[] args) {
        try {

            String textWithSpace = TextStatistics.readTextFromFile("src/rus_text.txt");
            String textWithoutSpace = TextStatistics.readTextFromFile("src/rus_text.txt");
            HashSet<Character> russianAlphabet = TextStatistics.prepareRussianAlphabet();

            String filteredTextWithSpace = TextFilter.filterAlphabetWithSpace(textWithSpace, russianAlphabet);
            String filteredTextWithoutSpace = TextFilter.filterRegularAlphabet(textWithoutSpace, russianAlphabet);


            double indexWithSpace = calculateIndex(filteredTextWithSpace);
            double indexWithoutSpace = calculateIndex(filteredTextWithoutSpace);


            System.out.println("Індекс відповідності з пробілами: " + indexWithSpace);
            System.out.println("Індекс відповідності без пробілів: " + indexWithoutSpace);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}