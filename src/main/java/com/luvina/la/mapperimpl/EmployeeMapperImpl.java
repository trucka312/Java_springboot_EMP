
package com.luvina.la.mapperimpl;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Certification;
import com.luvina.la.entity.Department;
import com.luvina.la.entity.Employee;
import com.luvina.la.entity.EmployeeCertification;
import com.luvina.la.mapper.EmployeeMapper;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.repository.EmployeeCertificationRepository;
import com.luvina.la.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Implement interface EmployeeMapper để thực hiện chuyển đổi giữa Employee và EmployeeDTO.
 */
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Autowired
    private EmployeeCertificationRepository employeeCertificationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CertificationRepository certificationRepository;

    /**
     * Chuyển đổi từ đối tượng EmployeeDTO sang đối tượng Employee.
     *
     * @param employeeDTO Đối tượng EmployeeDTO cần chuyển đổi.
     * @return Đối tượng Employee đã chuyển đổi.
     */
    @Override
    public Employee toEntity(EmployeeDTO employeeDTO) {
        Employee employee = null;
        if (employeeDTO.getEmployeeId() == null) {
            employee = new Employee();
        } else {
            employee = employeeRepository.findByEmployeeId(employeeDTO.getEmployeeId()).orElse(null);
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        employee.setEmployeeName(employeeDTO.getEmployeeName());
        employee.setEmployeeEmail(employeeDTO.getEmployeeEmail());
        employee.setEmployeeTelephone(employeeDTO.getEmployeeTelephone());
        employee.setEmployeeLoginId(employeeDTO.getEmployeeLoginId());
        employee.setEmployeeBirthDate(employeeDTO.getEmployeeBirthDate());

        if (employeeDTO.getEmployeeLoginPassword() != null) {
            employee.setEmployeeLoginPassword(bCryptPasswordEncoder.encode(employeeDTO.getEmployeeLoginPassword()));
        }

        employee.setEmployeeNameKana(employeeDTO.getEmployeeNameKana());
        employee.setRule(Employee.Rule.USER);

        if (employeeDTO.getDepartmentId() != null) {
        Department department = departmentRepository.findById(employeeDTO.getDepartmentId()).get();
        employee.setDepartment(department);
        }
        return employee;
    }

    /**
     * Chuyển đổi từ trang danh sách Employee sang trang danh sách EmployeeDTO.
     *
     * @param list Danh sách Employee ban đầu.
     * @return Trang danh sách EmployeeDTO đã chuyển đổi.
     */
    @Override
    public Page<EmployeeDTO> toList(Page<Employee> list) {
        Page<EmployeeDTO> page = new PageImpl<>(list.stream().map(this::toDto).collect(Collectors.toList()));
        return page;
    }

    /**
     * Chuyển đổi từ đối tượng Employee sang đối tượng EmployeeDTO.
     *
     * @param employee Đối tượng Employee cần chuyển đổi.
     * @return Đối tượng EmployeeDTO đã chuyển đổi.
     */
    @Override
    public EmployeeDTO toDto(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setEmployeeName(employee.getEmployeeName());
        employeeDTO.setEmployeeEmail(employee.getEmployeeEmail());
        employeeDTO.setEmployeeBirthDate(employee.getEmployeeBirthDate());
        employeeDTO.setDepartmentId(employee.getDepartment().getDepartmentId());
        employeeDTO.setDepartmentName(employee.getDepartment().getDepartmentName());
        employeeDTO.setEmployeeTelephone(employee.getEmployeeTelephone());
        employeeDTO.setEmployeeLoginId(employee.getEmployeeLoginId());
        employeeDTO.setEmployeeLoginPassword(employee.getEmployeeLoginId());
        employeeDTO.setEmployeeNameKana(employee.getEmployeeNameKana());

        if (employee.getEmployeeCertification() != null) {
            EmployeeCertification employeeCertification = employee.getEmployeeCertification();
            Certification certification = employeeCertification.getCertification();
            employeeDTO.setCertificationId(certification.getCertificationId());
            employeeDTO.setCertificationName(certification.getCertificationName());
            employeeDTO.setScore(employeeCertification.getScore());
            employeeDTO.setStartDate(employeeCertification.getStartDate());
            employeeDTO.setEndDate(employeeCertification.getEndDate());
        }
        return employeeDTO;
    }
}
