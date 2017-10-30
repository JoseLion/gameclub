package ec.com.levelap.gameclub.module.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.repository.TransactionRepo;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;

@Service
public class TransactionService extends BaseService<Transaction> {

	public TransactionService() {
		super(Transaction.class);
	}

	@Autowired
	private TransactionRepo transactionRepo;

	@Autowired
	private LevelapCryptography cryptoService;

	@Autowired
	private PublicUserService publicUserService;

	public TransactionRepo getTransactionRepo() {
		return transactionRepo;
	}

	public LevelapCryptography getCryptoService() {
		return cryptoService;
	}

	public PublicUserService getPublicUserService() {
		return publicUserService;
	}

}
