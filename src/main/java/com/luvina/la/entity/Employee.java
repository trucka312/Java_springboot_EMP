

package com.luvina.la.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Đại diện cho thông tin của nhân viên (Employee).
 * <p>
 * Lớp này ánh xạ với bảng 'employees' trong cơ sở dữ liệu.
 * Nó chứa các thuộc tính như ID nhân viên, tên nhân viên, email, tên đăng nhập,
 * mật khẩu đăng nhập, ngày sinh, số điện thoại, tên kana, phòng ban và quyền hạn.
 */
@Entity
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 5771173953267484096L;

    @Id
    @Column(name = "employee_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_email")
    private String employeeEmail;

    @Column(name = "employee_login_id")
    private String employeeLoginId;

    @Column(name = "employee_login_password")
    private String employeeLoginPassword;

    @Column(name = "employee_birth_date")
    @Temporal(TemporalType.DATE)
    private Date employeeBirthDate;

    @Column(name = "employee_telephone")
    private String employeeTelephone;

    @Column(name = "employee_name_kana")
    private String employeeNameKana;

    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne(mappedBy = "employee")
    @JsonIgnore
    private EmployeeCertification employeeCertification;

    public enum Rule {
        ADMIN, USER
    }

    private Rule rule;

}
