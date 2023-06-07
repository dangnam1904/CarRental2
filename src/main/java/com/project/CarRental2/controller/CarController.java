package com.project.CarRental2.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.CarRental2.constants.FiledName;
import com.project.CarRental2.model.BrandCar;
import com.project.CarRental2.model.Car;
import com.project.CarRental2.model.District;
import com.project.CarRental2.model.Province;
import com.project.CarRental2.model.User;
import com.project.CarRental2.model.Ward;
import com.project.CarRental2.service.BrandCarService;
import com.project.CarRental2.service.CarService;
import com.project.CarRental2.service.ProvinceService;
import com.project.CarRental2.service.UploadFile;
import com.project.CarRental2.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CarController implements FiledName {

	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private BrandCarService brandCarService;

	@Autowired
	private UserService userService;

	@Autowired
	private UploadFile uploadFile;

	@Autowired
	private CarService carService;

	@GetMapping(path = { "/register-car" })
	public String registerFormCar(Model model, HttpServletRequest request, RedirectAttributes ra) {
		HttpSession session = request.getSession();

		if (session.getAttribute("sesionUser") == null) {
			ra.addFlashAttribute("mes_login", "Cần phải đăng nhập");
			return "redirect:/";
		}
		model.addAttribute("car", new Car());

		model.addAttribute("province", provinceService.getAllProvinceOrderByName());
		model.addAttribute("brandcar", brandCarService.getAllBrandCarOderByNameAsc());
		return "pages/reigster-car";
	}

	@GetMapping(path = { "/edit-car/{idCar}" })
	public String getFormEditCar(Model model, HttpServletRequest request, RedirectAttributes ra,
			@PathVariable(name = "idCar", required = false) String idCar) {
		HttpSession session = request.getSession();

		if (session.getAttribute("sesionUser") == null) {
			ra.addFlashAttribute("mes_login", "Cần phải đăng nhập");
			return "redirect:/";
		}
		if (idCar == null || idCar == "") {
			return "redirect:/";
		} else {
			model.addAttribute("car", carService.getACarByIdCar(Integer.valueOf(idCar)));
		}
		model.addAttribute("province", provinceService.getAllProvinceOrderByName());
		model.addAttribute("brandcar", brandCarService.getAllBrandCarOderByNameAsc());
		return "pages/edit-car";
	}

	@PostMapping("/register-car")
	public String registerCar(@ModelAttribute("car") Car car, @RequestParam("brandcar") BrandCar brandCar,
			@RequestParam("province") Province province, @RequestParam("district") District district,
			@RequestParam("ward") Ward ward, @RequestParam(name = "img-main", required = false) MultipartFile avatarCar,
			@RequestParam(name = "img-sub", required = false) MultipartFile[] imgSub, HttpServletRequest request,
			RedirectAttributes ra) {
		HttpSession session = request.getSession();
		User use = (User) session.getAttribute("sesionUser");
		if (car.getIdCar() == 0) {
			List<Car> listCar = carService.getAllCarOrderByNameCarAsc();
			for (Car c : listCar) {
				if (c.getLicensePlates().equals(car.getLicensePlates())) {
					ra.addFlashAttribute("mes_faill", "Biển số xe đã tồn tại");
					return "redirect:/register-car";
				}
			}
			car.setAddressCar(car.getAddressCar() + "," + ward.getNameWard() + "," + district.getNameDistrict() + ","
					+ province.getNameProvince());
			car.setBrandCar(brandCar);
			car.setOldPromotionalPrice(car.getPromotionalPrice());
			car.setCreateDate(new Date());
			car.setUpdateDate(new Date());
			car.setAvatarCar(uploadFile.uploadSingleFile(avatarCar));
			car.setStatus(STATUS_PENDING);
			car.setImageCar(uploadFile.uploadMultiFile(imgSub));
			car.setUser(use);
			System.out.println(car.toString());
			carService.saveCar(car);
			ra.addFlashAttribute("mes_registerCar", "Đăng kí xe thành công");
		} else {
			Car oldCar = carService.getACarByIdCar(car.getIdCar());
			car.setAddressCar(car.getAddressCar() + "," + ward.getNameWard() + "," + district.getNameDistrict() + ","
					+ province.getNameProvince());
			car.setBrandCar(brandCar);
			car.setCreateDate(oldCar.getCreateDate());
			car.setUpdateDate(new Date());
			car.setOldPromotionalPrice(car.getPromotionalPrice());
			if (avatarCar.getOriginalFilename() == "") {
				car.setAvatarCar(oldCar.getAvatarCar());
			} else {
				car.setAvatarCar(uploadFile.uploadSingleFile(avatarCar));
				uploadFile.removeFile(oldCar.getAvatarCar());
			}
			car.setStatus(oldCar.getStatus());
			if (imgSub.length <= 1) {
				car.setImageCar(oldCar.getImageCar());
			} else {
				car.setImageCar(uploadFile.uploadMultiFile(imgSub));
				String[] arrayImg = oldCar.getImageCar().split(";");
				for (int i = 0; i < arrayImg.length; i++) {
					uploadFile.removeFile(arrayImg[i]);
				}
			}
			car.setUser(use);
			System.out.println(car.toString());
			carService.saveCar(car);
			ra.addFlashAttribute("mes_registerCar", "Sửa thành công");
		}

		return "redirect:/";
	}

	@GetMapping("/admin/car{search}")
	public String getListCar(Model model, @RequestParam(name = "search", required = false) String nameCar, 
			 HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getIdRole() == ROLE_CUSTOMMER_CARE 
					|| sessionUser.getRole().getIdRole() == ROLE_ADMIN ){
				/* biến = Biểu thức logic ? Câu lệnh khi biểu thức trả về true : Câu lệnh khi biếu thức trả về false; */
				List<Car> listCar= nameCar==null 
						? carService.getAllCarWithUserAndBrandOrderByNameCarAsc()
						:carService.findCarByNameCarContaining(nameCar);
			
				model.addAttribute("listcar", listCar);
				System.err.println(nameCar);
				return "admin/pages/car/list";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}

	@GetMapping("/admin/car/status-pending/{id}")
	public String changStatusPending(@PathVariable(name = "id") int id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getIdRole() == ROLE_CUSTOMMER_CARE 
					|| sessionUser.getRole().getIdRole() == ROLE_ADMIN ) {
				try {
					carService.changeStatusCar(STATUS_PENDING, id);
				} catch (JpaSystemException e) {
					System.err.println("ex");
				}
				return "redirect:/admin/car";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}

	}

	@GetMapping("/admin/car/status-approved/{id}")
	public String changStatusApproved(@PathVariable(name = "id") int id, 
			 HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getIdRole() == ROLE_CUSTOMMER_CARE 
					|| sessionUser.getRole().getIdRole() == ROLE_ADMIN ) {
				try {
					carService.changeStatusCar(STATUS_APPROVED, id);
				} catch (JpaSystemException e) {
					System.err.println("ex");
				}

				return "redirect:/admin/car";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}

	@GetMapping("/admin/car/add")
	public String getForm(Model model, RedirectAttributes ra,
			 HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getIdRole() == ROLE_CUSTOMMER_CARE 
					|| sessionUser.getRole().getIdRole() == ROLE_ADMIN ) {
				model.addAttribute("car", new Car());
				model.addAttribute("province", provinceService.getAllProvinceOrderByName());
				model.addAttribute("brandcar", brandCarService.getAllBrandCarOderByNameAsc());
				model.addAttribute("user", userService.getAllUserOrderByUsername());

				return "admin/pages/car/form-add";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}

	}

	@PostMapping("/admin/car/add")
	public String saveCarEdit(@ModelAttribute("car") Car car, @RequestParam("brandcar") BrandCar brandCar,
			@RequestParam("user") User user, @RequestParam("province") Province province,
			@RequestParam("district") District district, @RequestParam("ward") Ward ward,
			@RequestParam(name = "img-main", required = false) MultipartFile avatarCar,
			@RequestParam(name = "img-sub", required = false) MultipartFile[] imgSub,
			RedirectAttributes ra,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getIdRole() == ROLE_CUSTOMMER_CARE 
					|| sessionUser.getRole().getIdRole() == ROLE_ADMIN ) {
				List<Car> listCar = carService.getAllCarOrderByNameCarAsc();
				for (Car c : listCar) {
					if (c.getLicensePlates().equals(car.getLicensePlates())) {
						ra.addFlashAttribute("mes_faill", "Biển xe đã tồn tại");
						return "redirect:/admin/car/add";
					}
				}
				car.setAddressCar(car.getAddressCar() + "," + ward.getNameWard() + "," + district.getNameDistrict() + ","
						+ province.getNameProvince());
				car.setBrandCar(brandCar);
				car.setCreateDate(new Date());
				car.setUpdateDate(new Date());
				car.setAvatarCar(uploadFile.uploadSingleFile(avatarCar));
				car.setStatus(STATUS_PENDING);
				car.setImageCar(uploadFile.uploadMultiFile(imgSub));
				car.setOldPromotionalPrice(car.getPromotionalPrice());
				car.getUser().setIdUser(user.getIdUser());
				System.out.println(car.toString());
				carService.saveCar(car);
				ra.addFlashAttribute("mes_success", "Thêm thành công");
				return "redirect:/admin/car";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}

	@GetMapping("/admin/car/edit/{id}")
	public String editCar(Model model, @PathVariable(name = "id") int id_car, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getIdRole() == ROLE_CUSTOMMER_CARE 
					|| sessionUser.getRole().getIdRole() == ROLE_ADMIN ){

				String[] arrayAddress = carService.getACarByIdCar(id_car).getAddressCar().split(",");
				String province = arrayAddress[arrayAddress.length - 1];
				model.addAttribute("provin", province);
				model.addAttribute("car", carService.getACarByIdCar(id_car));
				model.addAttribute("province", provinceService.getAllProvinceOrderByName());
				model.addAttribute("brandcar", brandCarService.getAllBrandCarOderByNameAsc());
				model.addAttribute("user", userService.getAllUserOrderByUsername());
				return "admin/pages/car/form-edit";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}

	@PostMapping("/admin/car/edit")
	public String saveNewCar(@ModelAttribute("car") Car car, @RequestParam("brandcar") BrandCar brandCar,
			@RequestParam("user") User user, @RequestParam("province") Province province,
			@RequestParam("district") District district, @RequestParam("ward") Ward ward,
			@RequestParam(name = "img-main", required = false) MultipartFile avatarCar,
			@RequestParam(name = "img-sub", required = false) MultipartFile[] imgSub, 
			RedirectAttributes ra, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getIdRole() == ROLE_CUSTOMMER_CARE 
					|| sessionUser.getRole().getIdRole() == ROLE_ADMIN ) {
				Car oldCar = carService.getACarByIdCar(car.getIdCar());
				car.setAddressCar(car.getAddressCar() + "," + ward.getNameWard() + "," + district.getNameDistrict() + ","
						+ province.getNameProvince());
				car.setBrandCar(brandCar);
				car.setCreateDate(new Date());
				car.setUpdateDate(new Date());
				car.setOldPromotionalPrice(car.getPromotionalPrice());
				if (avatarCar.getOriginalFilename() == "") {
					car.setAvatarCar(oldCar.getAvatarCar());
				} else {
					car.setAvatarCar(uploadFile.uploadSingleFile(avatarCar));
					uploadFile.removeFile(oldCar.getAvatarCar());
				}
				if (imgSub.length <= 1) {
					car.setImageCar(oldCar.getImageCar());
				} else {
					car.setImageCar(uploadFile.uploadMultiFile(imgSub));
					String[] arrayImg = oldCar.getImageCar().split(";");
					for (int i = 0; i < arrayImg.length; i++) {
						uploadFile.removeFile(arrayImg[i]);
					}
				}
				car.setStatus(STATUS_PENDING);
				car.getUser().setIdUser(user.getIdUser());
				System.out.println(car.toString());
				carService.saveCar(car);
				ra.addFlashAttribute("mes_success", "Sửa thành công");
				return "redirect:/admin/car";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}

	}

	@GetMapping("/stop-car/{id}")
	public String stopCar(Model model, HttpServletRequest request, @PathVariable(name = "id") int idCar) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("sesionUser");
		if (user == null) {
			return "redirect:/login";
		} else {
			carService.changeStatusCar(STATUS_STOP, idCar);
		}
		return "redirect:/my-car";
	}

	@GetMapping("/active-car/{id}")
	public String activeCar(Model model, HttpServletRequest request, @PathVariable(name = "id") int idCar) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("sesionUser");
		if (user == null) {
			return "redirect:/login";
		} else {
			carService.changeStatusCar(STATUS_APPROVED, idCar);
		}
		return "redirect:/my-car";
	}

	@GetMapping("/get-car-status/{status-car}")
	public String getCarByStatus(Model model, HttpServletRequest request,
			@PathVariable(name = "status-car") int statusCar) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("sesionUser");
		if (user == null) {
			return "redirect:/login";
		} else {
			model.addAttribute("user", user);
			if (statusCar == ALL_STATUS) {
				model.addAttribute("listCar", carService.getAllCarByIdUser(user.getIdUser()));
			} else {
				model.addAttribute("listCar",
						carService.findCarByUserIdUserAndStatusOrderByNameCar(user.getIdUser(), statusCar));
			}
		}
		return "pages/my-car";
	}

}
