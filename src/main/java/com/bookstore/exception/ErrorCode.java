package com.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    BOOK_QUANTITY_EXCEEDED(1009, "Book quantity exceeded", HttpStatus.BAD_REQUEST),
    BOOK_NOT_EXISTED(1010, "Book not existed", HttpStatus.NOT_FOUND),
    AUTHOR_NOT_EXISTED(1011, "Author not existed", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_EXISTED(1012, "Category not existed", HttpStatus.NOT_FOUND),
    IMPORT_RECEIPT_NOT_EXISTED(1013, "Import receipt not existed", HttpStatus.NOT_FOUND),
    INVOICE_NOT_EXISTED(1014, "Invoice not existed", HttpStatus.NOT_FOUND),
    PAYMENT_RECEIPT_NOT_EXISTED(1015, "Payment receipt not existed", HttpStatus.NOT_FOUND),
    CATEGORY_EXISTED(1016, "Category existed", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_IMPORT_QUANTITY(1017, "Import quantity must be at least 150 books", HttpStatus.BAD_REQUEST),
    DEBT_AMOUNT_LIMIT_EXCEEDED(1018, "Debt amount limit exceeded", HttpStatus.BAD_REQUEST),
    BOOK_QUANTITY_UNDER_LIMIT(1019, "Book quantity under limit exceeded", HttpStatus.BAD_REQUEST),
    MONTHLY_DEBT_REPORT_NOT_EXISTED(1020,"Monthly debt report not existed",HttpStatus.NOT_FOUND),
    BOOK_EXISTED(1021, "Book title existed", HttpStatus.BAD_REQUEST),
    MONTHLY_INVENTORY_REPORT_NOT_EXISTED(1022,"Monthly inventory report not existed",HttpStatus.NOT_FOUND),
    MONTHLY_INVENTORY_REPORT_DETAIL_NOT_EXISTED(1023,"Monthly inventory report detail not existed",HttpStatus.NOT_FOUND);
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
