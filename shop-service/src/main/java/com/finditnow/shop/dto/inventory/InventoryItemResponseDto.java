package com.finditnow.shop.dto.inventory;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InventoryItemResponseDto {

    private Long id;
    private Long shopId;

    private String name;
    private String description;
    private String category;

    private Double price;
    private Integer stockQuantity;
    private String unit;
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
