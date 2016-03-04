package br.com.appday.product.receivers;

import br.com.appday.product.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by diegorubin on 04/03/16.
 */
public class ProductReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductReceiver.class);

    @Autowired
    public ProductReceiver() {
    }

    public void receiveMessage(String message) {
        LOGGER.info("product:" + message);
    }

}
