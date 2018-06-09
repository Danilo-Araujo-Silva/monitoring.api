package com.n26.finance.monitoring.api.controller;

import com.n26.finance.monitoring.api.model.pojo.TransactionPOJO;
import com.n26.finance.monitoring.api.model.util.JsonUtil;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.Instant;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest extends AbstractControllerTest {

	/**
	 *
	 */
	@Autowired
	private MockMvc mockMvc;

	/**
	 *
	 */
	private String endpointPrefix = "/transactions";

	/**
	 *
	 */
	@Before
	public void setup() {
	}

	/**
	 *
	 */
	@Test
	public void insertTransactionUpTo60Seconds() throws Exception {
		Instant timestamp = Instant.now();
		Double amount = RandomUtils.nextDouble();

		TransactionPOJO transactionPOJO = new TransactionPOJO(timestamp.toEpochMilli(), amount);
		String json = JsonUtil.toJson(transactionPOJO);

		Integer status = mockMvc
			.perform(
				post(endpointPrefix)
					.content(json)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
			)
			.andReturn()
			.getResponse()
			.getStatus();

		assert status.equals(HttpStatus.CREATED.value());
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void doNotInsertTransactionOlderThan60Seconds() throws Exception {
		Instant timestamp = Instant.now();
		timestamp = timestamp.minusSeconds(61);

		Double amount = RandomUtils.nextDouble();

		TransactionPOJO transactionPOJO = new TransactionPOJO(timestamp.toEpochMilli(), amount);
		String json = JsonUtil.toJson(transactionPOJO);

		Integer status = mockMvc
			.perform(
				post(endpointPrefix)
					.content(json)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
			)
			.andReturn()
			.getResponse()
			.getStatus();

		assert status.equals(HttpStatus.NO_CONTENT.value());
	}
}