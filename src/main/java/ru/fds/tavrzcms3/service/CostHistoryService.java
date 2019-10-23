package ru.fds.tavrzcms3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fds.tavrzcms3.domain.CostHistory;
import ru.fds.tavrzcms3.domain.PledgeSubject;
import ru.fds.tavrzcms3.repository.RepositoryCostHistory;

import java.util.List;

@Service
public class CostHistoryService {

    @Autowired
    RepositoryCostHistory repositoryCostHistory;

    public List<CostHistory> getCostHistoryPledgeSubject(PledgeSubject pledgeSubject){
        Sort sortByDateConclusion = new Sort(Sort.Direction.DESC, "dateConclusion");
        return repositoryCostHistory.findByPledgeSubject(pledgeSubject, sortByDateConclusion);
    }
    @Transactional
    public CostHistory insertCostHistoryInPledgeSubject(PledgeSubject pledgeSubject, CostHistory costHistory){
        costHistory.setPledgeSubject(pledgeSubject);
        return repositoryCostHistory.save(costHistory);
    }
}
