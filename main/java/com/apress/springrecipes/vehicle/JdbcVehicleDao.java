package com.apress.springrecipes.vehicle;

import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.prefs.Preferences;

/**
 * Date: 1/27/11
 * Time: 8:18 PM
 */
public class JdbcVehicleDao implements VehicleDao {

    private Logger logger = Logger.getLogger(JdbcVehicleDao.class);

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Vehicle vehicle) {
        String sql = "INSERT INTO vehicle (vehicle_no, color, wheel, seat) VALUES (?,?,?,?)";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, vehicle.getVechicleNo());
            ps.setString(2, vehicle.getColor());
            ps.setInt(3, vehicle.getWheel());
            ps.setInt(4, vehicle.getSeat());
            ps.executeUpdate();
            ps.close();
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
            throw new RuntimeException(t);
        } finally {
            if (connection != null) {
                try {
                    connection.clearWarnings();
                    connection.close();
                } catch (Throwable t) {
                    logger.warn(t.getLocalizedMessage(), t);
                }
            }
        }
    }

    @Override
    public void update(Vehicle vehicle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Vehicle vehicle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Vehicle findByVehicleNo(String vehicleNo) {
        String sql = "SELECT vehicle_no, color, wheel, seat FROM vehicle WHERE vehicle_no = ?";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (! rs.next()) { // bail if nothing found
                return null;
            }
            String number = rs.getString(1);
            String color = rs.getString(2);
            int wheel = rs.getInt(3);
            int seat = rs.getInt(4);
            Vehicle vehicle = new Vehicle(number, color, wheel, seat);
            rs.clearWarnings();
            rs.close();
            ps.clearParameters();
            ps.clearWarnings();
            ps.close();
            return vehicle;
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
            throw new RuntimeException(t);
        } finally {
            if (connection != null) {
                try {
                    connection.clearWarnings();
                    connection.clearWarnings();
                } catch (Throwable t) {
                    logger.warn(t.getLocalizedMessage(), t);
                }
            }
        }
    }

}
