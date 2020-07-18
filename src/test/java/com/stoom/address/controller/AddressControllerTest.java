package com.stoom.address.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stoom.address.model.Address;
import com.stoom.address.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

    private Address createValidAddress(){
        return new Address();
    }

    @BeforeEach()
    public void setUp() {
        repository.deleteAll();
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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

        String responseBody = this.mvc.perform(
                get(uri)
                        .contentType(APPLICATION_JSON_UTF8)
                        .accept(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Address> addresses = objectMapper.readValue(responseBody, new TypeReference<List<Address>>() {});

        assertThat(addresses).isNotEmpty();
    }

    @Test
    @DisplayName("[R] - Testing return from find by Id")
    public void should_return_a_address_from_find_by_id() throws Throwable {
        Address address = createValidAddress();
        repository.save(address);

        String responseBody = this.mvc.perform(
                get(uri + address.getId())
                        .contentType(APPLICATION_JSON_UTF8)
                        .accept(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Address addressReturn = objectMapper.readValue(responseBody, Address.class);

        assertThat(addressReturn).extracting(Address::getId).isEqualTo(address.getId()).isNotNull();
    }

    @Test
    @DisplayName("[R] Testing return from find by Id Invalid")
    public void should_return_a_address_from_find_by_id_invalid() throws Throwable {

        String responseBody = this.mvc.perform(
                get(uri +  UUID.randomUUID().toString())
                        .contentType(APPLICATION_JSON_UTF8)
                        .accept(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Address addressReturn = objectMapper.readValue(responseBody, Address.class);

        assertThat(addressReturn).extracting(Address::getId).isNull();
    }

}
