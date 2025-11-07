package com.Phamducdoanh.Backend.Builder;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchBuilder {
    String productName;
    Long minPrice;
    Long maxPrice;
    String Brand;
    Long CategoryId;
}
