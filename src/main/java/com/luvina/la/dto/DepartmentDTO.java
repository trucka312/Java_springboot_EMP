package com.luvina.la.dto;

import lombok.Data;

/**
 * Đối tượng DTO (Data Transfer Object) chứa thông tin về phòng ban.
 */
@Data
public class DepartmentDTO {

    private Long departmentId;
    private String departmentName;
}
