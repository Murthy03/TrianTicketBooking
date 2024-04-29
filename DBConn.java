package TTBooking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBConn {
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	TrainDetails d;
	private ArrayList<TrainDetails> td;
	private ArrayList<String> stNames;

	DBConn() {
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://192.168.110.48:5432/plf_training",
					"plf_training_admin", "pff123");
		} catch (Exception e) {
			System.out.println(e);
		}
		td = new ArrayList<>();
	}

	public String UserVerfication(String uORg, String pswd) throws SQLException {
		ps = con.prepareStatement("select * from nmurthy_UserLogin where Username=? or Email=? ");
		ps.setString(1, uORg);
		ps.setString(2, uORg);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			if (rs.getString(4).equals(pswd)) {
				return "pswdCoreect";
			} else {
				return "pswdWrong";
			}
		} else {
			return "UserInvalid";
		}
	}

	public ArrayList<TrainDetails> getTrainDetails(String dept, String dest) throws SQLException {
		ps = con.prepareStatement(
				"SELECT t.train_no, t.train_name FROM trains_207 t JOIN train_schedule_207 ts1 ON t.train_no = ts1.train_no JOIN train_schedule_207 ts2 ON t.train_no = ts2.train_no WHERE ts1.station_name = ? AND ts2.station_name =?");
		ps.setString(1, dept);
		ps.setString(2, dest);
		rs = ps.executeQuery();
		while (rs.next()) {
			td.add(new TrainDetails(String.valueOf(rs.getInt(1)), rs.getString(2)));
		}
		return td;
	}

	public ArrayList<String> getStationNames() throws SQLException {
		stNames = new ArrayList<>();
		ps = con.prepareStatement("select station_name from stations_207");
		rs = ps.executeQuery();
		while (rs.next()) {
			stNames.add(rs.getString(1));
		}
		return stNames;
	}

	public Double getFare(String departure, String destination) throws SQLException {
		ps = con.prepareStatement(
				" select fare from nmurthy_faretable where distance=(select max(distance) from nmurthy_faretable where distance<=(select distance from station_distances_207 where station_from=? and station_to=?));");
		ps.setString(1, departure);
		ps.setString(2, destination);
		rs = ps.executeQuery();
		rs.next();
		return rs.getDouble(1);
	}

}
