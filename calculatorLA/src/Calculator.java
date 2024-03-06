import java.util.Scanner;
import java.util.TreeMap;

public class Calculator {
    public static void main(String[] args) {

        Converter converter;
        converter = new Converter();
        String[] actions = {"+", "-", "/", "*"};
        String[] regexActions = {"\\+", "\\-", "\\/", "\\*"};
        Scanner scn = new Scanner(System.in);
        System.out.println("Введите выражение: ");
        String exp = scn.nextLine();
        //Определяем арифметическое действие:
        int actionIndex=-1;
        for (int i = 0; i < actions.length; i++) {
            if(exp.contains(actions[i])) {
                actionIndex = i;
                break;
            }
        }
        //Если не нашли арифметического действия, завершаем программу
        if(actionIndex==-1) {
            System.out.println("Некорректное выражение");
            return;
        }
        //Делим строчку по найденному арифметическому знаку

        String[] data = exp.split(regexActions[actionIndex]);
        //Определяем, находятся ли числа в одном формате (оба римские или оба арабские)
        if(converter.isRoman(data[0]) == converter.isRoman(data[1])) {
            int a,b;
            //Определяем, римские ли это числа
            boolean isRoman = converter.isRoman(data[0]);
            if(isRoman) {
                //Если римские, то конвертируем их в арабские
                a = converter.romanToInt(data[0]);
                b = converter.romanToInt(data[1]);
            }else {
                //Если арабские, конвертируем их из строки в число
                a = Integer.parseInt(data[0]);
                b = Integer.parseInt(data[1]);
            }
            //Выполняем с числами арифметическое действие
            int result = 0;
            switch (actions[actionIndex]) {
                case "+":
                    result = a+b;
                    break;
                case "-":
                    result = a-b;
                    break;
                case "*":
                    result = a*b;
                    break;
                case "/":
                    result = a/b;
                    break;

            }
            if(isRoman) {
                //если числа были римские, возвращаем результат в римском числе
                System.out.println(converter.intToRoman(result));
            }
            else {
                //если числа были арабские, возвращаем результат в арабском числе
                System.out.println(result);
            }
        }else {
            System.out.println("Числа должны быть в одном формате");
        }

    }
    public static class Converter {
        TreeMap<Character, Integer> romanKeyMap = new TreeMap<>();
        TreeMap<Integer, String> arabianKeyMap = new TreeMap<>();

        public Converter() {
            romanKeyMap.put('I', 1);
            romanKeyMap.put('V', 5);
            romanKeyMap.put('X', 10);
            romanKeyMap.put('L', 50);
            romanKeyMap.put('C', 100);

            arabianKeyMap.put(100, "C");
            arabianKeyMap.put(90, "XC");
            arabianKeyMap.put(50, "L");
            arabianKeyMap.put(40, "XL");
            arabianKeyMap.put(10, "X");
            arabianKeyMap.put(9, "IX");
            arabianKeyMap.put(5, "V");
            arabianKeyMap.put(4, "IV");
            arabianKeyMap.put(1, "I");

        }

        public boolean isRoman(String number) {
            return romanKeyMap.containsKey(number.charAt(0));
        }
        public String intToRoman(int number) {
            String roman = "";
            int arabianKey;
            do {
                arabianKey = arabianKeyMap.floorKey(number);
                roman += arabianKeyMap.get(arabianKey);
                number -= arabianKey;
            }while (number != 0);
            return roman;


        }

        public int romanToInt(String s) {
            int end = s.length() - 1;
            char[] arr = s.toCharArray();
            int arabian;
            int result;
            result = romanKeyMap.get(arr[end]);
            for (int i = end - 1; i >= 0; i--) {
                arabian = romanKeyMap.get(arr[i]);

                if (arabian < romanKeyMap.get(arr[i + 1])) {
                    result -= arabian;
                } else {
                    result += arabian;
                }
            }
            return result;
        }
    }
}

