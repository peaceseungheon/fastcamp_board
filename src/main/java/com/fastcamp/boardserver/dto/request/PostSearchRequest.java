package com.fastcamp.boardserver.dto.request;

import com.fastcamp.boardserver.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchRequest {

    private int postNo;
    private String name;
    private String contents;
    private int views;
    private int categoryNo;
    private int userNo;
    private CategoryDTO.SortStatus sortStatus;

}
