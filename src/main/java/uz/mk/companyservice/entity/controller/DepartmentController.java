package uz.mk.companyservice.entity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.mk.companyservice.entity.Department;
import uz.mk.companyservice.payload.ApiResponse;
import uz.mk.companyservice.payload.DepartmentDto;
import uz.mk.companyservice.service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<Department> DepartmentList = departmentService.getAll();
        return ResponseEntity.ok(DepartmentList);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        Department department = departmentService.getById(id);
        return ResponseEntity.ok(department);
    }

    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody DepartmentDto departmentDto) {
        ApiResponse response = departmentService.add(departmentDto);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT)
                .body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@Valid @PathVariable Integer id,@Valid @RequestBody DepartmentDto departmentDto) {
        ApiResponse response = departmentService.edit(id, departmentDto);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Integer id) {
        ApiResponse response = departmentService.deleteById(id);
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
