package uz.mk.companyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mk.companyservice.entity.Address;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    boolean existsByStreetAndHomeNumber(String street, String homeNumber);
    boolean existsByStreetAndHomeNumberAndIdNot(String street, String homeNumber, Integer id);
}
