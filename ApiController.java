package com.example;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import regulusbo.book.asset.*;
import regulusbo.book.entity.*;
import regulusbo.util.*;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final AccountRepository accountRepository;

    private final AccountBookRepository
        accountBookRepository;
	private final String dburl;
	private final String dbuser;
	private final String dbpassword;
	

    public ApiController(
            AccountRepository accountRepository,
            AccountBookRepository accountBookRepository,
			@Value("${db.url}")String url1,
			@Value("${db.user}")String user1,
			@Value("${db.password}")String password1) {
		
		this.dburl=url1;
		this.dbuser=user1;
		this.dbpassword=password1;

        this.accountRepository =
            accountRepository;

        this.accountBookRepository =
            accountBookRepository;
    }

    // =========================================================
    // DATABASE
    // =========================================================

    @GetMapping("/database")
    public ResponseEntity<?> database() {

        try {

            return ResponseEntity.ok(
                accountRepository
                    .getDatabaseName()
            );

        } catch (Exception e) {

            return error(e);
        }
    }

    // =========================================================
    // CASH
    // =========================================================

    
	
		@PostMapping("/cash")
	public ResponseEntity<?> processCashJson(@RequestBody String rawJson) {
		Map<String, Object> errorResponse = new HashMap<>();
		try {
			sqlSimpleUtil.openSqlConnection(dburl, dbuser, dbpassword);
			sqlSimpleUtil.sqlSetAutoCommit(true);
			cash c = new cash();
			c = jsonUtil.fromJson(rawJson, cash.class);
			accountAsset aa = new accountAsset();
			aa.setAcctId(c.account.firmId, c.account.accountId, c.account.correspondent, c.account.branch, c.account.rep);
			aa.loadBookSQL(c.account.firmId, c.account.accountId, null);
			if(aa.processDate.compareTo(c.processDate) < 0) {
				aa.endProcessDate();
				aa.startProcessDate(c.processDate);
			}
			aa.addCash(c);
			aa.saveBookSQL();
 
 
			return new ResponseEntity<String>(aa.outDemoJson(), HttpStatus.OK);
 
		} catch(BOException be) {
						return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
						return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
	}

    // =========================================================
    // TRADE
    // =========================================================

    
	
			@PostMapping("/trade")
	public ResponseEntity<?> processTradeJson(@RequestBody String rawJson) {
		Map<String, Object> errorResponse = new HashMap<>();
		try {
			sqlSimpleUtil.openSqlConnection(dburl, dbuser, dbpassword);
			sqlSimpleUtil.sqlSetAutoCommit(true);
			trade t = new trade();
			t = jsonUtil.fromJson(rawJson, trade.class);
			accountAsset aa = new accountAsset();
			aa.setAcctId(t.account.firmId, t.account.accountId, t.account.correspondent, t.account.branch, t.account.rep);
			aa.loadBookSQL(t.account.firmId, t.account.accountId, null);
			if(aa.processDate.compareTo(t.processDate) < 0) {
				aa.endProcessDate();
				aa.startProcessDate(t.processDate);
			}
			aa.addTrade(t);
			aa.saveBookSQL();
 
 
			return new ResponseEntity<String>(aa.outDemoJson(), HttpStatus.OK);
 
		} catch(BOException be) {
						return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
						return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
	}

    // =========================================================
    // STOCK
    // =========================================================

    
	
	@PostMapping("/stock")
	public ResponseEntity<?> processStockJson(@RequestBody String rawJson) {
		Map<String, Object> errorResponse = new HashMap<>();
		try {
			sqlSimpleUtil.openSqlConnection(dburl, dbuser, dbpassword);
			sqlSimpleUtil.sqlSetAutoCommit(true);
			stock s = new stock();
			s = jsonUtil.fromJson(rawJson, stock.class);
			accountAsset aa = new accountAsset();
			aa.setAcctId(s.account.firmId, s.account.accountId, s.account.correspondent, s.account.branch, s.account.rep);
			aa.loadBookSQL(s.account.firmId, s.account.accountId, null);
			if(aa.processDate.compareTo(s.processDate) < 0) {
				aa.endProcessDate();
				aa.startProcessDate(s.processDate);
			}
			aa.addStock(s);
			aa.saveBookSQL();
 
 
			return new ResponseEntity<String>(aa.outDemoJson(), HttpStatus.OK);
 
		} catch(BOException be) {
						return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
						return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
	}

    // =========================================================
// ASSET
// =========================================================
//
// GET /api/asset?firmId=1&accountId=ABC123
//
// or:
//
// GET /api/asset?firmId=1&accountId=ABC123&processDate=2026-10-07
//
// =========================================================

@GetMapping("/asset")
public ResponseEntity<?> getAccountAsset(
        @RequestParam Integer firmId,
        @RequestParam String accountId,
        @RequestParam(required = false) String processDate) {

    Map<String, Object> errorResponse = new HashMap<>();



    try {

        // 1. Open SQL connection
        sqlSimpleUtil.openSqlConnection(
            dburl, dbuser, dbpassword
        );

        //sqlSimpleUtil.sqlSetAutoCommit(true);


        // 2. Create accountAsset
        accountAsset aAsset =
            new accountAsset();


        // 3. Load account book JSON
        aAsset.loadBookSQL(
            firmId,
            accountId,
            processDate
        );


        // 4. Return demo JSON
        return new ResponseEntity<String>(
            aAsset.outDemoJson(),
            HttpStatus.OK
        );

    }
    catch (BOException be) {

        errorResponse.put(
            "error",
            be.getMessage()
        );

        return new ResponseEntity<>(
            errorResponse,
            HttpStatus.BAD_REQUEST
        );
    }
    catch (Exception e) {

        errorResponse.put(
            "error",
            e.getMessage()
        );

        return new ResponseEntity<>(
            errorResponse,
            HttpStatus.BAD_REQUEST
        );
    }
}

    // =========================================================
// ACCOUNTS
// =========================================================
//
// GET /api/accounts?firmId=1
//
// Returns:
// [
//   {
//     "accountId": "ABC123",
//     "accountName": "John Smith"
//   },
//   {
//     "accountId": "ABC456",
//     "accountName": "Mary Smith"
//   }
// ]
//
// =========================================================

	@GetMapping("/accounts")
	public ResponseEntity<?> getAccounts(
        @RequestParam(required = false) Integer firmId) {

		Map<String, Object> errorResponse =
			new HashMap<>();

		try {

			List<AccountSummary> accounts;

			if (firmId == null) {
				accounts =
					accountRepository.findAllAccounts();
			} else {
				accounts =
					accountRepository.findByFirmId(firmId);
			}

			return new ResponseEntity<>(
				accounts,
				HttpStatus.OK
			);

		} catch (Exception e) {

			e.printStackTrace();

			errorResponse.put(
				"error",
				e.getMessage()
			);

			return new ResponseEntity<>(
				errorResponse,
				HttpStatus.BAD_REQUEST
			);
		}
	}


// =========================================================
// CREATE ACCOUNT
// =========================================================
//
// POST /api/account/edit
//
// JSON body contains the account.
//
// =========================================================

	@PostMapping("/account/edit")
	public ResponseEntity<?> createAccount(
			@RequestBody Account account) {

		Map<String, Object> errorResponse =
			new HashMap<>();

		try {

        // -----------------------------------------
        // Validate required fields
        // -----------------------------------------

			if (account.getFirmId() == null) {

				errorResponse.put(
					"error",
					"firmId is required"
				);

				return new ResponseEntity<>(
					errorResponse,
					HttpStatus.BAD_REQUEST
				);
			}

			if (account.getAccountId() == null ||
				account.getAccountId().trim().isEmpty()) {

				errorResponse.put(
					"error",
					"accountId is required"
				);

				return new ResponseEntity<>(
					errorResponse,
					HttpStatus.BAD_REQUEST
				);
			}


        // -----------------------------------------
        // Check whether account already exists
        // -----------------------------------------

			Account existing =
				accountRepository.findByFirmAndAccount(
					account.getFirmId(),
					account.getAccountId()
				);

			if (existing != null) {

				errorResponse.put(
					"error",
					"Account already exists"
				);

				return new ResponseEntity<>(
					errorResponse,
					HttpStatus.CONFLICT
				);
			}


        // -----------------------------------------
        // Insert account
        // -----------------------------------------

			accountRepository.insert(
				account
			);

			return new ResponseEntity<Account>(
				account,
				HttpStatus.CREATED
			);

		}
		catch (Exception e) {

			errorResponse.put(
				"error",
				e.getMessage()
			);

			return new ResponseEntity<>(
				errorResponse,
				HttpStatus.BAD_REQUEST
			);
		}
	}


// =========================================================
// UPDATE ACCOUNT
// =========================================================
//
// PATCH
//
// /api/account/edit?firmId=1&accountId=ABC123
//
// JSON body contains ONLY fields being changed.
//
// Example:
// {
//     "accountName": "New Account Name",
//     "marginable": true
// }
//
// =========================================================

	@PatchMapping("/account/edit")
	public ResponseEntity<?> updateAccount(
			@RequestParam Integer firmId,
			@RequestParam String accountId,
			@RequestBody Account changes) {

		Map<String, Object> errorResponse =
			new HashMap<>();

		try {

        // -----------------------------------------
        // Load existing account
        // -----------------------------------------

			Account account =
				accountRepository.findByFirmAndAccount(
					firmId,
					accountId
				);

			if (account == null) {

				errorResponse.put(
					"error",
					"Account not found"
				);

				return new ResponseEntity<>(
					errorResponse,
					HttpStatus.NOT_FOUND
				);
			}


        // -----------------------------------------
        // Apply only supplied fields
        // -----------------------------------------

			if (changes.getCorrespondent() != null) {

				account.setCorrespondent(
					changes.getCorrespondent()
				);
			}

			if (changes.getBranch() != null) {

				account.setBranch(
					changes.getBranch()
				);
			}

			if (changes.getRep() != null) {

				account.setRep(
					changes.getRep()
				);
			}

			if (changes.getAccountName() != null) {

				account.setAccountName(
					changes.getAccountName()
				);
			}

			if (changes.getAccountCat() != null) {

				account.setAccountCat(
					changes.getAccountCat()
				);
			}

			if (changes.getMarginable() != null) {

				account.setMarginable(
					changes.getMarginable()
				);
			}

			if (changes.getAggregate() != null) {

				account.setAggregate(
					changes.getAggregate()
				);
			}

			if (changes.getTaxlotMethod() != null) {

				account.setTaxlotMethod(
					changes.getTaxlotMethod()
				);
			}


        // -----------------------------------------
        // Update database
        // -----------------------------------------

			accountRepository.update(
				firmId,
				accountId,
				account
			);

			return new ResponseEntity<Account>(
				account,
				HttpStatus.OK
			);

		}
		catch (Exception e) {

			errorResponse.put(
				"error",
				e.getMessage()
			);

			return new ResponseEntity<>(
				errorResponse,
				HttpStatus.BAD_REQUEST
			);
		}
	}


// =========================================================
// DELETE ACCOUNT
// =========================================================
//
// DELETE
//
// /api/account/edit?firmId=1&accountId=ABC123
//
// =========================================================

	@DeleteMapping("/account/edit")
	public ResponseEntity<?> deleteAccount(
			@RequestParam Integer firmId,
			@RequestParam String accountId) {

		Map<String, Object> response =
			new HashMap<>();

		try {

			int rows =
				accountRepository.delete(
					firmId,
					accountId
				);

			if (rows == 0) {

				response.put(
					"error",
					"Account not found"
				);

				return new ResponseEntity<>(
					response,
					HttpStatus.NOT_FOUND
				);
			}

			response.put(
				"message",
				"Account deleted successfully"
			);

			response.put(
				"firmId",
				firmId
			);

			response.put(
				"accountId",
				accountId
			);

			return new ResponseEntity<>(
				response,
				HttpStatus.OK
			);

		}
		catch (Exception e) {

			response.put(
				"error",
				e.getMessage()
			);

			return new ResponseEntity<>(
				response,
				HttpStatus.BAD_REQUEST
			);
		}
	}
    // =========================================================
    // HELPERS
    // =========================================================

		private void validateAccountExists(
				Integer firmId,
				String accountId)
				throws Exception {

			if (firmId == null) {

				throw new Exception(
					"firmId is required"
				);
			}

			if (isBlank(accountId)) {

				throw new Exception(
					"accountId is required"
				);
			}

			Account account =
				accountRepository
					.findByFirmAndAccount(
						firmId,
						accountId
					);

			if (account == null) {

				throw new Exception(
					"Account not found: " +
					firmId +
					"/" +
					accountId
				);
			}
		}

		private boolean isBlank(
				String value) {

			return value == null ||
				value.trim().isEmpty();
		}

		private ResponseEntity<?> badRequest(
				String message) {

			return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(message);
		}

		private ResponseEntity<?> error(
				Exception e) {

			e.printStackTrace();

			return ResponseEntity
				.status(
					HttpStatus.INTERNAL_SERVER_ERROR
				)
				.body(
					e.getMessage()
				);
		}
}