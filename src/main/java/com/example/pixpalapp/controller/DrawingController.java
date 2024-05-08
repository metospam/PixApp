package com.example.pixpalapp.controller;

import com.example.pixpalapp.dto.DrawingDto;
import com.example.pixpalapp.entity.Drawing;
import com.example.pixpalapp.entity.User;
import com.example.pixpalapp.exception.DrawingNotFoundException;
import com.example.pixpalapp.manager.ErrorManager;
import com.example.pixpalapp.payload.Request.DrawingCreateRequest;
import com.example.pixpalapp.repository.DrawingRepository;
import com.example.pixpalapp.service.DrawingService;
import com.example.pixpalapp.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/drawings")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DrawingController {

    DrawingService drawingService;
    DrawingRepository drawingRepository;
    UserService userService;
    ErrorManager errorManager;

//    @GetMapping("/current")
//    public DrawingResponse getDrawing(@NotNull HttpServletRequest servletRequest) {
//
//        Drawing drawing = drawingRepository.findById(30L).get();
//        List<HashMap<String, String>> coordinates = cellService.cellsToArray(drawing.getCells());
//
//        return new DrawingResponse(drawing.getId(), drawing.getName(), drawing.getX(), drawing.getY(), coordinates);
//    }

    @PostMapping
    public ResponseEntity<String> handleCreateDrawing(@RequestBody @Valid DrawingCreateRequest request,
                                                      @NotNull Principal principal, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldErrors().get(0);

            return ResponseEntity.unprocessableEntity().body(errorManager.displayError(fieldError));
        }

        User user = userService.getUserFromPrincipal(principal);

        DrawingDto drawingDto = DrawingDto.builder()
                .name(request.getName())
                .cells(request.getCells())
                .build();

        drawingService.createDrawing(drawingDto, user);

        return ResponseEntity.ok("drawing created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> handleUpdateDrawing(@PathVariable Long id, @RequestBody @Valid DrawingCreateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldErrors().get(0);

            return ResponseEntity.unprocessableEntity().body(errorManager.displayError(fieldError));
        }

        Drawing drawing = drawingRepository.findById(id)
                .orElseThrow(() -> new DrawingNotFoundException("Drawing not found"));

        DrawingDto drawingDto = DrawingDto.builder()
                .name(request.getName())
                .cells(request.getCells())
                .build();

        drawingService.updateDrawing(drawing, drawingDto);

        return ResponseEntity.ok("Drawing successfully updated");
    }
}
