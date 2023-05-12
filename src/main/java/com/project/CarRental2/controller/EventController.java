package com.project.CarRental2.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.CarRental2.constants.FiledName;
import com.project.CarRental2.model.Car;
import com.project.CarRental2.model.DetailNotification;
import com.project.CarRental2.model.Event;
import com.project.CarRental2.model.Notification;
import com.project.CarRental2.model.User;
import com.project.CarRental2.service.CarService;
import com.project.CarRental2.service.DetailNotificationService;
import com.project.CarRental2.service.EventService;
import com.project.CarRental2.service.NotificationService;
import com.project.CarRental2.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EventController implements FiledName {

	@Autowired
	private EventService eventService;

	@Autowired
	private CarService carService;

	@Autowired
	private UserService userService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private DetailNotificationService detailNotificationService;

	@GetMapping("/admin/event")
	public String getAllEvent(Model model,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("listEvent", eventService.getAllEvent());
				return "admin/pages/event/list";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}

	@GetMapping("/admin/event/add")
	public String getFormAddEvent(Model model,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("event", new Event());
				model.addAttribute("action", "Thêm");
				return "admin/pages/event/form";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}

	@PostMapping("/admin/event/save")
	public String getFormAddEvent(Model model, @ModelAttribute("event") Event event,
			 HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				if (event.getIdEvent() == 0) {
					event.setCreateDate(new Date());
					event.setUpdateDate(new Date());
				} else {
					Event oldEvent = eventService.getEventByIdEvent(event.getIdEvent());
					event.setCreateDate(oldEvent.getCreateDate());
				}
				event.setDateStartEvent(event.getDateStartEvent().substring(5, event.getDateStartEvent().length()));
				event.setDateEndEvent(event.getDateEndEvent().substring(5, event.getDateEndEvent().length()));
				eventService.saveEvent(event);

				return "redirect:/admin/event";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}

	@GetMapping("/admin/event/edit/{idEvent}")
	public String getFormEditEvent(Model model, @PathVariable(name = "idEvent") int idEvent,
			 HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("event", eventService.getEventByIdEvent(idEvent));
				model.addAttribute("action", "Sửa");
				return "admin/pages/event/form";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}

	@Bean
	public void getEvent() {
		boolean chekKDuplicate = false;
		List<Event> listEvent = eventService.getAllEvent();
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
		String dateConvert = dateFormat.format(now);
		List<Car> listCar = carService.getAllCarOrderByNameCarAsc();
		List<User> listUser = userService.getAllUserOrderByUsername();
		Notification notification = null;
		for (Event event : listEvent) {
			if (event.getDateStartEvent().compareTo(dateConvert) == 0
					|| event.getDateEndEvent().compareTo(dateConvert) > 0) {
				carService.updatePromotionalPriceCar(event.getDiscount());
				notification = new Notification(0, "logo1.png", event.getTitleEvent(), event.getContentEvent(), now,
						now);
				List<Notification> listNotifications = notificationService.getAllNotification();
				for (Notification noti : listNotifications) {
					if (noti.getTitleNoti().equals(notification.getTitleNoti()) == true) {
						chekKDuplicate = true;
					}
				}
				if (chekKDuplicate == false) {
					System.err.println("change promi price");
					notificationService.saveNotification(notification);
					Notification newNotification = notificationService.getNotificationLaster();

					for (User user : listUser) {
						DetailNotification detailNotification = new DetailNotification(0, NO_READING,"/", user,
								newNotification);
						detailNotificationService.saveDetail(detailNotification);
					}
				}
			}
			if (event.getDateEndEvent().compareTo(dateConvert) == 0) {
				System.err.println("restore price");
				for (Car car : listCar) {
					carService.resetPromotionalPriceCar(car.getOldPromotionalPrice(), car.getIdCar());
				}
			}

		}
	}
}
