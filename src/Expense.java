import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Expense {
    private String description;
    private double amount;

    @Override
    public String toString() {
        return "Descrição: '" + description + '\'' +
                ", valor: R$ " + amount;
    }
}
