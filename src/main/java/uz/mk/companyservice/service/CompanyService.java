package uz.mk.companyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.mk.companyservice.entity.Address;
import uz.mk.companyservice.entity.Company;
import uz.mk.companyservice.payload.ApiResponse;
import uz.mk.companyservice.payload.CompanyDto;
import uz.mk.companyservice.repository.AddressRepository;
import uz.mk.companyservice.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressService addressService;

    public ApiResponse add(CompanyDto companyDto) {
        boolean exists = companyRepository.existsByName(companyDto.getCompName());
        if (exists) {
            return new ApiResponse("A company with such a name already exists", false);
        }

        boolean existsAddress = addressRepository.existsByStreetAndHomeNumber(companyDto.getStreet(), companyDto.getHomeNumber());
        if (existsAddress) {
            return new ApiResponse("An address with such a street and home number already exists", false);
        }

        Company company = new Company();
        company.setName(companyDto.getCompName());
        company.setDirectorName(companyDto.getDirectorName());
        Address address = new Address();
        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());
        Address savedAddress = addressRepository.save(address);
        company.setAddress(savedAddress);
        companyRepository.save(company);
        return new ApiResponse("Company saved", true);
    }

    public List<Company> getAll() {
        List<Company> companyList = companyRepository.findAll();
        return companyList;
    }

    public Company getById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()) {
            return new Company();
        }
        return optionalCompany.get();
    }

    public ApiResponse edit(Integer id, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()) {
            return new ApiResponse("Company not found", false);
        }

        boolean existsByNameAndIdNot = companyRepository.existsByNameAndIdNot(companyDto.getCompName(), id);
        if (existsByNameAndIdNot) {
            return new ApiResponse("A company with such a name already exists", false);
        }

        Company company = optionalCompany.get();

        boolean existsAddress = addressRepository
                .existsByStreetAndHomeNumberAndIdNot(companyDto.getStreet(), companyDto.getHomeNumber(), company.getAddress().getId());
        if (existsAddress) {
            return new ApiResponse("An address with such a street and home number already exists", false);
        }


        Address address = company.getAddress();
        boolean existsByStreetAndHomeNumber = companyDto.getStreet().equals(address.getStreet()) && companyDto.getHomeNumber().equals(address.getHomeNumber());
        Integer dId = null;
        if (!existsByStreetAndHomeNumber) {
            address = new Address();
            dId = company.getAddress().getId();
        }

        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());
        company.setAddress(existsByStreetAndHomeNumber ? address : addressRepository.save(address));
        if (dId != null) {
            addressService.deleteById(dId);
        }
        company.setName(companyDto.getCompName());
        company.setDirectorName(companyDto.getDirectorName());
        companyRepository.save(company);
        return new ApiResponse("Company edited", true);

    }


    public ApiResponse deleteById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()) {
            return new ApiResponse("Company not found", false);
        }

        companyRepository.deleteById(id);
        return new ApiResponse("Company deleted", true);
    }


}
