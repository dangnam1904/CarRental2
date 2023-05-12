package com.project.CarRental2.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.CarRental2.model.Booking;

import jakarta.transaction.Transactional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

	String sql_query = "select  c.id_car,  c.old_promotional_price ,c.address_car, c.airbags, c.avatar_car,c.babyseat,c.bluetooth,c.bonnet,c.camera360,c.dash_camera,\n"
			+ "c.delivery_fee_for1km,c.driver,c.dvd_screen,c.etc,c.fuel,c.fuel_for100km,c.gps_locator,c.id_brand,c.id_user,c.image_car,c.impact_sensor,\n"
			+ "c.license_plates,c.limit_crossingfee1km,c.manual_transmission_car,c.maps,c.maximum_delivery_distance,c.maximum_kilometersper_day,\n"
			+ "c.model_year,c.name_car,c.number_of_seats,c.overview_car,c.parking_camera,c.poly_use_car,c.price, c.promotional_price,c.reverse_camera,\n"
			+ "c.spare_tire,c.speed_warning,c.sunroof,c.tpms,c.usb,u.username,u.name_display,u.phone,u.address as address_user, b.id_booking,\n"
			+ "b.address,b.bill_total,b.create_date,b.date_end,b.date_start,b.id_user as id_usebooking,b.status_bill,b.update_date from booking b join car c on c.id_car= b.id_car join users u on u.id_user=c.id_user ";

	List<Booking> findBookingByUserIdUserOrderByDateStartDesc(int idUser);
	
	List<Booking> findBookingByUserIdUserAndStatusBillOrderByDateStartDesc(int idUser, int statusBill);

	String sql = "select b.id_booking,b.id_car, b.id_user, b.phone,\n"
			+ "b.address,b.bill_total,b.create_date,b.date_end,b.date_start,b.id_user as id_usebooking, "
			+ "b.status_bill, b.update_date from booking b join car c on c.id_car= b.id_car join users u on u.id_user=c.id_user";

	@Modifying
	@Transactional
	@Query(value = "update booking set status_bill=:statusBill where id_booking =:idBooking", nativeQuery = true)
	void changeStatusBill(@Param("statusBill") int statusBill, @Param("idBooking") int idBooking);

	@Query(value = "" + sql + " where c.id_user=:idUser", nativeQuery = true)
	List<Booking> getAllBookingWithCarOwner(@Param("idUser") int idUser);

	@Query(value = "" + sql + " where c.id_user=:idUser and status_bill=:statusBill", nativeQuery = true)
	List<Booking> getAllBookingWithCarOwnerAndStatusBill(@Param("idUser") int idUser,
			@Param("statusBill") int statusBill);

	@Query(value = "" + sql + " where status_bill=:statusBill", nativeQuery = true)
	List<Booking> getAllBookingByStatusBill(@Param("statusBill") int statusBill);

	List<Booking> findBookingByCarIdCarAndDateStartGreaterThanEqualAndDateEndLessThanEqual(@Param("idCar") int idCar,
			@Param("dateStart") Date dateStart, @Param("dateEnd") Date dateEnd);

	@Query(value = "select *from booking  where id_car=:idCar and CONVERT(nvarchar(10),date_start,127)>=:dateStart"
			+ " and CONVERT(nvarchar(10),date_end,127)<=:dateEnd", nativeQuery = true)
	List<Booking> checkBillExistOnTime(@Param("idCar") int idCar, @Param("dateStart") String dateStart,
			@Param("dateEnd") String dateEnd);

	@Query(value = "select *from booking  where  CONVERT(nvarchar(10),date_start,127)>=:dateStart and"
			+ " CONVERT(nvarchar(10),date_end,127)<=:dateEnd", nativeQuery = true)
	List<Booking> getAllBookingOnTime(@Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd);

	int countByCarIdCar(int idCar);

	long count();

	List<Booking> findBookingByStatusBill(int statusBill);

	@Modifying
	@Query(value = "select CONVERT(nvarchar(10),date_start,127)  as ngaydat, sum(bill_total) as tongtien from booking where "
			+ "CONVERT(nvarchar(10),date_start,127)>=:dateStart and CONVERT(nvarchar(10),date_end,127)<=:dateEnd "
			+ "and status_bill=:statusBill group by CONVERT(nvarchar(10),date_start,127)", nativeQuery = true)
	String[] sumRevenueOnTime(@Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd,
			@Param("statusBill") int statusBill);
	
	@Modifying
	@Query(value = "select CONVERT(nvarchar(10),date_start,127)  as ngaydat, sum(bill_total) as tongtien from booking b join"
			+ " car c on c.id_car= b.id_car join users u on u.id_user=c.id_user where CONVERT(nvarchar(10),date_start,127)>=:dateStart"
			+ " and CONVERT(nvarchar(10),date_end,127)<=:dateEnd and status_bill=:statusBill and c.id_user=:idUser"
			+ " group by CONVERT(nvarchar(10),date_start,127)", nativeQuery = true)
	String[] sumRevenueOnTimeByIdUser(@Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd,
			@Param("statusBill") int statusBill, @Param("idUser") int idUser);
	
	@Modifying
	@Query(value=""+sql+" where CONVERT(nvarchar(10),date_start,127)>=:dateStart and CONVERT(nvarchar(10),date_end,127)<=:dateEnd"
			+ " and status_bill=:statusBill and c.id_user=:idUser ", nativeQuery = true)
	List<Booking> getAllBookingOnTimeByIdUserHaveCar(@Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd,
			@Param("statusBill") int statusBill, @Param("idUser") int idUser);

	@Modifying
	@Query(value = "select * from booking where CONVERT(nvarchar(10),date_start,127)>=:dateStart"
			+ " and CONVERT(nvarchar(10),date_end,127)<=:dateEnd and status_bill=:statusBill ", nativeQuery = true)
	List<Booking> getBookingOnTimeByStatusBill(@Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd,
			@Param("statusBill") int statusBill);
}
