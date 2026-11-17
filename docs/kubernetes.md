## 7.1
After deleting a pod, the readiness probe returned status Error, ContainerCreated, and then Running in that order. After the readiness probe succeeded, the second replica was fully operational.

## E1
Readiness is meant to determine whether or not a Pod should receive traffic while its running. Liveness should be used to restart Pods when the application itself is unhealthy and cannot recover without being restarted. Including PostgreSQL in liveness causes unnecessary restarts whenever the database is temporarily unavailable. This can make an outage worse because Catalog Service repeatedly restarts even though restarting it cannot fix the database.

## E2
When I made the PostGreSql data persistent, when I deleted a Pod, the information was retained when that Pod restarded. When I deleted the Redis Pod, after it restarted, I had to log back in. It did not retain my user session.

## E3
Doing both in one release would break a rolling upgrade because it could cause issues with older instances of the database and application. An older instance could still be writing NULL for the constraints when they are meant to be NOT NULL, causing database errors.

## E4
I first installed Envoy Gateway and created a gateway.yml file to configure the GatewayClass, Gateway, and HTTPRoute. The HTTPRoute routes incoming traffic to Edge Service.

I applied the configuration using:
kubectl apply -f kubernetes/platform/development/services/gateway.yml

I then started Minikube Tunnel:
minikube tunnel --profile polar

The Gateway defines the entry point into the cluster, including the protocol and port that receive incoming traffic. The HTTPRoute defines the routing rules, specifying which requests go to which service.