

package com.luvina.la.service;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;

import java.util.List;

/**
 * Interface EmployeeService định nghĩa các phương thức liên quan đến quản lý nhân viên.
 */
public interface EmployeeService {

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
    public List<EmployeeDTO> getAllEmployee(EmployeeDTO employeeDTO, Integer pageNo, Integer pageSize, String sortBy, String sortDirection);

    /**
     * Đếm số lượng nhân viên dựa trên các thông tin tìm kiếm.
     *
     * @param employeeDTO Đối tượng EmployeeDTO chứa thông tin tìm kiếm.
     * @return Số lượng nhân viên phù hợp.
     */
    public Integer countEmployees(EmployeeDTO employeeDTO);

    /**
     * Kiểm tra sự tồn tại của một nhân viên dựa trên ID đăng nhập.
     *
     * @param employeeLoginId ID đăng nhập của nhân viên.
     * @return True nếu nhân viên tồn tại, ngược lại trả về False.
     */
    public boolean checkExistsEmployeeLoginId(String employeeLoginId, Long employeeId);

    /**
     * Lưu thông tin nhân viên mới.
     *
     * @param employeeDTO Đối tượng EmployeeDTO chứa thông tin nhân viên mới.
     * @return Đối tượng Employee đã được lưu.
     */
    public Employee saveEmployee(EmployeeDTO employeeDTO);

    /**
     * Tìm kiếm nhân viên theo ID
     *
     * @param employeeId: ID của nhân viên cần tìm kiếm
     * @return EmployeeDTO: Đối tượng DTO chứa thông tin nhân viên đã tìm thấy
     */
    public EmployeeDTO findEmployeeById(Long employeeId);

    /**
     * Tìm kiếm nhân viên theo ID
     *
     * @param employeeId: ID của nhân viên cần tìm kiếm
     * @return EmployeeDTO: Đối tượng DTO chứa thông tin nhân viên đã tìm thấy
     */
    public void deleteEmployeeById(Long employeeId);

    /**
     * Kiểm tra sự tồn tại của nhân viên dựa trên ID
     *
     * @param employeeId: ID của nhân viên cần kiểm tra
     * @return boolean: true nếu tồn tại nhân viên với ID đã cho, false nếu không tồn tại
     */
    public boolean existsEmployeeId(Long employeeId);

    /**
     * Cập nhật thông tin nhân viên
     *
     * @param employeeDTO: Đối tượng DTO chứa thông tin nhân viên cần cập nhật
     * @return Employee: Đối tượng nhân viên đã được cập nhật
     */
    public Employee updateEmployee(EmployeeDTO employeeDTO);

}
