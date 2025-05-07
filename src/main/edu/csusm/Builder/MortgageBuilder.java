package edu.csusm.Builder;

public class MortgageBuilder {
    double principal;
    double interestRate;
    int termInYears;
    double downPayment = 0.0f;
    double insurance = 0.0f;
    double propertyTaxRate = 0.0f;
    int userId;

    public MortgageBuilder(double principal, double interestRate, int termInYears, int userId){
        this.principal = principal;
        this.interestRate = interestRate;
        this.termInYears = termInYears;
        this.userId = userId;
    }

    public MortgageBuilder(double principal) { this.principal = principal; }

    public MortgageBuilder() {}

    public MortgageBuilder addPrincipal(double principal) {
        this.principal = principal;
        return this;
    }

    public MortgageBuilder addInterestRate(double interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public MortgageBuilder addTermInYears(int termInYears) {
        this.termInYears = termInYears;
        return this;
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

    public MortgageBuilder setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public double getPrincipal() {
        return principal;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getTermInYears() {
        return termInYears;
    }

    public double getDownPayment() {
        return downPayment;
    }

    public double getInsurance() {
        return insurance;
    }

    public double getPropertyTaxRate() {
        return propertyTaxRate;
    }

    public int getUserId() {
        return userId;
    }

    public Mortgage build() {
        return new Mortgage(this);
    }
}
