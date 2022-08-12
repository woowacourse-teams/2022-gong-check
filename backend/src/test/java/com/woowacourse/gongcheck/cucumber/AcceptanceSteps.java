package com.woowacourse.gongcheck.cucumber;

import com.woowacourse.gongcheck.auth.application.EntranceCodeProvider;
import org.springframework.beans.factory.annotation.Autowired;

public class AcceptanceSteps {

    @Autowired
    public AcceptanceContext context;

    @Autowired
    public EntranceCodeProvider entranceCodeProvider;
}

