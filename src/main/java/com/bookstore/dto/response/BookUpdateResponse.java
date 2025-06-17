package com.bookstore.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookUpdateResponse {
    Integer bookId;
    String name;
    BigDecimal importPrice;
    int quantity;

}
