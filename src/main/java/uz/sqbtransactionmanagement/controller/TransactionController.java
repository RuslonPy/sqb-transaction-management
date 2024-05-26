package uz.sqbtransactionmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import uz.sqbtransactionmanagement.model.*;
import uz.sqbtransactionmanagement.service.TransactionService;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/wallet")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/getInformation")
    public GetInformationResult getInformation(@RequestBody GetInformationArguments args) {
        return transactionService.getInformation(args);
    }

    @PostMapping("/performTransaction")
    public PerformTransactionResult performTransaction(@RequestBody PerformTransactionArguments args) {
        // Simulate getting the limit from a previous getInformation call
        long limit = 124500000;
        try {
            return transactionService.performTransaction(args, limit);
        } catch (IOException e) {
            PerformTransactionResult result = new PerformTransactionResult();
            result.setStatus(500);
            result.setErrorMsg("Internal server error");
            return result;
        }
    }

    @GetMapping("/checkTransaction/{transactionId}")
    public GenericResult checkTransaction(@PathVariable long transactionId) {
        try {
            return transactionService.checkTransaction(transactionId);
        } catch (IOException e) {
            GenericResult result = new GenericResult();
            result.setStatus(500);
            result.setErrorMsg("Internal server error");
            return result;
        }
    }

    @PostMapping("/cancelTransaction/{transactionId}")
    public GenericResult cancelTransaction(@PathVariable long transactionId) {
        try {
            return transactionService.cancelTransaction(transactionId);
        } catch (IOException e) {
            GenericResult result = new GenericResult();
            result.setStatus(500);
            result.setErrorMsg("Internal server error");
            return result;
        }
    }

    @GetMapping("/getStatement")
    public GenericResult getStatement(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            return transactionService.getStatement(startDate, endDate);
        } catch (IOException e) {
            GenericResult result = new GenericResult();
            result.setStatus(500);
            result.setErrorMsg("Internal server error");
            return result;
        }
    }

    @PostMapping("/changePassword")
    public ChangePasswordResult changePassword(@RequestBody ChangePasswordArguments args) {
        return transactionService.changePassword(args);
    }
}
