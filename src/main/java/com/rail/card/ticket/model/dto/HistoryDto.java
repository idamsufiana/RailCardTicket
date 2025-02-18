package com.rail.card.ticket.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class HistoryDto {
    Date dateFrom;
    Date dateTo;
}
