package com.github.aleksanderkot00.onlinesportsbetting.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity(name = "EVENTS")
public class Event {

    @NotNull
    @Id
    @GeneratedValue
    private long eventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    private boolean finished = false;

    @NotNull
    @Size(min = 2, max = 35)
    private String teamOneName;

    @NotNull
    @Size(min = 2, max = 35)
    private String teamTwoName;

    private BigDecimal teamOneScore;
    private BigDecimal teamTwoScore;
}
