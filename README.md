# graalvm-aws-lambda
Testing Native Lambda using GraalVM

## Prerequisites

- Java 25 (Amazon Corretto or similar) for the JVM runtime
- [GraalVM CE 25.0.2](https://github.com/graalvm/graalvm-ce-builds/releases) for native compilation — install via SDKMAN: `sdk install java 25.0.2-graalce`
- [AWS SAM CLI](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/install-sam-cli.html)
- Docker (required by SAM for local invocation and native builds)

## Java Runtime

Uses the managed `java25` Lambda runtime. Easier to iterate on — no native compilation step.

**Build:**
```bash
sam build --template template-java.yaml
```

**Run locally:**
```bash
sam local invoke HelloWorldJavaFunction --event events/event.json
```

## Native Runtime (GraalVM)

Compiles to a native Linux ARM64 binary (`bootstrap`) and runs on the `provided.al2023` custom runtime. Faster cold starts and lower memory usage than the JVM runtime.

The `sam build` step runs `build.sh` which compiles inside a Linux Docker container — no separate build step needed.

**Build:**
```bash
sam build --template template-native.yaml
```

**Run locally:**
```bash
sam local invoke HelloWorldFunction --event events/event.json
```
