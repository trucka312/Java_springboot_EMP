

package com.luvina.la.service;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;
import com.luvina.la.entity.EmployeeCertification;

/**
 * Interface EmployeeCertificationService định nghĩa các phương thức để quản lý chứng chỉ của nhân viên.
 */
public interface EmployeeCertificationService {
    /**
     * Lưu thông tin chứng chỉ của nhân viên.
     *
     * @param employeeDTO Đối tượng EmployeeDTO chứa thông tin chứng chỉ của nhân viên.
     * @param employee    Đối tượng Employee liên kết với chứng chỉ.
     * @return Đối tượng EmployeeCertification đã được lưu.
     */
    public EmployeeCertification saveEmployeeCertification(EmployeeDTO employeeDTO, Employee employee);
}
