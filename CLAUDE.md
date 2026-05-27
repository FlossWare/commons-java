# jcommons - Project Guidance for Claude Code

This file provides context and guidelines for Claude Code when working on the jcommons project.

## Project Overview

**jcommons** is a foundational utility library for the Solenopsis Salesforce SOAP framework. It provides low-level utilities for SOAP operations, string handling, file I/O, and reflection.

## Architecture Context

This library is the foundation layer of the Solenopsis stack:

```
Solenopsis Session (authentication)
    ↓
Solenopsis SOAP (API clients)
    ↓
jcommons (this library)
```

**Critical Dependencies:**
- **solenopsis/soap** - Uses MethodUtil for SOAP endpoint introspection
- **solenopsis/session** - Uses Stringifiable interface for credentials

## Code Quality Standards

### Testing Requirements
- **93% instruction coverage minimum** (currently at 93%)
- **86% branch coverage minimum** (currently at 86%)
- **93% line coverage minimum** (currently at 93%)
- All new code MUST have comprehensive tests
- Integration tests in `*IT.java` files run via maven-failsafe-plugin

### Code Style
- **Google Java Style Guide** enforced via Checkstyle
- **No wildcard imports** - use specific imports only
- **No compiler warnings** - fix all warnings before committing
- **Final variables** where possible for immutability
- **Private constructors** for utility classes with assertion

### Security
- **OWASP Dependency Check** runs on every build (CVSS threshold: 7.0)
- **Path traversal protection** in FileUtil (CWE-22)
- **ObjectInputFilter** protection for deserialization
- **No deserialization of untrusted data**
- Address all CVEs immediately

### Static Analysis
- **SpotBugs** - zero bugs tolerance
- **PMD** - using standard rulesets
- **Checkstyle** - Google style

## Important Constraints

### Java Version
- **Java 17 is mandatory** (do not upgrade to Java 21+)
- Client base requires Java 17 compatibility
- See memory: feedback_java_version.md

### NOT Over-Engineered
The following utilities appear minimal but are actively used by Solenopsis:
- **MethodUtil** - SOAP endpoint annotation discovery (PortEnum.java, SoapUrlEnum.java)
- **ClassUtil** - Package name extraction
- **Stringifiable** - Credentials formatting (AbstractCredentials)

Do not deprecate or remove these without checking downstream usage.

## Deprecation Policy

Methods deprecated `@Deprecated(since = "1.22", forRemoval = true)`:
- StringUtil serialization methods (use JSON instead)
- FileUtil File-based methods (use NIO.2 Path instead)
- StringUtil.ensureString() (use requireNonBlank())

**Timeline:**
- v1.x: Maintain deprecated methods, security fixes only
- v2.0: Remove all deprecated methods

## Build Configuration

### CI/CD
- **GitHub Actions** - .github/workflows/cd-ci.yml
- **Auto-versioning** - pom.xml version bumped on each push
- **packagecloud.io** - Maven artifacts deployed automatically
- **Codecov** - Coverage reports uploaded

### Maven Plugins
- maven-compiler-plugin: Java 17 target
- maven-surefire-plugin: Unit tests (*Test.java)
- maven-failsafe-plugin: Integration tests (*IT.java)
- jacoco-maven-plugin: Coverage reports
- spotbugs-maven-plugin: Static analysis
- maven-pmd-plugin: Code quality
- maven-checkstyle-plugin: Style enforcement
- dependency-check-maven: Security scanning
- maven-source-plugin: Generate sources JAR
- maven-javadoc-plugin: Generate JavaDoc JAR
- license-maven-plugin: GPL v3.0 header validation

### Test Execution
```bash
# Unit tests only
mvn test

# Unit + integration tests
mvn verify

# With coverage
mvn clean verify
# See target/site/jacoco/index.html
```

## Common Tasks

### Adding New Utility Method
1. Add method to appropriate *Util class
2. Add comprehensive unit tests (null, empty, edge cases)
3. Update JavaDoc with @since tag
4. Run `mvn verify` - coverage must not drop
5. Update CHANGELOG.md

### Fixing Security Vulnerability
1. Check OWASP dependency-check report
2. Update dependency version in pom.xml properties
3. If false positive, add to dependency-check-suppressions.xml
4. Run `mvn clean verify`
5. Create GitHub issue, commit with "Fix CVE-XXXX-XXXXX"

### Deprecating API
1. Add `@Deprecated(since = "X.Y", forRemoval = true)`
2. Update JavaDoc with migration example
3. Add to CHANGELOG.md under "Deprecated" section
4. Do NOT remove until v2.0

### Before Committing
```bash
# Run full build
mvn clean verify

# Check for compiler warnings
mvn compile | grep WARNING

# Verify coverage hasn't dropped
# Check target/site/jacoco/index.html
```

## Commit Message Format

Use Conventional Commits:
```
feat: Add new SOAP header utility
fix: Resolve path traversal vulnerability in FileUtil
docs: Update README with coverage badges
test: Add integration tests for file operations
refactor: Simplify StringUtil concatenation
chore: Update Apache CXF to 4.2.1
```

For GitHub issues:
```
Fix GitHub issue #123: Description of fix

Detailed explanation of changes.

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>
```

## Do NOT
- ❌ Use wildcard imports (`import java.util.*;`)
- ❌ Add features without tests
- ❌ Commit with failing tests
- ❌ Ignore compiler warnings
- ❌ Skip security scans
- ❌ Remove "over-engineered" code without checking Solenopsis usage
- ❌ Upgrade to Java 21+ (must stay on Java 17)
- ❌ Push directly to main with failing CI

## Always Verify
- ✅ All tests pass (`mvn verify`)
- ✅ No compiler warnings
- ✅ Coverage meets thresholds (93%/86%/93%)
- ✅ SpotBugs shows zero bugs
- ✅ No security vulnerabilities
- ✅ GitHub Actions build passes
- ✅ CHANGELOG.md updated for user-facing changes

## Project Contacts

- **Maintainer**: Scot P. Floess (@sfloess)
- **Issues**: https://github.com/FlossWare/jcommons/issues
- **License**: GPL v3.0

## External Links

- **Solenopsis Framework**: https://github.com/solenopsis
- **Package Repository**: https://packagecloud.io/flossware/java
- **CI/CD**: https://github.com/FlossWare/jcommons/actions
