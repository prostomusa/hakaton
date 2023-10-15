package com.application.hakaton.controller;

import com.application.hakaton.model.*;
import com.application.hakaton.repository.BranchClientTypeAvgRepository;
import com.application.hakaton.repository.BranchRepository;
import com.application.hakaton.service.BranchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/branch")
@RequiredArgsConstructor
@Tag(name = "Ручки для отделений")
public class BranchController {

    private final BranchService branchService;

    private final BranchRepository branchRepository;

    private final BranchClientTypeAvgRepository branchClientTypeAvgRepository;

    private final Random random = new Random();

    @GetMapping("/list")
    public ResponseEntity<BranchListResponse> getBranchListResponse() {
        return ResponseEntity.ok(branchService.getListOfBranch());
    }

    /*@GetMapping("/loadAll")
    public void loadBranchClientTypeAvg() {
        List<Branch> branchList = branchRepository.findAll();
        List<BranchClientTypeAvg> branchClientTypeAvgs = new ArrayList<>();
        branchList.forEach(branch -> {
            List<ClientTypeBranch> clientTypeBranches =
                    branch.getClientTypeBranches();
            clientTypeBranches.forEach(clientTypeBranch -> {
                List<BranchOperatingMode> clientList = clientTypeBranch.getBranchOperatingModes();
                clientList.forEach(branchOperatingMode -> {

                    int avgClients = 3;
                    for (int i = branchOperatingMode.getFrom().getHour(); i < branchOperatingMode.getTo().getHour() + 1; i++) {
                        BranchClientTypeAvg branchClientTypeAvg = new BranchClientTypeAvg();
                        branchClientTypeAvg.setAvgClients(avgClients);
                        branchClientTypeAvg.setHour(i);
                        branchClientTypeAvg.setClientType(clientTypeBranch.getClientType());
                        branchClientTypeAvg.setBranch(branch);
                        branchClientTypeAvg.setDayOfWeek(branchOperatingMode.getWorkingDay());
                        branchClientTypeAvg.setAvgCustomerServiceTime(180);
                        int randomValue = random.ints(-2, 3)
                                .findFirst()
                                .getAsInt();
                        avgClients = avgClients + randomValue;
                        if (avgClients > 15) {
                            avgClients = 15;
                        } else if (avgClients < 2) {
                            avgClients = 2;
                        }
                        branchClientTypeAvgs.add(branchClientTypeAvg);
                    }
                });
            });
        });
        branchClientTypeAvgRepository.saveAll(branchClientTypeAvgs);
    }*/

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

    @PostMapping("/getOptimized")
    public ResponseEntity<BranchListFullResponse> getBranchListByRequestResponse(
            @RequestBody BranchOptimizedRequest branchOptimizedRequest
    ) {
        return ResponseEntity.ok(new BranchListFullResponse(branchService.getOptimized(branchOptimizedRequest)));
    }
}
