package com.finditnow.shop.repository;

import com.finditnow.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    List<Shop> findByOwnerUsername(String ownerUsername);

    List<Shop> findByCityIgnoreCaseAndShopTypeIgnoreCaseAndIsActiveTrue(String city, String shopType);

    List<Shop> findByCityIgnoreCaseAndIsActiveTrue(String city);

    List<Shop> findByShopTypeIgnoreCaseAndIsActiveTrue(String shopType);

    List<Shop> findByIsActiveTrue();
}
