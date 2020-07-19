package com.stoom.address.controller;

import com.stoom.address.model.Address;
import com.stoom.address.repository.AddressRepository;
import com.stoom.address.util.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static java.util.stream.Collectors.*;

@RestController
@RequestMapping("/address")
public class AddressController {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private AddressRepository repository;

    @Autowired
    private GeoService geoService;

    @GetMapping
    public ResponseEntity<List<Address>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> findById(@PathVariable("id") String uuid) {
        return repository.findById(UUID.fromString(uuid))
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Address> save(@Valid @RequestBody Address address) {

        return ResponseEntity.ok(repository.save(checkIfHaveLatAndLng(address)));
    }

    private Address checkIfHaveLatAndLng(Address address) {
        return address.getLatitude() == null || address.getLongitude() == null ?
                geoService.getLocalization(address):address;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
