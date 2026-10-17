# Selenium + Java: SauceDemo Login Tests

Small automation project from my Java + Selenium WebDriver training (Internshala, Oct 2026). It automates login test cases for the public practice site https://www.saucedemo.com.

**Status: written and uploaded, not yet run.** I wrote these tests without a local Maven setup, so I have not executed them yet. If a locator or message has changed on the site, a test may fail until I fix it. I will update this note with the real run result.

## Tests (JUnit 5)

| ID | Test | Expected |
|----|------|----------|
| TC01 | Valid login (standard_user) | Products page opens |
| TC02 | Wrong password | Error: username and password do not match |
| TC03 | Locked out user | Error: user has been locked out |
| TC04 | Empty username | Error: Username is required |
| TC05 | Empty password | Error: Password is required |
| TC06 | Logout after login | Back on the login page |

## Tech

Java 17+, Maven, Selenium 4.25 (Selenium Manager fetches the Chrome driver), JUnit 5.

## Run

Needs Java 17+, Maven and Chrome installed.

```
mvn test
```

## Notes

- Test credentials are the public demo logins shown on saucedemo.com.
- Next steps I plan: Page Object classes, a cart/checkout flow, and an HTML test report.
