package com.ManageBookStore.ManageBookStore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

//    .addResourceLocations("file:uploads/")xác định vị trí của các tài nguyên tĩnh trên hệ thống tệp.
//    Tiền tố "tệp:" chỉ ra rằng các tài nguyên  được đặt trong hệ thống tệp cục bộ và "tải lên/"
//    là đường dẫn thư mục liên quan đến thư mục gốc của dự án.

	@Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:uploads/");
    }
}
