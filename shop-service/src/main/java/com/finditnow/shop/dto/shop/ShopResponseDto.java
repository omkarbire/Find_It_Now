package com.finditnow.shop.dto.shop;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ShopResponseDto {

    private Long id;
    private String ownerUsername;

    private String name;
    private String description;
    private String phoneNumber;
    private String email;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String pincode;

    private Double latitude;
    private Double longitude;

    private String shopType;
    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
