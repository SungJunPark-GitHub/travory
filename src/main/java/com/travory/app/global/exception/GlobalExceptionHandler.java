package com.travory.app.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NotFoundException e,
                                 Model model) {

        model.addAttribute("message", e.getMessage());
        return "error/404";
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(AccessDeniedException e,
                                     Model model) {

        model.addAttribute("message", e.getMessage());
        return "error/403";
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(BadRequestException e,
                                   Model model) {

        model.addAttribute("message", e.getMessage());
        return "error/400";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMaxUploadSizeExceeded(Model model) {

        model.addAttribute("message", "업로드 파일은 5MB 이하만 사용할 수 있습니다.");
        return "error/400";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e,
                                  Model model) {

        model.addAttribute("message", "요청을 처리하는 중 오류가 발생했습니다.");
        return "error/500";
    }
}
