package ru.netology.travel.data.TablesSQL;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditRequestEntity {
    private int id;
    private String bank_id;
    private String created;
    private String status;
}
