package com.apress.springrecipes.vehicle;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Date: 2/13/11
 * Time: 9:59 PM
 */
public class Main {

    private static Logger logger = Logger.getLogger(Main.class);
    private static String beanXML = "com/apress/springrecipes/vehicle/vehicle-bean.xml";

    public static void main(String...args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(beanXML);
        VehicleDao vehicleDao = (VehicleDao)context.getBean("vehicleDao");
        Vehicle vehicle = new Vehicle("TEM0001", "Red", 4, 4);
        vehicleDao.insert(vehicle);
        //
        Vehicle vehicleFromBean = vehicleDao.findByVehicleNo("TEM00001");
        StringBuilder buffy = new StringBuilder();
        buffy.append("Vehicle No.: ").append(vehicleFromBean.getVechicleNo()).append("\n");
        buffy.append("Color:       ").append(vehicleFromBean.getColor()).append("\n");
        buffy.append("Wheel:       ").append(vehicleFromBean.getWheel()).append("\n");
        buffy.append("Seat:        ").append(vehicleFromBean.getSeat()).append("\n");
        logger.info(buffy);
    }
}
