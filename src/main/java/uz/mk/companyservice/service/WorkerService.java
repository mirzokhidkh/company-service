package uz.mk.companyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.mk.companyservice.entity.Address;
import uz.mk.companyservice.entity.Department;
import uz.mk.companyservice.entity.Worker;
import uz.mk.companyservice.payload.ApiResponse;
import uz.mk.companyservice.payload.WorkerDto;
import uz.mk.companyservice.repository.AddressRepository;
import uz.mk.companyservice.repository.DepartmentRepository;
import uz.mk.companyservice.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;


@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressService addressService;


    public ApiResponse add(WorkerDto workerDto) {
        boolean exists = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (exists) {
            return new ApiResponse("An Worker with such a phone number already exists", false);
        }

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (optionalDepartment.isEmpty()) {
            return new ApiResponse("Department not found", false);
        }

        boolean existsByStreetAndHomeNumber = addressRepository.existsByStreetAndHomeNumber(workerDto.getStreet(), workerDto.getHomeNumber());
        if (existsByStreetAndHomeNumber) {
            return new ApiResponse("An address with such a street and home number already exists", false);
        }

        Worker worker = new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());

        Address address = new Address();
        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        Address savedAddress = addressRepository.save(address);
        worker.setAddress(savedAddress);

        Department Department = optionalDepartment.get();
        worker.setDepartment(Department);

        workerRepository.save(worker);
        return new ApiResponse("Worker saved", true);
    }

    public List<Worker> getAll() {
        List<Worker> WorkerList = workerRepository.findAll();
        return WorkerList;
    }

    public Worker getById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isEmpty()) {
            return new Worker();
        }
        return optionalWorker.get();
    }

    public ApiResponse edit(Integer id, WorkerDto workerDto) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isEmpty()) {
            return new ApiResponse("Worker not found", false);
        }

        boolean exists = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id);
        if (exists) {
            return new ApiResponse("An Worker with such a phone number already exists", false);
        }

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (optionalDepartment.isEmpty()) {
            return new ApiResponse("Department not found", false);
        }

        Worker worker = optionalWorker.get();

        boolean existsAddress = addressRepository
                .existsByStreetAndHomeNumberAndIdNot(workerDto.getStreet(), workerDto.getHomeNumber(),worker.getAddress().getId());
        if (existsAddress) {
            return new ApiResponse("An address with such a street and home number already exists", false);
        }

        Address address = worker.getAddress();
        boolean existsByStreetAndHomeNumber = workerDto.getStreet().equals(address.getStreet()) && workerDto.getHomeNumber().equals(address.getHomeNumber());
        Integer dId = null;
        if (!existsByStreetAndHomeNumber) {
            address = new Address();
            dId = worker.getAddress().getId();
        }

        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        worker.setAddress(existsByStreetAndHomeNumber ? address : addressRepository.save(address));
        if (dId != null) {
            addressService.deleteById(dId);
        }
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());

        Department Department = optionalDepartment.get();
        worker.setDepartment(Department);
        workerRepository.save(worker);
        return new ApiResponse("Worker edited", true);

    }


    public ApiResponse deleteById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isEmpty()) {
            return new ApiResponse("Worker not found", false);
        }

        workerRepository.deleteById(id);
        return new ApiResponse("Worker deleted", true);
    }


}
