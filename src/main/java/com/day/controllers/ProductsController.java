package com.day.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.day.beans.Product;
import com.day.models.ProductDto;
import com.day.services.IProductsRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductsController {
	
	@Autowired
	private IProductsRepository productsRepository;
	
	@GetMapping( {"", "/"} )
	public String showProducts(Model model) {
//		List<Product> products = productsRepository.findAll();
		List<Product> products = productsRepository.findAll(Sort.by(Sort.Direction.ASC, "brand"));
		model.addAttribute("products", products);
		return "products/list";
	}
	
	@GetMapping( {"/create"} )
	public String showPageCreatedProduct(Model model) {
		ProductDto productDto = new ProductDto();
		model.addAttribute("productDto", productDto);
		return "products/createProduct";
	}

	@PostMapping( {"/create"} )
	public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result) {
		if(result.hasErrors()) {
			return "products/createProduct";
		}
		
		Product product = new Product();
		product.setName(productDto.getName());
		product.setBrand(productDto.getBrand());
		product.setDescription(productDto.getDescription());
		
		productsRepository.save(product); 
		return "redirect:/products";
	}
	
	@GetMapping({"/edit"})
	public String showEditProduct(Model model, @RequestParam int id) {
		try {
			Product product = productsRepository.findById(id).get();
			model.addAttribute("product", product);
			
			ProductDto productDto = new ProductDto();
			productDto.setName(product.getName());
			productDto.setBrand(product.getBrand());
			productDto.setDescription(product.getDescription());
			model.addAttribute("productDto", productDto);
			
		} catch (Exception e) {
			System.out.println("showEditProduct() " + e.getMessage());
			return "redirect:/products";
		}
		return "products/editProduct";
	}
	
	@PostMapping("/edit")
	public String editProduct(Model model, @RequestParam int id, @Valid @ModelAttribute ProductDto productDto, BindingResult result) {
		try {
			Product product = productsRepository.findById(id).get();
			model.addAttribute("product", product);
			
			if(result.hasErrors()) {
				return "products/editProduct";
			}
			
			product.setName(productDto.getName());
			product.setBrand(productDto.getBrand());
			product.setDescription(productDto.getDescription());
			
			productsRepository.save(product);
		} catch (Exception e) {
			System.out.println("editProduct() " + e.getMessage());
			return "redirect:/products";
		}
		return "redirect:/products";
	}
}
