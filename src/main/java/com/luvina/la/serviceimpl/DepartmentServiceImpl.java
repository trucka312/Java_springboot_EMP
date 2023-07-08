

package com.luvina.la.serviceimpl;

import com.luvina.la.entity.Department;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * Implement method của interface DepartmentService để cung cấp các chức năng quản lý phòng ban.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * Lấy danh sách tất cả các phòng ban.
     *
     * @return Danh sách các đối tượng Department hoặc danh sách rỗng nếu không có phòng ban nào.
     */
    @Override
    public List<Department> getAllDepartments() {
        return Optional.of(departmentRepository.findAll())
                .orElse(Collections.emptyList());
    }

    /**
     * Kiểm tra sự tồn tại của phòng ban dựa trên ID phòng ban.
     *
     * @param departmentId ID của phòng ban cần kiểm tra.
     * @return True nếu phòng ban tồn tại, False nếu không tồn tại.
     */
    @Override
    public boolean checkExistsDepartment(Long departmentId) {
        return departmentRepository.existsByDepartmentId(departmentId);
    }
}
