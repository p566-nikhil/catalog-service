After deleting a pod, the readiness probe returned status Error, ContainerCreated, and then Running in that order. After the readiness probe succeeded, the second replica was fully operational.

## E1
Readiness is meant to determine whether or not a Pod should receive traffic while its running. Liveness should be used to restart Pods when the application itself is unhealthy and cannot recover without being restarted. Including PostgreSQL in liveness causes unnecessary restarts whenever the database is temporarily unavailable. This can make an outage worse because Catalog Service repeatedly restarts even though restarting it cannot fix the database.

## E2
When I made the PostGreSql data persistent, when I deleted a Pod, the information was retained when that Pod restarded. When I deleted the Redis Pod, after it restarted, I had to log back in. It did not retain my user session.
