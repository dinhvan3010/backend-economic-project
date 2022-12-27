package net.codejava.controller;

import net.codejava.model.Product;
import net.codejava.model.User;
import net.codejava.model.WishList;
import net.codejava.services.IListConverter;
import net.codejava.services.IManageProductService;
import net.codejava.converter.WishListConverter;
import net.codejava.dto.ProductRespDTO;
import net.codejava.exceptions.MyAppException;
import net.codejava.repository.ProductRepository;
import net.codejava.repository.UserRepository;
import net.codejava.repository.WishListRepository;
import net.codejava.response.StatusResp;
import net.codejava.utils.StaticData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishListController extends AbstractRestController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    private IManageProductService productService;

    @Autowired
    IListConverter converter;


    @GetMapping("/list")
    public StatusResp getProductsFavourite() {
        StatusResp resp = new StatusResp();
        User user = getUserSession();
        List<WishList> list = wishListRepository.findByUser_Id(user.getId());
        List<ProductRespDTO> products = converter.toListResponse(list, WishListConverter::toRespDTO);
        resp.setData(products);
        return resp;
    }

    @PostMapping("/add")
    public StatusResp addProductFavourite(@RequestParam int productId) {
        StatusResp resp = new StatusResp();
        User user = getUserSession();
        WishList wishList = new WishList();
        Product product = productRepository.findOneById(productId);
        if (product == null) {
            throw new MyAppException(StaticData.ERROR_CODE.PRODUCT_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.PRODUCT_NOT_FOUND.getCode());
        }
        Boolean check = wishListRepository.existsByProductAndUser(product, userRepository.getById(user.getId()));
        if (Boolean.TRUE.equals(check)) {
            throw new MyAppException(StaticData.ERROR_CODE.PRODUCT_EXIST_FAVORITE.getMessage(), StaticData.ERROR_CODE.PRODUCT_EXIST_FAVORITE.getCode());
        }
        wishList.setProduct(product);
        wishList.setUser(userRepository.getById(user.getId()));
        wishListRepository.save(wishList);
        return resp;
    }

    @DeleteMapping("/delete")
    public StatusResp deleteWishList(@RequestParam int productId) {
        StatusResp resp = new StatusResp();
        User user = getUserSession();
        Product product = productRepository.findOneById(productId);
        Boolean check = wishListRepository.existsByProductAndUser(product, userRepository.getById(user.getId()));
        if (Boolean.FALSE.equals(check)) {
            throw new MyAppException(StaticData.ERROR_CODE.PRODUCT_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.PRODUCT_NOT_FOUND.getCode());
        }
        WishList wishList = wishListRepository.findAllByProduct_IdAndUser_Id(productId, user.getId());
        wishListRepository.deleteItemWishList(wishList.getId());
        return resp;
    }


    @GetMapping("/check")
    public StatusResp checkWishList(@RequestParam int productId) {
        StatusResp resp = new StatusResp();
        User user = getUserSession();
        Product product = productRepository.findOneById(productId);
        Boolean check = wishListRepository.existsByProductAndUser(product, userRepository.getById(user.getId()));
        resp.setData(check);
        return resp;
    }

}
