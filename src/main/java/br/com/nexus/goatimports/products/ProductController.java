package br.com.nexus.goatimports.products;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nexus.goatimports.utils.Utils;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductRepository productRepository;

    @PostMapping("/{idStore}")
    public ResponseEntity<?> create(@RequestBody ProductModel productModel, @PathVariable UUID idStore) {

        var product = productModel;
        product.setIdStore(idStore);

        var productCreated = this.productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
    }

    @GetMapping("/{idStore}")
    public ResponseEntity<?> get(@PathVariable UUID idStore) {

        var products = this.productRepository.findByIdStore(idStore);

        return ResponseEntity.ok().body(products);
    }

    @PutMapping("/{idProduct}")
    public ResponseEntity<?> update(@PathVariable UUID idProduct, @RequestBody ProductModel productModel){

        var product = this.productRepository.findById(idProduct).orElse(null);

        Utils.copyNonNullProperties(productModel, product);

        var productUpdated = this.productRepository.save(product);

        return ResponseEntity.ok().body(productUpdated);
    }

    @DeleteMapping("/{idProduct}")
    private ResponseEntity<?> delete(@PathVariable UUID idProduct){

        var product = this.productRepository.findById(idProduct);

        this.productRepository.deleteById(idProduct);

        return ResponseEntity.ok().body("Item deletado: " + product);
    }
}
