import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DAO.BudgetGoalDAO;
import DAO.DAO;
import Model.BudgetGoal;
import Model.UserModel;
import Model.UserSession;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DAOTest {

    private DAO dao;
    private BudgetGoalDAO bDao;
    private Connection testConnection;

    // Database configuration for testing
    private static final String TEST_DB_URL = "jdbc:postgresql://localhost:5432/budgetapp";
    private static final String USER = "postgres";
    private static final String PASS = "123";

    @Before
    public void setUp() throws SQLException {
        // Set up a connection to the test database
        testConnection = DriverManager.getConnection(TEST_DB_URL, USER, PASS);
        assertNotNull("Test connection should not be null", testConnection);

        // Create a test DAO instance
        dao = new DAO();

        // Ensure tables are dropped to start fresh for each test
        try (Statement stmt = testConnection.createStatement()) {
        }
    }

    @After
    public void tearDown() throws SQLException {
        // Close the connection after tests
        if (testConnection != null && !testConnection.isClosed()) {
            testConnection.close();
        }
    }

    @Test
    public void testCreateUserTable() {
        dao.createUserTable();

        // Verify that the table exists
        try (Statement stmt = testConnection.createStatement()) {
            stmt.execute("SELECT 1 FROM appUser");
            assertTrue("User table should exist", true);
        } catch (SQLException e) {
            throw new AssertionError("User table does not exist", e);
        }
    }

    @Test
    public void testCreateBudgetGoalTable() {
        dao.createUserTable(); // Ensure foreign key dependency is met
        dao.createBudgetGoalTable();

        // Verify that the table exists
        try (Statement stmt = testConnection.createStatement()) {
            stmt.execute("SELECT 1 FROM budgetgoals");
            assertTrue("Budget goals table should exist", true);
        } catch (SQLException e) {
            throw new AssertionError("Budget goals table does not exist", e);
        }
    }

    @Test
    public void testCreateTransactionTable() {
        dao.createUserTable(); // Ensure foreign key dependency is met
        dao.createTransactionTable();

        // Verify that the table exists
        try (Statement stmt = testConnection.createStatement()) {
            stmt.execute("SELECT 1 FROM usertransaction");
            assertTrue("Transaction table should exist", true);
        } catch (SQLException e) {
            throw new AssertionError("Transaction table does not exist", e);
        }
    }
    
    @Test
    public void testGetBudgetGoal() throws SQLException {
        // Insert test data into budgetgoals table
        String insertSQL = "INSERT INTO budgetgoals (userId, category, budgetAmount, startDate, endDate, notificationsEnabled) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = testConnection.prepareStatement(insertSQL)) {
            stmt.setInt(1, 1); // User ID
            stmt.setString(2, "Groceries");
            stmt.setDouble(3, 500.0);
            stmt.setDate(4, Date.valueOf(LocalDate.of(2024, 1, 1)));
            stmt.setDate(5, Date.valueOf(LocalDate.of(2024, 12, 31)));
            stmt.setBoolean(6, true);
            stmt.executeUpdate();
        }

        // Mock the user session (assuming UserSession class is testable/mocked)
        UserSession.getInstance().setCurrentUser(new UserModel(1, "testUser"));

        // Retrieve the budget goal
        BudgetGoal retrievedGoal = bDao.getBudgetGoal();

        // Validate the retrieved data
        assertNotNull("Retrieved budget goal should not be null", retrievedGoal);
        assertEquals("Category should match", "Groceries", retrievedGoal.getCategory());
        assertEquals("Budget amount should match", 500.0, retrievedGoal.getBudgetAmount(), 0.01);
        assertEquals("Start date should match", LocalDate.of(2024, 1, 1), retrievedGoal.getStartDate());
        assertEquals("End date should match", LocalDate.of(2024, 12, 31), retrievedGoal.getEndDate());
    }
}

