package com.jdm.legends.users.service.dto;


import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "temporary_users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryUser {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    private String fullName;

    @NotBlank
    private String userName;

    @Email
    private String emailAddress;

}
