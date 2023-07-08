

package com.luvina.la.serviceimpl;

import com.luvina.la.entity.Certification;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Implement method của interface CertificationService để cung cấp các chức năng quản lý chứng chỉ.
 */
@Service
public class CertificationServiceImpl implements CertificationService {

    @Autowired
    private CertificationRepository certificationRepository;

    /**
     * Lấy danh sách tất cả các chứng chỉ.
     *
     * @return Danh sách các đối tượng Certification hoặc danh sách rỗng nếu không có chứng chỉ nào.
     */
    @Override
    public List<Certification> getAllCertifications() {
        return Optional.of(certificationRepository.findAll())
                .orElse(Collections.emptyList());
    }

    /**
     * Kiểm tra sự tồn tại của chứng chỉ dựa trên ID chứng chỉ.
     *
     * @param certificationId ID của chứng chỉ cần kiểm tra.
     * @return True nếu chứng chỉ tồn tại, False nếu không tồn tại.
     */
    @Override
    public boolean checkExistsCertification(Long certificationId) {
        return certificationRepository.existsByCertificationId(certificationId);
    }
}
