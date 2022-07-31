package com.woowacourse.gongcheck.core.domain.task;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class TaskDescription {

    private static final int ANNOUNCEMENT_MAX_LENTH = 32;

    @Column(name = "description_announcement", length = ANNOUNCEMENT_MAX_LENTH)
    private String announcement;

    @Column(name = "description_image_url")
    private String imageUrl;

    protected TaskDescription() {
    }

    public TaskDescription(final String announcement, final String imageUrl) {
        checkAnnouncementLength(announcement);
        this.announcement = announcement;
        this.imageUrl = imageUrl;
    }

    private void checkAnnouncementLength(final String announcement) {
        if (Objects.isNull(announcement)) {
            return;
        }

        if (announcement.length() > ANNOUNCEMENT_MAX_LENTH) {
            throw new IllegalArgumentException("task의 설명은 " + ANNOUNCEMENT_MAX_LENTH + "자 이하여야합니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaskDescription that = (TaskDescription) o;
        return Objects.equals(announcement, that.announcement) && Objects
                .equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(announcement, imageUrl);
    }
}
