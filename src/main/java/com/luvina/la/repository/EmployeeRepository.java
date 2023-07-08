

package com.luvina.la.repository;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * EmployeeRepository là một interface sử dụng JpaRepository để thực hiện các hoạt động truy vấn với bảng Employee trong cơ sở dữ liệu.
 */
@Transactional
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmployeeLoginId(String employeeLoginId);

    Optional<Employee> findByEmployeeId(Long employeeId);

    /**
     * Method sử dụng để getALLEmployee, search và sort theo điều kiện đã cho
     *
     * @param employeeDTO lấy những properties trong employeeDTO để xử lý search điều kiện
     * @param rule        hiển thị theo rule
     * @param pageable    truyền data vào pageable để xử lý paging cũng như sort
     * @return Trả về Page<Employee>
     */
    @Query(value = "SELECT e " +
            "FROM Employee e " +
            "LEFT JOIN e.department d " +
            "LEFT JOIN e.employeeCertification ec " +
            "LEFT JOIN ec.certification c " +
            "WHERE (:#{#employeeDTO.employeeName} IS NULL OR e.employeeName LIKE '%' || " +
            "REPLACE(REPLACE(REPLACE(:#{#employeeDTO.employeeName}, '%', '\\\\%'), '_', '\\\\_'), '', '\\\\') || '%') " +
            "AND (:#{#employeeDTO.departmentId} IS NULL OR e.department.departmentId = :#{#employeeDTO.departmentId}) " +
            "AND e.rule = :rule ")
    public Page<Employee> findEmployeeByQuery(
            @Param("employeeDTO") Optional<EmployeeDTO> employeeDTO, Employee.Rule rule, Pageable pageable);

    /**
     * Truy vấn để lấy tổng số nhân viên thỏa mãn điều kiện tìm kiếm.
     *
     * @param employeeDTO Đối tượng DTO chứa thông tin tìm kiếm nhân viên (tùy chọn)
     * @param rule        Quy tắc áp dụng cho nhân viên
     * @return Tổng số nhân viên thỏa mãn điều kiện tìm kiếm
     */
    @Query(value = "SELECT COUNT(e) FROM Employee e " +
            "WHERE (:#{#employeeDTO.employeeName} IS NULL OR e.employeeName LIKE '%' || " +
            "REPLACE(REPLACE(REPLACE(:#{#employeeDTO.employeeName}, '%', '\\\\%'), '_', '\\\\_'), '', '\\\\') || '%') " +
            "AND (:#{#employeeDTO.departmentId} IS NULL OR e.department.departmentId = :#{#employeeDTO.departmentId}) " +
            "AND e.rule = :rule")
    public Integer getTotalEmployees(@Param("employeeDTO") Optional<EmployeeDTO> employeeDTO, Employee.Rule rule);

    /**
     * Kiểm tra sự tồn tại của nhân viên dựa trên tên đăng nhập.
     *
     * @param employeeLoginId Tên đăng nhập của nhân viên
     * @param employeeId  Mã nhân viên
     * @return true nếu nhân viên tồn tại, false nếu không tồn tại
     */
    public boolean existsByEmployeeLoginIdAndEmployeeIdNot(String employeeLoginId, Long employeeId);

    /**
     * Kiểm tra sự tồn tại của ID nhân viên
     *
     * @param employeeId: ID của nhân viên cần kiểm tra
     * @return boolean: true nếu tồn tại, false nếu không tồn tại
     */
    public boolean existsByEmployeeId(Long employeeId);
}
