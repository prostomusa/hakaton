package com.application.hakaton.repository;

import com.application.hakaton.entity.Branch;
import com.application.hakaton.entity.BranchClientTypeAvg;
import com.application.hakaton.model.enums.ClientTypeEnum;
import com.application.hakaton.model.enums.DayEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchClientTypeAvgRepository extends JpaRepository<BranchClientTypeAvg, Long> {

    BranchClientTypeAvg getByBranchIsAndClientTypeIsAndHourIsAndDayOfWeekIs(Branch branch, ClientTypeEnum clientType, int hour, DayEnum dayOfWeek);
}
