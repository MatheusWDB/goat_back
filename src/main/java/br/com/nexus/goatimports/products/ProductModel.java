package br.com.nexus.goatimports.products;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
//import java.sql.Blob;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_products")
public class ProductModel {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    
    private UUID idStore;

    private String product;
    private String description;
    private String size;
    private BigDecimal price;
    private Integer stock;
    //private Blob image;
    //private Enum status;

    @CreationTimestamp
    private LocalDateTime createAt;

    //private LocalDateTime deletedAt;
    //private Boolean deleted = false;

}
