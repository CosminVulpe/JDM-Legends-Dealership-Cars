package com.jdm.legends.common.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "TemporaryUser")
@Table
@Data
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

    @Nullable
    private String role;

    private boolean checkInformationStoredTemporarily;

    @ManyToMany(fetch = EAGER, cascade = { PERSIST, MERGE })
    @JoinTable(
            name = "temporary_user_history_bid",
            joinColumns =  @JoinColumn(name = "temporary_user_id") ,
            inverseJoinColumns =  @JoinColumn(name = "history_bid_id")
    )
    private List<HistoryBid> historyBidList = new ArrayList<>();

    public void addHistoryBid(HistoryBid historyBid) {
        historyBidList.add(historyBid);
        historyBid.getTemporaryUsersList().add(this);
    }
}
