package ru.fds.tavrzcms3.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ru.fds.tavrzcms3.domain.AppUser;
import ru.fds.tavrzcms3.domain.Employee;
import ru.fds.tavrzcms3.repository.*;


@Service
public class EmployeeService {

    @Autowired
    RepositoryEmployee repositoryEmployee;

    @Autowired
    RepositoryAppUser repositoryAppUser;

    public Employee getEmployee(User user){
        AppUser appUser = repositoryAppUser.findByName(user.getUsername());
        return repositoryEmployee.findByAppUser(appUser);
    }

    public Employee getEmployee(long employeeId){
        return repositoryEmployee.getOne(employeeId);
    }
}
