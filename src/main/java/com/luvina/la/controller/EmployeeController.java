

package com.luvina.la.controller;

import com.luvina.la.common.Message;
import com.luvina.la.common.Response;
import com.luvina.la.config.Constants;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;
import com.luvina.la.service.CertificationService;
import com.luvina.la.service.DepartmentService;
import com.luvina.la.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Class này chịu trách nhiệm xử lý các yêu cầu API liên quan đến employee.
 */
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CertificationService certificationService;


    /**
     * Lấy danh sách nhân viên dựa trên các tiêu chí tìm kiếm.
     *
     * @param employeeName  Tên nhân viên (tùy chọn).
     * @param departmentId  ID của phòng ban (tùy chọn).
     * @param pageNo        Số trang hiện tại (mặc định: 0).
     * @param pageSize      Số lượng nhân viên trên mỗi trang (mặc định: 2).
     * @param sortBy        Trường để sắp xếp (tùy chọn).
     * @param sortDirection Hướng sắp xếp (mặc định: asc).
     * @return Response chứa danh sách nhân viên và kết quả thành công.
     */
    @GetMapping("/employee")
    public ResponseEntity<Response> getAllEmployee(
            @RequestParam(name = "employee_name", required = false) String employeeName,
            @RequestParam(name = "department_id", required = false) Long departmentId,
            @RequestParam(defaultValue = "0", required = false, name = "offset") Integer pageNo,
            @RequestParam(required = false, name = "limit", defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc", required = false) String sortDirection) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeName(employeeName);
        employeeDTO.setDepartmentId(departmentId);
        try {
            List<EmployeeDTO> employeeDTOS = employeeService.getAllEmployee(employeeDTO, pageNo, pageSize, sortBy, sortDirection);
            int totalEmployees = this.employeeService.countEmployees(employeeDTO);
            return ResponseEntity.ok(Response.success(Constants.RESPONSE_CODE.SUCCESSFUL, totalEmployees)
                    .withData(employeeDTOS));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Response.error(Constants.RESPONSE_CODE.SERVER_ERROR, null).withMessage("ER015"));
        }
    }

    /**
     * Tạo mới nhân viên.
     *
     * @param employeeDTO Đối tượng EmployeeDTO chứa thông tin nhân viên mới.
     * @return Response chứa kết quả thành công và thông tin nhân viên đã tạo.
     * @throws ParseException Nếu xảy ra lỗi khi phân tích cú pháp.
     */
    @PostMapping("/employee")
    public ResponseEntity<Response> createEmployee(@RequestBody EmployeeDTO employeeDTO) throws ParseException {
        List<Message> messages = new ArrayList<>();
        ResponseEntity<Response> response = validateEmployee(employeeDTO);
        Employee employee = null;
        try {
            if (!Objects.requireNonNull(response.getBody()).getMessages().isEmpty()) {
                return response;
            } else {
                employee = employeeService.saveEmployee(employeeDTO);
                return ResponseEntity.ok(Response.success(Constants.RESPONSE_CODE.SUCCESSFUL).withData(employee)
                        .withMessage("ユーザの登録が完了しました。"));
            }
        } catch (Exception e) {
            messages.add(new Message("ER015", "システムエラーが発生しました。"));
            return ResponseEntity.ok()
                    .body(Response.error(Constants.RESPONSE_CODE.SERVER_ERROR, null)
                            .withData(employee).withMessages(messages));
        }
    }

    /**
     * Phương thức kiểm tra hợp lệ của thông tin nhân viên
     *
     * @param employeeDTO: đối tượng chứa thông tin nhân viên
     * @return Response: kết quả kiểm tra hợp lệ
     */
    public ResponseEntity<Response> validateEmployee(EmployeeDTO employeeDTO) throws ParseException {
        List<Message> messageList = new ArrayList<>();

        // Kiểm tra tên login nhân viên
        if ("".equals(employeeDTO.getEmployeeLoginId())) {
            messageList.add(new Message(Constants.ERROR_CODE.NOT_NULL, "[アカウント名] を入力してください"));
        } else if (employeeDTO.getEmployeeLoginId().length() > 50) {
            messageList.add(new Message(Constants.ERROR_CODE.LENGTH, "[アカウント名] 50桁以内の「画面項目名」を入力してください"));
        } else if (!employeeDTO.getEmployeeLoginId().matches("^[a-zA-Z_][a-zA-Z0-9_]*$")) {
            messageList.add(new Message("ER0019", "[アカウント名] は(a-z, A-Z, 0-9 と _)の桁のみです。最初の桁は数字ではない。"));
        } else if (checkExistingEmployeeLoginId(employeeDTO.getEmployeeLoginId(), employeeDTO.getEmployeeId())) {
            messageList.add(new Message("ER003", "[アカウント名] は既に存在しています。"));
        }

        // Kiểm tra tên nhân viên
        if ("".equals(employeeDTO.getEmployeeName())) {
            messageList.add(new Message(Constants.ERROR_CODE.NOT_NULL, "[氏名] は既に存在しています。"));
        } else if (employeeDTO.getEmployeeName().length() > 125) {
            messageList.add(new Message(Constants.ERROR_CODE.LENGTH, "[氏名] 125桁以内の「画面項目名」を入力してください"));
        }

        // Kiểm tra tên kana của nhân viên
        if ("".equals(employeeDTO.getEmployeeNameKana())) {
            messageList.add(new Message(Constants.ERROR_CODE.NOT_NULL, "[カタカナ氏名] は既に存在しています。"));
        } else if (employeeDTO.getEmployeeNameKana().length() > 125) {
            messageList.add(new Message(Constants.ERROR_CODE.LENGTH, "[カタカナ氏名] 125桁以内の「画面項目名」を入力してください"));
        } else if (!employeeDTO.getEmployeeNameKana().matches("^[\\u30A0-\\u30FF\\s]+$")) {
            messageList.add(new Message("ER009", "[カタカナ氏名] をカタカナで入力してください"));
        }

        // Kiểm tra ngày sinh của nhân viên
        if (employeeDTO.getEmployeeBirthDate() == null) {
            messageList.add(new Message(Constants.ERROR_CODE.NOT_NULL, "[生年月日] を入力してください"));
        }

        // Kiểm tra email của nhân viên
        if ("".equals(employeeDTO.getEmployeeEmail())) {
            messageList.add(new Message(Constants.ERROR_CODE.NOT_NULL, "[メールアドレス] を入力してください"));
        } else if (employeeDTO.getEmployeeEmail().length() > 125) {
            messageList.add(new Message(Constants.ERROR_CODE.LENGTH, "[メールアドレス] 125桁以内の「画面項目名」を入力してください"));
        }

        // Kiểm tra số điện thoại của nhân viên
        if ("".equals(employeeDTO.getEmployeeTelephone())) {
            messageList.add(new Message(Constants.ERROR_CODE.NOT_NULL, "[電話番号] を入力してください"));
        } else if (employeeDTO.getEmployeeTelephone().length() > 50) {
            messageList.add(new Message(Constants.ERROR_CODE.LENGTH, "[電話番号] 50桁以内の「画面項目名」を入力してください"));
        } else if (!isCharacterByte(employeeDTO.getEmployeeTelephone(), messageList)) {
            messageList.add(new Message("ER008", "に半角英数を入力してください"));
        }

        // Kiểm tra mật khẩu đăng nhập của nhân viên
        if (employeeDTO.getEmployeeId() == null) {
            if ("".equals(employeeDTO.getEmployeeLoginPassword())) {
                messageList.add(new Message(Constants.ERROR_CODE.NOT_NULL, "[パスワード] を入力してください"));
                if (employeeDTO.getEmployeeLoginPassword().length() < 8 || employeeDTO.getEmployeeLoginPassword()
                        .length() > 50) {
                    messageList.add(new Message("ER007", "[パスワード] を8＜＝桁数、＜＝50桁で入力してください"));
                }
            }
        }

        // Kiểm tra mã phòng ban
        if (employeeDTO.getDepartmentId() == null) {
            messageList.add(new Message("ER002", "[グループ] を入力してください"));
        } else if (employeeDTO.getDepartmentId() <= 0L) {
            messageList.add(new Message(Constants.ERROR_CODE.INTEGER_NUMBER, "[グループ] は半角で入力してください。"));
        } else if (!departmentService.checkExistsDepartment(employeeDTO.getDepartmentId())) {
            messageList.add(new Message(Constants.ERROR_CODE.NOT_EXISTS, "[グループ] は存在していません。"));
        }

        // Kiểm tra mã chứng chỉ
        if (employeeDTO.getCertificationId() != null) {
            if (employeeDTO.getStartDate() == null) {
                messageList.add(new Message(Constants.ERROR_CODE.NOT_NULL, "[資格交付日] を入力してください"));
            }
            if (employeeDTO.getEndDate() == null) {
                messageList.add(new Message(Constants.ERROR_CODE.NOT_NULL, "[失効日] を入力してください"));
            }
            if (employeeDTO.getStartDate() != null && employeeDTO.getEndDate() != null) {
                if (employeeDTO.getStartDate().after(employeeDTO.getEndDate())) {
                    messageList.add(new Message("ER012", "[失効日] は「資格交付日」より未来の日で入力してください。"));
                }
            }
            if (employeeDTO.getScore() == null) {
                messageList.add(new Message("ER011", "[点数] を入力してください"));
            } else if (employeeDTO.getScore().compareTo(BigDecimal.ZERO) < 0) {
                messageList.add(new Message(Constants.ERROR_CODE.INTEGER_NUMBER, "[点数] は半角で入力してください。"));
            }
            if (employeeDTO.getCertificationId() <= 0L) {
                messageList.add(new Message(Constants.ERROR_CODE.INTEGER_NUMBER, "[資格] は半角で入力してください。"));
            } else if (!certificationService.checkExistsCertification(employeeDTO.getCertificationId())) {
                messageList.add(new Message(Constants.ERROR_CODE.NOT_EXISTS, "[資格] は存在していません。"));
            }
        }
        return ResponseEntity.ok()
                .body(Response.error(Constants.RESPONSE_CODE.SERVER_ERROR, null).withMessages(messageList)
                        .withData(employeeDTO));
    }

    /**
     * Kiểm tra số byte của chuỗi ký tự
     *
     * @param employeeTelephone: chuỗi ký tự cần kiểm tra
     * @param messages:       danh sách thông báo
     * @return boolean: true nếu hợp lệ, false nếu không hợp lệ
     */
    private boolean isCharacterByte(String employeeTelephone, List<Message> messages) {
        for (int i = 0; i < employeeTelephone.length(); i++) {
            if (employeeTelephone.charAt(i) > 255) {
                messages.add(new Message("ER008", "[電話番号] に半角英数を入力してください"));
                return false;
            }
        }

//        if(employeeTelephone.matches("\\d+")){
//            messages.add(new Message("ER005", "[電話番号] をカタカナで入力してください"));
//            return false;
//        }
        return true;

    }

    /**
     * Kiểm tra sự tồn tại của mã đăng nhập nhân viên
     *
     * @param employeeLoginId: mã đăng nhập nhân viên cần kiểm tra
     * @return boolean: true nếu tồn tại, false nếu không tồn tại
     */
    @GetMapping("/exists-employee-login-id")
    private boolean checkExistingEmployeeLoginId(
            @RequestParam(name = "employee_login_id", required = false) String employeeLoginId,
            @RequestParam(name = "employee_id", required = false) Long employeeId) {
        return employeeService.checkExistsEmployeeLoginId(employeeLoginId, employeeId);
    }

    /**
     * Lấy thông tin nhân viên theo ID
     *
     * @param employeeId: ID của nhân viên cần lấy thông tin
     * @return Response: đối tượng chứa result và response
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Response> getEmployeeById(@PathVariable Long employeeId) {
        Optional<EmployeeDTO> employeeDTO = Optional.empty();
        List<Message> messages = new ArrayList<>();
        try {
            if (employeeId == null) {
                messages.add(new Message(Constants.ERROR_CODE.NOT_NULL, "「ＩＤ」を入力してください"));
                return ResponseEntity.ok()
                        .body(Response.success(Constants.RESPONSE_CODE.SERVER_ERROR).withData(null)
                                .withMessages(messages));
            } else {
                employeeDTO = Optional.ofNullable(employeeService.findEmployeeById(employeeId));
                if (employeeDTO.isEmpty()) {
                    messages.add(new Message("ER013", "「ＩＤ」 該当するユーザは存在していません。"));
                    return ResponseEntity.ok()
                            .body(Response.success(Constants.RESPONSE_CODE.SERVER_ERROR).withData(employeeDTO)
                                    .withMessages(messages));
                } else {
                    return ResponseEntity.ok()
                            .body(Response.success(Constants.RESPONSE_CODE.SUCCESSFUL).withData(employeeDTO));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.ok()
                    .body(Response.error(Constants.RESPONSE_CODE.SERVER_ERROR, null).withData(employeeDTO)
                            .withMessages(messages));
        }
    }

    /**
     * Xóa nhân viên theo ID
     *
     * @param employeeId: ID của nhân viên cần xóa
     * @return Response: đối tượng chứa result và response
     */
    @DeleteMapping("/employee/{employeeId}")
    public ResponseEntity<Response> deleteEmployeeById(@PathVariable Long employeeId) {
        List<Message> messages = new ArrayList<>();
        try {
            this.employeeService.deleteEmployeeById(employeeId);
            messages.add(new Message("MSG003", null));
            return ResponseEntity.ok(Response.success(Constants.RESPONSE_CODE.SUCCESSFUL, employeeId)
                    .withMessages(messages));
        } catch (Exception e) {
            messages.add(new Message("ER014", "ＩＤ 該当するユーザは存在していません。"));
            return new ResponseEntity<>(Response.error(Constants.RESPONSE_CODE.SERVER_ERROR, employeeId)
                    .withMessages(messages), HttpStatus.OK);
        }
    }

    /**
     * Cập nhật thông tin nhân viên theo ID
     *
     * @param employeeDTO Đối tượng EmployeeDTO chứa thông tin nhân viên mới.
     * @return Response chứa kết quả thành công và thông tin nhân viên đã tạo.
     */
    @PutMapping("/employee")
    public ResponseEntity<Response> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        List<Message> messages = new ArrayList<>();
        Employee employee = null;
        try {
            if (employeeDTO.getEmployeeId() == null) {
                messages.add(new Message(Constants.ERROR_CODE.NOT_NULL, "「ＩＤ」を入力してください"));
                return ResponseEntity.status(HttpStatus.OK).body(
                        Response.success(Constants.RESPONSE_CODE.SERVER_ERROR).withData(employeeDTO)
                                .withMessages(messages)
                );
            } else {
                if (!this.employeeService.existsEmployeeId(employeeDTO.getEmployeeId())) {
                    messages.add(new Message("ER013", "「ＩＤ」該当するユーザは存在していません。"));
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(Response.success(Constants.RESPONSE_CODE.SERVER_ERROR).withData(employeeDTO)
                                    .withMessages(messages)
                            );
                } else {
                    ResponseEntity<Response> response = validateEmployee(employeeDTO);
                    if (!Objects.requireNonNull(response.getBody()).getMessages().isEmpty()) {
                        return response;
                    } else {
                        employee = this.employeeService.updateEmployee(employeeDTO);
                        messages.add(new Message("MSG002", "ユーザの更新が完了しました。"));
                        return ResponseEntity.status(HttpStatus.OK)
                                .body(Response.success(Constants.RESPONSE_CODE.SUCCESSFUL, employee.getEmployeeId())
                                        .withMessages(messages));
                    }
                }
            }
        } catch (Exception e) {
            messages.add(new Message("ER015", "システムエラーが発生しました。"));
            return ResponseEntity.ok()
                    .body(Response.error(Constants.RESPONSE_CODE.SERVER_ERROR, employeeDTO.getEmployeeId())
                            .withData(employee).withMessages(messages));
        }
    }
}
