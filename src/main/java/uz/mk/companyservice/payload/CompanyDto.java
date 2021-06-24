package uz.mk.companyservice.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CompanyDto {

    @NotNull(message = "The company name mustn't be empty")
    private String compName;

    @NotNull(message = "The director name mustn't be empty")
    private String directorName;

    @NotNull(message = "The street name mustn't be empty")
    private String street;

    @NotNull(message = "The home number mustn't be empty")
    private String homeNumber;
}
