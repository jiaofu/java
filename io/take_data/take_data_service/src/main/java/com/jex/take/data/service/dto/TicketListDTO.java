package com.jex.take.data.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class TicketListDTO {
    private long now;
    private List<TicketListDTO> listDTOList;
}
