package com.example.crud.product;


import jakarta.validation.constraints.*;


public record ProductDTO(
        Long id,
        @NotBlank String name,
        @PositiveOrZero double price,
        @PositiveOrZero int stock,
        @Size(max = 255) String description
) {}