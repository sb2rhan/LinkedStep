package org.step.repository;

import org.junit.Assert;
import org.junit.Test;
import org.step.repository.impl.ConnectionRepository;

import java.util.Map;

public class ConnectionRepositoryTest {

    @Test
    public void sessionFactoryCreatedTest() {
        ConnectionRepository connectionRepository = new ConnectionRepository();

        Map<String, Object> informationFromDatabase = connectionRepository.getInformationFromDatabase();

        Assert.assertNotNull(informationFromDatabase);
        Assert.assertFalse(informationFromDatabase.isEmpty());
    }
}
