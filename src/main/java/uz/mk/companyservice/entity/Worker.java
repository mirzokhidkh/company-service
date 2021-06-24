package uz.mk.companyservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.mk.companyservice.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Worker extends AbsEntity {

    @Column(unique = true,nullable = false)
    private String phoneNumber;

    @OneToOne(optional = false)
    private Address address;

    @ManyToOne(optional = false)
    private Department department;

}
