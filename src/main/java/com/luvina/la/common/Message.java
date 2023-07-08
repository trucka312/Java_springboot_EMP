/**
 * Copyright (c) 2023 Luvina Software Company
 * <p>
 * Message.java, Jun 14, 2023 LA31-AM
 */

package com.luvina.la.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lớp Message đại diện cho thông điệp chung trong hệ thống.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String code;
    private String params;


}
