package com.standardchartered.banking.controller;

import com.standardchartered.banking.dto.TransferRequestDTO;
import com.standardchartered.banking.service.FundTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transfers")
public class FundTransferController {

    @Autowired
    private FundTransferService transferService;

    // POST /api/v1/transfers -> Atomic fund transfer
    @PostMapping
    public ResponseEntity<String> transferFunds(@RequestBody TransferRequestDTO request) {
        String result = transferService.executeTransfer(request);
        return ResponseEntity.ok(result);
    }
}
