/*
 * Copyright (c) 2023 Luvina Software Company
 *
 * Constants.java, Jun 09, 2023 LA31-AM
 */

package com.luvina.la.config;

public class Constants {

    private Constants() {
    }

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final boolean IS_CROSS_ALLOW = true;

    public static final String JWT_SECRET = "Luvina-Academe";
    public static final long JWT_EXPIRATION = 160 * 60 * 60; // 7 day

    // config endpoints public
    public static final String[] ENDPOINTS_PUBLIC = new String[]{
            "/",
            "/login/**",
            "/error/**"
    };

    // config endpoints for USER role
    public static final String[] ENDPOINTS_WITH_ROLE = new String[]{
            "/user/**"
    };

    // user attributies put to token
    public static final String[] ATTRIBUTIES_TO_TOKEN = new String[]{
            "employeeId",
            "employeeName",
            "employeeLoginId",
            "employeeEmail"
    };

    public static class RESPONSE_TYPE {
        public static final String SUCCESS = "SUCCESS";
        public static final String ERROR = "ERROR";
        public static final String WARNING = "WARNING";
        public static final String CONFIRM = "CONFIRM";
    }


    public static class RESPONSE_CODE {
        public static final String SUCCESSFUL = "200";
        public static final String SERVER_ERROR = "500";

    }

    public static class ERROR_CODE {
        public static final String NOT_NULL = "ER001";

        public static final String LENGTH = "ER006";

        public static  final String INTEGER_NUMBER = "ER018";

        public static  final String NOT_EXISTS = "ER004";
    }
}
