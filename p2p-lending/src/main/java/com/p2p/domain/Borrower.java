package com.p2p.domain;

public class Borrower {

// Status verifikasi KYC
private boolean verified;

// Nilai credit score borrower
private int creditScore;

//TC-(3/4) Refactor: Renaming Variable, sehingga lebih jelas fungsi dari variable tersebut
//Disini, field CREDITSCORE_THRESHOLD digunakan sebagai nilai batasan untuk skor kredit
public final static int CREDITSCORE_THRESHOLD = 600;

// Constructor untuk inisialisasi data borrower
public Borrower(boolean verified, int creditScore) {
this.verified = verified;
this.creditScore = creditScore;
}

// Getter untuk mengecek apakah borrower sudah verified
public boolean isVerified() {
return verified;
}

// Getter untuk mengambil credit score
public int getCreditScore() {
return creditScore;
}

public boolean canApplyLoan() {
    return isVerified();
}


}
// Class ini merepresentasikan pinjaman


