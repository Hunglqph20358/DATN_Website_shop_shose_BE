package com.example.backend.core.constant;

public class AppConstant {
        public static final Integer PAGE_SIZE = 10;
        public static final  String[] API_ADMIN = {
                "/api/admin/home",
                "/api/admin/discount",
                "/api/admin/discount/*",
                "/api/admin/voucher",
                "/api/admin/voucher/*"
        };
        public static final String[] API_STAFF = {
                "/api/staff/home"
        };
        public static final String[] API_VIEW = {
                "api/view"
        };
}
