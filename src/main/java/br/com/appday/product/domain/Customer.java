package br.com.appday.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by biasetti on 04/03/16.
 */
@Entity
public class Customer {

  /**
   * Instantiates a new Customer.
   */
  public Customer() {
  }

  /**
   * Instantiates a new Customer.
   *
   * @param name the name
   */
  public Customer(String name) {
    this.name = name;
  }

  @Id
  @GeneratedValue
  private Long id;

  @Column
  private String name;

  @Column
  private String surname;

  @Column
  private Date birthday;

  @Column
  private Date createdAt;

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets surname.
   *
   * @return the surname
   */
  public String getSurname() {
    return surname;
  }

  /**
   * Sets surname.
   *
   * @param surname the surname
   */
  public void setSurname(String surname) {
    this.surname = surname;
  }

  /**
   * Gets birthday.
   *
   * @return the birthday
   */
  public Date getBirthday() {
    return birthday;
  }

  /**
   * Sets birthday.
   *
   * @param birthday the birthday
   */
  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  /**
   * Gets created at.
   *
   * @return the created at
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets created at.
   *
   * @param createdAt the created at
   */
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

}
