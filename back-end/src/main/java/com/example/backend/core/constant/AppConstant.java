package com.example.backend.core.constant;

public class AppConstant {

        public static final Integer PAGE_SIZE = 10;
        public static final Integer CHO_XAC_NHAN = 0;
        public static final Integer CHO_XU_LY = 1;
        public static final Integer DANG_GIAO_HANG = 2;
        public static final Integer HOAN_THANH = 3;
        public static final Integer HOAN_HUY = 4;



        public static final  String[] API_ADMIN = {
                "/api/admin/home",
                "/api/admin/discount",
                "/api/admin/product",
                "/api/admin/discount/*",
                "/api/admin/voucher",
                "/api/admin/voucher/*",
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
                "admin/api/staff",
                "/api/admin/get-all-order",
                "/api/admin/get-order-detail/by-order/*",
                "/api/admin/cancel-order",
                "/api/admin/progressing-order",
                "/api/admin/complete-order",
                "/api/admin/ship-order",

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
                "/view/api/get-all-order",
                "/view/api/get-all-address",
                "/view/api/create-address",
                "/view/api/create-address",
                "/view/api/get-address",
                "/view/api/get-all-voucher",
                "/view/api/get-voucher",
                "/view/api/create-order-detail",
                "/view/api/get-order-detail/by-order/*",
                "/view/api/create-order/not-login",
                "/view/api/send-email-completeOrder",
                "/view/api/send-email-completeOrder/not-login",
        };
}
