package com.application.hakaton.repository;

import com.application.hakaton.entity.Atm;
import com.application.hakaton.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AtmRepository extends JpaRepository<Atm, Long>, JpaSpecificationExecutor<Atm> {

}
