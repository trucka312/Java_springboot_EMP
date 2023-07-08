/**
 * Copyright (c) 2023 Luvina Software Company
 * <p>
 * Response.java, Jun 14, 2023 LA31-AM
 */

package com.luvina.la.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Response đại diện cho phản hồi (response) từ hệ thống.
 */
@Data
public class Response {

    private String code;

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private Long employeeId;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
    private int totalRecords;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Object data;

    private List<Message> messages = new ArrayList<>();

    public Response(String code, int totalRecords, Object data) {
        this.code = code;
        this.totalRecords = totalRecords;
        this.data = data;
    }

    public Response(String code, int totalRecords) {
        this.code = code;
        this.totalRecords = totalRecords;
    }

    public Response(String code) {

        this.code = code;
    }

    public Response() {

    }

    public Response(String code, Long employeeId) {
        this.code = code;
        this.employeeId = employeeId;
    }

    /**
     * Method tạo một response success.
     *
     * @param code Mã của response.
     * @return Response success.
     */
    public static Response success(String code) {
        return new Response(code);
    }

    public static Response success(String code, Long employeeId) {
        return new Response(code, employeeId);
    }

    /**
     * Method tạo một response error.
     *
     * @param code Mã của response.
     * @return Response error.
     */
    public static Response error(String code, Long employeeId) {
        return new Response(code, employeeId);
    }

    /**
     * Method tạo một response warning.
     *
     * @param code Mã của response.
     * @return Response warning.
     */
    public static Response warning(String code) {
        return new Response(code);
    }

    /**
     * Method tạo một response success.
     *
     * @param code         Mã của response.
     * @param totalRecords Tổng số bản ghi.
     * @return Response success.
     */
    public static Response success(String code, int totalRecords) {
        return new Response(code, totalRecords);
    }

    /**
     * Thiết lập data của response.
     *
     * @param data Data cần thiết lập.
     * @return Response.
     */
    public Response withData(Object data) {
        this.data = data;
        return this;
    }

    /**
     * Thiết lập Message của response.
     *
     * @param message Message cần thiết lập.
     * @return Response.
     */
    public Response withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Thiết lập danh sách messages của response.
     *
     * @param messages Danh sách messages cần thiết lập.
     * @return Response.
     */
    public Response withMessages(List<Message> messages) {
        this.messages = messages;
        return this;
    }

}
