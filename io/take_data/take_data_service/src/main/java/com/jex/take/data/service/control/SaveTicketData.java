package com.jex.take.data.service.control;

import com.jex.take.data.service.dto.TickerDTO;

import java.util.List;

public interface SaveTicketData {

    void insertList(TickerDTO tickerDTO);

    void insertSave(List<TickerDTO> list);

     void checkList();
}
