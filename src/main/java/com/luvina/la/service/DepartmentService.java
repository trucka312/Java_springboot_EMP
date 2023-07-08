
package com.luvina.la.service;

import com.luvina.la.entity.Department;

import java.util.List;

/**
 * Interface DepartmentService định nghĩa các phương thức cung cấp các chức năng quản lý phòng ban.
 */
public interface DepartmentService {
    /**
     * Lấy danh sách tất cả các phòng ban.
     *
     * @return Danh sách các đối tượng Department hoặc danh sách rỗng nếu không có phòng ban nào.
     */
    public List<Department> getAllDepartments();

    /**
     * Kiểm tra sự tồn tại của phòng ban dựa trên ID phòng ban.
     *
     * @param departmentId ID của phòng ban cần kiểm tra.
     * @return True nếu phòng ban tồn tại, False nếu không tồn tại.
     */
    public boolean checkExistsDepartment(Long departmentId);
}
