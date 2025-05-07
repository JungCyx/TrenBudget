package edu.csusm.DAO;

import edu.csusm.Builder.MortgageBuilder;
import edu.csusm.Builder.MortgageIF;
import edu.csusm.Model.BudgetGoal;
import edu.csusm.Model.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MortgageDAO {
    public static MortgageIF currentMortgage;
    DAO dao = new DAO();

    public static MortgageIF getCurrentMortgage() { return currentMortgage; }
    public static void setCurrentMortgage(MortgageIF mortgage) { currentMortgage = mortgage; }

    //CREATE
    public void createMortgage(MortgageBuilder mortgageBuilder){
        String sql = "INSERT INTO mortgage (principal, interestRate, termInYears, downPayment, insurance, propertyTaxRate, userid) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int currentUserId = UserSession.getInstance().getCurrentUser().getId();

        try {
            Connection conn = dao.get_Connection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setDouble(1, mortgageBuilder.getPrincipal());
            stmt.setDouble(2, mortgageBuilder.getInterestRate());
            stmt.setInt(3, mortgageBuilder.getTermInYears());
            stmt.setDouble(4, mortgageBuilder.getDownPayment());
            stmt.setDouble(5, mortgageBuilder.getInsurance());
            stmt.setDouble(6, mortgageBuilder.getPropertyTaxRate());
            stmt.setInt(7, currentUserId);

            stmt.executeUpdate();
            System.out.println("Added mortgage into table successfully...");
        } catch (SQLException e) {
            System.out.println("Failed adding mortgage to table...");
            e.printStackTrace();
        }
    }

    //READ (id)
    public MortgageIF getMortgage() {
        int currentUserId = UserSession.getInstance().getCurrentUser().getId();
        MortgageIF mortgage = null;

        String sql = "SELECT * FROM mortgage WHERE userId = ? ORDER BY mortgageId DESC LIMIT 1";

        try {
            Connection conn = dao.get_Connection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, currentUserId);
            ResultSet res = stmt.executeQuery();

            res.next();
            mortgage = new MortgageBuilder()
                .addPrincipal(res.getDouble("principal"))
                .addInterestRate(res.getDouble("interestRate"))
                .addTermInYears(res.getInt("termInYears"))
                .addDownPayment(res.getDouble("downPayment"))
                .addInsurance(res.getDouble("insurance"))
                .addPropertyTaxRate(res.getDouble("propertyTaxRate"))
                .setUserId(currentUserId)
                .build();

            res.close();
        } catch (SQLException e) {
            System.out.println("Could not retrieve mortgage.");
            e.printStackTrace();
        }
        return mortgage;
    }

    public List<MortgageIF> getAllMortgages() {
        int currentUserId = UserSession.getInstance().getCurrentUser().getId();
        List<MortgageIF> mortgages = new ArrayList<>();

        String sql = "SELECT * FROM mortgage WHERE userId = ? ORDER BY mortgageId DESC";

        try {
            Connection conn = dao.get_Connection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, currentUserId);
            ResultSet res = stmt.executeQuery();

            while(res.next()) {
                var mortgage = new MortgageBuilder()
                        .addPrincipal(res.getDouble("principal"))
                        .addInterestRate(res.getDouble("interestRate"))
                        .addTermInYears(res.getInt("termInYears"))
                        .addDownPayment(res.getDouble("downPayment"))
                        .addInsurance(res.getDouble("insurance"))
                        .addPropertyTaxRate(res.getDouble("propertyTaxRate"))
                        .setUserId(currentUserId)
                        .build();

                mortgages.add(mortgage);
            }
            res.close();
        } catch (SQLException e) {
            System.out.println("Could not retrieve mortgages.");
            e.printStackTrace();
        }
        return mortgages;
    }

    public void deleteMortgageById(int mortgageId) {
        String sql = "DELETE FROM mortgage WHERE mortgageid = ?";

        try {
            Connection conn = dao.get_Connection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, mortgageId);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) System.out.println("Mortgage removed successfully.");
            else System.out.println("No mortgage found with that id.");
        } catch (SQLException e) {
            System.out.println("Could not delete mortgage with Id: " + mortgageId);
            e.printStackTrace();
        }
    }
}
