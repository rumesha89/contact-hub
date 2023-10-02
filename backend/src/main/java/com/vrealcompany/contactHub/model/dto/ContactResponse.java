package com.vrealcompany.contactHub.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String website;
    private CompanyResponse company;
}
