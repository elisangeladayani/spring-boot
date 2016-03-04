package br.com.appday.minio;

import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by diegorubin on 04/03/16.
 */
@Component
public class MinioTemplate {

    private String host;
    private String accessKey;
    private String secretKey;

    Logger LOGGER = LoggerFactory.getLogger(MinioTemplate.class);

    @Autowired
    public MinioTemplate(@Value("${minio.host}") String host, @Value("${minio.access-key}") String accessKey,
                         @Value("${minio.secret-key}") String secretKey) {

        this.host = host;
        this.accessKey = accessKey;
        this.secretKey = secretKey;

    }

    public MinioClient getMinioClient() {
        MinioClient minioClient = null;

        try{
            minioClient = new MinioClient(this.host, this.accessKey, this.secretKey);
        } catch (Exception e) {
           LOGGER.error("Error on connect with minio: ", e);
        }
        return minioClient;
    }

}
