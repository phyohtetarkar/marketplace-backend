package com.shoppingcenter.app.controller.user;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingcenter.app.controller.user.dto.UserDTO;
import com.shoppingcenter.app.controller.user.dto.UserEditDTO;
import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.UploadFile;
import com.shoppingcenter.service.user.UserService;
import com.shoppingcenter.service.user.model.User;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/profile")
@Tag(name = "Profile")
public class ProfileController {

    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper modelMapper;

    @PutMapping
    public void update(@RequestBody UserEditDTO user, Authentication authentication) {
        user.setId(authentication.getName());
        service.update(modelMapper.map(user, User.class));
    }

    @PostMapping(value = "image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void uploadImage(@RequestPart MultipartFile file, Authentication authentication) {
        try {
            if (file.isEmpty()) {
                return;
            }

            UploadFile uploadFile = new UploadFile();
            uploadFile.setInputStream(file.getInputStream());
            uploadFile.setOriginalFileName(file.getOriginalFilename());
            uploadFile.setSize(file.getSize());
            service.uploadImage(authentication.getName(), uploadFile);
        } catch (IOException e) {
            throw new ApplicationException("Failed to upload image");
        }
    }

    @GetMapping
    public UserDTO getLoginUser(Authentication authentication) {
        return modelMapper.map(service.findById(authentication.getName()), UserDTO.class);
    }

}
