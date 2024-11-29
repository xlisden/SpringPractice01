package com.day.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.day.beans.Product;

public interface IProductsRepository extends JpaRepository<Product, Integer>{

}
