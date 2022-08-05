package com.woowacourse.gongcheck.auth.application;

import com.woowacourse.gongcheck.exception.BusinessException;
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
            throw new BusinessException("유효하지 않은 입장코드입니다.");
        }
    }

    private void validateIdSize(final Long id) {
        if (id < MINIMUM_ID_SIZE) {
            throw new BusinessException("유효하지 않은 id입니다.");
        }
    }
}
