package com.woowacourse.gongcheck.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.ApplicationTest;
import com.woowacourse.gongcheck.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationTest
@DisplayName("EntranceCodeProvider 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EntranceCodeProviderTest {

    @Autowired
    private EntranceCodeProvider entranceCodeProvider;

    @Autowired
    private HashTranslator hashTranslator;

    @Nested
    class createEntranceCode_메서드는 {

        @Nested
        class 입력받은_id가_양수가_아닌_경우 {

            @ParameterizedTest
            @ValueSource(longs = {-1L, 0L})
            void 예외를_발생시킨다(final Long hostId) {
                assertThatThrownBy(() -> entranceCodeProvider.createEntranceCode(hostId))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("유효하지 않은 id입니다.");
            }
        }

        @Nested
        class id를_입력받는_경우 {

            private static final long ID = 1L;

            @Test
            void 입장코드를_반환한다() {
                String entranceCode = entranceCodeProvider.createEntranceCode(ID);
                assertThat(entranceCode).isNotNull();
            }
        }
    }

    @Nested
    class parseId_메서드는 {

        @Nested
        class 입력받은_입장코드가_id로_변환될_수_없는_경우 {

            private String invalidEntranceCode;

            @BeforeEach
            void setUp() {
                invalidEntranceCode = hashTranslator.encode("INVALID");
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> entranceCodeProvider.parseId(invalidEntranceCode))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("유효하지 않은 입장코드입니다.");
            }
        }

        @Nested
        class 변환한_id가_유효하지_않은_경우 {

            private String invalidEntranceCode;

            @BeforeEach
            void setUp() {
                invalidEntranceCode = hashTranslator.encode("-1");
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> entranceCodeProvider.parseId(invalidEntranceCode))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining("유효하지 않은 입장코드입니다.");
            }
        }

        @Nested
        class 유효한_입장코드를_입력받은_경우 {

            private static final long expected = 1L;

            private String entranceCode;

            @BeforeEach
            void setUp() {
                entranceCode = hashTranslator.encode(String.valueOf(expected));
            }

            @Test
            void id를_반환한다() {
                Long actual = entranceCodeProvider.parseId(entranceCode);

                assertThat(actual).isEqualTo(expected);
            }
        }
    }
}
