package org.step.repository;

import org.junit.Test;

import java.util.Map;

public class ConnectionRepositoryTest {

    @Test
    public void test() {
        ConnectionRepository connectionRepository = new ConnectionRepository();

        Map<String, Object> informationFromDatabase = connectionRepository.getInformationFromDatabase();

        System.out.println(informationFromDatabase);
    }
}
