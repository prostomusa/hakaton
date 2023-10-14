package com.application.hakaton.repository;

import com.application.hakaton.entity.Branch;
import com.application.hakaton.entity.ClientTypeBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientTypeBranchRepository extends JpaRepository<ClientTypeBranch, Long> {

}
