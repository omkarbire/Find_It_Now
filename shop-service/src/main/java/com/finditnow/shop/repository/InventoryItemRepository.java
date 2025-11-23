package com.finditnow.shop.repository;

import com.finditnow.shop.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    List<InventoryItem> findByShopId(Long shopId);

    List<InventoryItem> findByShopIdAndStatus(Long shopId, String status);

    Optional<InventoryItem> findByIdAndShopId(Long id, Long shopId);
}
