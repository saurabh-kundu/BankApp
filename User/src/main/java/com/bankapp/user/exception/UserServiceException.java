package com.bankapp.user.exception;

import com.bankapp.user.exception.model.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serial;

@Slf4j
@Component
public class UserServiceException {

    public static class NotFoundException extends BaseException {

        @Serial
        private static final long serialVersionUID = 3555714415375055302L;

        public NotFoundException(String msg) {
            super(msg);
        }
    }
}
