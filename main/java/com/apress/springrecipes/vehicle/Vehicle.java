package com.apress.springrecipes.vehicle;

/**
 * Date: 1/27/11
 * Time: 8:06 PM
 */
public class Vehicle {

    private String vechicleNo;
    private String color;
    private int wheel;
    private int seat;

    public Vehicle(String vechicleNo, String color, int wheel, int seat) {
        this.vechicleNo = vechicleNo;
        this.color = color;
        this.wheel = wheel;
        this.seat = seat;
    }

    public String getVechicleNo() {
        return vechicleNo;
    }

    public void setVechicleNo(String vechicleNo) {
        this.vechicleNo = vechicleNo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWheel() {
        return wheel;
    }

    public void setWheel(int wheel) {
        this.wheel = wheel;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (seat != vehicle.seat) return false;
        if (wheel != vehicle.wheel) return false;
        if (color != null ? !color.equals(vehicle.color) : vehicle.color != null) return false;
        if (vechicleNo != null ? !vechicleNo.equals(vehicle.vechicleNo) : vehicle.vechicleNo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = vechicleNo != null ? vechicleNo.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + wheel;
        result = 31 * result + seat;
        return result;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vechicleNo='" + vechicleNo + '\'' +
                ", color='" + color + '\'' +
                ", wheel=" + wheel +
                ", seat=" + seat +
                '}';
    }
}
