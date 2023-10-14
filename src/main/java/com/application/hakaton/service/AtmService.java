package com.application.hakaton.service;

import com.application.hakaton.entity.*;
import com.application.hakaton.exception.NotFoundException;
import com.application.hakaton.model.*;
import com.application.hakaton.model.atm.*;
import com.application.hakaton.model.enums.AtmServiceActivityEnum;
import com.application.hakaton.model.enums.ClientTypeEnum;
import com.application.hakaton.repository.AtmRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AtmService {

    private final AtmRepository atmRepository;

    public AtmLightListResponse getListOfAtm() {
        List<Atm> branches = atmRepository.findAll();
        return new AtmLightListResponse(branches
                .stream()
                .map(this::mapAtmLightResponseByAtm)
                .collect(Collectors.toList()));
    }

    public AtmFullResponse getAtmFullResponse(Long id) {
        Optional<Atm> atm = atmRepository.findById(id);
        if (atm.isEmpty()) {
            throw new NotFoundException("Branch with id %d is not exist".formatted(id));
        }
        return mapAtmFullResponseByAtm(atm.get());
    }

    public AtmUpdateListLoadResponse getUpdatedLoadAtms(
            AtmUpdateListLoadRequest atmUpdateListLoadRequest
    ) {
        List<Long> idList =
                atmUpdateListLoadRequest.getIdList();
        Specification<Atm> specification = (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            idList.forEach(id -> predicateList.add(
                    cb.and(
                            cb.equal(root.get("id"), id)
                    )
            ));
            return cb.or(predicateList.toArray(Predicate[]::new));
        };
        return new AtmUpdateListLoadResponse(atmRepository
                .findAll(specification).stream()
                .map(this::mapAtmUpdateLoadResponse)
                .collect(Collectors.toList()));
    }

    public AtmFullListResponse getAtmListByRequest(AtmRequest atmRequest) {
        Specification<Atm> specification = (root, query, cb) -> {
            Predicate predicate = cb.and(
                    setBetween(root.get("longitude"),
                            cb,
                            atmRequest.getFirstCorner().getLongitude(),
                            atmRequest.getLastCorner().getLongitude()
                    ),
                    setBetween(root.get("latitude"),
                            cb,
                            atmRequest.getFirstCorner().getLatitude(),
                            atmRequest.getLastCorner().getLatitude()
                    ),
                    atmRequest.getAddress() != null && atmRequest.getAddress().length() > 1
                            ? cb.like(root.get("address"), '%' + atmRequest.getAddress() + '%')
                            : cb.and()
            );
            Predicate predicateServices = cb.and();
            if (atmRequest.getServices() != null && !atmRequest.getServices().isEmpty()) {
                Join<Atm, AtmServiceEntity> atmServices = root.join("atmServiceEntities");
                Expression<String> atmServiceExpr = atmServices.get("service");
                predicateServices = cb.and(
                        atmServiceExpr.in(atmRequest.getServices()),
                        cb.equal(atmServices.get("serviceCapability"), AtmServiceActivityEnum.SUPPORTED),
                        cb.equal(atmServices.get("serviceActivity"), AtmServiceActivityEnum.AVAILABLE)
                );
            }
            return cb.and(predicate, predicateServices);
        };

        return new AtmFullListResponse(atmRepository
                .findAll(specification).stream()
                .map(this::mapAtmFullResponseByAtm)
                .collect(Collectors.toList()));
    }

    private AtmLightResponse mapAtmLightResponseByAtm(Atm atm) {
        AtmLightResponse atmLightResponse = new AtmLightResponse();
        atmLightResponse.setAddress(atm.getAddress());
        atmLightResponse.setId(atm.getId());
        atmLightResponse.setLongitude(atm.getLongitude());
        atmLightResponse.setLatitude(atm.getLatitude());
        return atmLightResponse;
    }

    private AtmFullResponse mapAtmFullResponseByAtm(Atm atm) {
        List<AtmServicesResponse> services = new ArrayList<>();
        atm.getAtmServiceEntities().forEach(atmServiceEntity ->
                services.add(new AtmServicesResponse(
                        atmServiceEntity.getService(),
                        atmServiceEntity.getServiceCapability(),
                        atmServiceEntity.getServiceActivity()
                ))
        );
        AtmFullResponse atmFullResponse = new AtmFullResponse();
        atmFullResponse.setAddress(atm.getAddress());
        atmFullResponse.setId(atm.getId());
        atmFullResponse.setLongitude(atm.getLongitude());
        atmFullResponse.setLatitude(atm.getLatitude());
        atmFullResponse.setServices(services);
        return atmFullResponse;
    }

    private AtmUpdateLoadResponse mapAtmUpdateLoadResponse(Atm atm) {
        AtmUpdateLoadResponse atmUpdateLoadResponse = new AtmUpdateLoadResponse();
        atmUpdateLoadResponse.setId(atm.getId());
        atmUpdateLoadResponse.setCurrentLoad(atm.getCurrentLoad());
        return atmUpdateLoadResponse;
    }

    private Predicate setBetween(Expression expression, CriteriaBuilder criteriaBuilder, Double val1, Double val2) {
        if (val1 > val2) {
            return criteriaBuilder.between(expression, val2, val1);
        }
        return criteriaBuilder.between(expression, val1, val2);
    }
}
