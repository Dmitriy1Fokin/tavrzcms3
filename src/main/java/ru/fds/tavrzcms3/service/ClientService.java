package ru.fds.tavrzcms3.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fds.tavrzcms3.domain.*;
import ru.fds.tavrzcms3.repository.*;
import ru.fds.tavrzcms3.specification.SpecificationBuilder;
import ru.fds.tavrzcms3.specification.SpecificationBuilderImpl;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ClientService {

    @Autowired
    RepositoryClient repositoryClient;
    @Autowired
    RepositoryClientLegalEntity repositoryClientLegalEntity;
    @Autowired
    RepositoryClientIndividual repositoryClientIndividual;
    @Autowired
    RepositoryPledgeAgreement repositoryPledgeAgreement;
    @Autowired
    RepositoryLoanAgreement repositoryLoanAgreement;
    @Autowired
    UploadUnloadService uploadUnloadService;
    @Autowired
    ClientManagerService clientManagerService;
    @Autowired
    EmployeeService employeeService;

    @Value("${excel_table.import.client.column.client_manager_id}")
    private int numColumnClientManager;
    @Value("${excel_table.import.client.column.employee_id}")
    private int numColumnEmployee;
    @Value("${excel_table.import.client.legal_entity.column.organizational_form}")
    private int numColumnOrganizationForm;
    @Value("${excel_table.import.client.legal_entity.column.name}")
    private int numColumnLegalEntityName;
    @Value("${excel_table.import.client.legal_entity.column.inn}")
    private int numColumnInn;




    public Client getClientByClientId(long clientId){
        return repositoryClient.findByClientId(clientId);
    }

    public List<Client> getClientByEmployee(Employee employee){
        return repositoryClient.findByEmployee(employee);
    }

    public int countOfCurrentPledgeAgreementsByPledgorId(long pledgorId){
        return repositoryPledgeAgreement.countAllByClientAndStatusPAEquals(repositoryClient.findByClientId(pledgorId), "открыт");
    }

    public List<PledgeAgreement> getCurrentPledgeAgreementsByPledgorId(long pledgorId){
        return repositoryPledgeAgreement.findByClientAndStatusPA(repositoryClient.findByClientId(pledgorId), "открыт");
    }

    public int countOfClosedPledgeAgreementsByPledgorId(long pledgorId){
        return repositoryPledgeAgreement.countAllByClientAndStatusPAEquals(repositoryClient.findByClientId(pledgorId), "закрыт");
    }

    public List<PledgeAgreement> getClosedPledgeAgreementsByPledgorId(long pledgorId){
        return repositoryPledgeAgreement.findByClientAndStatusPA(repositoryClient.findByClientId(pledgorId), "закрыт");
    }

    public int countOfCurrentLoanEgreementsByLoanerId(long loanerId){
        return repositoryLoanAgreement.countAllByClientAndStatusLAEquals(repositoryClient.findByClientId(loanerId), "открыт");
    }

    public List<LoanAgreement> getCurrentLoanAgreementsByLoanerId(long loanerId){
        return repositoryLoanAgreement.findByClientAndStatusLAEquals(repositoryClient.findByClientId(loanerId), "открыт");
    }

    public int countOfClosedLoanEgreementsByLoanerId(long loanerId){
        return repositoryLoanAgreement.countAllByClientAndStatusLAEquals(repositoryClient.findByClientId(loanerId), "закрыт");
    }

    public List<LoanAgreement> getClosedLoanAgreementsByLoanerId(long loanerId){
        return repositoryLoanAgreement.findByClientAndStatusLAEquals(repositoryClient.findByClientId(loanerId), "закрыт");
    }

    public Page<Client> getClientFromSearch(Map<String, String> searchParam){

        int currentPage = Integer.parseInt(searchParam.get("page"));
        int pageSize = Integer.parseInt(searchParam.get("size"));
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        SpecificationBuilder builder = new SpecificationBuilderImpl();

        if(searchParam.get("clientOption").equals("юл")){
            if(!searchParam.get("clientName").isEmpty())
                builder.with("name", ":", searchParam.get("clientName"), false);
            if(!searchParam.get("inn").isEmpty())
                builder.with("inn", ":", searchParam.get("inn"), false);

            Specification<ClientLegalEntity> specification = builder.build();

            return repositoryClientLegalEntity.findAll(specification, pageable);


        }else{
            if(!searchParam.get("clientName").isEmpty()){
                String[] words = searchParam.get("clientName").split("\\s");

                if(words.length == 1) {
                    builder.with("surname",":", words[0], false);
                }
                else if(words.length == 2){
                    builder.with("surname",":", words[0], false);
                    builder.with("name",":", words[1],false);
                }else if(words.length >= 3){
                    builder.with("surname",":", words[0], false);
                    builder.with("name",":", words[1],false);
                    builder.with("patronymic", ":", words[2],false);
                }
            }
            if(!searchParam.get("pasportNum").isEmpty())
                builder.with("pasportNum", ":", searchParam.get("pasportNum"),false);

            Specification<ClientIndividual> specification = builder.build();

            return repositoryClientIndividual.findAll(specification, pageable);
        }

    }

    @Transactional
    public Client updateInsertClient(Client client){
        if(client.getClass()==ClientLegalEntity.class)
            client = repositoryClientLegalEntity.save((ClientLegalEntity) client);
        else if(client.getClass()==ClientIndividual.class)
            client = repositoryClientIndividual.save((ClientIndividual) client);

        return client;
    }

    @Transactional
    public List<ClientLegalEntity> insertClientLegalEntityFromExcel(File file) throws IOException, InvalidFormatException {

        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook;
        Validator validator;
        Set<ConstraintViolation<ClientLegalEntity>> violations;
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();

        if(uploadUnloadService.getExtension(file.getName()).equalsIgnoreCase("xlsx")){
            workbook = new XSSFWorkbook(fileInputStream);
        }else if (uploadUnloadService.getExtension(file.getName()).equalsIgnoreCase("xls")){
            workbook = new HSSFWorkbook(fileInputStream);
        }else {
            throw new InvalidFormatException("Не верный формат файла");
        }

        Sheet sheet = workbook.getSheetAt(0);


        List<ClientLegalEntity> clientLegalEntityList = new ArrayList<>();

        for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i++){
            try {
                Row row = sheet.getRow(i);

                ClientLegalEntity clientLegalEntity = new ClientLegalEntity();

                if(row.getCell(numColumnClientManager)==null || row.getCell(numColumnClientManager).getCellType()==CellType.BLANK)
                    throw new InvalidFormatException("Отсутствует id клиентского менеджера в строке " + (i+1));
                else if(row.getCell(numColumnClientManager).getCellType()==CellType.NUMERIC) {
                    clientLegalEntity.
                            setClientManager(clientManagerService.
                                    getClientManager((long) row.getCell(numColumnClientManager).getNumericCellValue()).
                                    orElseThrow(() ->
                                            new InvalidFormatException("Клиентский менеджер с таким id(" +
                                                    row.getCell(numColumnClientManager).getNumericCellValue() +
                                                    ") отсутствует")));
                }
                else
                    throw new InvalidFormatException("Неверный формат/значение id клиентского менеджера в строке " + (i+1));

                if(row.getCell(numColumnEmployee)==null || row.getCell(numColumnEmployee).getCellType()==CellType.BLANK)
                    throw new InvalidFormatException("Отсутствует id ответственного сотрудника в строке " + (i+1));
                else if(row.getCell(numColumnEmployee).getCellType()==CellType.NUMERIC)
                    clientLegalEntity.setEmployee(employeeService.
                            getEmployee((long) row.getCell(numColumnEmployee).getNumericCellValue()).
                            orElseThrow(() ->
                                    new InvalidFormatException("Ответственный сотрудник с таким id(" +
                                            row.getCell(numColumnEmployee).getNumericCellValue() + ") отсутствует")));
                else
                    throw new InvalidFormatException("Неверный формат/значение id ответственного сотрудника в строке " + (i+1));

                if(row.getCell(numColumnOrganizationForm)==null || row.getCell(numColumnOrganizationForm).getCellType()==CellType.BLANK)
                    throw new InvalidFormatException("Отсутствует значение правовой формы организации в строке " + (i+1));
                else if(row.getCell(numColumnOrganizationForm).getCellType() == CellType.STRING)
                    clientLegalEntity.setOrganizationalForm(row.getCell(numColumnOrganizationForm).getRichStringCellValue().getString());
                else
                    throw new InvalidFormatException("Неверный формат/значение правовой формы организации в строке " + (i+1));

                if(row.getCell(numColumnLegalEntityName)==null || row.getCell(numColumnLegalEntityName).getCellType()==CellType.BLANK)
                    throw new InvalidFormatException("Отсутствует название организации  в строке " + (i+1));
                else if(row.getCell(numColumnLegalEntityName).getCellType() == CellType.STRING)
                    clientLegalEntity.setName(row.getCell(numColumnLegalEntityName).getRichStringCellValue().getString());
                else
                    throw new InvalidFormatException("Неверный формат/значение названия организации в строке " + (i+1));

                if(row.getCell(numColumnInn) == null || row.getCell(numColumnInn).getCellType() == CellType.BLANK){}
                else if(row.getCell(numColumnInn).getCellType() == CellType.NUMERIC)
                    clientLegalEntity.setInn(String.valueOf(row.getCell(numColumnInn).getNumericCellValue()));
                else if(row.getCell(numColumnInn).getCellType() == CellType.STRING)
                    clientLegalEntity.setInn(row.getCell(numColumnInn).getRichStringCellValue().getString());
                else
                    throw new InvalidFormatException("Неверный формат/значение ИНН в строке " + (i+1));


                violations = validator.validate(clientLegalEntity);
                if(violations.size() > 0){
                    String message = "Ошибка в строке " + (i+1) + ": ";
                    for (ConstraintViolation<ClientLegalEntity> c : violations)
                        message += c.getMessage() + "(" + c.getInvalidValue() + ") в поле: \"" + c.getPropertyPath() + "\". ";

                    throw new InvalidFormatException(message);
                }

                clientLegalEntityList.add(clientLegalEntity);

            }catch (IllegalStateException ise){
                throw new InvalidFormatException("Неверные данные в строке " + (i+1) + ".");
            }

        }

        clientLegalEntityList = repositoryClientLegalEntity.saveAll(clientLegalEntityList);

        return clientLegalEntityList;
    }

    public String getFullNameClient(long clientId){
        Client client = repositoryClient.findByClientId(clientId);


        if(client.getTypeOfClient().equals("фл")){
            ClientIndividual clientIndividual = repositoryClientIndividual.findByClient(client);

            return clientIndividual.getSurname() + " " + clientIndividual.getName() + " " + clientIndividual.getPatronymic();

        }else if(client.getTypeOfClient().equals("юл")){
            ClientLegalEntity clientLegalEntity = repositoryClientLegalEntity.findByClient(client);

            return clientLegalEntity.getOrganizationalForm() + " " + clientLegalEntity.getName();

        }else
            return null;
    }

}
