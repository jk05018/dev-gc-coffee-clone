package org.prgms.gccoffee.controller;

import java.util.List;

import org.prgms.gccoffee.model.Product;
import org.prgms.gccoffee.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	public String productPage(Model model){
		final List<Product> products = productService.getAllProducts();

		model.addAttribute("products", products);

		return "product-list";
	}

	@GetMapping("/new-product")
	public String newProductPage(){
		return "new-product";
	}

	@PostMapping("/products")
	public String newProduct(CreateProductRequest createProductRequest){
		productService.createProduct(createProductRequest.getProductName(), createProductRequest.getCategory(),
			createProductRequest.getPrice(), createProductRequest.getDescription());

		return "redirect:/products";


	}
}
