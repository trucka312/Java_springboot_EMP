

package com.luvina.la.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Đối tượng DTO (Data Transfer Object) chứa thông tin về nhân viên.
 */
@Data
public class EmployeeDTO implements Serializable {

    private static final long serialVersionUID = 6868189362900231672L;

    private Long employeeId;
    private String employeeName;
    private String employeeEmail;
    private String employeeLoginId;
    @Temporal(TemporalType.DATE)
    private Date employeeBirthDate;
    private String employeeTelephone;
    private String employeeNameKana;
    private String employeeLoginPassword;
    private String departmentName;
    private String certificationName;
    private Long departmentId;
    private BigDecimal score;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    private Date startDate;
    private Long certificationId;

}
