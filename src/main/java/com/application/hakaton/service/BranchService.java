package com.application.hakaton.service;

import com.application.hakaton.entity.Branch;
import com.application.hakaton.entity.ClientTypeBranch;
import com.application.hakaton.entity.BranchOperatingMode;
import com.application.hakaton.exception.NotFoundException;
import com.application.hakaton.model.*;
import com.application.hakaton.model.enums.ClientTypeEnum;
import com.application.hakaton.repository.BranchRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
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

    public BranchFullResponse getBranchFullResponse(Long id) {
        Optional<Branch> branch = branchRepository.findById(id);
        if (branch.isEmpty()) {
            throw new NotFoundException("Branch with id %d is not exist".formatted(id));
        }
        return mapBranchFullResponseByBranch(branch.get());
    }

    public BranchListFullResponse getBranchListByRequest(BranchRequest branchRequest) {
        Specification<Branch> specification = (root, query, cb) -> {
            Predicate predicate = cb.and(
                    setBetween(root.get("longitude"),
                            cb,
                            branchRequest.getFirstCorner().getLongitude(),
                            branchRequest.getLastCorner().getLongitude()
                    ),
                    setBetween(root.get("latitude"),
                            cb,
                            branchRequest.getFirstCorner().getLatitude(),
                            branchRequest.getLastCorner().getLatitude()
                    ),
                    branchRequest.getMetroStation() != null
                            ? cb.like(root.get("metroStation"), '%' + branchRequest.getMetroStation() + '%')
                            : cb.and(),
                    branchRequest.getAddress() != null && branchRequest.getAddress().length() > 1
                            ? cb.like(root.get("address"), '%' + branchRequest.getAddress() + '%')
                            : cb.and(),
                    cb.equal(root.get("hasRamp"), branchRequest.isHasRamp())
            );
            Expression<String> exp = root.get("clientTypeBranches").get("clientType");
            Predicate predicateListTypeClient = branchRequest.getClientTypeEnums() == null
                    || branchRequest.getClientTypeEnums().isEmpty()
            ? cb.and() : exp.in(branchRequest.getClientTypeEnums());
            return cb.and(predicate, predicateListTypeClient);
        };

        return new BranchListFullResponse(branchRepository
                .findAll(specification).stream()
                .map(this::mapBranchFullResponseByBranch)
                .collect(Collectors.toList()));
    }

    private BranchResponse mapBranchResponseByBranch(Branch branch) {
        BranchResponse branchResponse = new BranchResponse();
        branchResponse.setAddress(branch.getAddress());
        branchResponse.setId(branch.getId());
        branchResponse.setLongitude(branch.getLongitude());
        branchResponse.setLatitude(branch.getLatitude());
        return branchResponse;
    }

    private BranchFullResponse mapBranchFullResponseByBranch(Branch branch) {
        List<ClientTypeBranch> clientTypeBranches = branch.getClientTypeBranches();
        clientTypeBranches.sort(
                Comparator.comparing(ClientTypeBranch::getClientType)
        );
        BranchFullResponse branchResponse = new BranchFullResponse();
        List<ClientTypeEnum> clientTypes = new ArrayList<>();
        List<OperatingModeResponse> operatingModeResponses = new ArrayList<>();
        clientTypeBranches.forEach(clientTypeBranch -> {
            clientTypes.add(clientTypeBranch.getClientType());
            List<WorkingDateTime> workingDateTimes = new ArrayList<>();

            List<BranchOperatingMode> sortBranchOperatingModeList = clientTypeBranch.getBranchOperatingModes();
            sortBranchOperatingModeList.sort(
                    Comparator.comparing(BranchOperatingMode::getWorkingDay)
            );
            sortBranchOperatingModeList.forEach(op ->
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
        branchResponse.setId(branch.getId());
        branchResponse.setAddress(branch.getAddress());
        branchResponse.setLatitude(branch.getLatitude());
        branchResponse.setLongitude(branch.getLongitude());
        branchResponse.setClientTypes(clientTypes);
        branchResponse.setOperatingModeResponse(operatingModeResponses);
        branchResponse.setHasRamp(branch.isHasRamp());
        branchResponse.setMetroStation(branch.getMetroStation());
        branchResponse.setBranchServices(Arrays.asList(branch.getServices()));
        return branchResponse;
    }

    private Predicate setBetween(Expression expression, CriteriaBuilder criteriaBuilder, Double val1, Double val2) {
        if (val1 > val2) {
            return criteriaBuilder.between(expression, val2, val1);
        }
        return criteriaBuilder.between(expression, val1, val2);
    }
}
