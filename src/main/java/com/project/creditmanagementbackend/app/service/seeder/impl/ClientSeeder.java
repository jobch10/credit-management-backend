package com.project.creditmanagementbackend.app.service.seeder.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.creditmanagementbackend.app.dao.TbClientDao;
import com.project.creditmanagementbackend.app.dao.TbEntityDao;
import com.project.creditmanagementbackend.app.dao.TbTypeDocumentDao;
import com.project.creditmanagementbackend.app.service.seeder.DataSeeder;
import com.project.creditmanagementbackend.domain.dto.TbClientDto;
import com.project.creditmanagementbackend.domain.dto.TbEntityDto;
import com.project.creditmanagementbackend.domain.entity.TbClient;
import com.project.creditmanagementbackend.domain.entity.TbEntity;
import com.project.creditmanagementbackend.domain.entity.TbTypeDocument;
import com.project.creditmanagementbackend.domain.mapper.TbClientMapper;
import com.project.creditmanagementbackend.domain.mapper.TbEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientSeeder implements DataSeeder {

    private final TbClientDao clientDao;
    private final TbEntityDao entityDao;
    private final TbTypeDocumentDao typeDocumentDao;
    private final TbClientMapper clientMapper;
    private final TbEntityMapper entityMapper;
    private final ObjectMapper objectMapper;

    @Override
    public String getName() {
        return "clients";
    }

    @Override
    public int getOrder() {
        return 3;
    }

    @Override
    public void seed() {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:seeds/clients/*.json");

            for (Resource resource : resources) {
                log.info("Seeding client from: {}", resource.getFilename());
                Map<String, Object> data = objectMapper.readValue(resource.getInputStream(), Map.class);
                
                String documentNumber = (String) data.get("documentNumber");
                
                if (entityDao.findByDocumentNumber(documentNumber).isPresent()) {
                    log.info("Client with document {} already exists, skipping.", documentNumber);
                    continue;
                }

                TbTypeDocument tipoDoc = typeDocumentDao.findByCode((String) data.get("documentTypeCode"))
                        .orElseThrow(() -> new RuntimeException("Document type not found: " + data.get("documentTypeCode")));

                TbEntityDto entityDto = objectMapper.convertValue(data, TbEntityDto.class);
                TbClientDto clientDto = objectMapper.convertValue(data, TbClientDto.class);

                TbEntity entity = entityMapper.toEntity(entityDto);
                entity.setTypeDocument(tipoDoc);
                entity.setState(true);
                
                entity = entityDao.save(entity);

                TbClient client = clientMapper.toEntity(clientDto);
                client.setEntity(entity);
                client.setState(true);

                clientDao.save(client);
            }
        } catch (Exception e) {
            log.error("Error seeding clients: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to seed clients", e);
        }
    }
}
