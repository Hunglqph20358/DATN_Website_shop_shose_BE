package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.MauSacDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Color;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {})
public interface MauSacMapper extends EntityMapper<MauSacDTO, Color> {
}
