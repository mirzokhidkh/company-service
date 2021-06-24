package uz.mk.companyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.mk.companyservice.entity.Address;
import uz.mk.companyservice.payload.ApiResponse;
import uz.mk.companyservice.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public ApiResponse add(Address address) {
        boolean exists = addressRepository.existsByStreetAndHomeNumber(address.getStreet(), address.getHomeNumber());
        if (exists) {
            return new ApiResponse("An address with such a street and home number already exists", false);
        }

        addressRepository.save(address);

        return new ApiResponse("Address saved", true);
    }

    public List<Address> getAll() {
        List<Address> addressList = addressRepository.findAll();
        return addressList;
    }

    public Address getById(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isEmpty()) {
            return new Address();
        }
        return optionalAddress.get();
    }

    public ApiResponse edit(Integer id, Address address) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isEmpty()) {
            return new ApiResponse("Address not found", false);
        }

        boolean exists = addressRepository.existsByStreetAndHomeNumberAndIdNot(address.getStreet(), address.getHomeNumber(), id);
        if (exists) {
            return new ApiResponse("An address with such a street and home number already exists", false);
        }

        Address editingAddress = optionalAddress.get();
        editingAddress.setStreet(address.getStreet());
        editingAddress.setHomeNumber(address.getHomeNumber());
        addressRepository.save(editingAddress);

        return new ApiResponse("Address edited", true);

    }


    public ApiResponse deleteById(Integer id){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isEmpty()) {
            return new ApiResponse("Address not found", false);
        }

        addressRepository.deleteById(id);
        return new ApiResponse("Address deleted", true);
    }


}
