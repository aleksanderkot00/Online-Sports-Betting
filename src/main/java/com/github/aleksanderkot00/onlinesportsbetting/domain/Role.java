package com.github.aleksanderkot00.onlinesportsbetting.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity(name = "ROLES")
@Data
public class Role {

    @NotNull
    @Id
    @GeneratedValue
    private long roleId;

    @NotNull private String role;
}