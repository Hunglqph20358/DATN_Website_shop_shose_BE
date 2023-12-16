package com.example.backend.core.constant;

public class AppConstant {

        public static final Integer PAGE_SIZE = 10;
        public static final Integer CHO_XAC_NHAN = 0;
        public static final Integer CHO_XU_LY = 1;
        public static final Integer DANG_GIAO_HANG = 2;
        public static final Integer HOAN_THANH = 3;
        public static final Integer HOAN_HUY = 4;
        public static final Integer CHUA_THANH_TOAN = 1;
        public static final Integer DA_THANH_TOAN = 0;
        public static final Integer BO_LO_LAN1 = 1;
        public static final Integer BO_LO_LAN2 = 2;
        public static final Integer BO_LO_LAN3 = 3;
        public static final String ALIGN_LEFT = "LEFT";
        public static final String ALIGN_RIGHT = "RIGHT";
        public static final String STRING = "STRING";
        public static final String NUMBER = "NUMBER";
        public static final String LIST = "LIST";
        public static final String DOUBLE = "DOUBLE";
        public static final String ERRORS = "ERRORS";
        public static final String CENTER = "CENTER";
        public static final String NO = "NO";
        public static final String NEXT_LINE = "\n";
        public static final String CHAR_STAR = "*";
        public static final Long EXPORT_TEMPLATE = 0l;
        public static final Long EXPORT_DATA = 1l;
        public static final Long EXPORT_ERRORS = 2l;
        public static final String DOT = ".";
        public static final String YYYYMMDDHHSS = "yyyyMMddHHss";
        public static final String EXTENSION_XLSX = "xlsx";
        public static final String EXTENSION_XLS = "xls";
        public static final String COMMA_DELIMITER = ",";
        public static final String MIME_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8";
        public static final String ENCODING_UTF8 = "UTF-8";
        public static final Long IMPORT_INSERT = 0l;
        public static final Long IMPORT_UPDATE = 1l;



        public static final  String[] API_ADMIN = {

                "/api/admin/discount",
                "/api/admin/discount/*",
                "/api/admin/discount/product",
                "/api/admin/discount/searchByDate*",
                "/api/admin/discount/searchByDiscount*",
                "/api/admin/discount/searchByCategory*",
                "/api/admin/discount/searchByProduct*",
                "/api/admin/discount/searchByBrand*",
                "/api/admin/discount/kichHoat/*",
                "/api/admin/discount/KH",
                "/api/admin/discount/KKH",
                "/api/admin/discount/discount/export-data",

                "/api/admin/voucher",
                "/api/admin/voucher/*",
                "/api/admin/voucher/searchByDate*",
                "/api/admin/voucher/kichHoat/*",
                "/api/admin/voucher/searchByCustomer*",
                "/api/admin/voucher/searchByVoucher*",
                "/api/admin/voucher/KH",
                "/api/admin/voucher/KKH",
                "/api/admin/voucher/export-data",

                "/api/admin/voucherFS",
                "/api/admin/voucherFS/*",
                "/api/admin/voucherFS/searchByDate*",
                "/api/admin/voucherFS/searchByVoucherFS*",
                "/api/admin/voucherFS/searchByCustomer*",
                "/api/admin/voucherFS/kichHoat/*",
                "/api/admin/voucherFS/KH",
                "/api/admin/voucherFS/KKH",
                "/api/admin/voucherFS/export-data",

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
                "/view/api/update-address/config",
                "/view/api/detail-address/*",
                "/view/api/get-address",
                "/view/api/get-all-voucher",
                "/view/api/get-voucher",
                "/view/api/create-order-detail",
                "/view/api/get-order-detail/by-order/*",
                "/view/api/create-order/not-login",
                "/view/api/send-email-completeOrder",
                "/view/api/send-email-completeOrder/not-login",
                "/view/api/cancel-order-view",
        };
}
