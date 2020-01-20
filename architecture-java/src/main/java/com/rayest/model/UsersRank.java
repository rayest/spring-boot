package com.rayest.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UsersRank {
    private String username;
    private Long rank;
    private double score;
}
