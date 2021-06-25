package uz.mk.companyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.mk.companyservice.entity.Company;
import uz.mk.companyservice.payload.ApiResponse;
import uz.mk.companyservice.payload.CompanyDto;
import uz.mk.companyservice.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<Company> CompanyList = companyService.getAll();
        return ResponseEntity.ok(CompanyList);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        Company company = companyService.getById(id);
        return ResponseEntity.ok(company);
    }

    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody CompanyDto companyDto) {
        ApiResponse response = companyService.add(companyDto);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT)
                .body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit( @PathVariable Integer id, @Valid @RequestBody CompanyDto companyDto) {
        ApiResponse response = companyService.edit(id, companyDto);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Integer id) {
        ApiResponse response = companyService.deleteById(id);
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
