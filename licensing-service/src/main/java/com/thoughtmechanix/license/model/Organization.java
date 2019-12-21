package com.thoughtmechanix.license.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Organization implements Serializable {

    private String id;
    private String name;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
}