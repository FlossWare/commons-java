# Contributing to jcommons

Thank you for your interest in contributing to jcommons! This document provides guidelines and instructions for contributing to this project.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [Making Changes](#making-changes)
- [Coding Standards](#coding-standards)
- [Testing Requirements](#testing-requirements)
- [Submitting Changes](#submitting-changes)
- [Release Process](#release-process)

## Code of Conduct

This project adheres to the [Contributor Covenant Code of Conduct](CODE_OF_CONDUCT.md). By participating, you are expected to uphold this code.

## Getting Started

1. **Fork the repository** on GitHub
2. **Clone your fork** locally:
   ```bash
   git clone https://github.com/YOUR_USERNAME/jcommons.git
   cd jcommons
   ```
3. **Add upstream remote**:
   ```bash
   git remote add upstream https://github.com/FlossWare/jcommons.git
   ```
4. **Create a feature branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```

## Development Setup

### Prerequisites

- **Java 17+** (required)
- **Maven 3.6.3+** (required)
- **Git** (required)

### Build the Project

```bash
# Clean build with all quality checks
mvn clean verify

# Run tests only
mvn test

# Run specific test
mvn test -Dtest=StringUtilTest

# Generate coverage report
mvn clean test
# View report at: target/site/jacoco/index.html
```

### Quality Checks

The build runs multiple quality checks automatically:

- **JaCoCo** - Code coverage (minimum: 93% instruction, 86% branch, 93% line)
- **SpotBugs** - Static analysis for bugs
- **PMD** - Code quality analysis
- **Checkstyle** - Code style validation (Google Java Style)
- **Maven Enforcer** - Dependency and version validation
- **OWASP Dependency Check** - Security vulnerability scanning

All checks must pass for the build to succeed.

## Making Changes

### Branch Naming

Use descriptive branch names with prefixes:

- `feature/` - New features
- `fix/` - Bug fixes
- `docs/` - Documentation updates
- `refactor/` - Code refactoring
- `test/` - Test improvements

Examples:
- `feature/add-xml-utilities`
- `fix/null-pointer-in-string-util`
- `docs/update-readme-examples`

### Commit Messages

Follow the [Conventional Commits](https://www.conventionalcommits.org/) format:

```
<type>: <subject>

<body>

<footer>
```

**Types:**
- `feat:` - New feature
- `fix:` - Bug fix
- `docs:` - Documentation only
- `style:` - Code style changes (formatting, no logic change)
- `refactor:` - Code refactoring
- `test:` - Adding or updating tests
- `chore:` - Maintenance tasks

**Example:**
```
fix: Correct null handling in StringUtil.requireNonBlank

The method was not properly handling null inputs, causing
NullPointerException instead of IllegalArgumentException.

Added comprehensive tests for null and empty string cases.

Fixes #123
```

### Code Changes

1. **Write code** following our [Coding Standards](#coding-standards)
2. **Add tests** for all new functionality
3. **Update documentation** (JavaDoc, README, CHANGELOG)
4. **Run quality checks**: `mvn clean verify`
5. **Ensure 100% test coverage** for new code

## Coding Standards

### General Guidelines

- **Java 17** language features are allowed
- **Follow Google Java Style Guide** (enforced by Checkstyle)
- **Use meaningful names** for variables, methods, and classes
- **Keep methods focused** - one responsibility per method
- **Prefer immutability** where possible
- **Use final** for parameters and local variables when practical

### Naming Conventions

```java
// Classes: PascalCase
public class StringUtil { }

// Interfaces: PascalCase
public interface Stringifiable { }

// Methods and variables: camelCase
public void parseString() { }
private String userName;

// Constants: UPPER_CASE_WITH_UNDERSCORES
private static final String DEFAULT_SEPARATOR = "";
private static final Logger LOGGER = Logger.getLogger(...);

// Packages: lowercase
package org.flossware.jcommons.util;
```

### Error Messages

Use consistent error message phrasing:

```java
// ✅ Good
Objects.requireNonNull(obj, "Object must not be null");
StringUtil.requireNonBlank(str, "String must not be null or blank");

// ❌ Avoid
Objects.requireNonNull(obj, "Cannot have a null object!");
Objects.requireNonNull(obj, "Must have an object!");
```

### JavaDoc

All public APIs require JavaDoc:

```java
/**
 * Validates that a string is not null, empty, or whitespace-only.
 *
 * @param string the string to validate
 * @param message the error message if validation fails
 * @return the validated string
 * @throws IllegalArgumentException if the string is blank
 */
public static String requireNonBlank(String string, String message) {
    // implementation
}
```

### Logging

- Use `java.util.logging.Logger` (not SLF4J or Logback)
- Static logger field: `private static final Logger LOGGER`
- Appropriate levels: SEVERE (errors), WARNING (warnings), INFO (informational), FINE/FINER/FINEST (debug)
- Use parameterized logging:

```java
private static final Logger LOGGER = Logger.getLogger(ClassName.class.getName());

// ✅ Good
LOGGER.log(Level.INFO, "Processing file: {0}", fileName);

// ❌ Avoid
LOGGER.log(Level.INFO, "Processing file: " + fileName);
```

## Testing Requirements

### Test Coverage

- **Minimum coverage**: 93% instruction, 86% branch, 93% line (enforced by JaCoCo)
- **Goal**: 100% coverage for new code
- **Current status**: 100% instruction, 96% branch, 100% method, 100% line, 100% class

### Test Structure

```java
class StringUtilTest {
    
    @Test
    void testRequireNonBlank_withValidString() {
        String result = StringUtil.requireNonBlank("hello");
        assertEquals("hello", result);
    }
    
    @Test
    void testRequireNonBlank_withNull() {
        assertThrows(IllegalArgumentException.class,
            () -> StringUtil.requireNonBlank(null));
    }
    
    @Test
    void testRequireNonBlank_withEmptyString() {
        assertThrows(IllegalArgumentException.class,
            () -> StringUtil.requireNonBlank(""));
    }
}
```

### Test Naming

- Use descriptive names: `test<Method>_<Condition>`
- Examples:
  - `testRequireNonBlank_withValidString`
  - `testFromString_withNullInput`
  - `testSetHeader_throwsExceptionForNullService`

### What to Test

1. **Happy path** - Normal, expected usage
2. **Edge cases** - Null, empty, boundary values
3. **Error cases** - Invalid input, exception handling
4. **Security features** - ObjectInputFilter, path validation
5. **Integration** - Component interactions

## Submitting Changes

### Before Submitting

1. **Sync with upstream**:
   ```bash
   git fetch upstream
   git rebase upstream/main
   ```

2. **Run full build**:
   ```bash
   mvn clean verify
   ```

3. **Ensure all tests pass**:
   ```bash
   mvn test
   # Should show: Tests run: 277, Failures: 0, Errors: 0, Skipped: 0
   ```

4. **Check code coverage**:
   ```bash
   mvn clean test
   open target/site/jacoco/index.html
   ```

### Pull Request Process

1. **Push to your fork**:
   ```bash
   git push origin feature/your-feature-name
   ```

2. **Create Pull Request** on GitHub:
   - Use a clear, descriptive title
   - Reference related issues: "Fixes #123"
   - Describe what changed and why
   - Include test evidence (coverage, test counts)

3. **PR Template** (include in description):
   ```markdown
   ## Description
   Brief description of changes
   
   ## Related Issues
   Fixes #123
   
   ## Changes Made
   - Added X feature
   - Fixed Y bug
   - Updated Z documentation
   
   ## Testing
   - All 277 tests pass
   - Coverage: 100% instruction, 96% branch
   - Added 5 new tests for feature X
   
   ## Checklist
   - [ ] Tests added/updated
   - [ ] JavaDoc updated
   - [ ] README updated (if needed)
   - [ ] CHANGELOG updated
   - [ ] All quality checks pass
   ```

4. **Address review feedback**
5. **Wait for approval** from maintainers
6. **Squash and merge** (maintainers will handle)

## Release Process

Releases are automated via GitHub Actions:

1. **Development** on `main` branch
2. **CI/CD** runs on every push:
   - Runs all tests and quality checks
   - Generates JavaDoc
   - Runs OWASP dependency check
   - Deploys to packagecloud.io
   - Creates git tag
3. **Versioning** is automatic (patch bump on each merge)
4. **CHANGELOG** should be updated manually

### Version Scheme

- **v1.x** - Current stable branch
- **v2.0** - Future major release (breaking changes)

See [CHANGELOG.md](CHANGELOG.md) for deprecation schedule.

## Questions?

- **Issues**: https://github.com/FlossWare/jcommons/issues
- **Discussions**: Use GitHub Discussions for questions
- **Security**: See [SECURITY.md](SECURITY.md) for vulnerability reporting

## License

By contributing, you agree that your contributions will be licensed under the [GNU General Public License v3.0](LICENSE).

---

Thank you for contributing to jcommons! 🎉
