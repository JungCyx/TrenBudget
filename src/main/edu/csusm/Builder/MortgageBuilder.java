package edu.csusm.Builder;

public class MortgageBuilder {
    double total;
    double principal;
    double interestRate;
    int termInYears;
    double downPayment = 0.0f;
    double insurance = 0.0f;
    double propertyTaxRate = 0.0f;

    public MortgageBuilder(double principal, double interestRate, int termInYears){
        this.principal = principal;
        this.interestRate = interestRate;
        this.termInYears = termInYears;
    }

    public MortgageBuilder addDownPayment(double downPayment) {
        this.downPayment = downPayment;
        return this;
    }

    public MortgageBuilder addInsurance(double insurance) {
        this.insurance = insurance;
        return this;
    }

    public MortgageBuilder addPropertyTaxRate(double propertyTaxRate) {
        this.propertyTaxRate = propertyTaxRate;
        return this;
    }

    public Mortgage build() {
        return new Mortgage(this);
    }
}
