package com.woowacourse.gongcheck.application;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.application.response.SpacesResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.host.HostRepository;
import com.woowacourse.gongcheck.domain.space.Space;
import com.woowacourse.gongcheck.domain.space.SpaceRepository;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.NotFoundException;
import com.woowacourse.gongcheck.presentation.request.SpaceCreateRequest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class SpaceServiceTest {

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Test
    void 공간을_조회한다() {
        Host host = hostRepository.save(Host_생성("1234"));
        Space space1 = Space_생성(host, "잠실");
        Space space2 = Space_생성(host, "선릉");
        Space space3 = Space_생성(host, "양평같은방");
        spaceRepository.saveAll(List.of(space1, space2, space3));

        SpacesResponse result = spaceService.findPage(host.getId(), PageRequest.of(0, 2));

        assertAll(
                () -> assertThat(result.getSpaces()).hasSize(2),
                () -> assertThat(result.isHasNext()).isTrue()
        );
    }

    @Test
    void 공간을_생성한다() {
        Host host = hostRepository.save(Host_생성("1234"));
        SpaceCreateRequest spaceCreateRequest = new SpaceCreateRequest("잠실 캠퍼스", "https://image.com");
        Long spaceId = spaceService.createSpace(host.getId(), spaceCreateRequest);

        assertThat(spaceId).isNotNull();
    }

    @Test
    void 공간_생성_시_이미_존재하는_이름을_입력할_경우_예외가_발생한다() {
        Host host = hostRepository.save(Host_생성("1234"));
        String spaceName = "잠실 캠퍼스";
        Space space = Space_생성(host, spaceName);
        spaceRepository.save(space);

        SpaceCreateRequest request = new SpaceCreateRequest(spaceName, "https://image.com1");

        assertThatThrownBy(() -> spaceService.createSpace(host.getId(), request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("이미 존재하는 이름입니다.");
    }

    @Test
    void 존재하지_않는_호스트로_공간을_조회할_경우_예외를_던진다() {
        assertThatThrownBy(() -> spaceService.findPage(0L, PageRequest.of(0, 1)))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 호스트입니다.");
    }
}
