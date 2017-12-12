package ec.com.levelap.gameclub.module.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.gameclub.module.transaction.repository.TransactionRepo;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepo transactionRepo;

	public TransactionRepo getTransactionRepo() {
		return transactionRepo;
	}
}
