package com.luxoft.training.solid.store.receipt;

import com.luxoft.training.solid.store.Clock;
import com.luxoft.training.solid.store.IdGenerator;

public class ReceiptFactory {

    private final IdGenerator receiptNoGenerator;
    private final Clock clock;

    public enum Format {TEXT, HTML};

    public ReceiptFactory(IdGenerator receiptNoGenerator, Clock clock) {
        this.receiptNoGenerator = receiptNoGenerator;
        this.clock = clock;
    }

    public Receipt createReceipt(Format format) {
        switch (format) {
            case TEXT: return new TextReceipt(receiptNoGenerator.generateId(), clock.getDate());
            case HTML: return new HtmlReceipt(receiptNoGenerator.generateId(), clock.getDate());
            default: throw new UnknownReceiptFormat(format);
        }
    }

    private class UnknownReceiptFormat extends RuntimeException {
        public UnknownReceiptFormat(Format format) {
            super("Unknown receipt format: " + format);
        }
    }
}
