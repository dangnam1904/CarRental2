$(document).ready(function () {
	const tinhnang = document.querySelector("#carouselTinhNang");
	const addressCarDriver = document.querySelector("#carouselAddressDriver");
	const carNoDriver = document.querySelector("#carouselCarNotDriver");
	const carDriver = document.querySelector("#carouselCarDriver");
	const addressNotCarDriver = document.querySelector("#carouselAddressNotCarDriver");
	const imgCar= document.querySelector('#carouselImgCar');
	if(imgCar!=null){
		carouselImgCar();
	}
	if(tinhnang!=null){
		carouselTinhNang();
	}
	if(addressCarDriver!=null){
		carouselAddressDriver();
	}
	if(carNoDriver!=null){
		carouselNotDriver();
	}
	if(carDriver !=null){
		carouselDriver();
	}
	
	if(addressNotCarDriver!=null){
		carouselNotDriver();
	}
});

function carouselImgCar(){
	const multipleItemCarousel = document.querySelector("#carouselImgCar");

	if (window.matchMedia("(min-width:576px)").matches) {
		const carousel = new bootstrap.Carousel(multipleItemCarousel, {
			interval: false
		});
		var carouselWidth = $(".carousel-inner-ImgCar")[0].scrollWidth;
		var cardWidth = $(".carousel-item-ImgCar").width();
		var scrollPosition = 0;

		$("#carousel-control-next-ImgCar").on("click", function () {
			if (scrollPosition < carouselWidth - cardWidth * 4) {
				
				scrollPosition = scrollPosition + cardWidth;
				$(".carousel-inner-ImgCar").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
		$("#carousel-control-prev-ImgCar").on("click", function () {
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

	if (window.matchMedia("(min-width:576px)").matches) {
		const carousel = new bootstrap.Carousel(multipleItemCarousel, {
			interval: false
		});
		var carouselWidth = $(".carousel-inner-tt")[0].scrollWidth;
		var cardWidth = $(".carousel-item-tt").width();
		var scrollPosition = 0;

		$("#carousel-control-next-tt").on("click", function () {
			if (scrollPosition < carouselWidth - cardWidth * 4) {
				console.log("next-tt");
				scrollPosition = scrollPosition + cardWidth;
				$(".carousel-inner-tt").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
		$("#carousel-control-prev-tt").on("click", function () {
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

		$("#carousel-control-next-address-driver").on("click", function () {
			console.log(" next -driver")
			if (scrollPosition < carouselWidth - cardWidth * 4) {
				console.log("next có tài");
				scrollPosition = scrollPosition + cardWidth;
				$(".carousel-inner-address-driver").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
		$("#carousel-control-prev-address-driver").on("click", function () {
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

		$("#carousel-control-next-address-no-driver").on("click", function () {
			if (scrollPosition < carouselWidth - cardWidth * 4) {
				console.log("next ko tài");
				scrollPosition = scrollPosition + cardWidth;
				$(".carousel-inner-address-no-driver").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
		$("#carousel-control-prev-address-no-driver").on("click", function () {
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

		$("#carousel-control-next-no-driver").on("click", function () {
			if (scrollPosition < carouselWidth - cardWidth * 4) {
				console.log("next ko tài");
				scrollPosition = scrollPosition + cardWidth;
				$(".carousel-inner-no-driver").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
		$("#carousel-control-prev-no-driver").on("click", function () {
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

		$("#carousel-control-next-driver").on("click", function () {
			if (scrollPosition < carouselWidth - cardWidth * 4) {
				console.log("next ko tài");
				scrollPosition = scrollPosition + cardWidth;
				$(".carousel-inner-driver").animate({ scrollLeft: scrollPosition }, 600);
			}
		});
		$("#carousel-control-prev-driver").on("click", function () {
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

