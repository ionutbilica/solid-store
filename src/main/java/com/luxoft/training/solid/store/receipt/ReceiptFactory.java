package com.luxoft.training.solid.store.receipt;

public class ReceiptFactory {
    private static int receiptNo;

    public enum Format {TEXT, HTML};

    public Receipt createReceipt(Format format) {
        switch (format) {
            case TEXT: return new TextReceipt();
            case HTML: return new HtmlReceipt();
            default: throw new UnknownReceiptFormat(format);
        }
    }

    private class UnknownReceiptFormat extends RuntimeException {
        public UnknownReceiptFormat(Format format) {
            super("Unknown receipt format: " + format);
        }
    }
}
