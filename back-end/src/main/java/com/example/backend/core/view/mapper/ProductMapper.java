package com.example.backend.core.view.mapper;


import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Product;
import com.example.backend.core.view.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
}
