package com.application.hakaton.controller;

import com.application.hakaton.model.BranchFullResponse;
import com.application.hakaton.model.BranchListFullResponse;
import com.application.hakaton.model.BranchListResponse;
import com.application.hakaton.model.BranchRequest;
import com.application.hakaton.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/ping")
    public ResponseEntity<String> pingPong() {
        return ResponseEntity.ok("pong");
    }

    @GetMapping("/list")
    public ResponseEntity<BranchListResponse> getBranchListResponse() {
        return ResponseEntity.ok(branchService.getListOfBranch());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchFullResponse> getBranchById(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.getBranchFullResponse(id));
    }

    @GetMapping("/listRequest")
    public ResponseEntity<BranchListFullResponse> getBranchListByRequestResponse(
            @RequestBody BranchRequest branchRequest
    ) {
        return ResponseEntity.ok(branchService.getBranchListByRequest(branchRequest));
    }
}
