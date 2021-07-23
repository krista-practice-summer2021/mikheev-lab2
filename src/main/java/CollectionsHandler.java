import java.io.File; // импорт библиотек для работы с программой.
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

// написание программы для сортировки и возведения в квадрат четных чисел.

class CollectionsHandler {

    private File fileIn; // создаем
    private File fileOut; // файловую переменную.

    ArrayList inputLineList = new ArrayList();

    public CollectionsHandler() { // инициализация данных.
        inputLineList = new ArrayList<>();
    }

    public static void main(String[] args) {

        CollectionsHandler wordRepeatCounter = new CollectionsHandler();

        String pathIn = "src/main/resources/inputn.txt"; // задаем имя и путь до файла переменной pathIn
        String pathOut = "src/main/resources/outputn.txt"; // задаем имя и путь до файла переменной pathOut

        wordRepeatCounter.setFileIn(new File(pathIn).getAbsoluteFile()); //связываем путь
        wordRepeatCounter.setFileOut(new File(pathOut).getAbsoluteFile()); // до файла с файлом.

        wordRepeatCounter.ReadAndSort();
        wordRepeatCounter.Choice();
        wordRepeatCounter.square();
        wordRepeatCounter.writeCountInFile();
    }

    public void setFileIn(File fileIn) {
        this.fileIn = fileIn;
    } // связка файла

    public void setFileOut(File fileOut) {
        this.fileOut = fileOut;
    } // с путем.

    public void ReadAndSort() { // чтение построчно из файла.
        try (Scanner in = new Scanner(fileIn)) {
            while (in.hasNextLine()) { // чтение до тех пор, пока не достигнут конец файла.
                String line = in.nextLine();
                int i = Integer.parseInt(line);
                inputLineList.add(i);
                ;
            }
        } catch (FileNotFoundException e) { // если файл не найден то выполняем fillInStackTrace
            System.out.println(e.fillInStackTrace());

        }
    }
    public void Choice() {
        Integer temp;
        for (int i = 0; i < inputLineList.size(); i++) {
            temp = (Integer) inputLineList.get(i);
            if (temp % 2 != 0 || temp == 0) inputLineList.remove(i);
        }
    }
    public void square() {
        Integer temp;
        for (int i = 0; i < inputLineList.size(); i++) {
            temp = (Integer) inputLineList.get(i);
            inputLineList.set(i, temp*temp);
        }
        Collections.sort(inputLineList);
    }
    public void writeCountInFile() {
        try (FileWriter writer = new FileWriter(fileOut)) {
            for (int i = 0; i < inputLineList.size(); i++) {
                writer.write(inputLineList.get(i) + "\n");
            }
        } catch (IOException e) { // если возникла какая-то ошибка при вводе/выводе вызываем метод printStackTrace.
            e.printStackTrace(); // этот метод необходим для проверки ошибок и исключений.
        }
    }
}


// КОММЕНТИРОВАНИЕ ПЕРВОЕ ПРОГРАММЫ


/*import java.io.File; // импорт библиотек для работы с программой.
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


class CollectionsHandler {

    private File fileIn; // создаем
    private File fileOut; // файловую переменную.

    private List<String> inputLineList; // создаем список inputLineList
    private Set<String> wordSet; // создаем множество wordSet
    private Map<String, Integer> wordRepeatCountMap; // создаем интерфейс wordRepeatCountMap


    public CollectionsHandler() { // инициализация данных.
        inputLineList = new ArrayList<>();
        wordSet = new HashSet<>();
        wordRepeatCountMap = new HashMap<>();
    }

    public static void main(String[] args) {

        CollectionsHandler wordRepeatCounter = new CollectionsHandler();

        String pathIn = "src/main/resources/input.txt"; // задаем имя и путь до файла переменной pathIn
        String pathOut = "src/main/resources/output.txt"; // задаем имя и путь до файла переменной pathOut

        wordRepeatCounter.setFileIn(new File(pathIn).getAbsoluteFile()); //связываем путь
        wordRepeatCounter.setFileOut(new File(pathOut).getAbsoluteFile()); // до файла с файлом.

        wordRepeatCounter.readFileLines(); // чтение построчно из файла

        wordRepeatCounter.addWord("Ларчик"); // слова для
        wordRepeatCounter.addWord("Ларец"); // поиска
        wordRepeatCounter.addWord("Ларечище"); // в строках

        wordRepeatCounter.getWordSet().forEach(word -> { // поиск ключевых слов в строках файла.
            wordRepeatCounter.setWordRepeatCount(word);
        });

        wordRepeatCounter.writeCountInFileMap();
    }

    public void setFileIn(File fileIn) {
        this.fileIn = fileIn;
    } // связка файла
    public void setFileOut(File fileOut) {
        this.fileOut = fileOut;
    } // с путем.

    public void addWord(String element) {
        this.wordSet.add(element);
    } // добавление слова в множество
    public Set<String> getWordSet() {
        return wordSet;
    }

    public void setWordRepeatCount(String word) { //проверка на количество повторений слова в файле.
        int count = inputLineList.stream().mapToInt(line -> line.split(word).length - 1).sum();
        wordRepeatCountMap.put(word, count);
    }

    public List<String> filterLinesWithWord(List<String> lines, String word) { // сортировка строк с помощью ключевых слов.
        return lines.stream().filter(line -> line.contains(word)).collect(Collectors.toList());
    }

    public List<String> sortListByLineLength(List<String> lines) { // сортировка списка по длине, от малого, к большему.
        List<String> sortList = new ArrayList<>(lines);
        sortList.sort(Comparator.comparingInt(String::length));
        return sortList;
    }

    public void readFileLines() { // чтение построчно из файла.
        try (Scanner in = new Scanner(fileIn)) {
            while (in.hasNextLine()) { // чтение до тех пор, пока не достигнут конец файла.
                String line = in.nextLine();
                inputLineList.add(line);
            }
        } catch (FileNotFoundException e) { // если файл не найден то выполняем fillInStackTrace
            System.out.println(e.fillInStackTrace());

        }
    }

    public void writeCountInFileMap() {
        try (FileWriter writer = new FileWriter(fileOut)) {
            Map<String, Integer> sortedMap = new LinkedHashMap<>(wordRepeatCountMap);
            // построковая запись в файл
            // отсортированных по убыванию частот и соответствующих строк
            sortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(entry -> {
                String printStr = "«" + entry.getKey() + "»" + " повторяется " + entry.getValue() + " раз"; // запись в файл кол-во повторяющихся слов.
                try {
                    writer.write(printStr + "\n\n"); // вывод строки и переход на две строки вниз.
                    List<String> withWordLineList = filterLinesWithWord(inputLineList, entry.getKey());
                    sortListByLineLength(withWordLineList).forEach(sortedLine -> {  // вывод сортированных строк по длине.
                        try {
                            writer.write(sortedLine + "\n"); // вывод строки и переход на новую строку.
                        } catch (IOException e) { // если возникла какая-то ошибка при вводе/выводе вызываем метод printStackTrace.
                            e.printStackTrace(); // этот метод необходим для проверки ошибок и исключений.
                        }
                    });
                    writer.write("\n"); // переход на новую строку.
                } catch (IOException e) {  // если возникла какая-то ошибка при вводе/выводе вызываем метод printStackTrace.
                    e.printStackTrace(); // этот метод необходим для проверки ошибок и исключений.
                }
            });
        } catch (IOException e) { // если возникла какая-то ошибка при вводе/выводе вызываем метод printStackTrace.
            e.printStackTrace(); // этот метод необходим для проверки ошибок и исключений.
        }
    }
}


*/
