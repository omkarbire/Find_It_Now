package com.finditnow.shop.controller;

import com.finditnow.shop.dto.shop.ShopRequestDto;
import com.finditnow.shop.dto.shop.ShopResponseDto;
import com.finditnow.shop.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    // Create new shop (shop owner)
    @PostMapping
    public ResponseEntity<ShopResponseDto> createShop(
            @RequestHeader("X-USER-NAME") String ownerUsername,
            @Valid @RequestBody ShopRequestDto requestDto) {

        ShopResponseDto created = shopService.createShop(ownerUsername, requestDto);
        return ResponseEntity.ok(created);
    }

    // Get shops owned by current user
    @GetMapping("/my")
    public ResponseEntity<List<ShopResponseDto>> getMyShops(
            @RequestHeader("X-USER-NAME") String ownerUsername) {

        List<ShopResponseDto> shops = shopService.getMyShops(ownerUsername);
        return ResponseEntity.ok(shops);
    }

    // Get shop by id (public)
    @GetMapping("/{shopId}")
    public ResponseEntity<ShopResponseDto> getShopById(@PathVariable Long shopId) {
        ShopResponseDto shop = shopService.getShopById(shopId);
        return ResponseEntity.ok(shop);
    }

    // Update shop (only owner)
    @PutMapping("/{shopId}")
    public ResponseEntity<ShopResponseDto> updateShop(
            @PathVariable Long shopId,
            @RequestHeader("X-USER-NAME") String ownerUsername,
            @Valid @RequestBody ShopRequestDto requestDto) {

        ShopResponseDto updated = shopService.updateShop(shopId, ownerUsername, requestDto);
        return ResponseEntity.ok(updated);
    }

    // Search shops (for customers)
    @GetMapping
    public ResponseEntity<List<ShopResponseDto>> searchShops(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String shopType,
            @RequestParam(required = false, defaultValue = "true") Boolean onlyActive) {

        List<ShopResponseDto> shops = shopService.searchShops(city, shopType, onlyActive);
        return ResponseEntity.ok(shops);
    }
}
