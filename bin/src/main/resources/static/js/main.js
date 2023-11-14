$(document).ready(function() {
	const tinhnang = document.querySelector("#carouselTinhNang");
	const addressCarDriver = document.querySelector("#carouselAddressDriver");
	const carNoDriver = document.querySelector("#carouselCarNotDriver");
	const carDriver = document.querySelector("#carouselCarDriver");
	const addressNotCarDriver = document.querySelector("#carouselAddressNotCarDriver");

	const imgCar = document.querySelector('#carouselImgCar');
	if (imgCar != null) {
		carouselImgCar();
	}
	if (tinhnang != null) {
		carouselTinhNang();
	}
	if (addressCarDriver != null) {
		carouselAddressDriver();
	}
	if (carNoDriver != null) {
		carouselNotDriver();
	}
	if (carDriver != null) {
		carouselDriver();
	}

	if (addressNotCarDriver != null) {
		carouselAddressNotDriver()
	}
});

function carouselImgCar() {
	const multipleItemCarousel = document.querySelector("#carouselImgCar");

	if (window.matchMedia("(min-width:576px)").matches) {
		const carousel = new bootstrap.Carousel(multipleItemCarousel, {
			interval: false
		});
		var carouselWidth = $(".carousel-inner-ImgCar")[0].scrollWidth;
		var cardWidth = $(".carousel-item-ImgCar").width();
		var scrollPosition = 0;

		$("#carousel-control-next-ImgCar").on("click", function() {
			if (scrollPosition < carouselWidth - cardWidth * 4) {

				scrollPosition = scrollPosition + cardWidth;
				$(".carousel-inner-ImgCar").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
		$("#carousel-control-prev-ImgCar").on("click", function() {
			if (scrollPosition > 0) {

				scrollPosition = scrollPosition - cardWidth;
				$(".carousel-inner-ImgCar").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
	} else {
		$(multipleItemCarousel).addClass("slide");
	}
}
function carouselTinhNang() {
	const multipleItemCarousel = document.querySelector("#carouselTinhNang");
	 console.log(multipleItemCarousel);
	if (window.matchMedia("(min-width:576px)").matches) {
		const carousel = new bootstrap.Carousel(multipleItemCarousel, {
			interval: false
		});
		var carouselWidth = $(".carousel-inner-tt")[0].scrollWidth;
		var cardWidth = $(".carousel-item-tt").width();
		var scrollPosition = 0;
		console.log(cardWidth, carouselWidth)

		$("#carousel-control-next-tt").on("click", function() {
			if (scrollPosition < carouselWidth - cardWidth * 4) {
				console.log("next-tt");
				scrollPosition = scrollPosition + cardWidth;
				$(".carousel-inner-tt").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
		$("#carousel-control-prev-tt").on("click", function() {
			if (scrollPosition > 0) {
				console.log("prev-tt");
				scrollPosition = scrollPosition - cardWidth;
				$(".carousel-inner-tt").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
	} else {
		$(multipleItemCarousel).addClass("slide");
	}
}

function carouselAddressDriver() {

	const multipleItemCarousel = document.querySelector("#carouselAddressDriver");

	if (window.matchMedia("(min-width:576px)").matches) {
		const carousel = new bootstrap.Carousel(multipleItemCarousel, {
			interval: false
		});

		var carouselWidth = $(".carousel-inner-address-driver")[0].scrollWidth;

		var cardWidth = $(".carousel-item-address-driver").width();

		var scrollPosition = 0;

		$("#carousel-control-next-address-driver").on("click", function() {
			console.log(" next -driver")
			if (scrollPosition < carouselWidth - cardWidth * 4) {
				console.log("next có tài");
				scrollPosition = scrollPosition + cardWidth;
				$(".carousel-inner-address-driver").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
		$("#carousel-control-prev-address-driver").on("click", function() {
			if (scrollPosition > 0) {
				console.log("prev có tài");
				scrollPosition = scrollPosition - cardWidth;
				$(".carousel-inner-address-driver").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
	} else {
		$(multipleItemCarousel).addClass("slide");
	}
}

function carouselAddressNotDriver() {
	const multipleItemCarousel = document.querySelector("#carouselAddressNotCarDriver");
	if (window.matchMedia("(min-width:576px)").matches) {
		const carousel = new bootstrap.Carousel(multipleItemCarousel, {
			interval: false
		});

		var carouselWidth = $(".carousel-inner-address-no-driver")[0].scrollWidth;
		var cardWidth = $(".carousel-item-address-no-driver").width();

		var scrollPosition = 0;

		$("#carousel-control-next-address-no-driver").on("click", function() {
			if (scrollPosition < carouselWidth - cardWidth * 4) {
				console.log("next ko tài");
				scrollPosition = scrollPosition + cardWidth;
				$(".carousel-inner-address-no-driver").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
		$("#carousel-control-prev-address-no-driver").on("click", function() {
			if (scrollPosition > 0) {
				console.log("prev ko tài");
				scrollPosition = scrollPosition - cardWidth;
				$(".carousel-inner-address-no-driver").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
	} else {
		$(multipleItemCarousel).addClass("slide");
	}
}

function carouselNotDriver() {
	const multipleItemCarousel = document.querySelector("#carouselCarNotDriver");
	if (window.matchMedia("(min-width:576px)").matches) {
		const carousel = new bootstrap.Carousel(multipleItemCarousel, {
			interval: false
		});

		var carouselWidth = $(".carousel-inner-no-driver")[0].scrollWidth;
		var cardWidth = $(".carousel-item-no-driver").width();

		var scrollPosition = 0;

		$("#carousel-control-next-no-driver").on("click", function() {
			if (scrollPosition < carouselWidth - cardWidth * 4) {
				console.log("next ko tài");
				scrollPosition = scrollPosition + cardWidth;
				$(".carousel-inner-no-driver").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
		$("#carousel-control-prev-no-driver").on("click", function() {
			if (scrollPosition > 0) {
				console.log("prev ko tài");
				scrollPosition = scrollPosition - cardWidth;
				$(".carousel-inner-no-driver").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
	} else {
		$(multipleItemCarousel).addClass("slide");
	}
}

function carouselDriver() {
	const multipleItemCarousel = document.querySelector("#carouselCarDriver");
	if (window.matchMedia("(min-width:576px)").matches) {
		const carousel = new bootstrap.Carousel(multipleItemCarousel, {
			interval: false
		});

		var carouselWidth = $(".carousel-inner-driver")[0].scrollWidth;
		var cardWidth = $(".carousel-item-driver").width();

		var scrollPosition = 0;

		$("#carousel-control-next-driver").on("click", function() {
			if (scrollPosition < carouselWidth - cardWidth * 4) {
				console.log("next ko tài");
				scrollPosition = scrollPosition + cardWidth;
				$(".carousel-inner-driver").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
		$("#carousel-control-prev-driver").on("click", function() {
			if (scrollPosition > 0) {
				console.log("prev ko tài");
				scrollPosition = scrollPosition - cardWidth;
				$(".carousel-inner-driver").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
	} else {
		$(multipleItemCarousel).addClass("slide");
	}
}


// Get sum car has driver in address
$(document).ready(function() {

	var a = document.getElementsByClassName("name-car")
	console.log(a);
	var sumcar = document.getElementsByClassName("sum-car")
	for (let i = 0; i < a.length; i++) {

		$.ajax({
			url: "/sum-car-has-driver",
			data: {
				address: a[i].innerText
			},
			type: "GET",
			responseType: "application/json"
		}).done(function(ketqua) {
			sumcar[i].innerHTML = ketqua + "  xe +";
		})
	}

});

// Get sum car no driver in address
$(document).ready(function() {

	var a = document.getElementsByClassName("name-car-no-driver")
	var sumcar = document.getElementsByClassName("sum-car-no-driver")
	for (let i = 0; i < a.length; i++) {

		$.ajax({
			url: "/sum-car-no-driver",
			data: {
				address: a[i].innerText
			},
			type: "GET",
			responseType: "application/json"
		}).done(function(ketqua) {
			sumcar[i].innerHTML = ketqua + "  xe +";
		})
	}

});

$(document).ready(function() {

	var arrIdCar = document.getElementsByClassName("id-car")
	var arrSumDelivery = document.getElementsByClassName("sum-delivery")
	for (let i = 0; i < arrIdCar.length; i++) {

		$.ajax({
			url: "/sum-delivery-in-car",
			data: {
				idCar: arrIdCar[i].value
			},
			type: "GET",
			responseType: "application/json"
		}).done(function(ketqua) {
			arrSumDelivery[i].innerHTML ="Số chuyến: "+ ketqua ;
		})
	}

});

$(document).ready(function() {

	const now = new Date();
	const year = now.getFullYear();
	const month = (now.getMonth() + 1).toString().padStart(2, '0');
	const day = now.getDate().toString().padStart(2, '0');
	var daynew = Number(day) + 1;
	var dayEnd= daynew.toString().padStart(2, '0');
	
	const hours = now.getHours().toString().padStart(2, '0');
	const minutes = now.getMinutes().toString().padStart(2, '0');
	const datetimeStringStart = `${year}-${month}-${day}T${hours}:${minutes}`;
	const datetimeStringEnd = `${year}-${month}-${dayEnd}T${hours}:${minutes}`;

	document.getElementById("inputDateStart").value = datetimeStringStart;
	document.getElementById("inputDateEnd").value = datetimeStringEnd;

	var pricebooking = document.getElementById("price-booking").innerText;
	
	var pricebh = document.getElementById("pirce-bh").innerText;
	var arrpricebooking = splitData("K", pricebooking);
	var price_dv_by_price =(Number(arrpricebooking[0]*1000)*5/100+ 1000);
	document.getElementById("price-dv").innerText= price_dv_by_price.toString().substring(0,price_dv_by_price.toString().length-3)+"K/ngày";
	var pricedv = document.getElementById("price-dv").innerText;
	var arrpricedv = splitData("K", pricedv);
	var arrpricrbh = splitData("K", pricebh);
	
	var sumfeed = (Number(arrpricebooking[0]) + Number(arrpricedv[0]) + Number(arrpricrbh[0]));
	document.getElementById("price-total-per-day").innerText = sumfeed + "K/Ngày";
	document.getElementById("total_bill").innerText = new Intl.NumberFormat().format(sumfeed * 1000) + "đ";
	document.getElementById("input_total_bill").value = sumfeed * 1000;
	
	var start = document.getElementById("inputDateStart");
	var end = document.getElementById("inputDateEnd");
	var idCar= document.getElementById("idCar");
	let dateStart = splitData("T", start.value)
	let dateEnd = splitData("T", end.value)
	checkBillonTime(idCar.value, dateStart[0], dateEnd[0]);

});
function changeData() {
	var start = document.getElementById("inputDateStart");
	var end = document.getElementById("inputDateEnd");
	var idCar= document.getElementById("idCar");
	let dateStart = splitData("T", start.value)
	let dateS = splitData("-", dateStart[0]);
	let dateEnd = splitData("T", end.value)
	let dateE = splitData("-", dateEnd[0]);
	sumDate(dateS, dateE);
	
	
	checkBillonTime(idCar.value, dateStart[0], dateEnd[0]);
	
	var pricebooking = document.getElementById("price-booking").innerText;
	var pricedv = document.getElementById("price-dv").innerText;
	var pricebh = document.getElementById("pirce-bh").innerText;
	var arrpricebooking = splitData("K", pricebooking);
	var arrpricedv = splitData("K", pricedv);
	var arrpricrbh = splitData("K", pricebh);
	var sumfeed = (Number(arrpricebooking[0]) + Number(arrpricedv[0]) + Number(arrpricrbh[0]));
	document.getElementById("price-total-per-day").innerText = sumfeed + "K x " + sumDate(dateS, dateE) + "Ngày";
	var total_bill = sumfeed * 1000 * sumDate(dateS, dateE);
	console.log(total_bill)
	document.getElementById("total_bill").innerText = new Intl.NumberFormat().format(total_bill) + "đ";
	document.getElementById("input_total_bill").value = total_bill;
}

function splitData(regex, value) {
	return value.split(regex);
}

function checkBillonTime(idCar, dateStart, dateEnd){

	$.ajax({
		url: "/check-date",
		data: {
			idCar:idCar,
			dateStart:dateStart,
			dateEnd: dateEnd
		},
		type: "GET",
		responseType: "application/json"
	}).done(function(ketqua) {
		console.log(ketqua);
		if(ketqua==true){
			document.getElementById('noti-error').innerText= "Xe bận trong thời gian này";
			document.getElementById('btn-booking').classList.add("disabled")
		}else{
			document.getElementById('noti-error').innerText= "";
			document.getElementById('btn-booking').classList.remove("disabled")
		}
	})
}

function sumDate(dateStart, dateEnd) {
	sumdate = 0;
	if (dateEnd[0] - dateStart[0] > 0) {
		sumdate = sumdate + (dateEnd[0] - dateStart[0]) * 365;
	}
	if (dateEnd[1] - dateStart[1] > 0) {
		sumdate = (dateEnd[1] - dateStart[1]) * 30 + sumdate;
	}
	if (dateEnd[2] - dateStart[2] > 0) {
		sumdate = (dateEnd[2] - dateStart[2]) + sumdate;
	}
	return sumdate;
}

function GetDistrict(id) {
	var districts = document.getElementById('district');
	console.log(districts)
	$.ajax({
		url: "/../getDistrict",
		data: {
			id: id
		},
		type: "GET",
		responseType: "application/json"
	}).done(function(ketqua) {
		console.log(ketqua);
		district.length = 1;
		for (let d of ketqua) {
			districts.options[districts.options.length] = new Option(d.nameDistrict, d.idDistrict);
		}
	})

}

function getWard(id) {
	var wards = document.getElementById('ward');

	$.ajax({
		url: "/../getWard",
		data: {
			id: id
		},
		type: "GET",
		responseType: "application/json"
	}).done(function(ketqua) {
		console.log(ketqua);
		wards.length = 1;
		for (let d of ketqua) {
			wards.options[wards.options.length] = new Option(d.nameWard, d.idWard);
		}
	})
}

$(document).ready(function() {
	document.getElementById("content-noti").style.visibility="hidden"
})
var click= 1;
function openNotification(){
	click=click+1;
	if(click%2==0){
		document.getElementById("content-noti").style.visibility="visible"
	}else{
		document.getElementById("content-noti").style.visibility="hidden"
	}
}

function readingAllNotification(){
	window.location.href ="/reading-notification";	
}

function readingNoti(){
	var idNoti= document.querySelector(".id_noti")
	console.log(idNoti.value);
	/*$.ajax({
		url: "/reading-notification/",
		data: {
			id: id
		},
		type: "GET",
		responseType: "application/json"
	}).done(function(ketqua) {
		console.log(ketqua);
		wards.length = 1;
		for (let d of ketqua) {
			wards.options[wards.options.length] = new Option(d.nameWard, d.idWard);
		}
	})*/
}

