package com.p2p;

import com.p2p.domain.Loan;
import com.p2p.domain.Borrower;      //  
import com.p2p.service.LoanService;  

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoanServiceTest {
    //Penggunaan untuk Logger Log4J
    private static final Logger logger = LogManager.getLogger(LoanServiceTest.class);
     
     @Test
    void shouldRejectLoanWhenBorrowerNotVerified() {

    // =====================================================
    // SCENARIO:
    // Borrower tidak terverifikasi (KYC = false)
    // Ketika borrower mengajukan pinjaman
    // Maka sistem harus menolak dengan melempar exception
    // =====================================================

    // =========================
    // Arrange (Initial Condition)
    // =========================
    //Print ke debug saat run test untuk mengecek apakah sedang running atau tidak
        logger.info("Running TC-01: Checking unverified borrower...");   

    // Borrower belum lolos proses KYC
        Borrower borrower = new Borrower(false, 700);

    // Service untuk pengajuan loan
        LoanService loanService = new LoanService();

    // Jumlah pinjaman valid
        BigDecimal amount = BigDecimal.valueOf(1000);

    // =========================
    // Act (Action)
    // =========================
    // Borrower mencoba mengajukan loan
    
    // We wrap the call in assertThrows to tell JUnit: "This error is supposed to happen!"
    logger.debug("TC-01: Trying to create new loan if user verification = {}..", borrower.isVerified());   
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        loanService.createLoan(borrower, amount); //The actual function
    });

    // =========================
    // Assert (Expected Result)
    // =========================
        logger.error("TC-01: Caught expected exception: {}", exception.getMessage());
        assertEquals("Borrower not verified", exception.getMessage());

        logger.info("TC-01: Completed");
    }

     @Test
    void shouldRejectLoanWhenAmountIsZeroOrNegative() {
    
    // =====================================================
    // SCENARIO:
    // Borrower terverifikasi (KYC = true), ingin meminjam 0 atau negatif uang
    // Ketika borrower mengajukan pinjaman
    // Maka sistem harus menolak dengan melempar error exception
    // =====================================================
    
    // =========================
    // Arrange (Initial Condition)
    // =========================
    //Print ke debug saat run test untuk mengecek apakah sedang running atau tidak
        logger.info("Running TC-02: Checking invalid loan...");   
    
    // Borrower lolos proses KYC
        Borrower borrower = new Borrower(true, 700);

    // Service untuk pengajuan loan
        LoanService loanService = new LoanService();

    // Jumlah pinjaman tidak valid
        BigDecimal amount = BigDecimal.valueOf(0);

    // =========================
    // Act (Action)
    // =========================
    // Borrower mencoba mengajukan loan, pembuatan objek Exception untuk pengecekan di akhir
    logger.debug("TC-02: Trying to create new loan with {} amount..", amount);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        loanService.createLoan(borrower, amount);
    });

    // =========================
    // Assert (Expected Result)
    // =========================
        logger.error("TC-02: Caught expected exception: {}", exception.getMessage());
        assertEquals("Invalid loan amount", exception.getMessage());

        logger.info("TC-02: Completed");
    }

     @Test
    void shouldApproveLoanWhenCreditScoreHigh() {
    
    // =====================================================
    // SCENARIO:
    // Borrower terverifikasi, pinjaman diatas 0, dan skor kredit diatas batas (skor tinggi)
    // Batas = 600
    // Ketika borrower mengajukan pinjaman
    // Maka sistem harus menerima pinjaman
    // =====================================================

    // =========================
    // Arrange (Initial Condition)
    // =========================
    //Logging ke debug saat run test untuk mengecek apakah sedang running atau tidak
        logger.info("Running TC-03: Checking verified borrower...");   
    
    // Borrower lolos proses KYC dengan kredit skor tinggi
        Borrower borrower = new Borrower(true, 1000);
        LoanService loanService = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(1000);

    // =========================
    // Act (Action)
    // =========================

        logger.debug("TC-03: Trying to create new loan with {} amount and {} credit score..", amount, borrower.getCreditScore());
        Loan loan = loanService.createLoan(borrower, amount);

    // =========================
    // Assert (Expected Result)
    // =========================
    //Hasil akhir permintaan pinjaman harus diterima
        logger.info("TC-03: Validating loan status. Expected: APPROVED, Actual: {}", loan.getStatus());
        assertEquals(Loan.Status.APPROVED, loan.getStatus(), "Loan should be approved");
        
        logger.info("TC-03: Completed");
    }

     @Test
    void shouldRejectLoanWhenCreditScoreLow() {
    
    // =====================================================
    // SCENARIO:
    // Borrower terverifikasi, pinjaman diatas 0, tetapi skor kredit dibawah batasan (skor rendah)
    // Batas = 600
    // Ketika borrower mengajukan pinjaman
    // Maka sistem harus menolak pinjaman
    // =====================================================
    
    // =========================
    // Arrange (Initial Condition)
    // =========================
    //Logging ke debug saat run test untuk mengecek apakah sedang running atau tidak
        logger.info("Running TC-04: Checking low-score borrower...");   
    
    // Borrower lolos proses KYC dengan kredit skor rendah
        Borrower borrower = new Borrower(true, 400);
        LoanService loanService = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(1000);

    // =========================
    // Act (Action)
    // =========================

        logger.debug("TC-04: Trying to create new loan with {} amount and {} credit score..", amount, borrower.getCreditScore());
        Loan loan = loanService.createLoan(borrower, amount);

    // =========================
    // Assert (Expected Result)
    // =========================
    //Hasil akhir permintaan pinjaman harus ditolak
        logger.info("TC-04: Validating loan status. Expected: REJECTED, Actual: {}", loan.getStatus());
        assertEquals(Loan.Status.REJECTED, loan.getStatus(), "Loan should be rejected");

        logger.info("TC-04: Completed");
    }
}
