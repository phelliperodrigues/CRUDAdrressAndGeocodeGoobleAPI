package com.stoom.address.controller;

import com.stoom.address.model.Address;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @GetMapping
    public ResponseEntity<List<Address>> findAll(){
        return null;
    }
}
