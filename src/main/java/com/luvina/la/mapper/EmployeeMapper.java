

package com.luvina.la.mapper;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;


/**
 * Interface định nghĩa các method ánh xạ giữa đối tượng Employee và EmployeeDTO.
 * Sử dụng MapStruct để tự động tạo implementation cho interface này.
 */
@Mapper
public interface EmployeeMapper {
    EmployeeMapper MAPPER = Mappers.getMapper(EmployeeMapper.class);

    Employee toEntity(EmployeeDTO employeeDTO);

    //    Employee toDto(EmployeeDTO entity);
    Page<EmployeeDTO> toList(Page<Employee> list);

    EmployeeDTO toDto(Employee employee);

}
