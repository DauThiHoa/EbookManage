
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
		$(document).ready(function () {
			$().UItoTop({
				easingType: 'easeOutQuart'
			});
		});
	</script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>