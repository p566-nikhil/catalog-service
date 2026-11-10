1. Why is it a mistake to name feature profiles dev and prod?

It is a mistake because profiles are for specific features, not for describing development environments. 

2. You set polar.greeting in application.yml, in an environment variable, and as a JVM property. Which value is used, and why is the environment variable nevertheless the recommended mechanism?

The value from the JVM system property is used because it has a higher priority than the environment variables and the application.yml. Environment variable is still recommended because it makes it faster to change the configuration without having to rebuild the application.

3. What exactly makes Catalog Service stateless after Part B, and why does that let you run three instances of it behind a load balancer?

Catalog Service becomes stateless because the catalog now is stored in PostgreSQL. This means that a load balancer can send a request to any of the three instances without having to worry that one of the instances has books in its catalog that the others don't, since the catalog is stored elsewhere.

4. Why test against PostgreSQL in a container instead of an in-memory H2 database?

Testing against PostgreSQL allows you to make sure you are using the same database system for your tests as in production. This ensures that differences in SQL syntax or database behavior don't cause unexpected problems.

5. Why must you never modify V1__Initial_schema.sql once it has been applied to a shared database?

You must never modify V1__Initial_schema.sql once it has been applied to a shared database because of Flyway storing the checksum. If the file changes, the checksum will no longer match, causing Flyway validation to fail. Instead, a new migration should be created.

6. Why does BookRepositoryJdbcTests insert its test data with JdbcAggregateTemplate rather than with bookRepository.save?

BookRepositoryJdbcTests inserts its test data with JdbcAggregateTemplate rather than with bookRepository.save() because it lets you insert test data without calling the repository method you are testing.