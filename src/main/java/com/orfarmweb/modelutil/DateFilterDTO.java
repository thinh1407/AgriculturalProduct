package com.orfarmweb.modelutil;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class DateFilterDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startFill;
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    private Date endFill;
}
