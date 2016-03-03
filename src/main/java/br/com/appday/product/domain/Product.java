package br.com.appday.product.domain;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

public class Product {

    @Id
    String id;

    String name;

    String description;

    BigDecimal price;

    BigDecimal discount;

    Integer installmentQuantity;

    String imageLocation;

    BigDecimal freightValue;

    public Product() {

    }

    public Product(String name) {
        this.name = name;
    }

    /**
     * Getter for field id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for field name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for field description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for field price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Getter for field discount.
     *
     * @return the discount
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * Getter for field installmentQuantity.
     *
     * @return the installmentQuantity
     */
    public Integer getInstallmentQuantity() {
        return installmentQuantity;
    }

    /**
     * Getter for field imageLocation.
     *
     * @return the imageLocation
     */
    public String getImageLocation() {
        return imageLocation;
    }

    /**
     * Getter for field freightValue.
     *
     * @return the freightValue
     */
    public BigDecimal getFreightValue() {

        return freightValue;
    }

    public void setName(String name) {
        this.name = name;
    }

}
