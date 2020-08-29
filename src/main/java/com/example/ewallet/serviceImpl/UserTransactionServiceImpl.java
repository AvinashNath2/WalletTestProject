package com.example.ewallet.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.ewallet.dataaccessrepository.UserRepository;
import com.example.ewallet.datatransferobject.MoneyTransferDto;
import com.example.ewallet.datatransferobject.UserDTO;
import com.example.ewallet.exceptions.SystemException;
import com.example.ewallet.mapper.UserObjectMapper;
import com.example.ewallet.models.User;
import com.example.ewallet.models.UserTransaction;
import com.example.ewallet.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ewallet.dataaccessrepository.UserTransactionRepository;
import com.example.ewallet.datatransferobject.UserTransactionDTO;
import com.example.ewallet.mapper.TransactionObjectMapper;
import com.example.ewallet.exceptions.BalanceLowException;
import com.example.ewallet.exceptions.UserNotFoundException;
import com.example.ewallet.service.TransactionService;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class UserTransactionServiceImpl implements TransactionService {

    private UserTransactionRepository userTransactionRepository;

    private UserRepository userRepository;

    private UserService userService;

    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public UserTransactionDTO createTransaction(UserTransactionDTO userTransactionDTO, Long userAccountId) throws BalanceLowException {

        if (userTransactionDTO.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            throw new BalanceLowException(String.format("cannot perform a transaction of %.2f ",
                    userTransactionDTO.getAmount()));
        }

        User user = userRepository.findById(userAccountId).
                orElseThrow(() -> new SystemException(HttpStatus.BAD_REQUEST.value(), "user not found"));
        user.setBalance(user.getBalance().add(userTransactionDTO.getAmount()));
        userRepository.save(user);
        UserTransaction userTransaction = createUserTransactionObj(userTransactionDTO, userAccountId, userAccountId);
        return TransactionObjectMapper.doToDTO(userTransactionRepository.save(userTransaction));
    }


    @Override
    public List<UserTransactionDTO> transactionsByUserAccountID(Long accountId) {
        return TransactionObjectMapper.doToDTOList(userTransactionRepository.findByTransactionFrom(accountId));
    }

    @Transactional
    public MoneyTransferDto transferMoneyFromOneUserToAnother(UserTransactionDTO userTransactionDTO, Long toUserAccountId, Long fromUserAccountId)
            throws UserNotFoundException, BalanceLowException {
        List<UserTransactionDTO> userTransactions = new ArrayList<>();

        User userFrom = userRepository.findById(fromUserAccountId).orElseThrow(
                () -> new UserNotFoundException(String.format("user not found for id :'%d'", fromUserAccountId)));
        User userTo = userRepository.findById(toUserAccountId).orElseThrow(
                () -> new UserNotFoundException(String.format("user not found for id :'%d'", toUserAccountId)));

        UserTransaction userTransaction;
        MoneyTransferDto moneyTransferDto;
        if (userFrom.getBalance().compareTo(userTransactionDTO.getAmount()) >= 0) {
            userTransaction = createUserTransactionObj(userTransactionDTO, userFrom.getId(), userTo.getId());
            userTransaction = userTransactionRepository.save(userTransaction);
            userFrom.setBalance(userFrom.getBalance().subtract(userTransactionDTO.getAmount()));
            userTo.setBalance(userTo.getBalance().add(userTransactionDTO.getAmount()));
            userFrom = userRepository.save(userFrom);
            userTo = userRepository.save(userTo);

            moneyTransferDto = new MoneyTransferDto();
            moneyTransferDto.setSender(UserObjectMapper.doToDTO(userFrom));
            moneyTransferDto.setReceiver(UserObjectMapper.doToDTO(userTo));
            moneyTransferDto.setTransactionInfo(TransactionObjectMapper.doToDTO(userTransaction));
        } else {
            throw new BalanceLowException(String.format("Low balance cannot perform a transaction of %.2f ",
                    userTransactionDTO.getAmount()));
        }

        return moneyTransferDto;
    }

    private UserTransaction createUserTransactionObj(UserTransactionDTO userTransactionDTO, Long from, Long to) {
        UserTransaction userTransaction = TransactionObjectMapper.dtoToDO(userTransactionDTO);
        userTransaction.setCommissionAmount(userTransactionDTO.getAmount().multiply(new BigDecimal("0.02")));
        userTransaction.setChargeAmount(userTransactionDTO.getAmount().multiply(new BigDecimal("0.02")));
        userTransaction.setTransactionHash(UUID.randomUUID().toString().replace("-", ""));
        userTransaction.setTransactionFrom(from);
        userTransaction.setTransactionTo(to);
        userTransaction.setTransactionDate(new Date(System.currentTimeMillis()));
        return userTransaction;
    }

}