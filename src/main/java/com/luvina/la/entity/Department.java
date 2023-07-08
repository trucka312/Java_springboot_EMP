

package com.luvina.la.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Đại diện cho phòng ban (Department) .
 */
@Entity
@Table(name = "departments")
@Data
public class Department implements Serializable {

    @Id
    @Column(name = "department_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    private String departmentName;

    @OneToMany(mappedBy = "department", targetEntity = Employee.class)
    @JsonIgnore
    private List<Employee> employeeList = new ArrayList<>();
}
