package com.woowacourse.gongcheck.auth.application;

import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class EntranceCodeProvider {

    private static final int MINIMUM_ID_SIZE = 1;

    private final HashTranslator hashTranslator;

    public EntranceCodeProvider(final HashTranslator hashTranslator) {
        this.hashTranslator = hashTranslator;
    }

    public String createEntranceCode(final Long id) {
        validateIdSize(id);
        return hashTranslator.encode(String.valueOf(id));
    }

    public Long parseId(final String entranceCode) {
        String result = hashTranslator.decode(entranceCode);
        try {
            Long id = Long.parseLong(result);
            validateIdSize(id);
            return id;
        } catch (NumberFormatException | BusinessException e) {
            String message = String.format("유효하지 않은 입장코드입니다. entranceCode = %s", entranceCode);
            throw new BusinessException(message, ErrorCode.H002);
        }
    }

    private void validateIdSize(final Long id) {
        if (id < MINIMUM_ID_SIZE) {
            String message = String.format("유효하지 않은 id입니다. id = %d", id);
            throw new BusinessException(message, ErrorCode.H003);
        }
    }
}
