package ru.fds.tavrzcms3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.fds.tavrzcms3.domain.Client;
import ru.fds.tavrzcms3.domain.LoanAgreement;
import ru.fds.tavrzcms3.domain.PledgeAgreement;

import java.util.List;

public interface RepositoryPledgeAgreement extends JpaRepository<PledgeAgreement, Long>, JpaSpecificationExecutor<PledgeAgreement> {
    PledgeAgreement findByPledgeAgreementId(long pledgeAgreementId);
    List<PledgeAgreement> findByClient(Client client);
    int countAllByClientInAndStatusPAEquals(List<Client> clients, String statusPA);
    int countAllByClientInAndPervPoslEqualsAndStatusPAEquals(List<Client> clients, String perv, String statusPA);
    int countAllByClientAndStatusPAEquals(Client client, String statusPA);
    List<PledgeAgreement> findByClientAndStatusPA(Client client, String statusPA);
    List<PledgeAgreement> findByLoanAgreements(LoanAgreement loanAgreement);
    List<PledgeAgreement> findByLoanAgreementsAndStatusPAEquals(LoanAgreement loanAgreement, String statusPA);
    List<PledgeAgreement> findByClientInAndPervPoslEqualsAndStatusPAEquals(List<Client> clients, String pervPosl, String statusPA, Sort sort);
    Page<PledgeAgreement> findByClientInAndPervPoslEqualsAndStatusPAEquals(List<Client> clients, String pervPosl, String statusPA, Pageable pageable);
    List<PledgeAgreement> findByClientInAndStatusPAEquals(List<Client> clients, String statusPA, Sort sort);
    Page<PledgeAgreement> findByClientInAndStatusPAEquals(List<Client> clients, String statusPA, Pageable pageable);
    Page<PledgeAgreement> findAll(Specification specification, Pageable pageable);
    List<PledgeAgreement> findAllByNumPAContainingIgnoreCase(String numPA);
}
