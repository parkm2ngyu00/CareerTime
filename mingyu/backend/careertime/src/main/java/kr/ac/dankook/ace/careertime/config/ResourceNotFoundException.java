package kr.ac.dankook.ace.careertime.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND) // 예외 발생 시, HTTP 상태 코드를 NOT_FOUND로 설정
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message); // 예외 메시지를 부모 클래스에 전달
    }
}
