package com.finditnow.shop.service;

import com.finditnow.shop.dto.inventory.InventoryItemRequestDto;
import com.finditnow.shop.dto.inventory.InventoryItemResponseDto;

import java.util.List;

public interface InventoryService {

    InventoryItemResponseDto addItem(Long shopId, String ownerUsername, InventoryItemRequestDto dto);

    List<InventoryItemResponseDto> getItemsByShop(Long shopId, boolean onlyAvailable);

    InventoryItemResponseDto updateItem(Long shopId, Long itemId, String ownerUsername,
                                        InventoryItemRequestDto requestDto);

    InventoryItemResponseDto updateStock(Long shopId, Long itemId, String ownerUsername, Integer newStock);
}
