package com.example.crud.product;


import com.example.crud.common.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
@Transactional
public class ProductService {
    private final ProductRepository repo;


    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }


    public List<ProductDTO> findAll() {
        return repo.findAll().stream().map(ProductMapper::toDTO).toList();
    }


    public ProductDTO findById(Long id) {
        return repo.findById(id)
                .map(ProductMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Product %d not found".formatted(id)));
    }



    public ProductDTO create(ProductDTO dto) {
        Product saved = repo.save(ProductMapper.toEntity(dto));
        return ProductMapper.toDTO(saved);
    }


    public ProductDTO update(Long id, ProductDTO dto) {
        Product p = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Product %d not found".formatted(id)));
        ProductMapper.update(p, dto);
        return ProductMapper.toDTO(p);
    }


    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Product %d not found".formatted(id));
        }
        repo.deleteById(id);
    }
}