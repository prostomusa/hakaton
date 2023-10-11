package com.application.hakaton.controller;

import com.application.hakaton.model.BranchListResponse;
import com.application.hakaton.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/ping")
    public ResponseEntity<String> pingPong() {
        return ResponseEntity.ok("pong");
    }

    @GetMapping("/branchList")
    public ResponseEntity<BranchListResponse> getBranchListResponse() {
        return ResponseEntity.ok(branchService.getListOfBranch());
    }
}
