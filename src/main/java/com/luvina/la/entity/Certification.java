

package com.luvina.la.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Đại diện cho chứng chỉ (Certification).
 */
@Entity
@Table(name = "certifications")
@Data
public class Certification {

    @Id
    @Column(name = "certification_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificationId;

    @Column(name = "certification_name")
    private String certificationName;

    @Column(name = "certification_level")
    private int certificationLevel;

}
