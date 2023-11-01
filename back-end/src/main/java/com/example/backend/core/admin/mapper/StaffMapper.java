package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.StaffDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Staff;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface StaffMapper extends EntityMapper<StaffDTO, Staff> {
}
