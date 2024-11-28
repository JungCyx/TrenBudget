// package DAO;
// import java.beans.Statement;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;
// import java.sql.PreparedStatement;
// import Model.SavingsGoal;
// import View.SavingsGUI;



// public class SavingsGoalDAO{
//     public void saveSavingsGoal(){
//         String sqlStatement = "INSERT INTO SAVINGS (name, targetAmount, deadline, startingAmount, notificationsEnabled) VALUES ()";
//     try {
//         Connection conn  = DAO.ConnectionToDataBase();
//         PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);

//         preparedStatement.setString(1, SavingsGUI.savingsGoal.getName());
//     } catch (Exception e) {
//         // TODO: handle exception
//     }
// }

// }