FROM ghcr.io/graalvm/graalvm-community:25 AS builder
WORKDIR /project
COPY . .
RUN ./gradlew nativeCompile --no-daemon

FROM public.ecr.aws/lambda/provided:al2023-arm64
COPY --from=builder /project/build/native/nativeCompile/runtime /var/task/runtime
RUN chmod +x /var/task/runtime
ENTRYPOINT ["/var/task/runtime"]
CMD ["org.local.Main::handleRequest"]
