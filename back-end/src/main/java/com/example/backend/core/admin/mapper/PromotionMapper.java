package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.PromotionDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.PromotionDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface PromotionMapper extends EntityMapper<PromotionDTO, PromotionDetail> {

        @Mapping(source = "id", target = "id")
        PromotionDetail toEntity(PromotionDTO dto);


}
