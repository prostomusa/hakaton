package com.application.hakaton.controller;

import com.application.hakaton.model.atm.AtmFullListResponse;
import com.application.hakaton.model.atm.AtmFullResponse;
import com.application.hakaton.model.atm.AtmLightListResponse;
import com.application.hakaton.model.atm.AtmRequest;
import com.application.hakaton.service.AtmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/atm")
@RequiredArgsConstructor
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

    @GetMapping("/listRequest")
    public ResponseEntity<AtmFullListResponse> getBranchListByRequestResponse(
            @RequestBody AtmRequest atmRequest
    ) {
        return ResponseEntity.ok(atmService.getAtmListByRequest(atmRequest));
    }
}
