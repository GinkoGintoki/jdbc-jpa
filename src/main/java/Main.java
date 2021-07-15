import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Выберите действие:\n1. Добавить\n2. Удалить\n3. Переместить\n4. Выйти");
            String input = bufferedReader.readLine();
            while (true) {
                if (!input.matches("[1-4]")) {
                    System.out.print("Неверно введен номер действия! Пожалуйста введите снова: ");
                    input = bufferedReader.readLine();
                } else {
                    break;
                }
            }
            switch (input) {
                case "1" -> Action.add();

                case "2" -> Action.delete();

                case "3" -> Action.move();

                default -> System.exit(0);

            }
        }
    }
}
