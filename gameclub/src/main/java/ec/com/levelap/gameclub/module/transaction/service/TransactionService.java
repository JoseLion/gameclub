package ec.com.levelap.gameclub.module.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.repository.TransactionRepo;

@Service
public class TransactionService extends BaseService<Transaction> {

	public TransactionService() {
		super(Transaction.class);
	}

	@Autowired
	private TransactionRepo transactionRepo;

	public TransactionRepo getTransactionRepo() {
		return transactionRepo;
	}
	
	
}
