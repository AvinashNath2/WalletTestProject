package com.example.ewallet.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.ewallet.models.UserTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ewallet.dataaccessrepository.TransactionRepository;
import com.example.ewallet.datatransferobject.UserTransactionDTO;
import com.example.ewallet.mapper.TransactionObjectMapper;
import com.example.ewallet.exceptions.BalanceLowException;
import com.example.ewallet.exceptions.UserNotFoundException;
import com.example.ewallet.service.TransactionService;
import com.example.ewallet.service.UserAccountService;
import org.springframework.util.ObjectUtils;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserAccountService accountService;

    @Override
    @Transactional
    public UserTransactionDTO createTransaction(UserTransaction userTransaction) throws BalanceLowException {

        BigDecimal balance = transactionRepository.getBalance(userTransaction.getUserAccount().getId());

        if (balance.add(userTransaction.getAmount()).compareTo(BigDecimal.ZERO) >= 0) {
            return TransactionObjectMapper.doToDTO(transactionRepository.save(userTransaction));
        }

        throw new BalanceLowException(String.format("user's balance is %.2f and cannot perform a transaction of %.2f ",
                balance.doubleValue(), userTransaction.getAmount().doubleValue()));

    }

    @Override
    public List<UserTransactionDTO> transactionsByUserAccountID(Long accountId) {
        return TransactionObjectMapper.doToDTOList(transactionRepository.getTransactionsForUser(accountId));
    }

    @Transactional
    public List<UserTransactionDTO> transferMoneyFromOneUserToAnother(UserTransactionDTO walletDTO, Long toUserAccountId, Long fromUserAccountId)
            throws UserNotFoundException, BalanceLowException {
        List<UserTransactionDTO> userTransactions = new ArrayList<>();

        if (ObjectUtils.isEmpty(accountService.getAccountById(fromUserAccountId)))
            throw new UserNotFoundException(String.format("userAccount with '%d' not found ", fromUserAccountId));
        if (ObjectUtils.isEmpty(accountService.getAccountById(toUserAccountId))) {
            throw new UserNotFoundException(String.format("userAccount with '%d' not found ", toUserAccountId));
        }
        UserTransactionDTO userFrom;
        UserTransactionDTO userTo;

        walletDTO.setUserAccountId(fromUserAccountId);
        walletDTO.setAmount(walletDTO.getAmount().negate());
        userFrom = createTransaction(TransactionObjectMapper.dtoToDO(walletDTO));
        userTransactions.add(userFrom);

        walletDTO.setUserAccountId(toUserAccountId);
        walletDTO.setAmount(walletDTO.getAmount().negate());
        userTo = createTransaction(TransactionObjectMapper.dtoToDO(walletDTO));
        userTransactions.add(userTo);

        return userTransactions;
    }

}