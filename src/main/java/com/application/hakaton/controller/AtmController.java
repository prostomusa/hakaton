package com.application.hakaton.controller;

import com.application.hakaton.model.atm.*;
import com.application.hakaton.service.AtmService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/atm")
@RequiredArgsConstructor
@Tag(name = "Ручки для банкоматов")
public class AtmController {

    private final AtmService atmService;

    @GetMapping("/list")
    public ResponseEntity<AtmLightListResponse> getBranchListResponse() {
        return ResponseEntity.ok(atmService.getListOfAtm());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtmFullResponse> getBranchById(@PathVariable Long id) {
        return ResponseEntity.ok(atmService.getAtmFullResponse(id));
    }

    @PostMapping("/listRequest")
    public ResponseEntity<AtmFullListResponse> getBranchListByRequestResponse(
            @RequestBody AtmRequest atmRequest
    ) {
        return ResponseEntity.ok(atmService.getAtmListByRequest(atmRequest));
    }

    @PostMapping("/updatedCurrentLoad")
    public ResponseEntity<AtmUpdateListLoadResponse> getBranchListByRequestResponse(
            @RequestBody AtmUpdateListLoadRequest atmUpdateListLoadRequest
    ) {
        return ResponseEntity.ok(atmService.getUpdatedLoadAtms(atmUpdateListLoadRequest));
    }
}
