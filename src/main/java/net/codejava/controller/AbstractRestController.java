package net.codejava.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import net.codejava.Services.UserService;
import net.codejava.exceptions.MyAppException;
import net.codejava.jwt.JwtTokenUtil;
import net.codejava.utils.StaticData;

 
public abstract class AbstractRestController {
    Logger logger = LoggerFactory.getLogger(AbstractRestController.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    protected UserService userServiceImpl;

//    protected MySpringUserInfo getUserInfo(HttpServletRequest request){
//        try{
//            String jwt = request.getHeader(JWT_AUTHORIZATION);
//            Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwt);
//            return new MySpringUserInfo((String) claims.get("usrId"), (String) claims.get("email"));
//        }catch (Exception e){
//
//        }
//        throw new MyAppException(HttpStatus.UNAUTHORIZED.name(),
//                HttpStatus.UNAUTHORIZED.value());
//    }

    protected void checkBindingResult(BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder erroMsg = new StringBuilder();
            for (FieldError fieldError: bindingResult.getFieldErrors()){
                erroMsg.append(" " + fieldError.getField() + ": " + fieldError.getDefaultMessage());
                erroMsg.append("\r\n");
            }

            throw new MyAppException(StaticData.ERROR_CODE.INVALID_SUBMIT_DATA.getMessage()+ "\r\n Error:\r\n" + erroMsg.toString(), StaticData.ERROR_CODE.INVALID_SUBMIT_DATA.getCode());
        }
    }

//    protected User getUserSession(HttpServletRequest request){
//        MySpringUserInfo mySpringUserInfo = getUserInfo(request);
//        User userSession = userServiceImpl.getByUsrName(mySpringUserInfo.getUsrId());
//        if (userSession == null) {
//            throw new MyAppException(StaticData.ERROR_CODE.NOT_FOUND_USER_SESSION.getMessage(),
//                    StaticData.ERROR_CODE.NOT_FOUND_USER_SESSION.getCode());
//        }
//
//        return userSession;
//    }
}
