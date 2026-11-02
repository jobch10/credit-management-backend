package com.project.creditmanagementbackend.app.service.seeder.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.creditmanagementbackend.app.dao.TbTypeDocumentDao;
import com.project.creditmanagementbackend.app.service.seeder.DataSeeder;
import com.project.creditmanagementbackend.domain.dto.TbTypeDocumentDto;
import com.project.creditmanagementbackend.domain.entity.TbTypeDocument;
import com.project.creditmanagementbackend.domain.mapper.TbTypeDocumentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class TypeDocumentSeeder implements DataSeeder {

    private final TbTypeDocumentDao typeDocumentDao;
    private final TbTypeDocumentMapper typeDocumentMapper;
    private final ObjectMapper objectMapper;

    @Override
    public String getName() {
        return "type_document";
    }

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public void seed() {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:seeds/type_documents/*.json");

            for (Resource resource : resources) {
                log.info("Seeding document type from: {}", resource.getFilename());
                Map<String, Object> data = objectMapper.readValue(resource.getInputStream(), Map.class);
                
                String code = (String) data.get("code");
                
                if (typeDocumentDao.findByCode(code).isPresent()) {
                    log.info("Document type with code {} already exists, skipping.", code);
                    continue;
                }

                TbTypeDocumentDto dto = objectMapper.convertValue(data, TbTypeDocumentDto.class);
                TbTypeDocument entity = typeDocumentMapper.toEntity(dto);
                entity.setState(true);

                typeDocumentDao.save(entity);
            }
        } catch (Exception e) {
            log.error("Error seeding document types: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to seed document types", e);
        }
    }
}
