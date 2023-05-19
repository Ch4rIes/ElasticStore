package com.charleszuo.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Builder;
import java.math.BigDecimal;
import org.springframework.data.annotation.Id;

@Document(value="product")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Product {
    @Id
    private String id;
    private String name;
    private BigDecimal price;
    private String description;

}
