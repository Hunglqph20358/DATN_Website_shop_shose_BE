package com.example.backend.core.view.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.*;
import com.example.backend.core.view.dto.*;
import com.example.backend.core.view.mapper.*;
import com.example.backend.core.view.repository.*;
import com.example.backend.core.view.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductCustomRepository productCustomRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SoleRepository soleRepository;
    @Autowired
    private SoleMapper soleMapper;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private ImagesRepository imagesRepository;
    @Autowired
    private ImagesMapper imagesMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<ProductDTO> getProductNoiBatByBrand(Long thuongHieu) {
        return productCustomRepository.getProductNoiBatByBrand(thuongHieu);
    }

    @Override
    public ServiceResult<ProductDTO> getDetailProduct(Long idProduct) {
        Optional<Product> product = productRepository.findById(idProduct);
        ServiceResult<ProductDTO> result = new ServiceResult<>();
        Integer totalQuantity = 0;
        if (!product.isPresent()) {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Product không tồn tại !");
            result.setData(null);
            return result;
        }
        ProductDTO productDTO = productMapper.toDto(product.get());
        Optional<Brand> brand = brandRepository.findById(product.get().getIdBrand());
        Optional<Material> material = materialRepository.findById(product.get().getIdMaterial());
        Optional<Sole> sole = soleRepository.findById(product.get().getIdSole());
        Optional<Category> category = categoryRepository.findById(product.get().getIdCategory());
        List<Images> imageList = imagesRepository.findByIdProduct(product.get().getId());
        List<ProductDetail> listProductDetail = productDetailRepository.findByIdProduct(idProduct);
        MaterialDTO materialDTO = materialMapper.toDto(material.orElse(null));
        SoleDTO soleDTO = soleMapper.toDto(sole.get());
        CategoryDTO categoryDTO = categoryMapper.toDto(category.get());
        BrandDTO brandDTO = brandMapper.toDto(brand.orElse(null));
        for (ProductDetail pd: listProductDetail) {
            totalQuantity += pd.getQuantity();
        }
        productDTO.setProductDetailDTOList(productDetailMapper.toDto(listProductDetail));
        productDTO.setImagesDTOList(imagesMapper.toDto(imageList));
        productDTO.setBrandDTO(brandDTO);
        productDTO.setMaterialDTO(materialDTO);
        productDTO.setSoleDTO(soleDTO);
        productDTO.setCategoryDTO(categoryDTO);
        productDTO.setTotalQuantity(totalQuantity);
        productDTO.setListedPrice(listProductDetail.get(0).getListedPrice());
        productDTO.setPrice(listProductDetail.get(0).getPrice());
        result.setStatus(HttpStatus.OK);
        result.setMessage("Success");
        result.setData(productDTO);
        return result;
    }
}
