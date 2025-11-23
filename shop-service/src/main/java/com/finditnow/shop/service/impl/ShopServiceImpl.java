package com.finditnow.shop.service.impl;

import com.finditnow.shop.dto.shop.ShopRequestDto;
import com.finditnow.shop.dto.shop.ShopResponseDto;
import com.finditnow.shop.entity.Shop;
import com.finditnow.shop.exception.ResourceNotFoundException;
import com.finditnow.shop.repository.ShopRepository;
import com.finditnow.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    @Override
    public ShopResponseDto createShop(String ownerUsername, ShopRequestDto requestDto) {
        Shop shop = new Shop();
        shop.setOwnerUsername(ownerUsername);
        shop.setName(requestDto.getName());
        shop.setDescription(requestDto.getDescription());
        shop.setPhoneNumber(requestDto.getPhoneNumber());
        shop.setEmail(requestDto.getEmail());
        shop.setAddressLine1(requestDto.getAddressLine1());
        shop.setAddressLine2(requestDto.getAddressLine2());
        shop.setCity(requestDto.getCity());
        shop.setState(requestDto.getState());
        shop.setPincode(requestDto.getPincode());
        shop.setLatitude(requestDto.getLatitude());
        shop.setLongitude(requestDto.getLongitude());
        shop.setShopType(requestDto.getShopType());
        shop.setIsActive(true); // always active on creation

        Shop saved = shopRepository.save(shop);
        return mapToResponse(saved);
    }

    @Override
    public List<ShopResponseDto> getMyShops(String ownerUsername) {
        return shopRepository.findByOwnerUsername(ownerUsername)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ShopResponseDto getShopById(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not found with id: " + shopId));
        return mapToResponse(shop);
    }

    @Override
    public ShopResponseDto updateShop(Long shopId, String ownerUsername, ShopRequestDto requestDto) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not found with id: " + shopId));

        // Only owner can update
        if (!shop.getOwnerUsername().equals(ownerUsername)) {
            throw new RuntimeException("You are not allowed to update this shop");
        }

        if (StringUtils.hasText(requestDto.getName())) {
            shop.setName(requestDto.getName());
        }
        if (requestDto.getDescription() != null) {
            shop.setDescription(requestDto.getDescription());
        }
        if (StringUtils.hasText(requestDto.getPhoneNumber())) {
            shop.setPhoneNumber(requestDto.getPhoneNumber());
        }
        if (requestDto.getEmail() != null) {
            shop.setEmail(requestDto.getEmail());
        }
        if (requestDto.getAddressLine1() != null) {
            shop.setAddressLine1(requestDto.getAddressLine1());
        }
        if (requestDto.getAddressLine2() != null) {
            shop.setAddressLine2(requestDto.getAddressLine2());
        }
        if (requestDto.getCity() != null) {
            shop.setCity(requestDto.getCity());
        }
        if (requestDto.getState() != null) {
            shop.setState(requestDto.getState());
        }
        if (requestDto.getPincode() != null) {
            shop.setPincode(requestDto.getPincode());
        }
        if (requestDto.getLatitude() != null) {
            shop.setLatitude(requestDto.getLatitude());
        }
        if (requestDto.getLongitude() != null) {
            shop.setLongitude(requestDto.getLongitude());
        }
        if (requestDto.getShopType() != null) {
            shop.setShopType(requestDto.getShopType());
        }
        if (requestDto.getIsActive() != null) {
            shop.setIsActive(requestDto.getIsActive());
        }

        Shop updated = shopRepository.save(shop);
        return mapToResponse(updated);
    }

    @Override
    public List<ShopResponseDto> searchShops(String city, String shopType, Boolean onlyActive) {
        List<Shop> shops;

        boolean active = onlyActive == null || onlyActive; // default true

        if (StringUtils.hasText(city) && StringUtils.hasText(shopType) && active) {
            shops = shopRepository.findByCityIgnoreCaseAndShopTypeIgnoreCaseAndIsActiveTrue(city, shopType);
        } else if (StringUtils.hasText(city) && active) {
            shops = shopRepository.findByCityIgnoreCaseAndIsActiveTrue(city);
        } else if (StringUtils.hasText(shopType) && active) {
            shops = shopRepository.findByShopTypeIgnoreCaseAndIsActiveTrue(shopType);
        } else if (active) {
            shops = shopRepository.findByIsActiveTrue();
        } else {
            shops = shopRepository.findAll();
        }

        return shops.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ShopResponseDto mapToResponse(Shop shop) {
        return ShopResponseDto.builder()
                .id(shop.getId())
                .ownerUsername(shop.getOwnerUsername())
                .name(shop.getName())
                .description(shop.getDescription())
                .phoneNumber(shop.getPhoneNumber())
                .email(shop.getEmail())
                .addressLine1(shop.getAddressLine1())
                .addressLine2(shop.getAddressLine2())
                .city(shop.getCity())
                .state(shop.getState())
                .pincode(shop.getPincode())
                .latitude(shop.getLatitude())
                .longitude(shop.getLongitude())
                .shopType(shop.getShopType())
                .isActive(shop.getIsActive())
                .createdAt(shop.getCreatedAt())
                .updatedAt(shop.getUpdatedAt())
                .build();
    }
}
