import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        SplitBill splitBill = new SplitBill(); // создаем и запускам дележ счета
        splitBill.start();
    }
}

class SplitBill {  // основной класс
    Scanner scanner = new Scanner(System.in);
    ArrayList<Product> productList = new ArrayList<>();// создаем список под все заказы

    public void start(){
        double finalTotalCost = 0; // общая сумма

        int numberOfPayers = getNumberOfPayers(); // спрашиваем количество гостей, возвращает количество гостей, если ввели завершить - возвращает 0
        if (numberOfPayers != 0) {
            finalTotalCost = calculate();  // запускаем подсчет, возвращает общую сумму всех товаров
        }
        output(numberOfPayers, finalTotalCost); // запускаем вывод
    }

    private int getNumberOfPayers() { // узнаем количество людей, на которых делим счет, если вводят 'Завершить' - возвращает 0
        System.out.println("На скольких человек необходимо разделить счёт?");
        while (true){

            if (scanner.hasNextInt()) {  // проверяем, число ли введено
                int enterPayers = scanner.nextInt();
                    if (enterPayers > 0){     //проверяем, что гостей больше 0
                        return enterPayers;  //если да - возвращаем число гостей и завершаем цикл
                    } else {
                        System.out.println("Количество человек должно быть больше 0, пожалуйста введите корректное значение");
                    }

            } else { // если введено не число -  проверяем, введено ли 'Завершить'
                if (thisIsTheEnd(scanner.next())) {
                    return 0; // если введено слово 'Завершить' - возвращаем 0
                }
                System.out.println("Введено некорректное значение, пожалуйста введите корректное значение");
            }
        }
    }

    private double calculate() { // Принимает название товара и его цену, добавляет товар в список. Возвращает общую сумму товаров

        double totalCost = 0;

        while (true) {

            System.out.println("Введите название товара или команду 'Завершить'");

            String name = scanner.next();  // Считываем строку с названием

            if (thisIsTheEnd(name)) { // проверяем, введено ли 'Завершить'
                break;
            } else {

                System.out.println("Введите стоимость товара в формате 'Р.КК' или команду 'Завершить'");

                double price = getCost(); //узнаем цену товара, возвращает цену строго больше 0 или -1 в том случае, если ввели 'Завершить'
                if (price == -1) { // возвращается в том случае, если ввели 'Завершить'
                    break;
                }
                productList.add(new Product(name, price)); // создаем новый товар

                System.out.println("Товар " + name + " стоимостью " + price + " " + rublesEnd(price) + " добавлен");
                totalCost += price; // добавляем к общей сумме цену товара
            }
        }
        return totalCost;
    }

    private boolean thisIsTheEnd(String input){  // Проверяет: введено ли 'Завершить'
        return input.equalsIgnoreCase("Завершить");
    }

    private double getCost() {  // Получаем цену товара
        while (true) {

            if (scanner.hasNextDouble()) {  // проверяем тип введенных данных

                double price = scanner.nextDouble();

                if (price > 0) { // если цена больше 0 и формата РР.КК - возвращаем цену
                    return price;
                } else {
                    System.out.println("Цена должна быть больше 0, в формате РР.КК, пожалуйста введите корректное значение"); //если меньше или 0, просим ввести все заново
                }

            } else {

                if (thisIsTheEnd(scanner.next())) {
                    return -1;
                }
                System.out.println("Цена должна быть числом, пожалуйста введите корректное значение");
            }
        }
    }

    private String rublesEnd(double rubles) { // процедура возвращает слово рубль с правильным окончанием
        if (((int) rubles % 100) > 10 && ((int) rubles % 100) < 21 ) {
            return "рублей";
        }
        int i = (int) rubles % 10; // выясняем последнюю цифру
        if (i == 1) {
            return "рубль";
        } else if (i == 2 || i == 3  || i == 4) {
            return "рубля";
        } else {
            return "рублей";
        }
    }

    private void output(int numberOfPayers, double cost) {  //выводим список товаров и цену для одного
        if (productList.isEmpty()) {
            System.out.println("Как хорошо, что вам не за что платить");
        } else {
            System.out.println("Добавленные товары:");

            for (Product product : productList) {
                System.out.println("Товар " + product.name + " стоимостью " + product.price + " " + rublesEnd(product.price)); // для каждого товара выводим название и цену
            }
            double onePersonPay = cost /  numberOfPayers;
            System.out.println(String.format("%.2f", onePersonPay) + " " + rublesEnd(onePersonPay) + " c каждого человека"); // вывод сколько должен заплатить каждый
        }
    }
}

class Product {
    String name;
    double price;
    public Product(String name, double price) { //создаем по названию и цене формата РР.КК
        this.name = name;
        this.price = price;
    }
}