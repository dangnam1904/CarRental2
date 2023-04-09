package com.project.CarRental2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.CarRental2.model.Car;
@Repository
public interface CarRepository  extends JpaRepository<Car, Integer>{

	public static final  String sql_query="select c.id_car, c.address_car, c.airbags, c.avatar_car,c.babyseat,c.bluetooth,c.bonnet,c.camera360,c.create_date,c.dash_camera,\n"
			+ "c.delivery_fee_for1km,c.driver,c.dvd_screen,c.etc,c.fuel,c.fuel_for100km,c.gps_locator,c.id_brand,c.id_user,c.image_car,c.impact_sensor,\n"
			+ "c.license_plates,c.limit_crossingfee1km,c.manual_transmission_car,c.maps,c.maximum_delivery_distance,c.maximum_kilometersper_day,\n"
			+ "c.model_year,c.name_car,c.number_of_seats,c.overview_car,c.parking_camera,c.poly_use_car,c.price, c.promotional_price,c.reverse_camera,\n"
			+ "c.spare_tire,c.speed_warning,c.status,c.sunroof,c.tpms,c.update_date,c.usb,b.name_brand,u.username,u.name_display,u.phone,u.address\n"
			+ "from car c join brand_car b on c.id_brand=b.id_brand join users u on u.id_user=c.id_user";
	@Query(value = "select * from car order by name_car asc", nativeQuery = true)
	List<Car> getAllCarOrderByNameCarAsc();
	
	@Query(value = "update car set status=:status where id_car=:id_car", nativeQuery = true)
	void changeStatusCar(@Param("status") int status, @Param("id_car") int id_car);
	
	@Query(value = " "+sql_query+" order by name_car asc ", nativeQuery  = true)
	List<Car> getAllCarWithUserAndBrandOrderByNameCarAsc();
	
	@Query(value = ""+sql_query+" where c.driver=:driver order by name_car asc  ", nativeQuery = true)
	List<Car> getAllCarByDriverOderByName(@Param("driver") boolean driver);
	
	@Query(value = ""+sql_query+" where  c.driver=:driver and c.address_car like %:address% order by name_car asc  ", nativeQuery = true)
	List<Car> getAllCarByDriverInAddressOderByName(@Param("driver") boolean driver, @Param("address") String address);
	
	@Query(value = ""+sql_query+" where  c.driver=:driver and c.address_car like %:address% and c.promotional_price >0 order by name_car asc  ", nativeQuery = true)
	List<Car> getAllCarByDriverInAddressAndPromotionalPriceOderByName(@Param("driver") boolean driver, @Param("address") String address);
	List<Car> findCarByUserIdUserOrderByNameCarAsc(int id_user);
	
	int countByAddressCarContaining(String address);

	int countByAddressCarContainingAndDriver(String address, boolean driver);
	
}
