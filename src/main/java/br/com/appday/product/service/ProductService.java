package br.com.appday.product.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.appday.product.domain.Product;
import br.com.appday.product.domain.repository.ProductRepository;
import io.minio.MinioClient;

@Component
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void saveImage(String id, InputStream fileInputStream, String contentType) {
        Product product = productRepository.findOne(id);
        // TODO chamar minio
        try {
            MinioClient minioClient = new MinioClient("http://localhost:9000", "N4BEIOH6PAHSG25I4TFI",
                    "YN65o85VPABXYzYHbs4aNPdGotLknXmKokz91+3m");

            minioClient.putObject("dojo", id, fileInputStream, ((FileInputStream) fileInputStream).getChannel().size(), contentType);

            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO associar imagem do minio ao produto
        // TODO atualizar produto
    }

}
