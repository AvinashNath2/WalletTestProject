package com.example.ewallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class WalletTestProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletTestProjectApplication.class, args);
	}

}

