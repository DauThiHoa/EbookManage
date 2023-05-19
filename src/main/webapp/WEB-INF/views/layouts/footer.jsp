

<!-- footer -->
	<footer>
		<div class="footer-top-first">
			<div class="container py-md-5 py-sm-4 py-3">
				<!-- footer second section -->
				<div class="row w3l-grids-footer border-top border-bottom py-sm-4 py-3">
					<div class="col-md-4 offer-footer">
						<div class="row">
							<div class="col-4 icon-fot">
								<img src="../image1/serv-3.png" alt="">
							</div>
							<div class="col-8 text-form-footer">
								<h3>Free Shipping</h3>
							</div>
						</div>
					</div>
					<div class="col-md-4 offer-footer my-md-0 my-4">
						<div class="row">
							<div class="col-4 icon-fot">
								<img src="${pageContext.request.contextPath}/image1/serv-1.png" alt="">
							</div>
							<div class="col-8 text-form-footer">
								<h3>Fast Delivery</h3>
							</div>
						</div>
					</div>
					<div class="col-md-4 offer-footer">
						<div class="row">
							<div class="col-4 icon-fot">
								<img src="${pageContext.request.contextPath}/image1/serv-4.png" alt="">
							</div>
							<div class="col-8 text-form-footer">
								<h3>Huge Saving</h3>
							</div>
						</div>
					</div>

				</div>


			</div>
		</div>
	</footer>
	<!-- //footer -->
	<!-- copyright -->
	<div class="copy-right py-3">
		<div class="container">
			<p class="text-center text-white" style="font-weight: bold">2023 Ebook Store. All rights reserved.</p>
		</div>
	</div>
	<!-- //copyright -->
	<!-- js-files -->
	<script src="${pageContext.request.contextPath}/js/jquery-2.2.3.min.js"></script>
	<!-- nav smooth scroll -->
	<script>
<%--		hiển thị và ẩn menu thả xuống khi di chuột qua một phần tử cụ thể với lớp "thả xuống" --%>
		$(document).ready(function () {
			$(".dropdown").hover(
				function () {
					$('.dropdown-menu', this).stop(true, true).slideDown("fast");
					$(this).toggleClass('open');
				},
				function () {
					$('.dropdown-menu', this).stop(true, true).slideUp("fast");
					$(this).toggleClass('open');
				}
			);
		});
	</script>
	<!-- //nav smooth scroll -->
	<!-- popup modal (for location)-->
	<script src="${pageContext.request.contextPath}/js/jquery.magnific-popup.js"></script>
	<script>
<%--		tạo hiệu ứng hoạt hình thu phóng khi hiển thị nội dung nội tuyến trong cửa sổ bật lên --%>
		$(document).ready(function () {
			$('.popup-with-zoom-anim').magnificPopup({
				type: 'inline',
				fixedContentPos: false,
				fixedBgPos: true,
				overflowY: 'auto',
				closeBtnInside: true,
				preloader: false,
				midClick: true,
				removalDelay: 300,
				mainClass: 'my-mfp-zoom-in'
			});

		});
	</script>
	<!-- //popup modal (for location)-->

	<!-- cart-js -->
	<script src="${pageContext.request.contextPath}/js/minicart.js"></script>
	<script>
<%--		hiển thị Giỏ hàng --%>
		paypals.minicarts.render(); 
		paypals.minicarts.cart.on('checkout', function (evt) {
			var items = this.items(),
				len = items.length,
				total = 0,
				i;
			// Count the number of each item in the cart
			for (i = 0; i < len; i++) {
				total += items[i].get('quantity');
			}
		});
	</script>
	<script src="${pageContext.request.contextPath}/js/scroll.js"></script>
	<script src="${pageContext.request.contextPath}/js/SmoothScroll.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/move-top.js"></script>
	<script src="${pageContext.request.contextPath}/js/easing.js"></script>
	<script>
<%--		tạo hiệu ứng cuộn mượt mà khi nhấp vào các phần tử có lớp "cuộn" --%>
		jQuery(document).ready(function ($) {
			$(".scroll").click(function (event) {
				event.preventDefault();

				$('html,body').animate({
					scrollTop: $(this.hash).offset().top
				}, 1000);
			});
		});
	</script>
	<script>
<%--		tạo nút hoặc chức năng "quay lại đầu trang" trên trang web --%>
		$(document).ready(function () {
			$().UItoTop({
				easingType: 'easeOutQuart'
			});
		});
	</script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>