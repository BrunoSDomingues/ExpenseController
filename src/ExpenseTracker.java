import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExpenseTracker {
    private final Map<LocalDate, List<Expense>> dailyExpenses;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ExpenseTracker() {
        this.dailyExpenses = new HashMap<>();
    }

    public void addExpense(LocalDate date, Expense expense) {
        dailyExpenses.computeIfAbsent(date, k -> new ArrayList<>()).add(expense);
    }

    public void viewExpenses(LocalDate date) {
        List<Expense> expenses = dailyExpenses.getOrDefault(date, new ArrayList<>());
        String formattedDate = date.format(formatter);
        if (expenses.isEmpty()) {
            System.out.println("Nenhuma despesa para " + formattedDate + "!\n");
        } else {
            System.out.println("Despesas do dia " + formattedDate + ":");
            for (Expense expense : expenses) {
                System.out.println(expense);
            }
            System.out.println();
        }
    }

    public void viewAllExpenses() {
        if (dailyExpenses.isEmpty()) {
            System.out.println("Nenhuma despesa!\n");
        } else {
            for (Map.Entry<LocalDate, List<Expense>> entry : dailyExpenses.entrySet()) {
                LocalDate date = entry.getKey();
                String formattedDate = date.format(formatter);
                List<Expense> expenses = entry.getValue();

                System.out.println("Data: " + formattedDate);
                for (Expense expense : expenses) {
                    System.out.println(expense);
                }
                System.out.println();
            }
        }
    }

//    public void viewSumOfExpensesPerDay() {
//        if (dailyExpenses.isEmpty()) {
//            System.out.println("Nenhuma despesa!\n");
//        } else {
//            for (Map.Entry<LocalDate, List<Expense>> entry : dailyExpenses.entrySet()) {
//                LocalDate date = entry.getKey();
//                List<Expense> expenses = entry.getValue();
//
//                double total = expenses.stream().mapToDouble(Expense::getAmount).sum();
//
//                System.out.println("Data: " + date);
//                System.out.println("Total de Despesas para " + date + ": R$" + total + "\n");
//            }
//        }
//    }
//
//    public void viewSumOfExpenses(){
//        if (dailyExpenses.isEmpty()) System.out.println("Nenhuma despesa!\n");
//        double sum = 0.0;
//        for (Map.Entry<LocalDate, List<Expense>> entry: dailyExpenses.entrySet()) {
//            List<Expense> expenses = entry.getValue();
//
//            sum += expenses.stream().mapToDouble(Expense::getAmount).sum();
//        }
//        System.out.println("Gasto total do mês: R$ " + sum);
//    }
//
//    public void viewOverallHighestExpense() {
//        Expense highestExpense = dailyExpenses.values().stream()
//                .flatMap(List::stream)
//                .max(Comparator.comparingDouble(Expense::getAmount))
//                .orElse(null);
//
//        if (highestExpense != null) {
//            System.out.println("Maior Despesa Global: " + highestExpense + "\n");
//        } else {
//            System.out.println("Nenhuma despesa registrada ainda.\n");
//        }
//    }
//
//    public void viewDailyAverages() {
//        if (dailyExpenses.isEmpty()) {
//            System.out.println("Nenhuma despesa!\n");
//        } else {
//            for (Map.Entry<LocalDate, List<Expense>> entry : dailyExpenses.entrySet()) {
//                LocalDate date = entry.getKey();
//                List<Expense> expenses = entry.getValue();
//
//                double dailyAverage = expenses.stream().mapToDouble(Expense::getAmount).average().orElse(0.0);
//
//                System.out.println("Data: " + date);
//                System.out.println("Média Diária para " + date + ": R$" + dailyAverage + "\n");
//            }
//        }
//    }
}
