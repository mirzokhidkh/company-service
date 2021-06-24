package uz.mk.companyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mk.companyservice.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);
}
