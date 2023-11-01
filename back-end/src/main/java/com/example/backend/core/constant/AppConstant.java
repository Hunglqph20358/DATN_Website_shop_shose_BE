package com.example.backend.core.constant;

public class AppConstant {
        public static final Integer PAGE_SIZE = 10;
        public static final  String[] API_ADMIN = {
                "/api/admin/home"
        };
        public static final String[] API_STAFF = {
                "/api/staff/home"
        };
        public static final String[] API_VIEW_PERMIT = {
                "/view/api/get-product-noi-bat",
                "/view/api/get-detail-product/*",
                "/view/api/get-all-size",
                "/view/api/get-all-color",
                "/view/api/cart",
                "/view/api/create-payment",
                "/view/api/create-order",
                "/view/api/create-address"

        };
}
