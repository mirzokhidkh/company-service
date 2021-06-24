package uz.mk.companyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.mk.companyservice.entity.Company;
import uz.mk.companyservice.entity.Department;
import uz.mk.companyservice.payload.ApiResponse;
import uz.mk.companyservice.payload.DepartmentDto;
import uz.mk.companyservice.repository.CompanyRepository;
import uz.mk.companyservice.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;

    public ApiResponse add(DepartmentDto departmentDto) {
        boolean exists = departmentRepository.existsByNameAndCompanyId(departmentDto.getName(), departmentDto.getCompanyId());
        if (exists) {
            return new ApiResponse("An department with such a name and company id already exists", false);
        }
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (optionalCompany.isEmpty()) {
            return new ApiResponse("Company not found", false);
        }

        Department department = new Department();
        department.setName(departmentDto.getName());
        Company company = optionalCompany.get();
        department.setCompany(company);
        departmentRepository.save(department);
        return new ApiResponse("Department saved", true);
    }

    public List<Department> getAll() {
        List<Department> departmentList = departmentRepository.findAll();
        return departmentList;
    }

    public Department getById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isEmpty()) {
            return new Department();
        }
        return optionalDepartment.get();
    }

    public ApiResponse edit(Integer id, DepartmentDto departmentDto) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isEmpty()) {
            return new ApiResponse("Department not found", false);
        }

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (optionalCompany.isEmpty()) {
            return new ApiResponse("Company not found", false);
        }

        boolean exists = departmentRepository.existsByNameAndCompanyIdAndIdNot(departmentDto.getName(), departmentDto.getCompanyId(),id);
        if (exists) {
            return new ApiResponse("An department with such a name and company id already exists", false);
        }


        Department department = optionalDepartment.get();
        department.setName(departmentDto.getName());
        Company company = optionalCompany.get();
        department.setCompany(company);
        departmentRepository.save(department);

        departmentRepository.save(department);

        return new ApiResponse("Department edited", true);

    }


    public ApiResponse deleteById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isEmpty()) {
            return new ApiResponse("Department not found", false);
        }

        departmentRepository.deleteById(id);
        return new ApiResponse("Department deleted", true);
    }


}
