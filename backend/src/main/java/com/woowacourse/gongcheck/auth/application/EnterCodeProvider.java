package com.woowacourse.gongcheck.auth.application;

import com.woowacourse.gongcheck.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class EnterCodeProvider {

    private final Hashable hashable;

    public EnterCodeProvider(Hashable hashable) {
        this.hashable = hashable;
    }

    public String createEnterCode(final Long id) {
        validateIdFormat(id);
        return hashable.encode(String.valueOf(id));
    }

    public Long parseId(final String enterCode) {
        String result = hashable.decode(enterCode);
        try {
            Long id = Long.parseLong(result);
            validateIdFormat(id);
            return id;
        } catch (NumberFormatException | BusinessException e) {
            throw new BusinessException("유효하지 않은 입장코드입니다.");
        }
    }

    private void validateIdFormat(Long id) {
        if (id <= 0) {
            throw new BusinessException("유효하지 않은 id입니다.");
        }
    }
}
