
package com.luvina.la.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Đại diện cho chứng chỉ của nhân viên (EmployeeCetification).
 * <p>
 * Lớp này ánh xạ với bảng 'employees_certifications' trong cơ sở dữ liệu.
 * Nó chứa thông tin về chứng chỉ của nhân viên, bao gồm ID chứng chỉ nhân viên,
 * nhân viên liên quan, chứng chỉ liên quan, ngày bắt đầu, ngày kết thúc và điểm số.
 */
@Entity
@Table(name = "employees_certifications")
@Data
public class EmployeeCertification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_certification_id")
    private Long employeeCertificationId;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "certification_id", referencedColumnName = "certification_id", unique = true)
    private Certification certification;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    private BigDecimal score;

}
