package com.p2p.domain;

import java.math.BigDecimal;

public class Loan {
    // Enum untuk status loan
    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    private Status status;
    //TC-02: Penambahan untuk banyak pinjaman yang diinginkan
    private BigDecimal loanAmount;

    // Saat loan dibuat, status awal adalah PENDING
    public Loan() {
        this.status = Status.PENDING;
    }

    // Setter untuk mengubah status loan
    public void setStatus(Status status) {
        this.status = status;
    }

    // Getter untuk membaca status loan
    public Status getStatus() {
        return status;
    }

    //TC-2: Setter untuk mengubah nilai loan
    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    //TC-2: Getter untuk membaca nilai loan
    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    //TC-1: (3) : Fowler Method
    //Logika setLoanStatus dalam LoanService dipindah ke Loan.java (here)
    public void approve() {
        this.status = Status.APPROVED;
    }

    public void reject() {
        this.status = Status.REJECTED;
    }
}
