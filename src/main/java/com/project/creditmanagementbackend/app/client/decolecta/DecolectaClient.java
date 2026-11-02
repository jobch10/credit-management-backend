package com.project.creditmanagementbackend.app.client.decolecta;

import com.project.creditmanagementbackend.app.client.decolecta.dto.ReniecResponse;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.GeneralErrors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
@RequiredArgsConstructor
public class DecolectaClient {

    private final RestClient decolectaRestClient;

    public ReniecResponse consultarDni(String dni) {
        try {
            return decolectaRestClient.get()
                    .uri(uri -> uri.path("/reniec/dni")
                            .queryParam("numero", dni)
                            .build())
                    .retrieve()
                    .body(ReniecResponse.class);
        } catch (RestClientResponseException ex) {
            // 404 → no existe; 401/403 → token inválido; 5xx → caída del proveedor
            throw new BusinessException(GeneralErrors.EXTERNAL_SERVICE_ERROR);
        } catch (Exception ex) {
            throw new BusinessException(GeneralErrors.EXTERNAL_SERVICE_ERROR);
        }
    }
}