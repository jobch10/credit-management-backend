package com.project.creditmanagementbackend.domain.mapper;

import com.project.creditmanagementbackend.domain.dto.TbTypeDocumentDto;
import com.project.creditmanagementbackend.domain.entity.TbTypeDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TbTypeDocumentMapper extends BaseMapper<TbTypeDocumentDto, TbTypeDocument> {
}
