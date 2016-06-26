package com.luxoft.training.solid.store.receipt;

import com.luxoft.training.solid.store.receipt.Clock;

import java.util.Date;

public class LocalClock implements Clock {

    @Override
    public Date getDate() {
        return new Date();
    }
}
