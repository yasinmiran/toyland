package dev.yasint.toyland.models;

import dev.yasint.toyland.models.contracts.Completable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_details")
@Builder
public class Payment implements Completable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String number;
    private String cvv;
    private LocalDate expireDate;

    @Override
    public boolean isCompleted() {
        int year = LocalDate.now().getYear(), month = LocalDate.now().getMonthValue();
        return (name != null && !name.isBlank())
                && (number != null && !number.isBlank())
                && cvv != null && cvv.length() == 3
                && (expireDate != null
                && expireDate.getYear() >= year
                && expireDate.getMonthValue() >= month);
    }

}
