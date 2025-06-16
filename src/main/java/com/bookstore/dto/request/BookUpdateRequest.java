package com.bookstore.dto.request;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateRequest {
    Integer bookId;
    String name;
    List<String> authors;
    List<String> categories;
    Integer publishedYear;
    BigDecimal importPrice;
    Integer quantity;
}
