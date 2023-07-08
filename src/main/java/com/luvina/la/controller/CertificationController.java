

package com.luvina.la.controller;

import com.luvina.la.common.Response;
import com.luvina.la.config.Constants;
import com.luvina.la.entity.Certification;
import com.luvina.la.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class này chịu trách nhiệm xử lý các yêu cầu API liên quan đến certification.
 */
@RestController
public class CertificationController {

    @Autowired
    private CertificationService certificationService;

    /**
     * Lấy danh sách tất cả Certification.
     *
     * @return Response chứa danh sách Certification
     */
    @GetMapping("/certifications")
    public Response getAllCertifications() {
        try {
            List<Certification> certifications = certificationService.getAllCertifications();
            return Response.success("200")
                    .withData(certifications);
        } catch (Exception e) {
            return Response.error(Constants.RESPONSE_CODE.SERVER_ERROR,null);
        }
    }

}
