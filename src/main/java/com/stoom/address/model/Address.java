package com.stoom.address.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
public class Address  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "streetName is mandatory")
    private String streetName;

    @NotBlank(message = "number is mandatory")
    private String number;

    private String complement;

    @NotBlank(message = "neighbourhood is mandatory")
    private String neighbourhood;

    @NotBlank(message = "city is mandatory")
    private String city;

    @NotBlank(message = "state is mandatory")
    private String state;

    @NotBlank(message = "country is mandatory")
    private String country;

    @NotBlank(message = "zipcode is mandatory")
    private String zipcode;




    public Address from() { return this;}
    public Address in() { return this;}
    public Address with() { return this;}
    public Address and() { return this;}


    public Address streetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public Address number(String number) {
        this.number = number;
        return this;
    }

    public Address complement(String complement) {
        this.complement = complement;
        return this;
    }

    public Address neighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
        return this;
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    public Address state(String state) {
        this.state = state;
        return this;
    }

    public Address country(String country) {
        this.country  = country;
        return this;
    }

    public Address zipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }


    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", streetName='" + streetName + '\'' +
                ", number='" + number + '\'' +
                ", complement='" + complement + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}
