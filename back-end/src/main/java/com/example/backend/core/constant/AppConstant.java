package com.example.backend.core.constant;

public class AppConstant {
        public static final Integer PAGE_SIZE = 10;
        public static final  String[] API_ADMIN = {
                "/api/admin/home",
                "/api/admin/brand/*/*",
                "/api/admin/brand/*",
                "/api/admin/product/*",
                "/api/admin/product/*/*",
                "/api/admin/material/*",
                "/api/admin/material/*/*",
                "/api/admin/color/*",
                "/api/admin/color/*/*",
                "/api/admin/size/*",
                "/api/admin/size/*/*",
                "/api/admin/category/*",
                "/api/admin/category/*/*",
                "/api/admin/sole/*",
                "/api/admin/sole/*/*",

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
                "/view/api/staff/finbyId/*",
                "/view/api/customer/finbyId/*",
                "/view/api/create-payment",
                "/view/api/create-order",
                "/view/api/get-all-address",
                "/view/api/create-address"
        };
}
