Regulus REST API
Current architecture
The current project has two account-asset implementations.
- Active transaction path: ApiController currently uses the
  lowercase legacy regulusbo classes for /cash, /trade,
  /stock, and /asset.
- Spring account-master path: /database, /accounts, and
  /account/edit use AccountRepository and Spring JdbcTemplate.
- New account-book path: com.example.AccountAsset,
  AccountBookRepository, Cash, Stock, and Position are
  present, but the uploaded ApiController does not currently use
  them for the transaction endpoints.
File mapping
com.example
|-- App.java                    Spring Boot entry point
|-- ApiController.java          REST controller under /api
|-- SecurityConfig.java         permits /api/**
|-- Account.java                account master model
|-- AccountSummary.java         accountId/accountName response
|-- AccountRepository.java      dbo.accounts via JdbcTemplate
|-- AccountBook.java            dbo.accountBook model
|-- AccountBookRepository.java  latest/date lookup and save
|-- AccountAsset.java           new Spring/Jackson asset model (not active in controller)
|-- Cash.java                   new cash model (not active in controller)
|-- Stock.java                  new stock model (not active in controller)
|-- Position.java               new position model (not active in controller)
|-- Actsum.java
|-- ActsumRepository.java       not exposed by current controller
|-- Bal.java
|-- BalRepository.java          not exposed by current controller
|-- User.java
|-- UserRepository.java         not exposed by current controller
|-- TestRequest.java
`-- TestRepository.java         not exposed by current controller

regulusbo
|-- book.asset.accountAsset     active /cash /trade /stock /asset book
|-- book.entity.cash            active cash input
|-- book.entity.trade           active trade input
|-- book.entity.stock           active stock input
|-- util.jsonUtil               legacy JSON parser
`-- util.sqlSimpleUtil          legacy SQL connection
Database configuration
ApiController now injects the legacy connection settings instead of
hard-coding them:
db.url=jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=test;encrypt=true;trustServerCertificate=true;
db.user=YOUR_SQL_USER
db.password=YOUR_SQL_PASSWORD
Spring repositories also require the appropriate spring.datasource.*
properties. Do not commit real passwords to source control.
API summary
  Method            Endpoint              Input                Implementation
  GET               /api/database       none                 AccountRepository.getDatabaseName()
  POST              /api/cash           legacy cash JSON     regulusbo
  POST              /api/trade          legacy trade JSON    regulusbo
  POST              /api/stock          legacy stock JSON    regulusbo
  GET               /api/asset          firmId, accountId,   legacy accountAsset
                                          optional processDate 
  GET               /api/accounts       optional firmId      AccountRepository
  POST              /api/account/edit   Account JSON         create account
  PATCH             /api/account/edit   firmId/accountId +   update account
                                          partial JSON         
  DELETE            /api/account/edit   firmId/accountId     delete account
Base URL examples below use http://localhost:8089.
GET /api/database
curl "http://localhost:8089/api/database"
Example response:
test
GET /api/accounts
firmId is now optional.
All accounts:
curl "http://localhost:8089/api/accounts"
One firm:
curl "http://localhost:8089/api/accounts?firmId=1"
Example response:
[
  {"accountId":"12345678","accountName":"Test Account"},
  {"accountId":"ABC123","accountName":"Another Account"}
]
Without firmId, the controller calls findAllAccounts(). With
firmId, it calls findByFirmId().
POST /api/account/edit
{
  "firmId": 1,
  "accountId": "12345678",
  "correspondent": "COR1",
  "branch": "001",
  "rep": "100",
  "accountName": "Test Account",
  "accountCat": "CASH",
  "marginable": false,
  "aggregate": false,
  "taxlotMethod": "FIFO"
}
curl -X POST "http://localhost:8089/api/account/edit" ^
-H "Content-Type: application/json" ^
-d "{\"firmId\":1,\"accountId\":\"12345678\",\"correspondent\":\"COR1\",\"branch\":\"001\",\"rep\":\"100\",\"accountName\":\"Test Account\",\"accountCat\":\"CASH\",\"marginable\":false,\"aggregate\":false,\"taxlotMethod\":\"FIFO\"}"
firmId and accountId are required. Existing accounts return
409 Conflict; successful creation returns 201 Created.
PATCH /api/account/edit
curl -X PATCH "http://localhost:8089/api/account/edit?firmId=1&accountId=12345678" ^
-H "Content-Type: application/json" ^
-d "{\"accountName\":\"Updated Account\",\"marginable\":true}"
Example body:
{"accountName":"Updated Account","marginable":true}
Patchable fields are correspondent, branch, rep, accountName,
accountCat, marginable, aggregate, and taxlotMethod.
DELETE /api/account/edit
curl -X DELETE "http://localhost:8089/api/account/edit?firmId=1&accountId=12345678"
Example response:
{
  "message": "Account deleted successfully",
  "firmId": 1,
  "accountId": "12345678"
}
POST /api/cash
Current flow:
raw JSON -> legacy cash -> legacy accountAsset
         -> loadBookSQL(latest)
         -> process-date rollover if needed
         -> addCash()
         -> saveBookSQL()
         -> outDemoJson()
curl -X POST "http://localhost:8089/api/cash" ^
-H "Content-Type: application/json" ^
-d "@cash.json"
The exact request schema is the legacy regulusbo.book.entity.cash
schema. The controller directly depends on an account object and process
date, including:
{
  "account": {
    "firmId": 1,
    "accountId": "12345678",
    "correspondent": "",
    "branch": "",
    "rep": ""
  },
  "processDate": "2026-10-08"
}
POST /api/trade
curl -X POST "http://localhost:8089/api/trade" ^
-H "Content-Type: application/json" ^
-d "@trade.json"
A trade contained in the supplied demo account JSON has this shape:
{
  "accountType": 1,
  "tradeDate": "2026-10-07",
  "settleDate": "2026-10-08",
  "secType": "",
  "secId": "NVDA",
  "qty": 100.0,
  "price": 200.0,
  "amount": 20000.0,
  "controlId": "95SQD4B",
  "comm": 0.0,
  "regFee": 0.0,
  "transFee": 0.0
}
POST input must additionally match the account/process-date structure
required by the legacy trade class.
POST /api/stock
curl -X POST "http://localhost:8089/api/stock" ^
-H "Content-Type: application/json" ^
-d "@stock.json"
A stock movement contained in the supplied demo JSON has this shape:
{
  "accountType": 1,
  "tradeDate": "2026-10-07",
  "settleDate": null,
  "secType": "",
  "secId": "NVDA",
  "qty": 50.0,
  "price": 0.0,
  "amount": 0.0,
  "controlId": "9JIP7EN",
  "location": "99",
  "locType": 0,
  "restricted": false
}
POST input must additionally match the account/process-date structure
required by the legacy stock class.
GET /api/asset
Latest book:
curl "http://localhost:8089/api/asset?firmId=1&accountId=12345678"
Specific date:
curl "http://localhost:8089/api/asset?firmId=1&accountId=12345678&processDate=2026-10-07"
firmId and accountId are required; processDate is optional.
The endpoint opens the legacy SQL connection, creates
regulusbo.book.asset.accountAsset, calls loadBookSQL(), and returns
outDemoJson().
A shortened example based on the supplied test.json is:
{
  "account": {
    "firmId": 1,
    "accountId": "12345678",
    "correspondent": "",
    "branch": "",
    "rep": ""
  },
  "balances": [
    {
      "accountType": 1,
      "balance": -10000.0,
      "longMV": 20000.0,
      "shortMV": 0.0,
      "trdCount": 1,
      "stkCount": 1
    }
  ],
  "positions": [
    {
      "accountType": 1,
      "tradeDate": "2026-10-07",
      "settleDate": "2026-10-08",
      "secId": "NVDA",
      "tdQty": 100.0,
      "price": 200.0,
      "amount": 20000.0,
      "source": "TRD"
    }
  ],
  "actsum": {
    "cshAct": 0,
    "trdAct": 1,
    "stkAct": 1,
    "cashBal": -10000.0,
    "cashMV": 20000.0,
    "totalEquity": 30000.0
  },
  "processDate": "2026-10-07"
}
The actual demo output can also include trades, stocks, trade summary,
stock records, tax lots, blotters, and margin information.
New com.example AccountAsset
The newer com.example.AccountAsset is separate from the active legacy
controller path. It stores:
{
  "firmId": 1,
  "accountId": "12345678",
  "processDate": "2026-10-08",
  "cashBalance": 5000.00,
  "cashes": [],
  "trades": [],
  "stocks": [],
  "positions": {
    "NVDA": {
      "securityId": "NVDA",
      "securityType": "EQUITY",
      "description": "NVIDIA",
      "quantity": 100,
      "marketPrice": 200,
      "marketValue": 20000
    }
  }
}
loadBookSQL() uses AccountBookRepository.findLatest() when
processDate is absent and findByDate() when it is supplied. If no
book exists, it starts a new in-memory book. saveBookSQL() serializes
the asset and calls AccountBookRepository.save().
New Cash model
{
  "firmId": 1,
  "accountId": "12345678",
  "transactionId": "CASH001",
  "transactionType": "DEPOSIT",
  "amount": 1000.00,
  "currency": "USD",
  "transactionDate": "2026-10-08",
  "description": "Cash deposit"
}
For the new AccountAsset, WITHDRAWAL, WITHDRAW, and DEBIT
subtract cash; other transaction types add cash.
New Stock model
{
  "firmId": 1,
  "accountId": "12345678",
  "transactionId": "STK001",
  "transactionType": "RECEIVE",
  "securityId": "NVDA",
  "securityType": "EQUITY",
  "description": "NVIDIA",
  "quantity": 50,
  "price": 200.00,
  "currency": "USD",
  "transactionDate": "2026-10-08"
}
The new AccountAsset treats RECEIVE, RECEIPT, and IN as
increases and DELIVER, DELIVERY, and OUT as decreases.
AccountBook persistence
AccountBook maps:
accountBookID
firmId
accountId
processDate
bookJson
AccountBookRepository.findLatest() selects the latest row by process
date and accountBookID. findByDate() selects the latest row for one
process date. save() updates by (firmId, accountId, processDate) and
inserts when no row was updated.
Other classes not currently exposed by ApiController
- Actsum / ActsumRepository: accounting-summary fields and SQL
  access.
- Bal / BalRepository: balance/security-level fields and SQL
  access.
- User / UserRepository: id, name, email and SQL reads from
  users; there is no /api/users endpoint in the uploaded
  controller.
- TestRequest / TestRepository: test-table CRUD support.
Security
SecurityConfig disables CSRF and permits /api/**. Other requests
require authentication; form login and HTTP Basic are disabled. This is
useful for development/testing but should be reviewed before public
deployment.
Architecture note
                     ApiController
                          |
           +--------------+--------------+
           |                             |
           v                             v
 legacy transaction path          Spring repository path
 /cash /trade /stock /asset       /database /accounts /account/edit
           |                             |
 regulusbo accountAsset            AccountRepository
 sqlSimpleUtil                     JdbcTemplate
           |                             |
           +--------------+--------------+
                          |
                      SQL Server
Although ApiController injects AccountBookRepository, the uploaded
transaction endpoints still instantiate lowercase legacy accountAsset.
Therefore com.example.AccountAsset, Cash, Stock, and Position
are not yet the active transaction API implementation.

