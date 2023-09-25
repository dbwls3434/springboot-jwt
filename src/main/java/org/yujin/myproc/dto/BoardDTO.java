package org.yujin.myproc.dto;

import lombok.*;
import org.yujin.myproc.entity.Board;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BoardDTO {

    private long id;
    private String title;
    private String content;
    private long userId;
    private Date createDate;

    public BoardDTO makeEntityToDto(Board entity) {

        BoardDTO dto = BoardDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .userId(entity.getUser().getId())
                .createDate(entity.getCreateDate())
                .build();

        return dto;
    }

    public Board makeDtoToEntity(BoardDTO dto) {

        Board entity = Board.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                //.user(dto.getUser())
                //.createDate(dto.getCreateDate())
                .build();

        return entity;
    }
}
