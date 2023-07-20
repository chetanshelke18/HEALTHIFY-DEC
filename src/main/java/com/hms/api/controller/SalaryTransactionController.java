package com.hms.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.api.entity.TransactionDetails;
import com.hms.api.service.TransactionService;

@RestController
@RequestMapping(value = "/transactions")
public class SalaryTransactionController {

	@Autowired
	private TransactionService service;

	@PostMapping(value = "/generateSalary")
	public ResponseEntity<Integer> generateSalaryForUser(@RequestBody TransactionDetails transactionDetails) {
		int status = service.generateSalaryForUser(transactionDetails);

		return ResponseEntity.ok(status);

	}

	@GetMapping(value = "/generateSalaryReport/{username}/{from}/{to}")
	public ResponseEntity<String> generateSalaryreportForUser(@PathVariable String username, @PathVariable int from,
			@PathVariable int to) {
		String path = service.generateSalaryreportForUser(username, from, to);
		return ResponseEntity.ok(path);

	}

}
