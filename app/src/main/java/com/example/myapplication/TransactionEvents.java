package com.example.myapplication;

public interface TransactionEvents {
    String enterPin(int ptc, String amount);
    void transactionResult(boolean result);
}