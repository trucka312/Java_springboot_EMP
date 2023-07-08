

package com.luvina.la.serviceimpl;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;
import com.luvina.la.mapper.EmployeeMapper;
import com.luvina.la.repository.EmployeeCertificationRepository;
import com.luvina.la.repository.EmployeeRepository;
import com.luvina.la.service.EmployeeCertificationService;
import com.luvina.la.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

/**
 * Implement method của interface EmployeeService để cung cấp các chức năng quản lý nhân viên.
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeCertificationRepository employeeCertificationRepository;

    @Autowired
    private EmployeeCertificationService employeeCertificationService;

    /**
     * Lấy danh sách nhân viên dựa trên các thông tin tìm kiếm và phân trang.
     *
     * @param employeeDTO   Đối tượng EmployeeDTO chứa thông tin tìm kiếm.
     * @param pageNo        Số trang.
     * @param pageSize      Số lượng nhân viên trên mỗi trang.
     * @param sortBy        Thuộc tính để sắp xếp kết quả.
     * @param sortDirection Hướng sắp xếp (asc hoặc desc).
     * @return Danh sách EmployeeDTO của nhân viên phù hợp.
     */
    @Override
    public List<EmployeeDTO> getAllEmployee(EmployeeDTO employeeDTO, Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {
        Pageable pageable = null;
        if (sortBy != null) {
            Sort sort = null;
            if (sortBy.equals("ord_employee_name")) {
                sortBy = "employeeName";
                sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
            } else if (sortBy.equals("ord_end_date")) {
                sortBy = "employeeCertification.endDate";
                sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
            } else if (sortBy.equals("ord_certification_name")) {
                sortBy = "c.certificationName";
                sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
            }
            pageable = PageRequest.of(pageNo, pageSize, sort);
        } else {
            pageable = PageRequest.of(pageNo, pageSize);
        }
        Page<Employee> employeeList = employeeRepository.findEmployeeByQuery(Optional.ofNullable(employeeDTO), Employee.Rule.USER, pageable);
        return employeeMapper.toList(employeeList)
                .getContent();
    }

    /**
     * Đếm số lượng nhân viên dựa trên các thông tin tìm kiếm.
     *
     * @param employeeDTO Đối tượng EmployeeDTO chứa thông tin tìm kiếm.
     * @return Số lượng nhân viên phù hợp.
     */
    @Override
    public Integer countEmployees(EmployeeDTO employeeDTO) {
        return employeeRepository.getTotalEmployees(Optional.ofNullable(employeeDTO), Employee.Rule.USER);
    }

    /**
     * Kiểm tra sự tồn tại của một nhân viên dựa trên ID đăng nhập.
     *
     * @param employeeLoginId ID đăng nhập của nhân viên.
     * @param employeeId      Mã nhân viên
     * @return True nếu nhân viên tồn tại, ngược lại trả về False.
     */
    @Override
    public boolean checkExistsEmployeeLoginId(String employeeLoginId, Long employeeId) {
        if (employeeId == null) {
            employeeId = 0L;
        }
        return employeeRepository.existsByEmployeeLoginIdAndEmployeeIdNot(employeeLoginId, employeeId);
    }

    /**
     * Lưu thông tin nhân viên mới.
     *
     * @param employeeDTO Đối tượng EmployeeDTO chứa thông tin nhân viên mới.
     * @return Đối tượng Employee đã được lưu.
     */
    @Override
    public Employee saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employeeRepository.save(employee);
        employeeCertificationService.saveEmployeeCertification(employeeDTO, employee);
        return employee;
    }

    /**
     * Tìm kiếm thông tin nhân viên theo ID
     *
     * @param employeeId: ID của nhân viên cần tìm kiếm
     * @return EmployeeDTO: đối tượng chứa thông tin nhân viên
     */
    public EmployeeDTO findEmployeeById(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findByEmployeeId(employeeId);
        return employee.map(value -> employeeMapper.toDto(value)).orElse(null);
    }

    /**
     * Xóa nhân viên theo ID
     *
     * @param employeeId: ID của nhân viên cần xóa
     */

    @Override
    public void deleteEmployeeById(Long employeeId) {
        employeeCertificationRepository.deleteByEmployeeId(employeeId);
        employeeRepository.deleteById(employeeId);
    }

    /**
     * Kiểm tra sự tồn tại của ID nhân viên
     *
     * @param employeeId: ID của nhân viên cần kiểm tra
     * @return boolean: true nếu tồn tại, false nếu không tồn tại
     */

    @Override
    public boolean existsEmployeeId(Long employeeId) {
        return employeeRepository.existsByEmployeeId(employeeId);
    }

    /**
     * Cập nhật thông tin nhân viên
     *
     * @param employeeDTO: Đối tượng DTO chứa thông tin nhân viên cần cập nhật
     * @return Employee: Đối tượng nhân viên đã được cập nhật
     */
    @Override
    public Employee updateEmployee(EmployeeDTO employeeDTO) {
        Employee updateEmployee = employeeMapper.toEntity(employeeDTO);
        employeeRepository.save(updateEmployee);
        employeeCertificationService.saveEmployeeCertification(employeeDTO, updateEmployee);
        return updateEmployee;
    }
}
