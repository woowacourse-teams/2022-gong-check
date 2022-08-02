package com.woowacourse.gongcheck.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("EnterCodeProvider 클래스")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EnterCodeProviderTest {

    @Autowired
    private EnterCodeProvider enterCodeProvider;

    @Autowired
    private Hashable hashable;

    @Nested
    class createEnterCode_메서드는 {

        @Nested
        class 입력받은_id가_양수가_아닌_경우 {

            @ParameterizedTest
            @ValueSource(longs = {-1L, 0L})
            void 예외를_발생시킨다(final Long hostId) {
                assertThatThrownBy(() -> enterCodeProvider.createEnterCode(hostId))
                        .isInstanceOf(BusinessException.class)
                        .hasMessage("유효하지 않은 id입니다.");
            }
        }

        @Nested
        class id를_입력받는_경우 {

            private static final long ID = 1L;

            @Test
            void 입장코드를_반환한다() {
                String enterCode = enterCodeProvider.createEnterCode(ID);
                assertThat(enterCode).isNotNull();
            }
        }
    }

    @Nested
    class parseId_메서드는 {

        @Nested
        class 입력받은_입장코드가_id로_변환될_수_없는_경우 {

            private String invalidEnterCode;

            @BeforeEach
            void setUp() {
                invalidEnterCode = hashable.encode("INVALID");
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> enterCodeProvider.parseId(invalidEnterCode))
                        .isInstanceOf(BusinessException.class)
                        .hasMessage("유효하지 않은 입장코드입니다.");
            }
        }

        @Nested
        class 변환한_id가_유효하지_않은_경우 {

            private String invalidEnterCode;

            @BeforeEach
            void setUp() {
                invalidEnterCode = hashable.encode("-1");
            }

            @Test
            void 예외를_발생시킨다() {
                assertThatThrownBy(() -> enterCodeProvider.parseId(invalidEnterCode))
                        .isInstanceOf(BusinessException.class)
                        .hasMessage("유효하지 않은 입장코드입니다.");
            }
        }

        @Nested
        class 유효한_입장코드를_입력받은_경우 {

            private static final long expected = 1L;

            private String enterCode;

            @BeforeEach
            void setUp() {
                enterCode = hashable.encode(String.valueOf(expected));
            }

            @Test
            void id를_반환한다() {
                Long actual = enterCodeProvider.parseId(enterCode);

                assertThat(actual).isEqualTo(expected);
            }
        }
    }
}
