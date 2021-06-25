package uz.mk.companyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.mk.companyservice.entity.Worker;
import uz.mk.companyservice.payload.ApiResponse;
import uz.mk.companyservice.payload.WorkerDto;
import uz.mk.companyservice.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/worker")
public class WorkerController {

    @Autowired
    WorkerService workerService;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<Worker> WorkerList = workerService.getAll();
        return ResponseEntity.ok(WorkerList);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        Worker Worker = workerService.getById(id);
        return ResponseEntity.ok(Worker);
    }

    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody WorkerDto workerDto) {
        ApiResponse response = workerService.add(workerDto);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT)
                .body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@Valid @PathVariable Integer id,@Valid @RequestBody WorkerDto workerDto) {
        ApiResponse response = workerService.edit(id, workerDto);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Integer id) {
        ApiResponse response = workerService.deleteById(id);
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
