## First build output:
PS C:\Users\nikhi\Desktop\catalog-service\catalog-service> .\gradlew.bat bootJar

[Incubating] Problems report is available at: file:///C:/Users/nikhi/Desktop/catalog-service/catalog-service/build/reports/problems/problems-report.html

Deprecated Gradle features were used in this build, making it incompatible with Gradle 10.

You can use '--warning-mode all' to show the individual deprecation warnings and determine if they come from your own scripts or plugins.

For more on this, please refer to https://docs.gradle.org/9.8.2/userguide/command_line_interface.html#sec:command_line_warnings in the Gradle documentation.

BUILD SUCCESSFUL in 4s
4 actionable tasks: 4 up-to-date
Consider enabling configuration cache to speed up this build: https://docs.gradle.org/9.8.2/userguide/configuration_cache_enabling.html
PS C:\Users\nikhi\Desktop\catalog-service\catalog-service> docker build -t catalog-service:dockerfile .
[+] Building 1.0s (15/15) FINISHED                                                                                                           docker:desktop-linux
 => [internal] load build definition from Dockerfile                                                                                                         0.0s
 => => transferring dockerfile: 777B                                                                                                                         0.0s
 => [internal] load metadata for docker.io/library/eclipse-temurin:21-jre                                                                                    0.5s
 => [internal] load .dockerignore                                                                                                                            0.0s
 => => transferring context: 2B                                                                                                                              0.0s
 => [builder 1/4] FROM docker.io/library/eclipse-temurin:21-jre@sha256:ca73b4d3961703d6d7830a4361f49ef0478f8f28b39defc07bb72f4cc7f834f6                      0.0s
 => => resolve docker.io/library/eclipse-temurin:21-jre@sha256:ca73b4d3961703d6d7830a4361f49ef0478f8f28b39defc07bb72f4cc7f834f6                              0.0s
 => [internal] load build context                                                                                                                            0.0s
 => => transferring context: 124B                                                                                                                            0.0s
 => CACHED [stage-1 2/7] RUN useradd spring                                                                                                                  0.0s
 => CACHED [stage-1 3/7] WORKDIR /application                                                                                                                0.0s
 => CACHED [builder 2/4] WORKDIR /builder                                                                                                                    0.0s
 => CACHED [builder 3/4] COPY build/libs/catalog-service-0.0.1-SNAPSHOT.jar application.jar                                                                  0.0s
 => CACHED [builder 4/4] RUN java -Djarmode=tools -jar application.jar extract --layers --destination extracted                                              0.0s
 => CACHED [stage-1 4/7] COPY --from=builder /builder/extracted/dependencies/ ./                                                                             0.0s
 => CACHED [stage-1 5/7] COPY --from=builder /builder/extracted/spring-boot-loader/ ./                                                                       0.0s
 => CACHED [stage-1 6/7] COPY --from=builder /builder/extracted/snapshot-dependencies/ ./                                                                    0.0s
 => CACHED [stage-1 7/7] COPY --from=builder /builder/extracted/application/ ./                                                                              0.0s
 => exporting to image                                                                                                                                       0.2s
 => => exporting layers                                                                                                                                      0.0s
 => => exporting manifest sha256:18c2fc5c82fd905d029c2ecd3a659c7fd46546f5ad3f7fd50d0c816bdb69a4a9                                                            0.0s
 => => exporting config sha256:28ca4f58f8a2e4a99a18ec11aa111be52b9aaec59ee42e53199bf7d43e957e28                                                              0.0s
 => => exporting attestation manifest sha256:19c33dfff32101b4b7533053e581c390becf4857a3b4d88fd0134ee5ec97a4c2                                                0.0s
 => => exporting manifest list sha256:8282977754ae2f192160a0f0e655262d1df1b0dee8fdabc70e499690da88fd6b                                                       0.0s
 => => naming to docker.io/library/catalog-service:dockerfile                                                                                                0.0s
 => => unpacking to docker.io/library/catalog-service:dockerfile                                                                                             0.0s
PS C:\Users\nikhi\Desktop\catalog-service\catalog-service> 

## Second build output:
PS C:\Users\nikhi\Desktop\catalog-service\catalog-service> .\gradlew.bat bootJar                       

