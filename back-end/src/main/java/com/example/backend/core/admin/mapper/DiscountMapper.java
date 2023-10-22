package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.DiscountDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.DiscountDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface DiscountMapper extends EntityMapper<DiscountDTO, DiscountDetail> {

}
