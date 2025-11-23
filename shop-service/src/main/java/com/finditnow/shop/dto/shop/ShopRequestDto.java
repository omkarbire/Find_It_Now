package com.finditnow.shop.dto.shop;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ShopRequestDto {

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @NotBlank
    private String phoneNumber;

    private String email;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String pincode;

    private Double latitude;
    private Double longitude;

    // e.g. MEDICAL, GROCERY, ELECTRONICS
    private String shopType;

    // Optional for update, ignored in create
    private Boolean isActive;
}
