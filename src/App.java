import java.time.LocalDate;
import java.util.Scanner;

public class App {
    private final ExpenseTracker tracker;
    private final Scanner scanner;
    private final InputParser parser;

    public App() {
        this.tracker = new ExpenseTracker();
        this.scanner = new Scanner(System.in);
        this.parser = new InputParser(scanner);
    }

    public void start(){
        while (true) {
            System.out.println("1. Adicionar despesa");
            System.out.println("2. Ver despesas de um dia");
            System.out.println("3. Ver dias e despesas");
//            System.out.println("4. Ver dias e soma das despesas");
//            System.out.println("5. Ver gasto total");
//            System.out.println("6. Ver gasto médio diário");
//            System.out.println("7. Ver dia mais caro");
//            System.out.println("8. Ver maior despesa");
            System.out.println("4. Sair");

            int choice = parser.getIntInput();

            switch (choice) {
                case 1:
                    LocalDate date = parser.getDateInput("Digite a data da despesa (DD/MM/YYYY): ");

                    String description = parser.getStringInput();
                    double amount = parser.getDoubleInput();
                    Expense newExpense = new Expense(description, amount);

                    tracker.addExpense(date, newExpense);

                    System.out.println("Despesa adicionada com sucesso!\n");
                    break;
                case 2:
                    LocalDate dateToView = parser.getDateInput("Digite a data desejada (DD/MM/YYYY): ");
                    tracker.viewExpenses(dateToView);
                    break;
                case 3:
                    tracker.viewAllExpenses();
                    break;
                case 4:
                    System.out.println("Saindo...");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!\n");
            }
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }
}
