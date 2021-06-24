package uz.mk.companyservice.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepartmentDto {

    @NotNull(message = "The department name mustn't be empty")
    private String name;

    @NotNull(message = "The Company Id mustn't be empty")
    private Integer companyId;
}
