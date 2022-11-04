package dev.yasint.toyland.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditProductRequestDTO {

    private Long productId;
    private AddProductRequestDTO productInfo;

}
