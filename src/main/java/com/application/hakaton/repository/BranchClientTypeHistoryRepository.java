package com.application.hakaton.repository;

import com.application.hakaton.entity.BranchClientTypeAvg;
import com.application.hakaton.entity.BranchClientTypeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchClientTypeHistoryRepository extends JpaRepository<BranchClientTypeHistory, Long> {
}
