package com.application.hakaton.cron;

import com.application.hakaton.entity.*;
import com.application.hakaton.repository.AtmRepository;
import com.application.hakaton.repository.BranchClientTypeAvgRepository;
import com.application.hakaton.repository.BranchRepository;
import com.application.hakaton.repository.ClientTypeBranchRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class CrontTasks {

    private final Logger logger = LoggerFactory.getLogger(CrontTasks.class);

    private final BranchRepository branchRepository;
    private final AtmRepository atmRepository;

    private final ClientTypeBranchRepository clientTypeBranchRepository;

    private final BranchClientTypeAvgRepository branchClientTypeAvgRepository;

    private final Random random = new Random();

    @Scheduled(fixedDelay = 60000)
    public void updateLoadBranchs() {
        logger.info("Start Schedule Branchs");
        List<Branch> branchList = branchRepository.findAll();
        branchList.forEach(branch -> {
            List<ClientTypeBranch> clientTypeBranches =
                    branch.getClientTypeBranches();
            clientTypeBranches.forEach(clientTypeBranch -> {
                    int currentLoad = clientTypeBranch.getCurrentLoad();
                    int randomValue = random.ints(-1, 2)
                            .findFirst()
                            .getAsInt();
                    currentLoad = currentLoad + randomValue;
                    if (currentLoad > 15) {
                       currentLoad = 15;
                    } else if (currentLoad < 1) {
                        currentLoad = 1;
                    }
                    clientTypeBranch.setCurrentLoad(currentLoad);
            });
            clientTypeBranchRepository.saveAll(clientTypeBranches);
        });
        logger.info("End Schedule Branchs");
    }

    @Scheduled(fixedDelay = 60000)
    public void updateLoadAtms() {
        logger.info("Start Schedule Atm");
        List<Atm> atmList = atmRepository.findAll();
        atmList.forEach(atm -> {
            int currentLoad = atm.getCurrentLoad();
            int randomValue = random.ints(-1, 2)
                    .findFirst()
                    .getAsInt();
            currentLoad = currentLoad + randomValue;
            if (currentLoad > 15) {
                currentLoad = 15;
            } else if (currentLoad < 1) {
                currentLoad = 1;
            }
            atm.setCurrentLoad(currentLoad);
        });
        atmRepository.saveAll(atmList);
        logger.info("Stop Schedule Atm");
    }

}
