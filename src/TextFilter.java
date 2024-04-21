import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class TextFilter {
    public static void main(String[] args) throws IOException {

        HashSet<Character> alphabet = new HashSet<>();
        for (char ch = 'а'; ch <= 'я'; ch++) {
            alphabet.add(ch);
        }
        alphabet.add('ё');
        alphabet.add('є');
        alphabet.add('ї');
        alphabet.add('ґ');
        alphabet.add('і');
        alphabet.add('ў');

        String text = TextStatistics.readTextFromFile("src/rus_text.txt").substring(0,100);

        String filteredTextRegular = filterRegularAlphabet(text, alphabet);
        System.out.println("Результат у режимі звичайного алфавіту: " + filteredTextRegular);


        String filteredTextSpace = filterAlphabetWithSpace(text, alphabet);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Result_without_space.txt"))){
            bw.write(filteredTextRegular);
            try (BufferedWriter bw1 = new BufferedWriter(new FileWriter("Result_with_space.txt"))) {
                bw1.write(filteredTextSpace);
            }
        } catch (IOException e) {
            System.out.println("Помилка запису в файл: " + e.getMessage());
        }
        System.out.println("Результат у режимі алфавіту з пробілом: " + filteredTextSpace);
    }

    public static String filterRegularAlphabet(String text, HashSet<Character> alphabet) {
        StringBuilder filteredText = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch) && alphabet.contains(Character.toLowerCase(ch))) {
                filteredText.append(Character.toLowerCase(ch));
            }
        }
        return filteredText.toString();
    }


    public static String filterAlphabetWithSpace(String text, HashSet<Character> alphabet) {
        StringBuilder filteredText = new StringBuilder();
        boolean spaceDetected = false;
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch) && alphabet.contains(Character.toLowerCase(ch))) {
                filteredText.append(Character.toLowerCase(ch));
                spaceDetected = false;
            } else if (Character.isWhitespace(ch)) {
                if (!spaceDetected) {
                    filteredText.append(' '); // додати тільки один пробіл
                    spaceDetected = true;
                }
            }
        }
        // Видалення пробілів на початку та в кінці тексту
        return filteredText.toString().trim();
    }

}