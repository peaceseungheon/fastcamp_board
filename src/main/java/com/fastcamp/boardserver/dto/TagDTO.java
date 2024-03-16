package com.fastcamp.boardserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {

    private int tagNo;

    private String name;

    private String url;

    private int postNo;

}
