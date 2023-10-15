package com.application.hakaton.service;

import com.application.hakaton.entity.Branch;
import com.application.hakaton.entity.BranchClientTypeAvg;
import com.application.hakaton.entity.ClientTypeBranch;
import com.application.hakaton.entity.BranchOperatingMode;
import com.application.hakaton.exception.NotFoundException;
import com.application.hakaton.model.*;
import com.application.hakaton.model.enums.BranchServiceEnum;
import com.application.hakaton.model.enums.DayEnum;
import com.application.hakaton.repository.BranchClientTypeAvgRepository;
import com.application.hakaton.repository.BranchRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;

    private final BranchClientTypeAvgRepository branchClientTypeAvgRepository;

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

    public BranchUpdateListLoadResponse getUpdatedLoadBranchs(
            BranchUpdateListLoadRequest branchUpdateListLoadRequest
    ) {
        List<Long> idList =
                branchUpdateListLoadRequest.getIdList();
        Specification<Branch> specification = (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            idList.forEach(id -> predicateList.add(
                    cb.and(
                            cb.equal(root.get("id"), id)
                    )
            ));
            return cb.or(predicateList.toArray(Predicate[]::new));
        };
        return new BranchUpdateListLoadResponse(branchRepository
                .findAll(specification).stream()
                .map(this::mapBranchUpdateListLoadResponse)
                .collect(Collectors.toList()));
    }

    public List<BranchFullResponse> getOptimized(BranchOptimizedRequest request) {
        TreeMap<Double, BranchFullResponse> map = new TreeMap<>();
        LocalDateTime nowDateTime = LocalDateTime.now();
        DayEnum dayEnum = getDayEnum(nowDateTime);
        request.getBranchs().forEach(branchWithRoadTime -> {
            try {
                Branch branch = branchRepository.findById(branchWithRoadTime.getId()).get();
                ClientTypeBranch clientTypeBranch = branch.getClientTypeBranches()
                        .stream()
                        .filter(clientTypeBranch1 -> request.getClientType().equals(clientTypeBranch1.getClientType()))
                        .findFirst().get();
                BranchOperatingMode branchOperatingMode = clientTypeBranch.getBranchOperatingModes()
                        .stream()
                        .filter(branchOperatingMode1 -> dayEnum.equals(branchOperatingMode1.getWorkingDay()))
                        .findFirst().get();
                int currentLoad = clientTypeBranch.getCurrentLoad();
                int avgClientNowCount = branchClientTypeAvgRepository
                        .getByBranchIsAndClientTypeIsAndHourIsAndDayOfWeekIs(
                                branch, request.getClientType(), nowDateTime.getHour(), dayEnum).getAvgClients();
                BranchClientTypeAvg branchClientTypeAvg = branchClientTypeAvgRepository
                        .getByBranchIsAndClientTypeIsAndHourIsAndDayOfWeekIs(
                                branch, request.getClientType(),
                                nowDateTime.plusSeconds(branchWithRoadTime.getRoadTime()).getHour(), dayEnum);
                int avgClientCountToTimeRoad = branchClientTypeAvg.getAvgClients();
                int avgCustomerServiceTime = branchClientTypeAvg.getAvgCustomerServiceTime();
                Double formula = (((
                        (currentLoad / avgClientNowCount) * avgClientCountToTimeRoad)
                ) / clientTypeBranch.getActiveWindows()) * avgCustomerServiceTime
                        + Double.valueOf(branchWithRoadTime.getRoadTime()) + 180;
                LocalTime timeWithFormula = nowDateTime.plusSeconds(formula.intValue()).toLocalTime();
                if (branchOperatingMode.getFrom().isBefore(timeWithFormula) &&
                        branchOperatingMode.getTo().isAfter(timeWithFormula)
                ) {
                    map.put(formula, mapBranchFullResponseByBranch(branch));
                }
            } catch (Exception e) {
                System.out.println("Все плохо!");
            }

        });
        return new ArrayList<>(map.values());
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
                    branchRequest.isHasRamp() ? cb.equal(root.get("hasRamp"), branchRequest.isHasRamp()) : cb.and(),
                    branchRequest.isAllowPets() ? cb.equal(root.get("allowPets"), branchRequest.isAllowPets()) : cb.and(),
                    branchRequest.isHasChildZone() ? cb.equal(root.get("hasChildZone"), branchRequest.isHasChildZone()) : cb.and(),
                    branchRequest.isHasWifi() ? cb.equal(root.get("hasWifi"), branchRequest.isHasWifi()) : cb.and(),
                    branchRequest.isHasTicketOffice() ? cb.equal(root.get("hasTicketOffice"), branchRequest.isHasTicketOffice()) : cb.and(),
                    branchRequest.isHasPrime() ? cb.equal(root.get("hasPrime"), branchRequest.isHasPrime()) : cb.and(),
                    branchRequest.isHasPrivilege() ? cb.equal(root.get("hasPrivilege"), branchRequest.isHasPrivilege()) : cb.and(),
                    cb.equal(root.get("clientTypeBranches").get("clientType"), branchRequest.getClientType())
            );

            Expression<DayEnum> expWorkingDay = root.get("clientTypeBranches").get("branchOperatingModes").get("workingDay");
            Predicate predicateWorkingHolidays = branchRequest.isHolidayWorking()
                    ? cb.and() : expWorkingDay.in(List.of(DayEnum.SATURDAY, DayEnum.SUNDAY));

            return cb.and(
                    predicate,
                    predicateWorkingHolidays
            );
        };

        return new BranchListFullResponse(branchRepository
                .findAll(specification).stream()
                .filter(br -> {
                    if (!branchRequest.isWorkingNow()) {
                        return true;
                    }
                    LocalDateTime localDateTime = LocalDateTime.now();
                    DayEnum dayEnum = getDayEnum(localDateTime);
                    LocalTime getLocalTime = localDateTime.toLocalTime();
                    return br.getClientTypeBranches()
                            .stream()
                            .filter(clientTypeBranch -> clientTypeBranch.getClientType().equals(branchRequest.getClientType()))
                            .flatMap(clientTypeBranch -> clientTypeBranch.getBranchOperatingModes().stream())
                            .anyMatch(branchOperatingMode ->
                                    branchOperatingMode.getWorkingDay().equals(dayEnum) &&
                                            branchOperatingMode.getTo().isAfter(getLocalTime) &&
                                            branchOperatingMode.getFrom().isBefore(getLocalTime)
                            );
                })
                .filter(br -> {
                    if (branchRequest.getBranchServices() == null || branchRequest.getBranchServices().isEmpty()) {
                        return true;
                    }
                    List<String> services = new ArrayList<>();
                    br.getClientTypeBranches().forEach(tt -> {
                        if (branchRequest.getClientType().equals(tt.getClientType())) {
                            services.addAll(List.of(tt.getServices()));
                        }
                    });
                    return branchRequest.getBranchServices().stream()
                            .anyMatch(service -> services.contains(service.toString()));
                })
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

    private BranchUpdateLoadResponse mapBranchUpdateListLoadResponse(Branch branch) {
        BranchUpdateLoadResponse branchResponse = new BranchUpdateLoadResponse();
        branchResponse.setId(branch.getId());
        List<BranchCertainClient> clients = new ArrayList<>();
        branch.getClientTypeBranches().forEach(clientTypeBranch -> {
            BranchCertainClient branchCertainClient = new BranchCertainClient();
            branchCertainClient.setClientType(clientTypeBranch.getClientType());
            branchCertainClient.setCurrentLoad(clientTypeBranch.getCurrentLoad());
            clients.add(branchCertainClient);
        });
        branchResponse.setClients(clients);
        return branchResponse;
    }

    private BranchFullResponse mapBranchFullResponseByBranch(Branch branch) {
        List<ClientTypeBranch> clientTypeBranches = branch.getClientTypeBranches();
        clientTypeBranches.sort(
                Comparator.comparing(ClientTypeBranch::getClientType)
        );
        BranchFullResponse branchResponse = new BranchFullResponse();
        List<BranchClientService> branchClientServices = new ArrayList<>();
        List<OperatingModeResponse> operatingModeResponses = new ArrayList<>();
        clientTypeBranches.forEach(clientTypeBranch -> {
            branchClientServices.add(new BranchClientService(
                    clientTypeBranch.getClientType(),
                           Arrays.stream(clientTypeBranch.getServices())
                                   .map(BranchServiceEnum::valueOf)
                                   .collect(Collectors.toList())
                    )
            );
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
                    clientTypeBranch.getCurrentLoad(),
                    clientTypeBranch.getActiveWindows()
            ));
        });
        branchResponse.setId(branch.getId());
        branchResponse.setName(branch.getName());
        branchResponse.setAddress(branch.getAddress());
        branchResponse.setLatitude(branch.getLatitude());
        branchResponse.setLongitude(branch.getLongitude());
        branchResponse.setBranchClientServices(branchClientServices);
        branchResponse.setOperatingModeResponse(operatingModeResponses);
        branchResponse.setHasRamp(branch.isHasRamp());
        branchResponse.setHasPrivilege(branch.isHasPrivilege());
        branchResponse.setHasPrime(branch.isHasPrime());
        branchResponse.setAllowPets(branch.isAllowPets());
        branchResponse.setHasWifi(branch.isHasWifi());
        branchResponse.setHasChildZone(branch.isHasChildZone());
        branchResponse.setHasTicketOffice(branch.isHasTicketOffice());
        branchResponse.setMetroStation(branch.getMetroStation());
        return branchResponse;
    }

    private Predicate setBetween(Expression expression, CriteriaBuilder criteriaBuilder, Double val1, Double val2) {
        if (val1 > val2) {
            return criteriaBuilder.between(expression, val2, val1);
        }
        return criteriaBuilder.between(expression, val1, val2);
    }

    private DayEnum getDayEnum(LocalDateTime localDateTime) {
        if (localDateTime.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            return DayEnum.MONDAY;
        }
        if (localDateTime.getDayOfWeek().equals(DayOfWeek.THURSDAY)) {
            return DayEnum.THURSDAY;
        }
        if (localDateTime.getDayOfWeek().equals(DayOfWeek.WEDNESDAY)) {
            return DayEnum.WEDNESDAY;
        }
        if (localDateTime.getDayOfWeek().equals(DayOfWeek.TUESDAY)) {
            return DayEnum.TUESDAY;
        }
        if (localDateTime.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
            return DayEnum.FRIDAY;
        }
        if (localDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            return DayEnum.SATURDAY;
        }
        return DayEnum.SUNDAY;
    }
}
