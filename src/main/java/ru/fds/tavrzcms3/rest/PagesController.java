package ru.fds.tavrzcms3.rest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fds.tavrzcms3.Service.*;
import ru.fds.tavrzcms3.domain.*;

import java.util.List;


@Controller
public class PagesController {

    private final EmployeeService employeeService;
    private final PledgeAgreementService pledgeAgreementService;
    private final PledgeSubjectService pledgeSubjectService;
    private final InsuranceService insuranceService;
    private final EncumbranceService encumbranceService;
    private final ClientService clientService;
    private final LoanAgreementService loanAgreementService;
    private final CostHistoryService costHistoryService;
    private final MonitoringService monitoringService;

    public PagesController(EmployeeService employeeService,
                           PledgeAgreementService pledgeAgreementService,
                           PledgeSubjectService pledgeSubjectService,
                           InsuranceService insuranceService,
                           EncumbranceService encumbranceService,
                           ClientService clientService,
                           LoanAgreementService loanAgreementService,
                           CostHistoryService costHistoryService,
                           MonitoringService monitoringService) {
        this.employeeService = employeeService;
        this.pledgeAgreementService = pledgeAgreementService;
        this.pledgeSubjectService = pledgeSubjectService;
        this.insuranceService = insuranceService;
        this.encumbranceService = encumbranceService;
        this.clientService = clientService;
        this.loanAgreementService = loanAgreementService;
        this.costHistoryService = costHistoryService;
        this.monitoringService = monitoringService;
    }

    @GetMapping("/")
    public String homePage(@AuthenticationPrincipal User user, Model model) {
        Employee employee = employeeService.getEmployeeByAppUser(user);
        model.addAttribute("employee", employee);

        int countOfPE = employeeService.getCountOfAllPledgeAgreements(user);
        model.addAttribute("countOfAllPledgeAgreement", countOfPE);

        int countOfPervPE = employeeService.getCountOfPervPledgeAgreements(user);
        model.addAttribute("countOfPervPledgeAgreements", countOfPervPE);

        int countOfPoslPE = employeeService.getCountOfPoslPledgeAgreements(user);
        model.addAttribute("countOfPoslPledgeAgreements", countOfPoslPE);

        int countOfLoanAgreements = employeeService.getCountOfLoanAgreements(user);
        model.addAttribute("countOfLoanAgreements", countOfLoanAgreements);

        return "home";
    }

