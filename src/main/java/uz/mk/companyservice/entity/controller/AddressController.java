package uz.mk.companyservice.entity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.mk.companyservice.entity.Address;
import uz.mk.companyservice.payload.ApiResponse;
import uz.mk.companyservice.service.AddressService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<Address> addressList = addressService.getAll();
        return ResponseEntity.ok(addressList);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        Address address = addressService.getById(id);
        return ResponseEntity.ok(address);
    }

    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody Address address) {
        ApiResponse response = addressService.add(address);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT)
                .body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@Valid @PathVariable Integer id, @RequestBody Address address) {
        ApiResponse response = addressService.edit(id, address);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Integer id) {
        ApiResponse response = addressService.deleteById(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
