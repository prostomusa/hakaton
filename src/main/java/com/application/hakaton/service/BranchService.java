package com.application.hakaton.service;

import com.application.hakaton.entity.Branch;
import com.application.hakaton.entity.ClientTypeBranch;
import com.application.hakaton.model.*;
import com.application.hakaton.repository.BranchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchListResponse getListOfBranch() {
        List<Branch> branches = branchRepository.findAll();
        return new BranchListResponse(branches
                .stream()
                .map(this::mapBranchResponseByBranch)
                .collect(Collectors.toList()));
    }

    private BranchResponse mapBranchResponseByBranch(Branch branch) {
        List<ClientTypeBranch> clientTypeBranches = branch.getClientTypeBranches();
        clientTypeBranches.sort(
                Comparator.comparing(ClientTypeBranch::getClientType)

        );
        BranchResponse branchResponse = new BranchResponse();
        List<ClientTypeEnum> clientTypes = new ArrayList<>();
        List<OperatingModeResponse> operatingModeResponses = new ArrayList<>();
        clientTypeBranches.forEach(clientTypeBranch -> {
            clientTypes.add(clientTypeBranch.getClientType());
            List<WorkingDateTime> workingDateTimes = new ArrayList<>();

            clientTypeBranch.getOperatingModes().forEach(op ->
                    workingDateTimes.add(new WorkingDateTime(
                        op.getWorkingDay(),
                        op.getFrom(),
                        op.getTo())
                    ));
            operatingModeResponses.add(new OperatingModeResponse(
                    clientTypeBranch.getClientType(),
                    clientTypeBranch.getHolidays(),
                    workingDateTimes,
                    clientTypeBranch.getCurrentLoad()
            ));
        });
        branchResponse.setAddress(branch.getAddress());
        branchResponse.setId(branch.getId());
        branchResponse.setLongitude(branch.getLongitude());
        branchResponse.setLatitude(branch.getLatitude());
        branchResponse.setClientTypes(clientTypes);
        branchResponse.setOperatingModeResponse(operatingModeResponses);
        return branchResponse;
    }
}
