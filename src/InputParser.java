import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputParser {
    private final Scanner scanner;

    public InputParser(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getStringInput() {
        while (true) {
            System.out.print("Insira a descrição: ");
            String input = scanner.nextLine().trim();

            if (input.matches("^[a-zA-Z]+$")) {
                return input;
            } else {
                System.out.println("Descrição inválida (usar somente letras)");
            }
        }
    }

    public double getDoubleInput() {
        while (true) {
            try {
                System.out.print("Insira o valor da despesa: ");
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido (usar somente números, e usar ponto para decimais");
            }
        }
    }

    public int getIntInput() {
        while (true) {
            try {
                System.out.print("Digite a operação desejada: ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
            }
        }
    }

    public LocalDate getDateInput(String prompt){
        while (true){
            try {
                System.out.println(prompt);
                String dateString = scanner.nextLine().trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return LocalDate.parse(dateString, formatter);
            } catch (Exception e) {
                System.out.println("Data inválida. Por favor, digite no formato correto.");
            }
        }
    }
}
