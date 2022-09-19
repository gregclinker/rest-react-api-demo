package com.ps.demo;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@DatabaseSetup("/test-data.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public abstract class AbstractTest {
    protected final static List<Long> VALID_CARD_NUMBERS = Arrays.asList(4003600006849000L, 4003600006849018L, 4003600006849026L);
    protected final static List<Long> IN_VALID_CARD_NUMBERS = Arrays.asList(4003600000000016L, 1358954993914436L);
}
