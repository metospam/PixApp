package com.example.pixpalapp.interceptor.Palette;

import com.example.pixpalapp.entity.Palette;
import com.example.pixpalapp.entity.User;
import com.example.pixpalapp.repository.PaletteRepository;
import com.example.pixpalapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNullApi;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Component
public class PaletteAccessInterceptor implements HandlerInterceptor {

    PaletteRepository paletteRepository;
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String paletteId = pathVariables.get("id");

        Palette palette = paletteRepository.findById(Long.valueOf(paletteId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Palette not found"));

        User currentUser;

        try {
            currentUser = userService.getUserFromPrincipal(SecurityContextHolder.getContext().getAuthentication());
        } catch (UsernameNotFoundException e) {
            currentUser = null;
        }

        if (!palette.isPublic() && (!(Objects.equals(palette.getUser().getId(), Objects.requireNonNull(currentUser).getId())))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("You do not have permission to this palette.");

            return false;
        }

        return true;
    }
}
