

package com.luvina.la.repository;

import com.luvina.la.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * CertificationRepository là một interface sử dụng JpaRepository để thực hiện các hoạt động truy vấn với bảng Certification trong cơ sở dữ liệu.
 */
@Transactional
@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {

    /**
     * Kiểm tra sự tồn tại của chứng chỉ dựa trên certificationId.
     *
     * @param certificationId ID của chứng chỉ cần kiểm tra.
     * @return true nếu chứng chỉ tồn tại, ngược lại trả về false.
     */
    public boolean existsByCertificationId(Long certificationId);
}
