# Changelog

All notable changes to the jcommons library will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.21] - 2026-05-20

### Changed
- Updated documentation to reflect version 1.20 changes
- CHANGELOG.md now documents versions 1.15-1.20

## [1.20] - 2026-05-20

### Added
- Logback configuration for structured logging
- JaCoCo plugin for code coverage reporting
- Enhanced Surefire plugin configuration with IT test exclusion
- JavaDoc generation in GitHub Actions workflow (non-blocking)
- Security scanning in CI/CD pipeline

### Changed
- Project name simplified to "jcommons" (from "FlossWare JCommons")
- Enhanced .gitignore for comprehensive coverage artifacts
- Updated GitHub Actions workflow with coverage and security checks
- Dependency version updates: JUnit 5.11.4, Mockito 5.14.2, SLF4J 2.0.16, Logback 1.5.12

## [1.15] - 2026-05-19

### Added
- SLF4J logging framework integration
- Mockito testing framework
- JUnit Jupiter engine for test execution

### Changed
- Documentation consistently uses "jcommons" naming

## [1.14] - 2026-05-15

### Changed
- **BREAKING**: Renamed project from "commons" to "jcommons"
  - Maven artifactId: org.flossware:commons → org.flossware:jcommons
  - Java packages: org.flossware.commons.* → org.flossware.jcommons.*
  - GitHub repository: FlossWare/commons → FlossWare/jcommons

### Migration Guide
Update your pom.xml:
```xml
<dependency>
    <groupId>org.flossware</groupId>
    <artifactId>jcommons</artifactId>
    <version>1.14</version>
</dependency>
```

Update your imports:
```java
// Old
import org.flossware.commons.util.SoapUtil;

// New
import org.flossware.jcommons.util.SoapUtil;
```

## [1.10] - 2024-09-03

### Changed
- Replaced all wildcard imports with explicit imports for better code clarity
- All test files now use explicit static imports from JUnit assertions

### Improved
- Better IDE support and code readability
- Clearer dependencies in test files

## [1.9] - 2024-09-03

### Added
- Comprehensive test suite with 104 unit tests
  - `StringUtilTest` - 26 tests for string operations
  - `ArrayUtilTest` - 10 tests for array validation
  - `ObjectUtilTest` - 4 tests for object utilities
  - `ClassUtilTest` - 7 tests for class utilities
  - `FileUtilTest` - 12 tests for file operations
  - `SoapUtilTest` - 17 tests for SOAP utilities
  - `LoggerUtilTest` - 9 tests for logging
  - `UrlUtilTest` - 10 tests for URL operations
  - `AbstractBaseTest` - 9 tests for base class
- Security warnings in JavaDoc for serialization methods
- Input validation to all `SoapUtil` methods with proper null checks
- Comprehensive README with usage examples and architecture diagram
- CHANGELOG.md for version tracking

### Changed
- **BREAKING**: Serialization methods now throw exceptions instead of returning null
  - `StringUtil.toString()` throws `RuntimeException` on failure
  - `StringUtil.fromString()` throws `RuntimeException` on failure
  - `StringUtil.toCompressedString()` throws `RuntimeException` on failure
  - `StringUtil.fromCompressedString()` throws `RuntimeException` on failure
- Fixed logger initialization in `StringUtil` (was logging to wrong class)
- Improved `generateUniqueString()` to use UUID instead of `System.currentTimeMillis()`
- Serialization now uses Base64 encoding for proper binary-to-string conversion
- `ensureString()` methods now deprecated in favor of `requireNonBlank()`
- Enhanced exception messages throughout for better debugging

### Fixed
- Logger in `StringUtil` now correctly references `StringUtil.class` instead of `StringUtils.class`
- Binary serialization encoding issues (now uses Base64)
- Missing input validation in `SoapUtil.setUrl()`, `setHeader()`, `setHeaders()`, and `computeQName()`

### Security
- Added prominent warnings about Java deserialization risks in JavaDoc
- Documented that serialization methods are for internal/trusted use only
- Improved validation to prevent null pointer exceptions

## [1.8] - 2024-09-03

### Changed
- Updated dependencies to latest versions
- Apache Commons Lang3 updated to 3.17.0
- Apache CXF updated to 4.0.4

## [1.7] - 2024-09-03

### Changed
- Dependency updates via automated workflow

## Older Versions

Previous versions (1.0 - 1.6) were internal releases with various utility additions and dependency updates.

---

## Upgrade Guide

### Upgrading from 1.8 to 1.9

#### Breaking Changes

**Serialization Methods Now Throw Exceptions**

Previously, methods returned `null` on failure:
```java
// Old behavior (1.8)
String serialized = StringUtil.toString(obj);
if (serialized == null) {
    // Handle error
}
```

Now they throw exceptions:
```java
// New behavior (1.9)
try {
    String serialized = StringUtil.toString(obj);
} catch (IllegalArgumentException | RuntimeException e) {
    // Handle error
}
```

**Impact**: If you were checking for `null` returns, update to use try-catch blocks.

#### Deprecations

- `StringUtil.ensureString()` → Use `StringUtil.requireNonBlank()` instead

```java
// Deprecated
String value = StringUtil.ensureString(input);

// Recommended
String value = StringUtil.requireNonBlank(input);
```

#### Improvements You Get

- ✅ Better error messages when validation fails
- ✅ UUID-based unique strings (truly unique under concurrency)
- ✅ Proper Base64 encoding for serialization
- ✅ Comprehensive test coverage ensures stability
- ✅ All `SoapUtil` methods now validate inputs

No code changes required unless you:
1. Catch `null` from serialization methods (now throws exceptions)
2. Use deprecated `ensureString()` (still works, but deprecated)
