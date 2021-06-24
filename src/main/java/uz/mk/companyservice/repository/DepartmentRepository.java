package uz.mk.companyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mk.companyservice.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    boolean existsByNameAndCompanyId(String name, Integer company_id);
    boolean existsByNameAndCompanyIdAndIdNot(String name, Integer company_id, Integer id);
}
