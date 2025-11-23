package com.finditnow.shop.service;

import com.finditnow.shop.dto.shop.ShopRequestDto;
import com.finditnow.shop.dto.shop.ShopResponseDto;

import java.util.List;

public interface ShopService {

    ShopResponseDto createShop(String ownerUsername, ShopRequestDto requestDto);

    List<ShopResponseDto> getMyShops(String ownerUsername);

    ShopResponseDto getShopById(Long shopId);

    ShopResponseDto updateShop(Long shopId, String ownerUsername, ShopRequestDto requestDto);

    List<ShopResponseDto> searchShops(String city, String shopType, Boolean onlyActive);
}
