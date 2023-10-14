package com.application.hakaton.controller;

import com.application.hakaton.model.*;
import com.application.hakaton.service.BranchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/branch")
@RequiredArgsConstructor
@Tag(name = "Ручки для отделений")
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/list")
    public ResponseEntity<BranchListResponse> getBranchListResponse() {
        return ResponseEntity.ok(branchService.getListOfBranch());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchFullResponse> getBranchById(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.getBranchFullResponse(id));
    }

    @PostMapping("/listRequest")
    public ResponseEntity<BranchListFullResponse> getBranchListByRequestResponse(
            @RequestBody BranchRequest branchRequest
    ) {
        return ResponseEntity.ok(branchService.getBranchListByRequest(branchRequest));
    }

    @PostMapping("/updatedCurrentLoad")
    public ResponseEntity<BranchUpdateListLoadResponse> getBranchListByRequestResponse(
            @RequestBody BranchUpdateListLoadRequest branchUpdateListLoadRequest
    ) {
        return ResponseEntity.ok(branchService.getUpdatedLoadBranchs(branchUpdateListLoadRequest));
    }
}
