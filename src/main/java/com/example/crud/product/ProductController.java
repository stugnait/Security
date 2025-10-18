package com.example.crud.product;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    public List<ProductDTO> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    public ProductDTO byId(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ProductDTO create(@RequestBody @Valid ProductDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ProductDTO update(@PathVariable Long id, @RequestBody @Valid ProductDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PatchMapping("/{id}/stock/{amount}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ProductDTO changeStock(@PathVariable Long id, @PathVariable int amount) {
        ProductDTO dto = service.findById(id);
        Product entity = ProductMapper.toEntity(dto);
        entity.setStock(amount);
        return service.update(id, ProductMapper.toDTO(entity));
    }

    @DeleteMapping("/clear")
    @PreAuthorize("hasRole('ADMIN')")
    public void clearAll() {
        service.findAll().forEach(p -> service.delete(p.id()));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    public List<ProductDTO> search(@RequestParam String name) {
        return service.findAll().stream()
                .filter(p -> p.name().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    @PatchMapping("/{id}/price/{price}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ProductDTO changePrice(@PathVariable Long id, @PathVariable double price) {
        ProductDTO dto = service.findById(id);
        Product entity = ProductMapper.toEntity(dto);
        entity.setPrice(price);
        return service.update(id, ProductMapper.toDTO(entity));
    }

    @PostMapping("/seed")
    @PreAuthorize("hasRole('ADMIN')")
    public void seedProducts() {
        service.create(new ProductDTO(null, "Demo Coffee", 10.5, 20, "Seeded"));
        service.create(new ProductDTO(null, "Demo Tea", 5.5, 50, "Seeded"));
    }
}
