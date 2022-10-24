package com.ittalents.airbnb.controller;

import com.ittalents.airbnb.model.dto.PhotoDto;
import com.ittalents.airbnb.model.dto.propertyDTOs.GeneralPropertyResponseDto;
import com.ittalents.airbnb.model.dto.propertyDTOs.PropertyCreationDto;
import com.ittalents.airbnb.model.repositories.PropertyRepository;
import com.ittalents.airbnb.model.repositories.UserRepository;
import com.ittalents.airbnb.services.PropertyService;
import com.ittalents.airbnb.util.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class PropertyController extends MasterController{

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("properties/add")
    public PropertyCreationDto add(@RequestBody PropertyCreationDto dto, HttpServletRequest request){
         SessionManager.validateLogin(request);
        return propertyService.add(dto, (Long)request.getSession().getAttribute(SessionManager.USER_ID));
    }
    @PostMapping("/properties/{id}/photo")
    public PhotoDto uploadPhoto(@PathVariable long id, @RequestParam(name = "photo")MultipartFile photo, HttpServletRequest req){
        SessionManager.validateLogin(req);
        return propertyService.uploadPhoto(id, photo);
    }


    @GetMapping("/users/properties")
    public List<GeneralPropertyResponseDto> getUserProperties(HttpServletRequest request) {
        SessionManager.validateLogin(request);
        return propertyService.getUserProperties((Long) request.getSession().getAttribute(SessionManager.USER_ID));
    }
    @GetMapping("/properties/{id}")
    public GeneralPropertyResponseDto getById(@PathVariable long id){
        return propertyService.getPropertyById(id);
    }

    @GetMapping("/properties")
    public List<GeneralPropertyResponseDto> getAll(){
        return propertyService.findAll();
    }


}
