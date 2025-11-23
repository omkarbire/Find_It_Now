package com.finditnow.shop.controller;

import com.finditnow.shop.dto.inventory.InventoryItemRequestDto;
import com.finditnow.shop.dto.inventory.InventoryItemResponseDto;
import com.finditnow.shop.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shops/{shopId}/items")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // Add item to shop (owner)
    @PostMapping
    public ResponseEntity<InventoryItemResponseDto> addItem(
            @PathVariable("shopId") Long shopId,
            @RequestHeader("X-User-Name") String ownerUsername,   // 👈 match gateway header
            @Valid @RequestBody InventoryItemRequestDto requestDto
    ) {
        InventoryItemResponseDto created =
                inventoryService.addItem(shopId, ownerUsername, requestDto);
        return ResponseEntity.ok(created);
    }

    // Get all items for a shop (public)
    @GetMapping
    public ResponseEntity<List<InventoryItemResponseDto>> getItems(
            @PathVariable("shopId") Long shopId,
            @RequestParam(name = "onlyAvailable", required = false, defaultValue = "false")
            boolean onlyAvailable
    ) {
        List<InventoryItemResponseDto> items =
                inventoryService.getItemsByShop(shopId, onlyAvailable);

        return ResponseEntity.ok(items);
    }

    // Update item details (owner)
    @PutMapping("/{itemId}")
    public ResponseEntity<InventoryItemResponseDto> updateItem(
            @PathVariable("shopId") Long shopId,
            @PathVariable("itemId") Long itemId,
            @RequestHeader("X-User-Name") String ownerUsername,   // 👈 same header name
            @Valid @RequestBody InventoryItemRequestDto requestDto
    ) {
        InventoryItemResponseDto updated =
                inventoryService.updateItem(shopId, itemId, ownerUsername, requestDto);
        return ResponseEntity.ok(updated);
    }

    // Update stock quantity (owner)
    @PatchMapping("/{itemId}/stock")
    public ResponseEntity<InventoryItemResponseDto> updateStock(
            @PathVariable("shopId") Long shopId,
            @PathVariable("itemId") Long itemId,
            @RequestHeader("X-User-Name") String ownerUsername,
            @RequestParam("newStock") Integer newStock
    ) {
        InventoryItemResponseDto updated =
                inventoryService.updateStock(shopId, itemId, ownerUsername, newStock);
        return ResponseEntity.ok(updated);
    }
}
