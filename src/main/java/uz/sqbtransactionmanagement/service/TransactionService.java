package uz.sqbtransactionmanagement.service;

import org.springframework.stereotype.Service;
import uz.sqbtransactionmanagement.exceptions.InvalidCredentialsException;
import uz.sqbtransactionmanagement.model.*;
import uz.sqbtransactionmanagement.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private static final String TRANSACTION_FILE = "transactions.txt";

    public GetInformationResult getInformation(GetInformationArguments args) {
        GetInformationResult result = new GetInformationResult();
        result.setTimeStamp(LocalDateTime.now());
        result.setStatus(0);

        Random rand = new Random();
        long limit = rand.nextInt(124500001); // Max 124500000

        List<GenericParam> responseParams = new ArrayList<>(args.getParameters());
        responseParams.add(new GenericParam("balance", String.valueOf(limit)));
        responseParams.add(new GenericParam("name", "Sardor Sh.Q."));
        result.setParameters(responseParams);

        return result;
    }

    public PerformTransactionResult performTransaction(PerformTransactionArguments args, long limit) throws IOException {
        PerformTransactionResult result = new PerformTransactionResult();
        result.setTimeStamp(LocalDateTime.now());
        if (!validateCredentials(args.getUsername(), args.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        // Validate the parameters
        boolean isValid = ValidationUtil.validateParameters(args.getParameters());
        if (!isValid) {
            result.setStatus(400);
            result.setErrorMsg("Invalid parameters");
            return result;
        }

        // Validate the amount with the limit from GetInformation
        if (args.getAmount() > limit) {
            result.setStatus(400);
            result.setErrorMsg("Amount exceeds the limit");
            return result;
        }
        long providerTrnId = new Random().nextLong();
        long balance = 200500; // Пример остатка на счете
        int traffic = 25600;

        result.setProviderTrnId(providerTrnId);
        result.setParameters(Arrays.asList(
                new GenericParam("balance", String.valueOf(balance)),
                new GenericParam("traffic", String.valueOf(traffic)),
                new GenericParam("date", LocalDateTime.now().toString())
        ));
        result.setErrorMsg("Ok");
        result.setStatus(0);
        result.setTimeStamp(LocalDateTime.now());

        saveTransaction(args);
        return result;
    }

    public GenericResult checkTransaction(long transactionId) throws IOException {
        GenericResult result = new GenericResult();
        result.setTimeStamp(LocalDateTime.now());

        if (findTransactionById(transactionId) != null) {
            result.setStatus(0); // Assuming 0 is success
        } else {
            result.setStatus(404);
            result.setErrorMsg("Transaction not found");
        }

        return result;
    }

    public GenericResult cancelTransaction(long transactionId) throws IOException {
        GenericResult result = new GenericResult();
        result.setTimeStamp(LocalDateTime.now());

        List<String> transactions = Files.readAllLines(Paths.get(TRANSACTION_FILE));
        List<String> updatedTransactions = transactions.stream()
                .filter(line -> !line.startsWith(transactionId + ","))
                .collect(Collectors.toList());

        Files.write(Paths.get(TRANSACTION_FILE), updatedTransactions);

        result.setStatus(0); // Assuming 0 is success
        return result;
    }

    public GenericResult getStatement(LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        GetStatementResult result = new GetStatementResult();
        result.setTimeStamp(LocalDateTime.now());
        result.setStatus(0);

        List<String> transactions = Files.readAllLines(Paths.get(TRANSACTION_FILE));
        List<TransactionStatement> statements = new ArrayList<>();

        for (String line : transactions) {
            String[] parts = line.split(",");
            LocalDateTime transactionTime = LocalDateTime.parse(parts[4]);

            if (!transactionTime.isBefore(startDate) && !transactionTime.isAfter(endDate)) {
                TransactionStatement statement = new TransactionStatement();
                statement.setTransactionId(Long.parseLong(parts[0]));
                statement.setAmount(Long.parseLong(parts[1]));
                statement.setProviderTrnId(Long.parseLong(parts[2]));
                statement.setTransactionTime(transactionTime);
                statements.add(statement);
            }
        }

        result.setStatements(statements);
        return result;
    }

    public ChangePasswordResult changePassword(ChangePasswordArguments args) {

        ChangePasswordArguments changePasswordArguments = new ChangePasswordArguments();
        changePasswordArguments.setNewPassword(args.getNewPassword());
        ChangePasswordResult result = new ChangePasswordResult();
        result.setTimeStamp(LocalDateTime.now());
        result.setStatus(0);
        return result;
    }

    private void saveTransaction(PerformTransactionArguments args) throws IOException {
        StringBuilder transactionRecord = new StringBuilder();
        transactionRecord.append(args.getTransactionId()).append(",");
        transactionRecord.append(args.getAmount()).append(",");
        transactionRecord.append(args.getServiceId()).append(",");
        transactionRecord.append(args.getUsername()).append(",");
        transactionRecord.append(args.getTransactionTime().toString()).append("\n");

        Files.write(Paths.get(TRANSACTION_FILE), transactionRecord.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    private PerformTransactionArguments findTransactionById(long transactionId) throws IOException {
        List<String> transactions = Files.readAllLines(Paths.get(TRANSACTION_FILE));

        for (String line : transactions) {
            String[] parts = line.split(",");
            if (Long.parseLong(parts[0]) == transactionId) {
                PerformTransactionArguments transaction = new PerformTransactionArguments();
                transaction.setTransactionId(transactionId);
                transaction.setAmount(Long.parseLong(parts[1]));
                transaction.setServiceId(Long.parseLong(parts[2]));
                transaction.setUsername(parts[3]);
                transaction.setTransactionTime(LocalDateTime.parse(parts[4]));
                return transaction;
            }
        }

        return null;
    }

    private boolean validateCredentials(String username, String password) {
        return "user".equals(username) && "pwd".equals(password);
    }
}
