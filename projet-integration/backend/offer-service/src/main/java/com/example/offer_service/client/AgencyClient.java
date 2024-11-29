package com.example.offer_service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.offer_service.dto.Agence;
@FeignClient(name = "agency-service" , url="http://localhost:8081")
public interface AgencyClient {
    @GetMapping("/api/agences/{id}")
    Agence getAgenceById(@PathVariable("id") Long id);
}

