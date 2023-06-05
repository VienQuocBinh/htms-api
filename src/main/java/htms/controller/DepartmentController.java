package htms.controller;

import htms.model.Account;
import htms.model.Department;
import htms.model.Role;
import htms.repository.AccountRepository;
import htms.repository.DepartmentRepository;
import htms.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/department")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @GetMapping
    public ResponseEntity<Object> get() {
        return ResponseEntity.ok(departmentRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        departmentRepository.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<Object> create() {
        Role role = Role.builder().name("admin").build();
        roleRepository.save(role);
        List<Account> accounts = new ArrayList<>();

        Account account = Account.builder()
                .email("ag@gmail.com")
                .title("DD")
                .createdBy(UUID.randomUUID())
                .role(role)
                .build();

        accounts.add(accountRepository.save(account));
        account = Account.builder()
                .email("123213@gmail.com")
                .title("DDsdfef")
                .createdBy(UUID.randomUUID())
                .role(role)
                .build();
        accounts.add(accountRepository.save(account));

        Department department = Department.builder()
                .name("Department name")
                .accounts(accounts)
                .build();
        department = departmentRepository.save(department);

        for (Account account1 : accounts) {
            account1.setDepartment(department);
            accountRepository.save(account1);
        }

        return ResponseEntity.ok(Department.builder()
                .id(department.getId())
                .name(department.getName())
                .accounts(accounts)
                .build());
    }
}
