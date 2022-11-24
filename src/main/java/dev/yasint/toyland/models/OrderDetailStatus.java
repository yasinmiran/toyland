package dev.yasint.toyland.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.yasint.toyland.models.enumerations.EOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "fk_order_detail_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    private OrderDetail orderDetail;

    @Enumerated(EnumType.STRING)
    private EOrderStatus status;

    private LocalDateTime createdAt;
}
