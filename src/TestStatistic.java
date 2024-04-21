import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

class TextStatistics {
    // Функція для фільтрації тексту та обчислення частот символів та біграм
    public static HashMap<String, Double> calculateStatisticsSymbols(String text) {
        // Лічильники символів та біграм
        HashMap<String, Integer> characterFreq = new HashMap<>();

        // Обчислення частот символів
        for (char ch : text.toCharArray()) {
            String character = String.valueOf(ch);
            characterFreq.put(character, characterFreq.getOrDefault(character, 0) + 1);
        }
        // Розрахунок частот
        int totalCharacters = text.length();

        HashMap<String, Double> frequencies = new HashMap<>();
        for (String character : characterFreq.keySet()) {
            double frequency = (double) characterFreq.get(character) / totalCharacters;
            frequencies.put(character, frequency);
        }

        return frequencies;
    }

    public static HashMap<String, Double> calculateStatisticsBigrams(String text) {
        // Лічильники символів та біграм
        HashMap<String, Integer> bigramFreq = new HashMap<>();
        HashSet<String> uniqueBigrams = new HashSet<>();

        // Обчислення частот біграм
        for (int i = 0; i < text.length() - 1; i++) {
            String bigram = text.substring(i, i + 2);
            bigramFreq.put(bigram, bigramFreq.getOrDefault(bigram, 0) + 1);
            uniqueBigrams.add(bigram);
        }

        // Розрахунок частот
        int totalBigrams = text.length() - 1;
        HashMap<String, Double> frequencies = new HashMap<>();
        for (String bigram : uniqueBigrams) {
            double frequency = (double) bigramFreq.get(bigram) / totalBigrams;
            frequencies.put(bigram, frequency);
        }

        return frequencies;
    }

    // Функція для читання тексту з файлу
    public static String readTextFromFile(String filename) throws IOException {
        StringBuilder text = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line).append("\n");
            }
        }
        return text.toString();
    }

    public static void main(String[] args) {
        try {
            // Читання тексту з файлу та фільтрація
            String textWithSpace = readTextFromFile("src/rus_text.txt");
            String textWithoutSpace = textWithSpace;
            HashSet<Character> russianAlphabet = prepareRussianAlphabet();

            String filteredTextWithSpace = TextFilter.filterAlphabetWithSpace(textWithSpace, russianAlphabet);
            String filteredTextWithoutSpace = TextFilter.filterRegularAlphabet(textWithoutSpace, russianAlphabet);

            HashMap<String, Double> statisticsSymbolsWithSpace = calculateStatisticsSymbols(filteredTextWithSpace);
            HashMap<String, Double> statisticsSymbolsWithoutSpace = calculateStatisticsSymbols(filteredTextWithoutSpace);

            HashMap<String, Double> statisticsBigramsWithSpace = calculateStatisticsBigrams(filteredTextWithSpace);
            HashMap<String, Double> statisticsBigramsWithoutSpace = calculateStatisticsBigrams(filteredTextWithoutSpace);

            System.out.println("""
                    Частоти символів із урахуванням пробілу записані в файл - Statistic_Symbols_with_space.txt
                    Частоти символів без пробілу записані в файл - Statistic_Symbols_without_space.txt
                    Частоти біграм із урахуванням пробілу записані в файл - Statistic_Bigrams_without_space.txt
                    Частоти біграм без пробілів записані в файл - Statistic_Bigrams_without_space.txt
                    """);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("Statistic_Symbols_with_space.txt"))) {
                bw.write(String.valueOf(statisticsSymbolsWithSpace));
            }
            try (BufferedWriter bw1 = new BufferedWriter(new FileWriter("Statistic_Symbols_without_space.txt"))) {
                bw1.write(String.valueOf(statisticsSymbolsWithoutSpace));
            }
            try (BufferedWriter bw1 = new BufferedWriter(new FileWriter("Statistic_Bigrams_with_space.txt"))) {
                bw1.write(String.valueOf(statisticsBigramsWithSpace));
            }
            try (BufferedWriter bw1 = new BufferedWriter(new FileWriter("Statistic_Bigrams_without_space.txt"))) {
                bw1.write(String.valueOf(statisticsBigramsWithoutSpace));
            } catch (IOException e) {
                System.out.println("Помилка запису в файл: " + e.getMessage());
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Функція для підготовки алфавіту російської мови
    public static HashSet<Character> prepareRussianAlphabet() {
        HashSet<Character> alphabet = new HashSet<>();
        for (char ch = 'а'; ch <= 'я'; ch++) {
            alphabet.add(ch);
        }
        alphabet.add('ё');
        return alphabet;
    }
}
