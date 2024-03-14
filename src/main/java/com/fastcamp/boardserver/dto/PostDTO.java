package com.fastcamp.boardserver.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDTO {

    private int id;

    private String  name;

    private boolean isNotice;

    private String contents;

    private Date createTime;

    private int views;

    private int categoryNo;

    private int userNo;

    private int fileId;

    private Date updateTime;

}
