package com.apress.springrecipes.vehicle;

/**
 * Date: 1/27/11
 * Time: 8:15 PM
 */
public interface VehicleDao {
    public void insert(Vehicle vehicle);
    public void update(Vehicle vehicle);
    public void delete(Vehicle vehicle);
    public Vehicle findByVehicleNo(String vehicleNo);
}
