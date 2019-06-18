package com.github.aleksanderkot00.onlinesportsbetting.domain.details;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Entity
public class CategoryWatchingDetails {

    @NotNull
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    private long categoryId;

    public CategoryWatchingDetails() {
    }

    public CategoryWatchingDetails(@NotNull long categoryId) {
        this.dateTime = LocalDateTime.now();
        this.categoryId = categoryId;
    }
}
