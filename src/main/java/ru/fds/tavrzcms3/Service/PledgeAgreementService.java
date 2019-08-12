package ru.fds.tavrzcms3.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.fds.tavrzcms3.domain.*;
import ru.fds.tavrzcms3.repository.*;
import ru.fds.tavrzcms3.specification.PledgeAgreementSpecificationsBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PledgeAgreementService {

    @Autowired
    RepositoryPledgeAgreement repositoryPledgeAgreement;
    @Autowired
    RepositoryPledgeSubject repositoryPledgeSubject;
    @Autowired
    RepositoryLoanAgreement repositoryLoanAgreement;
    @Autowired
    RepositoryClient repositoryClient;
    @Autowired
    RepositoryClientLegalEntity repositoryClientLegalEntity;
    @Autowired
    RepositoryClientIndividual repositoryClientIndividual;
    @Autowired
    RepositoryEmployee repositoryEmployee;

    public synchronized double getRsDz(long pledgeAgreementId){
        PledgeAgreement pledgeAgreement = repositoryPledgeAgreement.getOne(pledgeAgreementId);
        List<PledgeSubject> pledgeSubjects = repositoryPledgeSubject.findByPledgeAgreements(pledgeAgreement);
        double totalSum = 0;
        for (PledgeSubject ps : pledgeSubjects)
            totalSum += ps.getRsDz();

        return  totalSum;
    }

    public synchronized double getRsZz(long pledgeAgreementId){
        PledgeAgreement pledgeAgreement = repositoryPledgeAgreement.getOne(pledgeAgreementId);
        List<PledgeSubject> pledgeSubjects = repositoryPledgeSubject.findByPledgeAgreements(pledgeAgreement);
        double totalSum = 0;
        for(PledgeSubject ps : pledgeSubjects)
            totalSum += ps.getRsZz();

        return totalSum;
    }

    public synchronized double getZsDz(long pledgeAgreementId){
        PledgeAgreement pledgeAgreement = repositoryPledgeAgreement.getOne(pledgeAgreementId);
        List<PledgeSubject> pledgeSubjects = repositoryPledgeSubject.findByPledgeAgreements(pledgeAgreement);
        double totalSum = 0;
        for(PledgeSubject ps : pledgeSubjects)
            totalSum += ps.getZsDz();

        return totalSum;
    }

    public synchronized double getZsZz(long pledgeAgreementId){
        PledgeAgreement pledgeAgreement = repositoryPledgeAgreement.getOne(pledgeAgreementId);
        List<PledgeSubject> pledgeSubjects = repositoryPledgeSubject.findByPledgeAgreements(pledgeAgreement);
        double totalSum = 0;
        for(PledgeSubject ps : pledgeSubjects)
            totalSum += ps.getZsZz();

        return totalSum;
    }

    public synchronized double getSs(long pledgeAgreementId){
        PledgeAgreement pledgeAgreement = repositoryPledgeAgreement.getOne(pledgeAgreementId);
        List<PledgeSubject> pledgeSubjects = repositoryPledgeSubject.findByPledgeAgreements(pledgeAgreement);
        double totalSum = 0;
        for(PledgeSubject ps : pledgeSubjects)
            totalSum += ps.getSs();

        return totalSum;
    }

    public synchronized Set<Date> getDatesOfConclusion(long pledgeAgreementId){
        PledgeAgreement pledgeAgreement = repositoryPledgeAgreement.getOne(pledgeAgreementId);
        List<PledgeSubject> pledgeSubjects = repositoryPledgeSubject.findByPledgeAgreements(pledgeAgreement);
        Set<Date> dates = new LinkedHashSet<>();
        for(PledgeSubject ps : pledgeSubjects)
            dates.add(ps.getDateConclusion());

        return  dates;
    }

    public synchronized Set<Date> getDatesOfMonitoring(long pledgeAgreementId){
        PledgeAgreement pledgeAgreement = repositoryPledgeAgreement.getOne(pledgeAgreementId);
        List<PledgeSubject> pledgeSubjects = repositoryPledgeSubject.findByPledgeAgreements(pledgeAgreement);
        Set<Date> dates = new LinkedHashSet<>();
        for(PledgeSubject ps : pledgeSubjects)
            dates.add(ps.getDateMonitoring());

        return  dates;
    }

    public synchronized Set<String> getResultsOfMonitoring(long pledgeAgreementId){
        PledgeAgreement pledgeAgreement = repositoryPledgeAgreement.getOne(pledgeAgreementId);
        List<PledgeSubject> pledgeSubjects = repositoryPledgeSubject.findByPledgeAgreements(pledgeAgreement);
        Set<String> results = new LinkedHashSet<>();
        for(PledgeSubject ps : pledgeSubjects)
            results.add(ps.getStatusMonitoring());

        return  results;
    }

    public synchronized PledgeAgreement getPledgeAgreement(long pledgeAgreementId){
        return repositoryPledgeAgreement.getOne(pledgeAgreementId);
    }

    public synchronized Set<String> getTypeOfCollateral(long pledgeAgreementId){
        PledgeAgreement pledgeAgreement = repositoryPledgeAgreement.getOne(pledgeAgreementId);
        List<PledgeSubject> pledgeSubjects = repositoryPledgeSubject.findByPledgeAgreements(pledgeAgreement);
        Set<String> typeOfCollateralSet = new LinkedHashSet<>();
        for(PledgeSubject ps : pledgeSubjects)
            typeOfCollateralSet.add(ps.getTypeOfCollateral());

        return  typeOfCollateralSet;
    }

    public synchronized int countOfCurrentLoanAgreementsForPladgeAgreement(long pledgeAgreementId){
        return repositoryLoanAgreement.countAllByPledgeAgreementsAndStatusLAEquals(repositoryPledgeAgreement.findByPledgeAgreementId(pledgeAgreementId), "открыт");
    }

    public synchronized List<LoanAgreement> getCurrentLoanAgreementsForPledgeAgreement(long pledgeAgreementId){
        return repositoryLoanAgreement.findByPledgeAgreementsAndStatusLAEquals(repositoryPledgeAgreement.findByPledgeAgreementId(pledgeAgreementId), "открыт");
    }

    public synchronized int countOfClosedLoanAgreementsForPledgeAgreement(long pledgeAgreementId){
        return repositoryLoanAgreement.countAllByPledgeAgreementsAndStatusLAEquals(repositoryPledgeAgreement.findByPledgeAgreementId(pledgeAgreementId), "закрыт");
    }

    public synchronized List<LoanAgreement> getClosedLoanAgreementsForPledgeAgreement(long pledgeAgreementId){
        return repositoryLoanAgreement.findByPledgeAgreementsAndStatusLAEquals(repositoryPledgeAgreement.findByPledgeAgreementId(pledgeAgreementId), "закрыт");
    }

    public synchronized List<PledgeAgreement> getCurrentPledgeAgreementsForPledgor(long pledgorId, String pervPosl){
        return repositoryPledgeAgreement.findByPledgorAndPervPoslEqualsAndStatusPAEquals(repositoryClient.getOne(pledgorId), pervPosl, "открыт");
    }

    public synchronized int countOfCurrentPledgeAgreementsForEmployee(long employeeId){
        Employee employee = repositoryEmployee.getOne(employeeId);
        List<Client> pledgors = repositoryClient.findByEmployee(employee);
        return repositoryPledgeAgreement.countAllByPledgorInAndStatusPAEquals(pledgors, "открыт");
    }

    public synchronized int countOfPervCurrentPledgeAgreementsForEmployee(long employeeId){
        Employee employee = repositoryEmployee.getOne(employeeId);
        List<Client> pledgors = repositoryClient.findByEmployee(employee);
        return repositoryPledgeAgreement.countAllByPledgorInAndPervPoslEqualsAndStatusPAEquals(pledgors, "перв", "открыт");
    }

    public synchronized List<PledgeAgreement> getPervCurrentPledgeAgreementsForEmployeee(long employeeId){
        Employee employee = repositoryEmployee.getOne(employeeId);
        List<Client> pledgors = repositoryClient.findByEmployee(employee);
        return  repositoryPledgeAgreement.findByPledgorInAndPervPoslEqualsAndStatusPAEquals(pledgors,  "перв", "открыт");
    }

    public synchronized int countOfPoslCurrentPledgeAgreementsForEmployee(long employeeId){
        Employee employee = repositoryEmployee.getOne(employeeId);
        List<Client> pledgors = repositoryClient.findByEmployee(employee);
        return repositoryPledgeAgreement.countAllByPledgorInAndPervPoslEqualsAndStatusPAEquals(pledgors, "посл", "открыт");
    }

    public synchronized int countOfLoanAgreementsForEmployee(long employeeId){
        Employee employee = repositoryEmployee.getOne(employeeId);
        List<Client> loaners = repositoryClient.findByEmployee(employee);
        return repositoryLoanAgreement.countAllByLoanerInAndStatusLAEquals(loaners, "открыт");
    }

    public synchronized int countOfMonitoringNotDone(long employeeId){
        List<PledgeAgreement> pledgeAgreementList = getPervCurrentPledgeAgreementsForEmployeee(employeeId);
        Calendar dateNow = new GregorianCalendar();
        Calendar firstDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR) - 1, dateNow.get(Calendar.MONTH), 1);
        Calendar secondDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR) - 1, dateNow.get(Calendar.MONTH), dateNow.getMaximum(Calendar.DAY_OF_MONTH));
        Date firstDate = new Date(firstDateCalendar.getTimeInMillis());
        Date secondDate = new Date(secondDateCalendar.getTimeInMillis());
        int countOfMonitoring = 0;
        for(PledgeAgreement pa : pledgeAgreementList){
            int count = repositoryPledgeSubject.countByPledgeAgreementsAndDateMonitoringBetween(pa, firstDate, secondDate);
            if(count > 0)
                countOfMonitoring += 1;
        }

        return countOfMonitoring;
    }

    public synchronized int countOfMonitoringIsDone(long employeeId){
        List<PledgeAgreement> pledgeAgreementList = getPervCurrentPledgeAgreementsForEmployeee(employeeId);
        Calendar dateNow = new GregorianCalendar();
        Calendar firstDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR), dateNow.get(Calendar.MONTH), 1);
        Calendar secondDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR), dateNow.get(Calendar.MONTH), dateNow.getMaximum(Calendar.DAY_OF_MONTH));
        Date firstDate = new Date(firstDateCalendar.getTimeInMillis());
        Date secondDate = new Date(secondDateCalendar.getTimeInMillis());
        int countOfMonitoring = 0;
        for(PledgeAgreement pa : pledgeAgreementList){
            int count = repositoryPledgeSubject.countByPledgeAgreementsAndDateMonitoringBetween(pa, firstDate, secondDate);
            if(count > 0)
                countOfMonitoring += 1;
        }

        return countOfMonitoring;
    }

    public synchronized int countOfMonitoringOverdue(long employeeId){
        List<PledgeAgreement> pledgeAgreementList = getPervCurrentPledgeAgreementsForEmployeee(employeeId);
        Calendar dateNow = new GregorianCalendar();
        Calendar beforeDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR) - 1, dateNow.get(Calendar.MONTH) - 1, dateNow.getMaximum(Calendar.DAY_OF_MONTH));
        Date beforeDate = new Date(beforeDateCalendar.getTimeInMillis());
        int countOfMonitoring = 0;
        for(PledgeAgreement pa : pledgeAgreementList){
            int count = repositoryPledgeSubject.countByPledgeAgreementsAndDateMonitoringBefore(pa, beforeDate);
            if(count > 0)
                countOfMonitoring += 1;
        }

        return countOfMonitoring;
    }

    public synchronized List<PledgeAgreement> getPledgeAgreementWithMonitoringNotDone(long employeeId){
        List<PledgeAgreement> pledgeAgreementList = getPervCurrentPledgeAgreementsForEmployeee(employeeId);
        Calendar dateNow = new GregorianCalendar();
        Calendar firstDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR) - 1, dateNow.get(Calendar.MONTH), 1);
        Calendar secondDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR) - 1, dateNow.get(Calendar.MONTH), dateNow.getMaximum(Calendar.DAY_OF_MONTH));
        Date firstDate = new Date(firstDateCalendar.getTimeInMillis());
        Date secondDate = new Date(secondDateCalendar.getTimeInMillis());
        List<PledgeAgreement> pledgeAgreementListWithMonitoringNotDone = new ArrayList<>();
        for (PledgeAgreement pa : pledgeAgreementList){
            int count = repositoryPledgeSubject.countByPledgeAgreementsAndDateMonitoringBetween(pa, firstDate, secondDate);
            if(count > 0)
                pledgeAgreementListWithMonitoringNotDone.add(pa);
        }

        return pledgeAgreementListWithMonitoringNotDone;
    }

    public synchronized List<PledgeAgreement> getPledgeAgreementWithMonitoringIsDone(long employeeId){
        List<PledgeAgreement> pledgeAgreementList = getPervCurrentPledgeAgreementsForEmployeee(employeeId);
        Calendar dateNow = new GregorianCalendar();
        Calendar firstDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR), dateNow.get(Calendar.MONTH), 1);
        Calendar secondDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR), dateNow.get(Calendar.MONTH), dateNow.getMaximum(Calendar.DAY_OF_MONTH));
        Date firstDate = new Date(firstDateCalendar.getTimeInMillis());
        Date secondDate = new Date(secondDateCalendar.getTimeInMillis());
        List<PledgeAgreement> pledgeAgreementListWithMonitoringIsDone = new ArrayList<>();
        for (PledgeAgreement pa : pledgeAgreementList){
            int count = repositoryPledgeSubject.countByPledgeAgreementsAndDateMonitoringBetween(pa, firstDate, secondDate);
            if(count > 0)
                pledgeAgreementListWithMonitoringIsDone.add(pa);
        }

        return pledgeAgreementListWithMonitoringIsDone;
    }

    public synchronized List<PledgeAgreement> getPledgeAgreementWithMonitoringOverDue(long employeeId){
        List<PledgeAgreement> pledgeAgreementList = getPervCurrentPledgeAgreementsForEmployeee(employeeId);
        Calendar dateNow = new GregorianCalendar();
        Calendar beforeDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR) - 1, dateNow.get(Calendar.MONTH) - 1, dateNow.getMaximum(Calendar.DAY_OF_MONTH));
        Date beforeDate = new Date(beforeDateCalendar.getTimeInMillis());
        List<PledgeAgreement> pledgeAgreementListWithMonitoringOverdue = new ArrayList<>();
        for (PledgeAgreement pa : pledgeAgreementList){
            int count = repositoryPledgeSubject.countByPledgeAgreementsAndDateMonitoringBefore(pa, beforeDate);
            if(count > 0)
                pledgeAgreementListWithMonitoringOverdue.add(pa);
        }

        return pledgeAgreementListWithMonitoringOverdue;
    }

    public synchronized int countOfConclusionNotDone(long employeeId){
        List<PledgeAgreement> pledgeAgreementList = getPervCurrentPledgeAgreementsForEmployeee(employeeId);
        Calendar dateNow = new GregorianCalendar();
        Calendar firstDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR) - 1, dateNow.get(Calendar.MONTH), 1);
        Calendar secondDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR) - 1, dateNow.get(Calendar.MONTH), dateNow.getMaximum(Calendar.DAY_OF_MONTH));
        Date firstDate = new Date(firstDateCalendar.getTimeInMillis());
        Date secondDate = new Date(secondDateCalendar.getTimeInMillis());
        int countOfConclusion = 0;
        for(PledgeAgreement pa : pledgeAgreementList){
            int count = repositoryPledgeSubject.countByPledgeAgreementsAndDateConclusionBetween(pa, firstDate, secondDate);
            if(count > 0)
                countOfConclusion += 1;
        }

        return countOfConclusion;
    }

    public synchronized int countOfConclusionIsDone(long employeeId){
        List<PledgeAgreement> pledgeAgreementList = getPervCurrentPledgeAgreementsForEmployeee(employeeId);
        Calendar dateNow = new GregorianCalendar();
        Calendar firstDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR), dateNow.get(Calendar.MONTH), 1);
        Calendar secondDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR), dateNow.get(Calendar.MONTH), dateNow.getMaximum(Calendar.DAY_OF_MONTH));
        Date firstDate = new Date(firstDateCalendar.getTimeInMillis());
        Date secondDate = new Date(secondDateCalendar.getTimeInMillis());
        int countOfConclusion = 0;
        for(PledgeAgreement pa : pledgeAgreementList){
            int count = repositoryPledgeSubject.countByPledgeAgreementsAndDateConclusionBetween(pa, firstDate, secondDate);
            if(count > 0)
                countOfConclusion += 1;
        }

        return countOfConclusion;
    }

    public synchronized int countOfConclusionOverdue(long employeeId){
        List<PledgeAgreement> pledgeAgreementList = getPervCurrentPledgeAgreementsForEmployeee(employeeId);
        Calendar dateNow = new GregorianCalendar();
        Calendar beforeDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR) - 1, dateNow.get(Calendar.MONTH) - 1, dateNow.getMaximum(Calendar.DAY_OF_MONTH));
        Date beforeDate = new Date(beforeDateCalendar.getTimeInMillis());
        int countOfConclusion = 0;
        for(PledgeAgreement pa : pledgeAgreementList){
            int count = repositoryPledgeSubject.countByPledgeAgreementsAndDateConclusionBefore(pa, beforeDate);
            if(count > 0)
                countOfConclusion += 1;
        }

        return countOfConclusion;
    }

    public synchronized List<PledgeAgreement> getPledgeAgreementWithConclusionNotDone(long employeeId){
        List<PledgeAgreement> pledgeAgreementList = getPervCurrentPledgeAgreementsForEmployeee(employeeId);
        Calendar dateNow = new GregorianCalendar();
        Calendar firstDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR) - 1, dateNow.get(Calendar.MONTH), 1);
        Calendar secondDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR) - 1, dateNow.get(Calendar.MONTH), dateNow.getMaximum(Calendar.DAY_OF_MONTH));
        Date firstDate = new Date(firstDateCalendar.getTimeInMillis());
        Date secondDate = new Date(secondDateCalendar.getTimeInMillis());
        List<PledgeAgreement> pledgeAgreementListWithConclusionNotDone = new ArrayList<>();
        for (PledgeAgreement pa : pledgeAgreementList){
            int count = repositoryPledgeSubject.countByPledgeAgreementsAndDateConclusionBetween(pa, firstDate, secondDate);
            if(count > 0)
                pledgeAgreementListWithConclusionNotDone.add(pa);
        }

        return pledgeAgreementListWithConclusionNotDone;
    }

    public synchronized List<PledgeAgreement> getPledgeAgreementWithConclusionIsDone(long employeeId){
        List<PledgeAgreement> pledgeAgreementList = getPervCurrentPledgeAgreementsForEmployeee(employeeId);
        Calendar dateNow = new GregorianCalendar();
        Calendar firstDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR), dateNow.get(Calendar.MONTH), 1);
        Calendar secondDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR), dateNow.get(Calendar.MONTH), dateNow.getMaximum(Calendar.DAY_OF_MONTH));
        Date firstDate = new Date(firstDateCalendar.getTimeInMillis());
        Date secondDate = new Date(secondDateCalendar.getTimeInMillis());
        List<PledgeAgreement> pledgeAgreementListWithConclusionIsDone = new ArrayList<>();
        for (PledgeAgreement pa : pledgeAgreementList){
            int count = repositoryPledgeSubject.countByPledgeAgreementsAndDateConclusionBetween(pa, firstDate, secondDate);
            if(count > 0)
                pledgeAgreementListWithConclusionIsDone.add(pa);
        }

        return pledgeAgreementListWithConclusionIsDone;
    }

    public synchronized List<PledgeAgreement> getPledgeAgreementWithConclusionOverDue(long employeeId){
        List<PledgeAgreement> pledgeAgreementList = getPervCurrentPledgeAgreementsForEmployeee(employeeId);
        Calendar dateNow = new GregorianCalendar();
        Calendar beforeDateCalendar = new GregorianCalendar(dateNow.get(Calendar.YEAR) - 1, dateNow.get(Calendar.MONTH) - 1, dateNow.getMaximum(Calendar.DAY_OF_MONTH));
        Date beforeDate = new Date(beforeDateCalendar.getTimeInMillis());
        List<PledgeAgreement> pledgeAgreementListWithConclusionOverdue = new ArrayList<>();
        for (PledgeAgreement pa : pledgeAgreementList){
            int count = repositoryPledgeSubject.countByPledgeAgreementsAndDateConclusionBefore(pa, beforeDate);
            if(count > 0)
                pledgeAgreementListWithConclusionOverdue.add(pa);
        }

        return pledgeAgreementListWithConclusionOverdue;
    }

    public synchronized List<PledgeAgreement> getPledgeAgreementFromSearch(Map<String, String> searchParam){
        PledgeAgreementSpecificationsBuilder builder = new PledgeAgreementSpecificationsBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");

        if(!searchParam.get("numPA").isEmpty())
            builder.with("numPA", ":", searchParam.get("numPA"), false);


        if(!searchParam.get("pledgor").isEmpty()) {
            if (searchParam.get("pledgorOption").equals("юл")) {
                List<ClientLegalEntity> pledgors = repositoryClientLegalEntity.findByNameContainingIgnoreCase(searchParam.get("pledgor"));
                if(pledgors.size() == 1) {
                    builder.with("pledgor", ":", pledgors.get(0), false);
                }
                else if(pledgors.size() > 1){
                    for(ClientLegalEntity cle : pledgors) {
                        builder.with("pledgor", ":", cle, true);
                    }
                }
            }
            else{
                String[] words = searchParam.get("pledgor").split("\\s");
                if(words.length == 1){
                    List<ClientIndividual> pledgors = repositoryClientIndividual.findBySurnameContainingIgnoreCase(words[0]);

                    if(pledgors.size() == 1)
                        builder.with("pledgor", ":", pledgors.get(0), false);
                    else if(pledgors.size() > 1){
                        for(ClientIndividual ci : pledgors) {
                            builder.with("pledgor", ":", ci, true);
                        }
                    }
                }
                else if(words.length == 2){
                    List<ClientIndividual> pledgors = repositoryClientIndividual.findBySurnameContainingIgnoreCaseAndNameContainingIgnoreCase(words[0], words[1]);

                    if(pledgors.size() == 1)
                        builder.with("pledgor", ":", pledgors.get(0), false);
                    else if(pledgors.size() > 1){
                        for(ClientIndividual ci : pledgors) {
                            builder.with("pledgor", ":", ci, true);
                        }
                    }
                }
            }
        }

        if(!searchParam.get("dateBeginPA").isEmpty()){
            try {
                Date date = simpleDateFormat.parse(searchParam.get("dateBeginPA"));
                builder.with("dateBeginPA", searchParam.get("dateBeginPAOption"), date, false);
            }catch (ParseException e){
                System.out.println("Не верный фортат dateBeginPA");
            }
        }

        if(!searchParam.get("dateEndPA").isEmpty()){
            try {
                Date date = simpleDateFormat.parse(searchParam.get("dateEndPA"));
                builder.with("dateEndPA", searchParam.get("dateEndPAOption"), date, false);
            }catch (ParseException e){
                System.out.println("Не верный фортат dateEndPA");
            }
        }

        if(!searchParam.get("pervPslOption").equals("all"))
            builder.with("pervPosl", ":", searchParam.get("pervPslOption"), false);

        builder.with("statusPA", ":", searchParam.get("statusPA"), false);


        Specification<PledgeAgreement> cpec = builder.build();

        return repositoryPledgeAgreement.findAll(cpec);

    }

}
