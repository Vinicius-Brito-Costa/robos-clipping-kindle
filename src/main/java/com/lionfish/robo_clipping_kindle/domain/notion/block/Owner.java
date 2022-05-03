package com.lionfish.robo_clipping_kindle.domain.notion.block;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Owner{
    private String type;
    private User user;
}