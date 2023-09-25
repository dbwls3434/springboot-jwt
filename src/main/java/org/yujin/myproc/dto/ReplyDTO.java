package org.yujin.myproc.dto;

import lombok.*;
import org.yujin.myproc.entity.Reply;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ReplyDTO {

    private long id;
    private String content;
    private long boardId;
    private Date createDate;

    public ReplyDTO makeEntityToDto(Reply entity) {

        ReplyDTO dto = ReplyDTO.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .boardId(entity.getBoard().getId())
                .createDate(entity.getCreateDate())
                .build();

        return dto;
    }

    public Reply makeDtoToEntity(ReplyDTO dto) {

        Reply entity = Reply.builder()
                .id(dto.getId())
                .content(dto.getContent())
                //.boardId(dto.getBoard().getId())
                //.createDate(dto.getCreateDate())
                .build();

        return entity;
    }
}
