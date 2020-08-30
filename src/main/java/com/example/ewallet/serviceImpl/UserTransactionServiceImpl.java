package com.example.ewallet.serviceImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.ewallet.dataaccessrepository.UserRepository;
import com.example.ewallet.datatransferobject.MoneyTransferDto;
import com.example.ewallet.datatransferobject.PassbookDTO;
import com.example.ewallet.datatransferobject.UserDTO;
import com.example.ewallet.exceptions.SystemException;
import com.example.ewallet.mapper.UserObjectMapper;
import com.example.ewallet.models.TransactionStatus;
import com.example.ewallet.models.User;
import com.example.ewallet.models.UserTransaction;
import com.example.ewallet.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class UserTransactionServiceImpl implements TransactionService {

    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Value("${additional.price.charge}")
    private BigDecimal chargeAmount;

    @Value("${additional.price.commission}")
    private BigDecimal commissionAmount;

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
        UserTransaction userTransaction = createUserTransactionObj(userTransactionDTO, userAccountId, userAccountId, TransactionStatus.ACTIVE);
        return TransactionObjectMapper.doToDTO(userTransactionRepository.save(userTransaction));
    }


    @Override
    public PassbookDTO getPassbookDetail(Long accountId) throws UserNotFoundException {
        UserDTO account = userService.getAccountById(accountId);
        PassbookDTO passbookDTO = PassbookDTO.builder().transactions(TransactionObjectMapper.doToDTOList(userTransactionRepository.getTransactionByUserId(accountId))).userProfile(account).build();
        return passbookDTO;
    }

    @Override
    @Transactional
    public MoneyTransferDto reverseTransaction(String transactionHash) throws UserNotFoundException {
        UserTransaction transaction = userTransactionRepository.findByTransactionHash(transactionHash).
                orElseThrow(() -> new SystemException(HttpStatus.BAD_REQUEST.value(), "Transaction not found for Hash : "+ transactionHash));
        User userFrom = userRepository.findById(transaction.getTransactionFrom()).orElseThrow(
                () -> new UserNotFoundException(String.format("user not found for id :'%d'", transaction.getTransactionFrom())));
        User userTo = userRepository.findById(transaction.getTransactionTo()).orElseThrow(
                () -> new UserNotFoundException(String.format("user not found for id :'%d'", transaction.getTransactionTo())));
        userFrom.setBalance(userFrom.getBalance().add(userFrom.getBalance()));
        //total amount need to be added back to sender
        BigDecimal totalChargeAmount = transaction.getAmount().add(transaction.getCommissionAmount()).add(transaction.getChargeAmount());
        userFrom.setBalance(userTo.getBalance().add(totalChargeAmount));
        userTo.setBalance(userTo.getBalance().subtract(transaction.getAmount()));
        transaction.setTransactionStatus(TransactionStatus.INACTIVE);
        userFrom = userRepository.save(userFrom);
        userTo = userRepository.save(userTo);

        MoneyTransferDto moneyTransferDto = new MoneyTransferDto();
        moneyTransferDto.setSender(UserObjectMapper.doToDTO(userFrom));
        moneyTransferDto.setReceiver(UserObjectMapper.doToDTO(userTo));
        moneyTransferDto.setTransactionInfo(TransactionObjectMapper.doToDTO(transaction));
        return moneyTransferDto;
    }

    @Override
    public List<UserTransaction> getTransactionByStatus(TransactionStatus transactionStatus, Long userId) throws UserNotFoundException {
        return userTransactionRepository.getTransactionByTransactionStatus(userId, transactionStatus);
    }

    @Transactional
    public MoneyTransferDto transferMoneyFromOneUserToAnother(UserTransactionDTO userTransactionDTO, Long toUserAccountId, Long fromUserAccountId)
            throws UserNotFoundException, BalanceLowException {

        User userFrom = userRepository.findById(fromUserAccountId).orElseThrow(
                () -> new UserNotFoundException(String.format("user not found for id :'%d'", fromUserAccountId)));
        User userTo = userRepository.findById(toUserAccountId).orElseThrow(
                () -> new UserNotFoundException(String.format("user not found for id :'%d'", toUserAccountId)));

        BigDecimal totalAmountToBeDeducted = userTransactionDTO.getAmount().add(userTransactionDTO.getAmount().multiply(commissionAmount).add(userTransactionDTO.getAmount().multiply(chargeAmount)));

        UserTransaction userTransaction;
        MoneyTransferDto moneyTransferDto;
        if (userFrom.getBalance().compareTo(userTransactionDTO.getAmount().add(chargeAmount).add(totalAmountToBeDeducted)) > 0) {
            userTransaction = createUserTransactionObj(userTransactionDTO, userFrom.getId(), userTo.getId(), TransactionStatus.ACTIVE);
            userTransaction = userTransactionRepository.save(userTransaction);
            //applying commission and charge fee for sender
            BigDecimal totalChargeAmount = userTransactionDTO.getAmount().add(userTransaction.getCommissionAmount()).add(userTransaction.getChargeAmount());
            userFrom.setBalance(userFrom.getBalance().subtract(totalChargeAmount));
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

    private UserTransaction createUserTransactionObj(UserTransactionDTO userTransactionDTO, Long from, Long to, TransactionStatus transactionStatus) {
        UserTransaction userTransaction = TransactionObjectMapper.dtoToDO(userTransactionDTO);
        userTransaction.setCommissionAmount(userTransactionDTO.getAmount().multiply(commissionAmount));
        userTransaction.setChargeAmount(userTransactionDTO.getAmount().multiply(chargeAmount));
        userTransaction.setTransactionHash(UUID.randomUUID().toString().replace("-", ""));
        userTransaction.setTransactionFrom(from);
        userTransaction.setTransactionTo(to);
        userTransaction.setTransactionStatus(transactionStatus);
        userTransaction.setTransactionUpdatedDate(new Date(System.currentTimeMillis()));
        return userTransaction;
    }

}