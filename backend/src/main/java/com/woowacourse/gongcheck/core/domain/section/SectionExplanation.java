package com.woowacourse.gongcheck.core.domain.section;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class SectionExplanation {

    private static final int DESCRIPTION_MAX_LENTH = 32;

    @Column(name = "description", length = DESCRIPTION_MAX_LENTH)
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    protected SectionExplanation() {
    }

    public SectionExplanation(final String description, final String imageUrl) {
        checkDescriptionLength(description);
        this.description = description;
        this.imageUrl = imageUrl;
    }

    private void checkDescriptionLength(final String description) {
        if (Objects.isNull(description)) {
            return;
        }

        if (description.length() > DESCRIPTION_MAX_LENTH) {
            throw new IllegalArgumentException("section의 설명은 " + DESCRIPTION_MAX_LENTH + "자 이하여야합니다.");
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
        final SectionExplanation that = (SectionExplanation) o;
        return Objects.equals(description, that.description) && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, imageUrl);
    }
}
