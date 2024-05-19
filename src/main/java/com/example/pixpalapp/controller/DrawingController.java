package com.example.pixpalapp.controller;

import com.example.pixpalapp.dto.Drawing.DrawingDto;
import com.example.pixpalapp.dto.Drawing.GetDrawingsDto;
import com.example.pixpalapp.entity.Drawing;
import com.example.pixpalapp.entity.User;
import com.example.pixpalapp.manager.ErrorManager;
import com.example.pixpalapp.payload.Request.Drawing.DrawingCreateRequest;
import com.example.pixpalapp.payload.Request.Drawing.DrawingUpdateRequest;
import com.example.pixpalapp.payload.Request.Drawing.GetDrawingsRequest;
import com.example.pixpalapp.payload.Response.Drawing.GetDrawingResponse;
import com.example.pixpalapp.payload.Response.Drawing.GetDrawingsResponse;
import com.example.pixpalapp.repository.DrawingRepository;
import com.example.pixpalapp.service.DrawingService;
import com.example.pixpalapp.service.StorageService;
import com.example.pixpalapp.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/drawings")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DrawingController {

    DrawingService drawingService;
    DrawingRepository drawingRepository;
    UserService userService;
    StorageService storageService;
    ErrorManager errorManager;

    @GetMapping
    public GetDrawingsResponse handleGetDrawings(GetDrawingsRequest request) {
        GetDrawingsDto dto = GetDrawingsDto.builder()
                .tagNames(request.getTagNames())
                .search(request.getSearch())
                .build();

        List<Drawing> drawings = drawingService.getDrawings(dto.getTagNames(), dto.getSearch());

        return new GetDrawingsResponse(drawings);
    }

    @GetMapping("/{id}")
    public GetDrawingResponse handleGetDrawing(@PathVariable Long id) {
        Drawing drawing = drawingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drawing not found"));

        String imageBase64 = storageService.encodeImage(drawing.getImage());

        return new GetDrawingResponse(drawing.getName(), imageBase64);
    }

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

    @PutMapping("/{id}/update")
    public ResponseEntity<String> handleUpdateDrawing(@PathVariable Long id, @RequestBody @Valid DrawingUpdateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldErrors().get(0);

            return ResponseEntity.unprocessableEntity().body(errorManager.displayError(fieldError));
        }

        Drawing drawing = drawingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drawing not found"));

        DrawingDto drawingDto = DrawingDto.builder()
                .name(request.getName())
                .cells(request.getCells())
                .isPublic(request.isPublic())
                .build();

        drawingService.updateDrawing(drawing, drawingDto);

        return ResponseEntity.ok("Drawing successfully updated");
    }

    @PostMapping("/{id}/favorite")
    public ResponseEntity<String> handleToggleFavorite(@PathVariable Long id, @NotNull Principal principal) throws IOException {
        User user = userService.getUserFromPrincipal(principal);
        DrawingDto drawingDto = DrawingDto.builder()
                .id(id)
                .build();
        userService.toggleFavoriteDrawing(user, drawingDto);

        return ResponseEntity.ok("Drawing added to favorites");
    }

    @GetMapping("/favorite")
    public GetDrawingsResponse handleGetFavorite(@NotNull Principal principal) throws AccessDeniedException {
        User user = userService.getUserFromPrincipal(principal);
        List<Drawing> drawings = user.getFavoriteDrawings();

        return new GetDrawingsResponse(drawings);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> handleDeleteDrawing(@PathVariable Long id, Principal principal) throws AccessDeniedException {
        User user = userService.getUserFromPrincipal(principal);
        System.out.println(user.getId());
        Drawing drawing = drawingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drawing not found"));

        drawingRepository.delete(drawing);

        return ResponseEntity.ok("Drawing deleted successfully");
    }
}
