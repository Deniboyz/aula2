package com.daniel.aula2.dto;

import com.daniel.aula2.entities.Category;
import com.daniel.aula2.entities.Product;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {

    private Long id;
    @NotBlank(message = "Campo requerido")
    @Size(min = 3, max = 80, message = "Nome precisa ter de 3 a 80 caracteres")
    private String name;
    @NotBlank
    @Size(min = 10, message = "A descrição deve ter no minimo 10 caracteres")
    private String description;
    @Positive(message = "O preço deve ser positivo")
    @NotNull(message = "Campo requerido")
    private Double price;
    private String imgUrl;

    @NotEmpty(message = "Deve haver pelo menos uma categoria")
    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductDTO() {
    }

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();;
        this.description = entity.getdescription();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
        for (Category cat : entity.getCategories()){
            categories.add(new CategoryDTO(cat));
        }
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
