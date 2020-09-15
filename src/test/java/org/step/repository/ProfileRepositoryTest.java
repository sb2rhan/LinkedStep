package org.step.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.step.entity.Profile;

public class ProfileRepositoryTest {

    @Before
    public void setup() {
        Profile build = Profile.builder()
                .abilities("abilities")
                .graduation("graduation")
                .workExperience("experience")
                .build();

        System.out.println(build.toString());
    }

    @After
    public void clean() {

    }

    @Test
    public void test() {

    }
}