[Incubating] Problems report is available at: file:///C:/Users/nikhi/Desktop/catalog-service/catalog-service/build/reports/problems/problems-report.html

Deprecated Gradle features were used in this build, making it incompatible with Gradle 10.

You can use '--warning-mode all' to show the individual deprecation warnings and determine if they come from your own scripts or plugins.

For more on this, please refer to https://docs.gradle.org/9.8.2/userguide/command_line_interface.html#sec:command_line_warnings in the Gradle documentation.

BUILD SUCCESSFUL in 7s
4 actionable tasks: 3 executed, 1 up-to-date
Consider enabling configuration cache to speed up this build: https://docs.gradle.org/9.8.2/userguide/configuration_cache_enabling.html
PS C:\Users\nikhi\Desktop\catalog-service\catalog-service> docker build -t catalog-service:dockerfile .
[+] Building 4.4s (15/15) FINISHED                                                                                                           docker:desktop-linux
 => [internal] load build definition from Dockerfile                                                                                                         0.0s
 => => transferring dockerfile: 777B                                                                                                                         0.0s
 => [internal] load metadata for docker.io/library/eclipse-temurin:21-jre                                                                                    0.3s
 => [internal] load .dockerignore                                                                                                                            0.0s
 => => transferring context: 2B                                                                                                                              0.0s
 => [internal] load build context                                                                                                                            1.3s
 => => transferring context: 28.10MB                                                                                                                         1.2s
 => [builder 1/4] FROM docker.io/library/eclipse-temurin:21-jre@sha256:ca73b4d3961703d6d7830a4361f49ef0478f8f28b39defc07bb72f4cc7f834f6                      0.0s
 => => resolve docker.io/library/eclipse-temurin:21-jre@sha256:ca73b4d3961703d6d7830a4361f49ef0478f8f28b39defc07bb72f4cc7f834f6                              0.0s
 => CACHED [builder 2/4] WORKDIR /builder                                                                                                                    0.0s
 => [builder 3/4] COPY build/libs/catalog-service-0.0.1-SNAPSHOT.jar application.jar                                                                         0.6s
 => [builder 4/4] RUN java -Djarmode=tools -jar application.jar extract --layers --destination extracted                                                     1.1s
 => CACHED [stage-1 2/7] RUN useradd spring                                                                                                                  0.0s
 => CACHED [stage-1 3/7] WORKDIR /application                                                                                                                0.0s
 => CACHED [stage-1 4/7] COPY --from=builder /builder/extracted/dependencies/ ./                                                                             0.0s
 => CACHED [stage-1 5/7] COPY --from=builder /builder/extracted/spring-boot-loader/ ./                                                                       0.0s
 => CACHED [stage-1 6/7] COPY --from=builder /builder/extracted/snapshot-dependencies/ ./                                                                    0.0s
 => [stage-1 7/7] COPY --from=builder /builder/extracted/application/ ./                                                                                     0.1s
 => exporting to image                                                                                                                                       0.4s
 => => exporting layers                                                                                                                                      0.1s
 => => exporting manifest sha256:dcf12b69bc33eb79e84653063e3facc251ea933638fb1e0afe74f52b420a17ca                                                            0.0s
 => => exporting config sha256:8247ab31aaef838439aea8d058ee5ac8d8652d67f98c0de580f49723035121af                                                              0.0s
 => => exporting attestation manifest sha256:971b5bd086a71df88c969b2843402ee41c70d9d387dddfc71d446ea614f4e991                                                0.0s
 => => exporting manifest list sha256:d2b3669c429945a4bf0276e0d38893bcda39d8a576fa2d45c06d23e3e63ed3d3                                                       0.0s
 => => naming to docker.io/library/catalog-service:dockerfile                                                                                                0.0s
 => => unpacking to docker.io/library/catalog-service:dockerfile                                                                                             0.0s
PS C:\Users\nikhi\Desktop\catalog-service\catalog-service> 

## 3.1 
Running docker compose down and up again removes the books because the data is not persistent. When a container is made, the test data is injected into it. When a container is shut down, the test data is discarded. There is no permanent storage set up yet.