package com.p2p.service;
import com.p2p.domain.*;
import java.math.BigDecimal;

public class LoanService {

    public Loan createLoan(Borrower borrower, BigDecimal amount) {
        // =========================
        // VALIDASI UTAMA (TC-01, TC-02, TC-03,)
        // =========================
        
        // Jika borrower belum terverifikasi,
        // maka proses harus dihentikan
        validateBorrower(borrower);

        //TC-02: Jika borrower ingin meminjam uang yang BUKAN positif, hentikan proses.
        //Step 1: Extract and turn this into function
        //    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
        //     throw new IllegalArgumentException("Invalid loan amount");
        // }
        validateAmount(amount);
        
        // Membuat objek loan baru
        Loan loan = new Loan();

        //TC-2: Penambahan fungsi untuk memasukkan banyak pinjaman jika keduanya sudah divalidasi
        loan.setLoanAmount(amount);
        // =========================
        // LOGIC SEDERHANA (sementara)
        // =========================
        // Jika credit score tinggi → APPROVED
        // Jika tidak → REJECTED

        //TC-1 (3) Fowler: Replace Conditional Logic (preparation step)
        //loan.setStatus(Loan.Status.APPROVED) dan loan.setStatus(Loan.Status.REJECT) 
        //menjadi loan.approve() dan loan.reject()
        // if (borrower.getCreditScore() >= Borrower.CREDITSCORE_THRESHOLD) {
        //     loan.approve();
        // } else {
        //     loan.reject();
        // }

        //TC-3/4 Fowler: Extraction method. Evaluasi status dalam createLoan() dipindah menjadi fungsi
        evaluateStatus(borrower, loan);
        
        return loan;
    }

    // ===========================
    //  PRIvaTE VALIDATION METHOD
    // ===========================

    //TC-1 (1) Fowler: Extraction Method
    //Validator dalam LoanService menjadi fungsi di luar createLoan()
    private void validateBorrower(Borrower borrower) {
    
    //TC-1 (2) Fowler: Move Method
    //Logika !borrower.isVerified() diubah menjadi canApplyLoan yang berasal dari domain Loan
        if (!borrower.canApplyLoan()) {
                throw new IllegalArgumentException("Borrower not verified");
        }
    }

    //TC-2 (3) Fowler: Extraction Method
    //Validator menjadi fungsi
    private void validateAmount(BigDecimal amount) {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Invalid loan amount");
        }
    }

    //TC-3/4 Fowler : Extraction Method
    private void evaluateStatus(Borrower borrower, Loan loan) {
        if (borrower.getCreditScore() >= Borrower.CREDITSCORE_THRESHOLD) {
            loan.approve();
        } else {
            loan.reject();
        }
    }
}
