package br.com.appday.product.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import br.com.appday.product.domain.Product;
import br.com.appday.product.domain.repository.ProductRepository;
import io.minio.MinioClient;

@Component
@Profile("!teste")
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private RedisTemplate<String, Product> productsCache;

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        for(Product product : products) {
            product.setInCache(inCache(product));
        }
        return products;
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void saveImage(String id, String filePath) {

        try {
            minioClient.putObject("dojo", id, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO associar imagem do minio ao produto
        // TODO atualizar produto
    }

    // exemplos de uso do redisTemplate
    public void sendMessage(Product product) {
        redisTemplate.convertAndSend("products", product.getName());
    }

    public void insertInRedisCache(Product product) {
        ValueOperations<String, Product> value = productsCache.opsForValue();
        value.set(productKey(product), product);
        sendMessage(product);
    }

    private boolean inCache(Product product) {
        return productsCache.hasKey(productKey(product));
    }

    private String productKey(Product product) {
        return "product." + product.getId();
    }

}
