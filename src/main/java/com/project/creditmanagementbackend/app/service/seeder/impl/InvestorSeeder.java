package com.project.creditmanagementbackend.app.service.seeder.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.creditmanagementbackend.app.dao.TbEntityDao;
import com.project.creditmanagementbackend.app.dao.TbInvestorDao;
import com.project.creditmanagementbackend.app.dao.TbTypeDocumentDao;
import com.project.creditmanagementbackend.app.service.seeder.DataSeeder;
import com.project.creditmanagementbackend.domain.dto.TbEntityDto;
import com.project.creditmanagementbackend.domain.dto.TbInvestorDto;
import com.project.creditmanagementbackend.domain.entity.TbEntity;
import com.project.creditmanagementbackend.domain.entity.TbInvestor;
import com.project.creditmanagementbackend.domain.entity.TbTypeDocument;
import com.project.creditmanagementbackend.domain.mapper.TbEntityMapper;
import com.project.creditmanagementbackend.domain.mapper.TbInvestorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvestorSeeder implements DataSeeder {

    private final TbInvestorDao investorDao;
    private final TbEntityDao entityDao;
    private final TbTypeDocumentDao typeDocumentDao;
    private final TbInvestorMapper investorMapper;
    private final TbEntityMapper entityMapper;
    private final ObjectMapper objectMapper;

    @Override
    public String getName() {
        return "investors";
    }

    @Override
    public int getOrder() {
        return 4;
    }

    @Override
    public void seed() {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:seeds/investors/*.json");

            for (Resource resource : resources) {
                log.info("Seeding investor from: {}", resource.getFilename());
                Map<String, Object> data = objectMapper.readValue(resource.getInputStream(), Map.class);
                
                String documentNumber = (String) data.get("documentNumber");
                
                if (entityDao.findByDocumentNumber(documentNumber).isPresent()) {
                    log.info("Investor with document {} already exists, skipping.", documentNumber);
                    continue;
                }

                TbTypeDocument tipoDoc = typeDocumentDao.findByCode((String) data.get("documentTypeCode"))
                        .orElseThrow(() -> new RuntimeException("Document type not found: " + data.get("documentTypeCode")));

                TbEntityDto entityDto = objectMapper.convertValue(data, TbEntityDto.class);
                TbInvestorDto investorDto = objectMapper.convertValue(data, TbInvestorDto.class);

                TbEntity entity = entityMapper.toEntity(entityDto);
                entity.setTypeDocument(tipoDoc);
                entity.setState(true);

                entity = entityDao.save(entity);

                TbInvestor investor = investorMapper.toEntity(investorDto);
                investor.setEntity(entity);
                investor.setStatus(true);

                investorDao.save(investor);
            }
        } catch (Exception e) {
            log.error("Error seeding investors: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to seed investors", e);
        }
    }
}
