package com.bookstore.dto.request;

import java.math.BigDecimal;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCreationRequest {
    String name;
    Integer publishedYear;
    BigDecimal importPrice;
    int quantity;
    List<String> authors;
    List<String> categories;

    public List<String> getAuthors() {
        return this.authors;
    }
}
