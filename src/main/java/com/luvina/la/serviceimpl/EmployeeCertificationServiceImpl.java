

package com.luvina.la.serviceimpl;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Certification;
import com.luvina.la.entity.Employee;
import com.luvina.la.entity.EmployeeCertification;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.repository.EmployeeCertificationRepository;
import com.luvina.la.service.EmployeeCertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implement method của interface EmployeeCertificationService để cung cấp các chức năng quản lý chứng chỉ của nhân viên.
 */
@Service
@Transactional
public class EmployeeCertificationServiceImpl implements EmployeeCertificationService {

    @Autowired
    private EmployeeCertificationRepository employeeCertificationRepository;

    @Autowired
    private CertificationRepository certificationRepository;

    /**
     * Lưu thông tin chứng chỉ của nhân viên.
     *
     * @param employeeDTO Đối tượng EmployeeDTO chứa thông tin chứng chỉ.
     * @param employee    Đối tượng Employee chứa thông tin nhân viên.
     * @return Đối tượng EmployeeCertification đã được lưu.
     */
    @Override
    public EmployeeCertification saveEmployeeCertification(EmployeeDTO employeeDTO, Employee employee) {
        EmployeeCertification employeeCertification = null;
        Certification certification = null;
        if (employeeDTO.getCertificationId() != null) {
            certification = certificationRepository.findById(employeeDTO.getCertificationId()).get();
            if (employee.getEmployeeCertification() == null) {
                employeeCertification = new EmployeeCertification();
            } else {
                employeeCertification = employeeCertificationRepository.findEmployeeCertificationByEmployee_EmployeeId(employee.getEmployeeId());
            }
            employeeCertification.setEmployee(employee);
            employeeCertification.setCertification(certification);
            employeeCertification.setStartDate(employeeDTO.getStartDate());
            employeeCertification.setEndDate(employeeDTO.getEndDate());
            employeeCertification.setScore(employeeDTO.getScore());
            employeeCertificationRepository.save(employeeCertification);
        } else {
            if (employee.getEmployeeCertification() != null) {
                employeeCertificationRepository.deleteByEmployeeId(employee.getEmployeeId());
            }
        }
        return employeeCertification;
    }
}
