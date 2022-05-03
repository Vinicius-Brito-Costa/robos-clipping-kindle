package com.lionfish.robo_clipping_kindle.domain.notion.block;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
    private String object;
    private String id;
    private String name;
    private String avatar_url;
    private String type;
    private Person person;
}