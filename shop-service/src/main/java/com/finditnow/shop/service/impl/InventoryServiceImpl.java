package com.finditnow.shop.service.impl;

import com.finditnow.shop.dto.inventory.InventoryItemRequestDto;
import com.finditnow.shop.dto.inventory.InventoryItemResponseDto;
import com.finditnow.shop.entity.InventoryItem;
import com.finditnow.shop.entity.Shop;
import com.finditnow.shop.exception.ResourceNotFoundException;
import com.finditnow.shop.repository.InventoryItemRepository;
import com.finditnow.shop.repository.ShopRepository;
import com.finditnow.shop.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final ShopRepository shopRepository;
    private final InventoryItemRepository inventoryItemRepository;

    @Override
    public InventoryItemResponseDto addItem(Long shopId, String ownerUsername, InventoryItemRequestDto dto) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not found with id: " + shopId));

        if (!shop.getOwnerUsername().equals(ownerUsername)) {
            // simple runtime exception is fine for now; you can create a custom one later
            throw new RuntimeException("You cannot add items to this shop");
        }

        InventoryItem item = InventoryItem.builder()
                .shop(shop)
                .name(dto.getName())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity())
                .unit(dto.getUnit())
                .status("AVAILABLE")
                .build();

        inventoryItemRepository.save(item);

        return mapToResponse(item);
    }

    @Override
    public List<InventoryItemResponseDto> getItemsByShop(Long shopId, boolean onlyAvailable) {
        List<InventoryItem> items;
        if (onlyAvailable) {
            items = inventoryItemRepository.findByShopIdAndStatus(shopId, "AVAILABLE");
        } else {
            items = inventoryItemRepository.findByShopId(shopId);
        }

        return items.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryItemResponseDto updateItem(Long shopId, Long itemId, String ownerUsername,
                                               InventoryItemRequestDto requestDto) {

        Shop shop = getOwnedShop(shopId, ownerUsername);

        InventoryItem item = inventoryItemRepository.findByIdAndShopId(itemId, shop.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item not found with id: " + itemId + " for shop id: " + shopId));

        if (requestDto.getName() != null) {
            item.setName(requestDto.getName());
        }
        if (requestDto.getDescription() != null) {
            item.setDescription(requestDto.getDescription());
        }
        if (requestDto.getCategory() != null) {
            item.setCategory(requestDto.getCategory());
        }
        if (requestDto.getPrice() != null) {
            item.setPrice(requestDto.getPrice());
        }
        if (requestDto.getStockQuantity() != null) {
            item.setStockQuantity(requestDto.getStockQuantity());
        }
        if (requestDto.getUnit() != null) {
            item.setUnit(requestDto.getUnit());
        }
        if (requestDto.getStatus() != null) {
            item.setStatus(requestDto.getStatus());
        }

        InventoryItem updated = inventoryItemRepository.save(item);
        return mapToResponse(updated);
    }

    @Override
    public InventoryItemResponseDto updateStock(Long shopId, Long itemId, String ownerUsername, Integer newStock) {
        Shop shop = getOwnedShop(shopId, ownerUsername);

        InventoryItem item = inventoryItemRepository.findByIdAndShopId(itemId, shop.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item not found with id: " + itemId + " for shop id: " + shopId));

        item.setStockQuantity(newStock);
        if (newStock != null && newStock == 0) {
            item.setStatus("OUT_OF_STOCK");
        } else if (newStock != null && newStock > 0 && !"AVAILABLE".equals(item.getStatus())) {
            item.setStatus("AVAILABLE");
        }

        InventoryItem updated = inventoryItemRepository.save(item);
        return mapToResponse(updated);
    }

    private Shop getOwnedShop(Long shopId, String ownerUsername) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not found with id: " + shopId));

        if (!shop.getOwnerUsername().equals(ownerUsername)) {
            throw new RuntimeException("You are not allowed to modify inventory for this shop");
        }
        return shop;
    }

    private InventoryItemResponseDto mapToResponse(InventoryItem item) {
        return InventoryItemResponseDto.builder()
                .id(item.getId())
                .shopId(item.getShop().getId())
                .name(item.getName())
                .description(item.getDescription())
                .category(item.getCategory())
                .price(item.getPrice())
                .stockQuantity(item.getStockQuantity())
                .unit(item.getUnit())
                .status(item.getStatus())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }
}
