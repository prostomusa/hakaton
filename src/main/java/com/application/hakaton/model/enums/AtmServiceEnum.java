package com.application.hakaton.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AtmServiceEnum {
    SUPPORT_EUR("Снятие евро"),
    SUPPORT_RUB("Снятие рублей"),
    SUPPORT_USD("Снятие долларов"),
    SUPPORT_RUB_CHARGE("Внесение рублей"),
    QR_CODE("Поддержка qr code"),
    NFC_CARD("Поддержка NFC");

    private final String value;
}
