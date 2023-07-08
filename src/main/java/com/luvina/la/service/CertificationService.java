

package com.luvina.la.service;

import com.luvina.la.entity.Certification;

import java.util.List;

/**
 * Interface CertificationService định nghĩa các phương thức cung cấp các chức năng quản lý chứng chỉ.
 */
public interface CertificationService {
    /**
     * Lấy danh sách tất cả các chứng chỉ.
     *
     * @return Danh sách các đối tượng Certification hoặc danh sách rỗng nếu không có chứng chỉ nào.
     */
    public List<Certification> getAllCertifications();

    /**
     * Kiểm tra sự tồn tại của chứng chỉ dựa trên ID chứng chỉ.
     *
     * @param certificationId ID của chứng chỉ cần kiểm tra.
     * @return True nếu chứng chỉ tồn tại, False nếu không tồn tại.
     */
    public boolean checkExistsCertification(Long certificationId);
}
