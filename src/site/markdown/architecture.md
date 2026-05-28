# Architecture

## Project Structure

jcommons sits at the foundation of the Solenopsis stack:

```
┌─────────────────────────┐
│  Solenopsis Session     │  ← Authentication & session mgmt
└───────────┬─────────────┘
            │ depends on
┌───────────▼─────────────┐
│  Solenopsis SOAP        │  ← Salesforce API clients
└───────────┬─────────────┘
            │ depends on
┌───────────▼─────────────┐
│  jcommons               │  ← Foundation utilities (this)
└─────────────────────────┘
```

## Package Organization

### org.flossware.jcommons

Base package containing:
- **Stringifiable** interface - For objects that can be converted to strings
- **AbstractStringifiable** - Base implementation with customizable formatting

### org.flossware.jcommons.util

Core utility classes:

#### SoapUtil
Apache CXF SOAP client utilities:
- `computeQName()` - Extract QName from `@WebServiceClient` annotations
- `setUrl()` - Configure SOAP endpoint URL
- `setHeader()` - Set SOAP headers for authentication
- `getSoapFactory()` - Get SOAP factory instance

**Used by**: Solenopsis SOAP clients (Metadata, Enterprise, Partner, Tooling APIs)

#### StringUtil
String manipulation and validation:
- `requireNonBlank()` - Validate non-blank strings
- `concat()` - Concatenate with separators
- `generateUniqueString()` - UUID-based unique IDs
- `encodeUrl()` - URL encoding

**Deprecated**: Serialization methods (use JSON instead)

#### FileUtil
File I/O utilities:
- `ensureFileExists()` - File existence validation
- `getFileInputStream()` - Safe file input stream creation

**Deprecated**: File-based methods (migrate to NIO.2 Path API)

#### ArrayUtil, ObjectUtil, LoggerUtil, ClassUtil, UrlUtil, MethodUtil
Supporting utilities for validation, reflection, and URL handling.

## Technology Stack

- **Java 17** (mandatory, do not upgrade to 21+)
- **Apache CXF 4.2.1** - SOAP client framework
- **Apache Commons Lang3 3.20.0** - Baseline utilities
- **Jakarta XML WS/SOAP APIs** - JAX-WS specifications
- **JUnit 5** - Testing framework
- **Mockito 5.x** - Mocking framework
- **JaCoCo** - Code coverage

## Build Architecture

### Maven Multi-Phase Build

1. **validate** - License header validation
2. **compile** - Java 17 compilation
3. **test** - Unit tests (287 tests)
4. **verify** - Integration tests (20 tests), static analysis
5. **package** - JAR creation (main, sources, javadoc)
6. **install** - Local Maven repository
7. **deploy** - packagecloud.io deployment (CI only)

### Quality Gates

All must pass for successful build:

- ✅ **JaCoCo Coverage** - 93% instruction, 86% branch, 93% line
- ✅ **SpotBugs** - Zero bugs (effort=Max, threshold=Low)
- ✅ **PMD** - Zero violations
- ✅ **Checkstyle** - Google Java Style Guide
- ✅ **OWASP Dependency Check** - No CVEs above CVSS 7.0
- ✅ **License Headers** - GPL v3.0 in all source files

## Security Architecture

### Path Traversal Protection (CWE-22)
FileUtil validates paths to prevent directory traversal attacks.

### Deserialization Safety
ObjectInputFilter restricts deserialization to trusted packages:
- `org.flossware.*`
- `java.lang.*`
- `java.util.*`
- Arrays

**Untrusted packages are REJECTED with warning logs.**

### Dependency Security
- OWASP Dependency Check scans all dependencies
- Dependabot automated updates
- CodeQL security scanning in CI

## CI/CD Pipeline

### GitHub Actions Workflow

```yaml
1. Checkout code
2. Set up Java 17
3. Build with Maven wrapper
   - mvn clean verify
4. Security scans (OWASP, CodeQL)
5. Upload coverage to Codecov
6. Deploy to packagecloud.io
7. Create GitHub Release
```

### Automated Versioning
- pom.xml version bumps on each push to main
- GitHub Releases created automatically
- Release notes generated from commits

## Dependency Chain

```
jcommons
├── Apache CXF 4.2.1
│   ├── Jakarta XML WS API 4.0.3
│   ├── Jakarta XML SOAP API 3.0.2
│   └── Apache Neethi 3.2.2
├── Apache Commons Lang3 3.20.0
└── SLF4J 2.0.x (provided)
```

**Critical**: Solenopsis Session module uses jcommons for session caching (serialization), so security updates in jcommons cascade to all Solenopsis users.

## Deprecation Policy

Methods deprecated since v1.22 scheduled for removal in v2.0:
- StringUtil serialization (security risk)
- FileUtil File-based methods (migrate to NIO.2)
- StringUtil.ensureString() (use requireNonBlank())

See [CHANGELOG.md](https://github.com/FlossWare/jcommons/blob/main/CHANGELOG.md) for migration guides.