    @GetMapping("/pledge_agreements")
    public String pledgeEgreementPage(@RequestParam("employeeId") long employeeId, @RequestParam("pervPosl") String pervPosl, Model model) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        model.addAttribute("employee", employee);
        model.addAttribute("pervPosl", pervPosl);
        return "pledge_agreements";
    }

    @GetMapping("/pledge_subjects")
    public String pledgeSubjectsPage(@RequestParam("pledgeAgreementId") long pledgeAgreementId, Model model){
        PledgeAgreement pledgeAgreement = pledgeAgreementService.getPledgeAgreementById(pledgeAgreementId);
        model.addAttribute("pledgeAgreement", pledgeAgreement);
        return "pledge_subjects";
    }

    @GetMapping("/insurances")
    public String insurancesPage(@RequestParam("pledgeSubjectId") long pledgeSubjectId, Model model){
        PledgeSubject pledgeSubject = pledgeSubjectService.getPledgeSubjectById(pledgeSubjectId);
        List<Insurance> insuranceList = insuranceService.getInsurancesByPledgeSubject(pledgeSubject);
        model.addAttribute("pledgeSubjectName", pledgeSubject.getName());
        model.addAttribute("insuranceList", insuranceList);
        return "insurances";
    }

    @GetMapping("/encumbrances")
    public  String encumbrancePage(@RequestParam("pledgeSubjectId") long pledgeSubjectId, Model model){
        PledgeSubject pledgeSubject = pledgeSubjectService.getPledgeSubjectById(pledgeSubjectId);
        List<Encumbrance> encumbranceList = encumbranceService.getEncumbranceByPledgeSubject(pledgeSubject);
        model.addAttribute("pledgeSubjectName", pledgeSubject.getName());
        model.addAttribute("encumbranceList", encumbranceList);
        return "encumbrances";
    }

    @GetMapping("/pledge_subject_detail")
    public String pledgeSubjectDetailPage(@RequestParam("pledgeSubjectId") long pledgeSubjectId, Model model){
        PledgeSubject pledgeSubject = pledgeSubjectService.getPledgeSubjectById(pledgeSubjectId);
        model.addAttribute("pledgeSubject", pledgeSubject);
        return "pledge_subject_detail";
    }

    @GetMapping("/pledgor")
    public String pledgorPage(@RequestParam("pledgorId") long pledgorId, Model model){
        Client pledgor = clientService.getClientByClientId(pledgorId);
        model.addAttribute("pledgor", pledgor);
        return  "pledgor";
    }

    @GetMapping("/pledge_agreement_detail")
    public String pledgeAgreementsDetailPage(@RequestParam("pledgeAgreementId") long pledgeAgreementId, Model model){
        PledgeAgreement pledgeAgreement = pledgeAgreementService.getPledgeAgreementById(pledgeAgreementId);
        model.addAttribute("pledgeAgreement", pledgeAgreement);
        return "pledge_agreement_detail";
    }

    @GetMapping("/loan_agreement_detail")
    public String loanEgreementDetailPage(@RequestParam("loanAgreementId") long loanAgreementId, Model model){
        LoanAgreement loanAgreement = loanAgreementService.getLoanAgreementById(loanAgreementId);
        model.addAttribute("loanAgreement", loanAgreement);
        return "loan_agreement_detail";
    }

    @GetMapping("/loaner")
    public String loanerPage(@RequestParam("loanerId") long loanerId, Model model){
        Client loaner = clientService.getClientByClientId(loanerId);
        model.addAttribute("loaner", loaner);
        return "loaner";
    }

    @GetMapping("cost_history")
    public String costHistoryPage(@RequestParam("pledgeSubjectId") long pledgeSubjectId, Model model){
        PledgeSubject pledgeSubject = pledgeSubjectService.getPledgeSubjectById(pledgeSubjectId);
        List<CostHistory> costHistoryList = costHistoryService.getCostHistoryByPledgeSubjectId(pledgeSubjectId);
        model.addAttribute("pledgeSubjectName", pledgeSubject.getName());
        model.addAttribute("costHistoryList", costHistoryList);
        return "cost_history";
    }

    @GetMapping("monitoring")
    public String monitoringPage(@RequestParam("pledgeSubjectId") long pledgeSubjectId, Model model){
        PledgeSubject pledgeSubject = pledgeSubjectService.getPledgeSubjectById(pledgeSubjectId);
        List<Monitoring> monitoringList = monitoringService.getMonitoringByPledgeSubjectId(pledgeSubjectId);
        model.addAttribute("pledgeSubjectName", pledgeSubject.getName());
        model.addAttribute("monitoringList", monitoringList);
        return "monitoring";
    }

    @GetMapping("/loan_agreements")
    public String loanAgreementsPage(@RequestParam("employeeId") long employeeId, Model model) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        model.addAttribute("employee", employee);
        return "loan_agreements";
    }




//    @GetMapping("/edit")
//    public String editPage(@RequestParam("id") long id, Model model) {
//        Employee employee = repositoryEmployee.findById(id).orElseThrow(NotFoundException::new);
//        model.addAttribute("employee", employee);
//        return "edit";
//    }
//
//    @PostMapping("/edit")
//    public String changeEmployee(@RequestParam("id") long id, @RequestParam("surname") String suname, @RequestParam("name") String name, Model model){
//        Employee emp = repositoryEmployee.findById(id).get();
//        emp.setSurname(suname);
//        emp.setName(name);
//        repositoryEmployee.save(emp);
//        System.out.println("Cool!!!!!!!!!!!!!!!!!!!!!!");
//        model.addAttribute("employee", emp);
//        return "edit";
//    }
//
//    @GetMapping("/user")
//    public String userPage() {
//        //myService.onlyUser();
//        return "user";
//    }
//
//    @GetMapping("/admin")
//    public String adminPage() {
//        //myService.onlyAdmin();
//        return "admin";
//    }
//
//    @GetMapping("/authenticated")
//    public String authenticatedPage() {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder
//                .getContext().getAuthentication().getPrincipal();
//        System.out.println(userDetails.getUsername());
//        return "authenticated";
//    }
//
//    @GetMapping("/success")
//    public String successPage() {
//        return "success";
//    }


}
