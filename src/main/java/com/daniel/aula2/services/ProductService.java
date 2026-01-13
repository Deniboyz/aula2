package com.daniel.aula2.services;

import com.daniel.aula2.dto.ProductDTO;
import com.daniel.aula2.entities.Product;
import com.daniel.aula2.repositories.ProductRepository;
import com.daniel.aula2.services.exceptions.DatabaseException;
import com.daniel.aula2.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO findByid(Long id){

        Optional<Product> result = productRepository.findById(id);

        Product product = result.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));

        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(String name, Pageable pageable){

        Page<Product> result = productRepository.searchByName(name, pageable);

        return result.map(ProductDTO::new);

    }

    @Transactional
    public ProductDTO insert(ProductDTO productDTO){

        Product entity = new Product();

        copyDtoToEntity(productDTO, entity);

        entity = productRepository.save(entity);

        return new ProductDTO(entity);

    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO){

        try {

            Product entity = productRepository.getReferenceById(id);

            copyDtoToEntity(productDTO, entity);

            entity = productRepository.save(entity);

            return new ProductDTO(entity);

        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){

        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try{

            productRepository.deleteById(id);

        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Falha de integridade Referencial");
        }


    }

    private void copyDtoToEntity(ProductDTO productDTO, Product entity){
        entity.setName(productDTO.getName());
        entity.setdescription(productDTO.getDescription());
        entity.setPrice(productDTO.getPrice());
        entity.setImgUrl(productDTO.getImgUrl());
    }

}
