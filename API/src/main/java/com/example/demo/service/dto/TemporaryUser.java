package com.example.demo.service.dto;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "TemporaryUser")
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryUser {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String userName;
    private String firstName;
    private String lastName;
    private String emailAddress;

    @ManyToMany(mappedBy = "temporaryUsersList", fetch = LAZY)
    private List<HistoryBid> historyBid;

}
