package com.example.crud.product;


public class ProductMapper {
    public static ProductDTO toDTO(Product p) {
        return new ProductDTO(
                p.getId(), p.getName(), p.getPrice(), p.getStock(), p.getDescription()
        );
    }


    public static Product toEntity(ProductDTO dto) {
        Product p = new Product();
        p.setId(dto.id());
        p.setName(dto.name());
        p.setPrice(dto.price());
        p.setStock(dto.stock());
        p.setDescription(dto.description());
        return p;
    }


    public static void update(Product p, ProductDTO dto) {
        p.setName(dto.name());
        p.setPrice(dto.price());
        p.setStock(dto.stock());
        p.setDescription(dto.description());
    }
}