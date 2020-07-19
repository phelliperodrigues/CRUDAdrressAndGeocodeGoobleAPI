package com.stoom.address.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.stoom.address.model.Address;
import com.stoom.address.repository.AddressRepository;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Swith Test for Address Controller")
public class AddressControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private AddressRepository repository;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    private String uri = "/address/";

    @BeforeEach()
    public void setUp() {
        repository.deleteAll();
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private Address createValidAddress() {
        return new Address()
                .from().streetName("Praca Almirante Pena Boto")
                .with().number("50")
                .and().complement("Apt 34 Bl 14")
                .in().neighbourhood("Jardim Satelite")
                .in().city("Sao Paulo")
                .in().state("Sao Paulo")
                .in().country("Brasil")
                .with().zipcode("04816100");
    }
    private Address createInvalidAddress() {
        return new Address()
                .from().streetName("Praca Almirante Pena Boto")
                .and().complement("Apt 34 Bl 14")
                .in().neighbourhood("Jardim Satelite")
                .in().state("Sao Paulo")
                .in().country("Brasil")
                .with().zipcode("04816100");
    }

    @Test
    @DisplayName("[R] - Testing whether a route exists to fetch all addresses")
    public void should_return_ok_status_when_find_all_address() throws Throwable {
        this.mvc.perform(
                get(uri)
                        .contentType(APPLICATION_JSON_UTF8)
                        .accept(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[R] - Testing return of the GET resource, validating whether a record exists")
    public void should_return_a_list_with_an_address() throws Throwable {

        repository.save(createValidAddress());

        var responseBody = this.mvc.perform(
                get(uri)
                        .contentType(APPLICATION_JSON_UTF8)
                        .accept(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var addresses = objectMapper.readValue(responseBody, new TypeReference<List<Address>>() {});

        assertThat(addresses).isNotEmpty();
    }

    @Test
    @DisplayName("[R] - Testing return from find by Id")
    public void should_return_a_address_from_find_by_id() throws Throwable {
        var address = createValidAddress();
        repository.save(address);

        var responseBody = this.mvc.perform(
                get(uri + address.getId())
                        .contentType(APPLICATION_JSON_UTF8)
                        .accept(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var addressReturn = objectMapper.readValue(responseBody, Address.class);

        assertThat(addressReturn).extracting(Address::getId).isEqualTo(address.getId()).isNotNull();
    }



    @Test
    @DisplayName("[R] Testing return from find by Id Invalid")
    public void should_return_a_address_from_find_by_id_invalid() throws Throwable {

        this.mvc.perform(
                get(uri +  UUID.randomUUID().toString())
                        .contentType(APPLICATION_JSON_UTF8)
                        .accept(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("[C] Testing the create valid address")
    public void when_post_request_to_address_and_valid_address_then_correct_response() throws Throwable {

        mvc.perform(post(uri)
                .content(objectMapper.writeValueAsString(createValidAddress()))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("[C] Testing the create invalid address")
    public void when_post_request_to_address_and_invalid_address_then_correct_response() throws Throwable {
        mvc.perform(post(uri)
                .content(objectMapper.writeValueAsString(createInvalidAddress()))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.number", Is.is("number is mandatory")))
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("[C] Testing the create incomplete address and valid longitude and latitude")
    public void when_post_request_to_address_and_invalid_address_then_validation_longitude_and_latitude() throws Throwable {
        repository.deleteAll();
        String jsonRequest = new Gson().toJson(createValidAddress());
        mvc.perform(post(uri)
                .content(jsonRequest)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Address addressResponse = repository.findAll().stream().findFirst().orElse(null);

        assertThat(addressResponse).extracting(Address::getLatitude).isEqualTo("-23.7042771");
        assertThat(addressResponse).extracting(Address::getLongitude).isEqualTo("-46.6868483");
    }
    @Test
    @DisplayName("[D] - Testing resource from deleting Address by Id")
    public void shout_delete_address_by_id() throws Throwable{
        var address = createValidAddress();
        repository.save(address);

        this.mvc.perform(
                delete(uri + address.getId())
                        .contentType(APPLICATION_JSON_UTF8)
                        .accept(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

}


