

package com.luvina.la.controller;

import com.luvina.la.common.Response;
import com.luvina.la.config.Constants;
import com.luvina.la.entity.Department;
import com.luvina.la.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class này chịu trách nhiệm xử lý các yêu cầu API liên quan đến department.
 */
@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * Lấy danh sách tất cả Department.
     *
     * @return Response chứa danh sách Certification
     */
    @GetMapping("/departments")
    public Response getAllDepartment() {
        try {
            List<Department> departments = departmentService.getAllDepartments();
            return Response.success("200", departments.size())
                    .withData(departments);
        } catch (Exception e) {
            return Response.error(Constants.RESPONSE_CODE.SERVER_ERROR,null);
        }
    }
}
