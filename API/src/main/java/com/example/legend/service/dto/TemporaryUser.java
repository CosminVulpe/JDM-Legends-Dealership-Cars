package com.example.legend.service.dto;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "TemporaryUser")
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TemporaryUser implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank
    private String userName;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    private String emailAddress;
    private LocalDate timeOfTheCreation;

    @ManyToMany(mappedBy = "temporaryUsersList", fetch = LAZY)
    private List<HistoryBid> historyBidList;

}
