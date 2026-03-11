.PHONY: build-HelloWorldJavaFunction build-HelloWorldFunction

build-HelloWorldJavaFunction:
	./gradlew fatJar
	cd $(ARTIFACTS_DIR) && jar xf $(CURDIR)/build/libs/graalvm-aws-lambda-1.0-SNAPSHOT-all.jar

build-HelloWorldFunction:
	./build.sh
	cp build/native/nativeCompile/runtime $(ARTIFACTS_DIR)/runtime
	cp src/main/config/bootstrap $(ARTIFACTS_DIR)/bootstrap
	chmod +x $(ARTIFACTS_DIR)/runtime $(ARTIFACTS_DIR)/bootstrap
