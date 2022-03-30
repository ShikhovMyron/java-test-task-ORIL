package test.task.controller;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.task.service.CryptocurrencyService;

@RestController
@RequestMapping("/cryptocurrencies")
public class CryptocurrencyController {

    private static final Logger logger = Logger.getLogger(CryptocurrencyController.class);
    private final CryptocurrencyService cryptocurrencyService;

    public CryptocurrencyController(CryptocurrencyService cryptocurrencyService) {
        this.cryptocurrencyService = cryptocurrencyService;
    }

    @GetMapping
    public ResponseEntity<Object> getSelectedPage(
            @RequestParam(value = "name", defaultValue = "") String currencyName,
            @RequestParam(value = "page", defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", defaultValue = "10") int pageSize
    ) {
        try {
            return ResponseEntity.ok(cryptocurrencyService
                    .getWithPagination(currencyName, pageNumber, pageSize));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/minprice")
    public ResponseEntity<Object> getMinPrice(
            @RequestParam(value = "name", defaultValue = "") String currencyName
    ) {
        try {
            return ResponseEntity.ok(cryptocurrencyService.getMinCryptoPrice(currencyName));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/maxprice")
    public ResponseEntity<Object> getMaxPrice(
            @RequestParam(value = "name", defaultValue = "") String currencyName
    ) {
        try {
            return ResponseEntity.ok(cryptocurrencyService.getMaxCryptoPrice(currencyName));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
