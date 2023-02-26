package net.codejava.controller.admin;

import net.codejava.controller.AbstractRestController;
import net.codejava.converter.BrandConverter;
import net.codejava.converter.OrderDetailConverter;
import net.codejava.converter.UserConverter;
import net.codejava.dto.BrandRespDTO;
import net.codejava.dto.OrderDetailRespDTO;
import net.codejava.dto.UserRespDTO;
import net.codejava.exceptions.MyAppException;
import net.codejava.model.Brand;
import net.codejava.model.Profile;
import net.codejava.model.User;
import net.codejava.repository.BrandRepository;
import net.codejava.repository.UserRepository;
import net.codejava.request.AdminUpdateUserRequest;
import net.codejava.request.RegisterRequest;
import net.codejava.request.UpdateBrandRequest;
import net.codejava.response.StatusResp;
import net.codejava.services.IListConverter;
import net.codejava.services.IManageBrandService;
import net.codejava.services.IManageUserService;
import net.codejava.utils.StaticData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController extends AbstractRestController {

    @Autowired
    IListConverter listConverter;


    @Autowired
    IManageUserService userService;

    @Autowired
    UserRepository userRepository;


    @GetMapping("/list")
    public StatusResp getListUser() {
        StatusResp resp = new StatusResp();
        List<User> users = userService.getAllUser();
        List<UserRespDTO> userRespDTOS = listConverter.toListResponse(users, UserConverter::toRespDTO);
        resp.setData(userRespDTOS);
        return resp;
    }

    @GetMapping("/detail")
    public StatusResp getUserDetail(@RequestParam int userId) {
        StatusResp resp = new StatusResp();
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new MyAppException(StaticData.ERROR_CODE.USER_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.USER_NOT_FOUND.getCode());
        }
        UserRespDTO userRespDTO = UserConverter.toRespDTO(user);
        resp.setData(userRespDTO);
        return resp;
    }

    @PostMapping(value = "/add")
    public StatusResp registerUser(@RequestPart("user") @Valid RegisterRequest request, BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        StatusResp statusResp = new StatusResp();
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new MyAppException(StaticData.ERROR_CODE.NEW_PASSWORD_CONFIRMATION_NOT_MATCH.getMessage(), StaticData.ERROR_CODE.NEW_PASSWORD_CONFIRMATION_NOT_MATCH.getCode());
        }
        if (userService.existsByEmail(request.getEmail())) {
            throw new MyAppException(StaticData.ERROR_CODE.CUSTOMER_EXIST_EMAIL.getMessage(), StaticData.ERROR_CODE.CUSTOMER_EXIST_EMAIL.getCode());
        }
        userService.registerUser(request);
        return statusResp;
    }

    @PutMapping("/disable")
    public StatusResp disableUser(@RequestParam int userId) {
        StatusResp resp = new StatusResp();
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new MyAppException(StaticData.ERROR_CODE.USER_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.USER_NOT_FOUND.getCode());
        }
        user.setEnabled(false);
        userRepository.save(user);
        return resp;
    }

    @PutMapping(value = "/update")
    public StatusResp updateInfo(@RequestPart("user") @Valid @RequestBody AdminUpdateUserRequest request, BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        StatusResp statusResp = new StatusResp();
//        if (!request.getPassword().equals(request.getConfirmPassword())) {
//            throw new MyAppException(StaticData.ERROR_CODE.NEW_PASSWORD_CONFIRMATION_NOT_MATCH.getMessage(), StaticData.ERROR_CODE.NEW_PASSWORD_CONFIRMATION_NOT_MATCH.getCode());
//        }
        User user = userService.findUserById(request.getUserId());
        if (user == null) {
            throw new MyAppException(StaticData.ERROR_CODE.USER_NOT_FOUND.getMessage(), StaticData.ERROR_CODE.USER_NOT_FOUND.getCode());
        }
        Profile profile = user.getProfile();
        if (request.getFirstName() != null) {
            profile.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            profile.setLastName(request.getLastName());
        }
        if (request.getGender() != null) {
            profile.setGender(request.getGender());
        }
        if (request.getImage() != null) {
            profile.setImage(request.getImage());
        }
        if (request.getBirthday() != null) {
            profile.setBirthday(request.getBirthday());
        }
        userRepository.save(user);
        return statusResp;
    }


}
