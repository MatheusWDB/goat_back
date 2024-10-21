package br.com.nexus.goatimports.products;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface IProductRepository extends JpaRepository<ProductModel, UUID> {
    List<ProductModel> findByIdStore(UUID idStore);
}
