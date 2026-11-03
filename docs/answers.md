1. Why is a self-contained JAR with an embedded server better suited to the cloud than a WAR
deployed to a shared application server?

A self-contained JAR is better because it includes the entire application in the package. This makes it easier to deploy and scale without needing to configure a separate server.

2. In the thread-per-request model, what happens to the 51st simultaneous request with the config
uration of Step 2.2? What are two ways to handle more load, and which one does a cloud native
application prefer?

Since the maximum number of threads is 50, the 51st request would have to wait until a thread frees up. Two ways to handle more load are to scale horizontally which means having more instances and to scale vertically which means having a bigger machine. Cloud native applications prefer horizontal scaling.

3. Why does InMemoryBookRepository use a ConcurrentHashMap instead of a HashMap?

ConcurrentHashMap is thread-safe whereas a HashMap is not. This allows multiple requests to access and modify the book catalog at the same time safely.

4. Why is 422 a better answer than 400 for a duplicate ISBN?

422 is better because it means that the syntax and request is fine but there is a duplicate ISBN. 400 would mean that the request itself is invalid or has an invalid input.

5. Give one advantage and one limitation of @WebMvcTest compared with @SpringBootTest(webEnvironment = RANDOM_PORT).

One advantage of @WebMvcTest is that it has faster execution speed. One disadvantage is that it does not test the full application.

6. Why should the commit stage be fast, and what should you do if a slow test makes it exceed its
time budget?

The commit stage should be fast because it provides automated feedback after pushing code. By having it be fast, you are able to get feedback faster, leading to more code changes. If a slow test causes the commit stage to exceed its time budget, it should be optimized or moved to a later stage of the deployment lifecycle.