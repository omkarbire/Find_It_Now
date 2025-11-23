package com.finditnow.shop.dto.inventory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryItemRequestDto {

    @NotBlank
    private String name;

    private String description;

    private String category;  // TABLET, SYRUP, GROCERY, etc.

    @NotNull
    @Min(0)
    private Double price;

    @NotNull
    @Min(0)
    private Integer stockQuantity;

    private String unit;      // PIECE, BOX, STRIP, KG, etc.

    // Optional: default AVAILABLE
    private String status;
}
