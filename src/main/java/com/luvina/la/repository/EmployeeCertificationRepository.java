

package com.luvina.la.repository;

import com.luvina.la.entity.EmployeeCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * EmployeeCertificationRepository là một interface sử dụng JpaRepository để thực hiện các hoạt động truy vấn với bảng EmployeeCertification trong cơ sở dữ liệu.
 */
@Transactional
@Repository
public interface EmployeeCertificationRepository extends JpaRepository<EmployeeCertification, Long> {
    /**
     * Lưu thông tin chứng chỉ của nhân viên.
     *
     * @param employeeDTO Đối tượng EmployeeDTO chứa thông tin chứng chỉ của nhân viên.
     * @param employee    Đối tượng Employee chứa thông tin nhân viên liên quan đến chứng chỉ.
     * @return Đối tượng EmployeeCertification đã được lưu.
     */
    public EmployeeCertification findByEmployee_EmployeeId(Long employeeId);

    /**
     * Xóa chứng chỉ nhân viên dựa trên ID nhân viên
     *
     * @param employeeId: ID của nhân viên để xóa chứng chỉ
     */
    @Modifying
    @Query(value = "DELETE FROM EmployeeCertification ec WHERE ec.employee.employeeId = :employeeId")
    public void deleteByEmployeeId(@Param("employeeId") Long employeeId);

    public EmployeeCertification findEmployeeCertificationByEmployee_EmployeeId(Long employeeId);

}
