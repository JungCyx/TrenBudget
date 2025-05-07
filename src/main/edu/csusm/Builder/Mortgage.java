package edu.csusm.Builder;

import static java.lang.Math.pow;

public class Mortgage implements MortgageIF {
    private final static int MONTHS_PER_YEAR = 12;
    private double principal;
    private double interestRate;
    private int termInYears;
    private double downPayment;
    private double insurance;
    private double propertyTaxRate;
    private int userId;

    public Mortgage(MortgageBuilder builder){
        this.principal = builder.principal;
        this.interestRate = builder.interestRate;
        this.termInYears = builder.termInYears;
        this.downPayment = builder.downPayment;
        this.insurance = builder.insurance;
        this.propertyTaxRate = builder.propertyTaxRate;
    }

    @Override
    public double getAmortization() {
        double loanAmount = principal - downPayment;
        double monthlyInterestRate = interestRate / MONTHS_PER_YEAR / 100;
        int totalPayments = termInYears * MONTHS_PER_YEAR;

        if (monthlyInterestRate == 0) { return loanAmount / totalPayments; }

        double numerator = monthlyInterestRate * pow(1 + monthlyInterestRate,totalPayments);
        double denominator = pow(1 + monthlyInterestRate,totalPayments) - 1;

        return loanAmount * ( numerator / denominator );
    }

    @Override
    public double downPaymentNeeded() {
        return principal * 0.2;
    }
}
