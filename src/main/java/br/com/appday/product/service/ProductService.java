package br.com.appday.product.service;

import br.com.appday.product.domain.Product;
import br.com.appday.product.domain.repository.ProductRepository;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!teste")
public class ProductService {

    Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Value("{spring.sendgrid.api-key}")
    private String API_KEY;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private RedisTemplate<String, Product> productsCache;

    @Autowired
    private SendGrid sendGrid;

    private MailSender mailSender;

    private SimpleMailMessage templateMessage;

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            product.setInCache(inCache(product));
        }
        return products;
    }

    public Product fundById(String id) {
        return productRepository.findOne(id);
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

    public void sendMail(String emailAddress, String subject, String body) {

        SendGrid sendgrid = new SendGrid(API_KEY);
        SendGrid.Email welcomeMail = new SendGrid.Email();
        welcomeMail.addTo(emailAddress);
        welcomeMail.setSubject(subject);
        welcomeMail.setText(body);

        try {
            SendGrid.Response response = sendgrid.send(welcomeMail);
            System.out.println(response.getMessage());
        } catch (SendGridException sge) {
            LOGGER.error(sge.getMessage());
        }

    }
}
