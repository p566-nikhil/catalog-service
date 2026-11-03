## Catalog Service

## Build and Run
Build the application:
./gradlew build

Run the application:
./gradlew bootRun

The application runs on port 9001

## Run Tests
Run all tests:
./gradlew test

## GitHub Actions Commit Stage Status Badge
![Commit Stage](https://github.com/p566-nikhil/catalog-service/actions/workflows/commit-stage.yml/badge.svg)

## E2 Path versus body ISBN
When PUT /books/1111111111 is sent with a body that contains "isbn": "2222222222" and neither book exists, the book is added to the catalog with the isbn given in the body and not the url. This is not acceptable. What should happen is that if the isbns do not match then a 400 status should be returned.

## E4 DELETE on a missing book
Returning 204 when deleting a missing book is correct because DELETE itself is idempotent. So deleting a book once has the same effect as deleting it multiple times.