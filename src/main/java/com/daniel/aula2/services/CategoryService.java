package com.daniel.aula2.services;

import com.daniel.aula2.dto.CategoryDTO;
import com.daniel.aula2.entities.Category;
import com.daniel.aula2.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){

        List<Category> result = categoryRepository.findAll();

        return result.stream().map(CategoryDTO::new).toList();

    }

}
