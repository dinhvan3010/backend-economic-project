package net.codejava.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.codejava.Model.Brand;
import net.codejava.Model.Product;
import net.codejava.Model.ProductFavoutite;
import net.codejava.Model.User;
import net.codejava.Services.IListConverter;
import net.codejava.Services.IManageProductService;
import net.codejava.converter.FavoutiteConverter;
import net.codejava.converter.ProductConverter;
import net.codejava.dto.DataPagingResp;
import net.codejava.dto.ProductRespDTO;
import net.codejava.repository.BrandRepository;
import net.codejava.repository.ProductFavouriteRepository;
import net.codejava.repository.ProductRepository;
import net.codejava.repository.UserRepository;
import net.codejava.request.AddFavouriteRequest;
import net.codejava.response.StatusResp;

@RestController
@RequestMapping("/api/product")
public class ProductController extends AbstractRestController {
	@Autowired
	ProductRepository productRepository;

	@Autowired
	BrandRepository brandRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductFavouriteRepository favouriteRepository;

	@Autowired
	private IManageProductService productService;

	@Autowired
	IListConverter converter;

	@GetMapping("/list")
	public StatusResp getProducts(@RequestParam(required = false) String name,
			@RequestParam(defaultValue = "unitPrice") String softBy, @RequestParam(required = false) int brandId,
			@RequestParam (required = false) int cateloryId, @RequestParam int pageNum, @RequestParam int pageSize) {
		StatusResp resp = new StatusResp();
		Pageable paging = PageRequest.of(pageNum, pageSize);
		DataPagingResp<ProductRespDTO> dataPagingResp = productService.getProduct(pageNum, pageSize, name, softBy,
				brandId, cateloryId);
		Brand brand = brandRepository.findOneById(brandId);
		List<Product> products = productRepository.findAllByBrand_IdAndCatelory_Id(brandId, cateloryId);
		List<ProductRespDTO> list = converter.toListResponse(products, ProductConverter::toRespDTO);
		resp.setData(list);
		return resp;
	}

	@GetMapping("/favourite/list")
	public StatusResp getProductsFavourite() {
		StatusResp resp = new StatusResp();
		User user = getUserSession();
		List<ProductFavoutite> list = favouriteRepository.findByUser_Id(user.getId());
		List<ProductRespDTO> products = converter.toListResponse(list, FavoutiteConverter::toRespDTO);
		resp.setData(products);
		return resp;
	}

	@PostMapping("/favourite/add")
	public StatusResp addProductFavourite(@RequestBody AddFavouriteRequest request) {
		StatusResp resp = new StatusResp();
		User user = getUserSession();
		ProductFavoutite favoutite = new ProductFavoutite();
		Product product = productRepository.findOneById(request.getProductId());
		favoutite.setProduct(product);
		favoutite.setUser(userRepository.getById(user.getId()));
		favouriteRepository.save(favoutite);

		Boolean check = favouriteRepository.existsByProductAndUser(product, userRepository.getById(user.getId()));

		return resp;
	}

}
