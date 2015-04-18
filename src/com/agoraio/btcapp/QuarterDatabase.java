package com.agoraio.btcapp;

/**
 * @author yangkklt
 *
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QuarterDatabase {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/agora";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "";

	public static void getData(Date[] date, double[] open, double[] close,
			double[] high, double[] low, int limit) {
		Connection conn = null;
		Statement stmt = null;
		Statement statement = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query

			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql = "select table1.TIME, table1. OPEN, table1.HIGH, table1.LOW, CLOSE, table1.VOLUME from"
					+ " (select TIME,MAX(HIGH) as HIGH, MIN(LOW) as LOW, OPEN, MAX(TIME) AS CLOSE_DATE,SUM(VOLUME) as VOLUME from"
					+ " Okcoin group by DATE(time) , HOUR(time) , MINUTE(time) DIV 15) as table1, Okcoin where table1.CLOSE_DATE = Okcoin.TIME order by table1.TIME desc limit "
					+ limit;
			String query = "select * from ( " + sql
					+ " ) tempTable order by tempTable.time asc ;";
			System.out.println(query);
			// String sql =
			// "select table1.TIME, table1. OPEN, table1.HIGH, table1.LOW, CLOSE, table1.VOLUME from"
			// +
			// " (select TIME,MAX(HIGH) as HIGH, MIN(LOW) as LOW, OPEN, MAX(TIME) AS CLOSE_DATE,SUM(VOLUME) as VOLUME from"
			// +
			// " Okcoin group by DATE(time) , HOUR(time) , MINUTE(time) DIV 15) as table1, Okcoin where table1.CLOSE_DATE = Okcoin.TIME limit "
			// + limit + ";";
			ResultSet resultSet = null;
			statement = conn.prepareStatement(query);
			resultSet = statement.executeQuery(query);
			int i = 0;
			while (resultSet.next()) {

				String temp = resultSet.getString(1);
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				date[i] = (Date) sdf.parse(temp);
				open[i] = resultSet.getDouble("OPEN");
				close[i] = resultSet.getDouble("CLOSE");
				high[i] = resultSet.getDouble("HIGH");
				low[i] = resultSet.getDouble("LOW");
				i++;
			}
			// System.out.println(open[479]);
			// STEP 5: Extract data from result set

			// STEP 6: Clean-up environment

			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
		System.out.println("Goodbye!");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date[] date = new Date[480];
		double[] high = new double[480];
		double[] low = new double[480];
		double[] open = new double[480];
		double[] close = new double[480];
		QuarterDatabase.getData(date, open, close, high, low, 100);
	}

}
