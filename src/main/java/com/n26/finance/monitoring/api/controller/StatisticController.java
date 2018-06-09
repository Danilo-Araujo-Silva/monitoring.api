package com.n26.finance.monitoring.api.controller;

import com.n26.finance.monitoring.api.model.bo.StatisticBO;
import com.n26.finance.monitoring.api.model.pojo.StatisticPOJO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 */
@Controller("statisticController")
@CrossOrigin(origins = "*")
public class StatisticController {

	/**
	 *
	 */
	private StatisticBO statisticBO = new StatisticBO();

	/**
	 *
	 */
	@GetMapping("/statistics")
	public ResponseEntity<StatisticPOJO> getStatistics() {
		return new ResponseEntity<StatisticPOJO>(statisticBO.run(), HttpStatus.OK);
	}
}
