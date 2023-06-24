package com.jdm.legends.users.service.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static jakarta.persistence.GenerationType.IDENTITY;

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
