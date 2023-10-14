package com.application.hakaton.model.atm;

import com.application.hakaton.model.CoordRequest;
import com.application.hakaton.model.enums.AtmServiceEnum;
import com.application.hakaton.model.enums.ClientTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AtmRequest {

    private CoordRequest firstCorner;

    private CoordRequest lastCorner;

    private List<AtmServiceEnum> services;

    private String address;

}
