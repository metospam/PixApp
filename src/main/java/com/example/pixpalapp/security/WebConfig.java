package com.example.pixpalapp.security;

import com.example.pixpalapp.interceptor.Drawing.DrawingAccessInterceptor;
import com.example.pixpalapp.interceptor.Drawing.DrawingOwnershipInterceptor;
import com.example.pixpalapp.interceptor.Palette.PaletteAccessInterceptor;
import com.example.pixpalapp.interceptor.Palette.PaletteOwnershipInterceptor;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Configuration
public class WebConfig implements WebMvcConfigurer {

    DrawingOwnershipInterceptor drawingOwnershipInterceptor;
    DrawingAccessInterceptor drawingAccessInterceptor;
    PaletteOwnershipInterceptor paletteOwnershipInterceptor;
    PaletteAccessInterceptor paletteAccessInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(drawingOwnershipInterceptor)
                .addPathPatterns("/api/v1/drawings/{id}/delete")
                .addPathPatterns("/api/v1/drawings/{id}/update");
        registry.addInterceptor(drawingAccessInterceptor)
                .addPathPatterns("/api/v1/drawings/{id}")
                .addPathPatterns("/api/v1/drawings/{id}/favorite");

        registry.addInterceptor(paletteOwnershipInterceptor)
                .addPathPatterns("/api/v1/palettes/{id}/update")
                .addPathPatterns("/api/v1/palettes/{id}/delete");
        registry.addInterceptor(paletteAccessInterceptor)
                .addPathPatterns("/api/v1/palettes/{id}")
                .addPathPatterns("/api/v1/palettes/{id}/favorite");
    }
}
