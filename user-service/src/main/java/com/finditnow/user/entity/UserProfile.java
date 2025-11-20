package com.finditnow.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to auth user (username from JWT)
    @Column(nullable = false, unique = true)
    @NotBlank
    private String username;

    @NotBlank
    @Column(nullable = false)
    private String fullName;

    @NotBlank
    @Column(name = "phone_number", nullable = false)  // 🔥 map to DB column
    private String phone;                             // 🔥 Java field = phone

    @Column
    private String addressLine1;

    @Column
    private String addressLine2;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String pincode;

    // For geo-based search later
    private Double latitude;
    private Double longitude;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
