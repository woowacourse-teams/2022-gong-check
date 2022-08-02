package com.woowacourse.gongcheck.auth.application;

import com.woowacourse.gongcheck.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class EntranceCodeProvider {

    private final Hashable hashable;

    public EntranceCodeProvider(final Hashable hashable) {
        this.hashable = hashable;
    }

    public String createEntranceCode(final Long id) {
        validateIdFormat(id);
        return hashable.encode(String.valueOf(id));
    }

    public Long parseId(final String entranceCode) {
        String result = hashable.decode(entranceCode);
        try {
            Long id = Long.parseLong(result);
            validateIdFormat(id);
            return id;
        } catch (NumberFormatException | BusinessException e) {
            throw new BusinessException("유효하지 않은 입장코드입니다.");
        }
    }

    private void validateIdFormat(final Long id) {
        if (id <= 0) {
            throw new BusinessException("유효하지 않은 id입니다.");
        }
    }
}
