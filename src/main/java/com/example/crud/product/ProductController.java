package com.example.crud.product;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public List<ProductDTO> all() {
        return service.findAll();
    }


    @GetMapping("/{id}")
    public ProductDTO byId(@PathVariable Long id) {
        return service.findById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@RequestBody @Valid ProductDTO dto) {
        return service.create(dto);
    }


    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable Long id, @RequestBody @Valid ProductDTO dto) {
        return service.update(id, dto);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}