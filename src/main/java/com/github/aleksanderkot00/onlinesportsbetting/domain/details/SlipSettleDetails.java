package com.github.aleksanderkot00.onlinesportsbetting.domain.details;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@Entity
public class SlipSettleDetails {

    @NotNull
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private LocalDateTime settleDateTime;

    @NotNull
    private BigDecimal stoke;

    @NotNull
    private boolean winning;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SLIP_ID")
    private Slip slip;
}
