

package com.luvina.la.repository;

import com.luvina.la.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * DepartmentRepository là một interface sử dụng JpaRepository để thực hiện các hoạt động truy vấn với bảng Department trong cơ sở dữ liệu.
 */
@Transactional
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByDepartmentId(Long departmentId);
}
