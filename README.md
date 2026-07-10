# commons-java

Foundation utilities for the [Solenopsis](https://github.com/solenopsis) Salesforce SOAP framework.

[![Build Status](https://github.com/FlossWare/commons-java/workflows/CD-CI/badge.svg)](https://github.com/FlossWare/commons-java/actions)
[![codecov](https://codecov.io/gh/FlossWare/commons-java/branch/main/graph/badge.svg)](https://codecov.io/gh/FlossWare/commons-java)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Maven Central](https://img.shields.io/badge/maven--central-packagecloud-orange)](https://packagecloud.io/flossware/java)
[![Java Version](https://img.shields.io/badge/Java-17%2B-blue)](https://openjdk.org/projects/jdk/17/)
[![Coverage](https://img.shields.io/badge/coverage-96%25-brightgreen)](https://github.com/FlossWare/commons-java/actions)
[![Quality](https://img.shields.io/badge/quality-A%2B-brightgreen)](https://github.com/FlossWare/commons-java/actions)

## Purpose

This library provides low-level utilities used by the Solenopsis framework for Salesforce SOAP operations:
- **[solenopsis/soap](https://github.com/solenopsis/soap)** - Salesforce SOAP client generation (Apex, Metadata, Enterprise, Partner, Tooling APIs)
- **[solenopsis/session](https://github.com/solenopsis/session)** - Salesforce session management and authentication

## Features

### SOAP Utilities (`org.flossware.commons.util.SoapUtil`)
Core utilities for Apache CXF SOAP clients:
- Configure SOAP service headers and endpoints
- Compute QNames from `@WebServiceClient` annotations  
- Set custom headers for Salesforce API calls
- Manage SOAP factory instances

### String Utilities (`org.flossware.commons.util.StringUtil`)
- String concatenation with separators
- URL encoding
- Unique ID generation with UUID
- Validation (`requireNonBlank`)
- Serialization/deserialization (internal use only)

### File Utilities (`org.flossware.commons.util.FileUtil`)
- Safe file I/O with validation
- File existence checking
- FileInputStream creation with proper error handling

### Additional Utilities
- **ArrayUtil** - Array validation and null-checking
- **ClassUtil** - Reflection and package utilities
- **LoggerUtil** - Enhanced logging with varargs support
- **ObjectUtil** - Object validation helpers
- **UrlUtil** - URL parsing and manipulation

## Installation

### Maven
```xml
<dependency>
    <groupId>org.flossware</groupId>
    <artifactId>commons-java</artifactId>
    <version>LATEST</version> <!-- Check releases: https://github.com/FlossWare/commons-java/releases -->
</dependency>

<repositories>
    <repository>
        <id>flossware-packagecloud</id>
        <url>https://packagecloud.io/flossware/java/maven2</url>
    </repository>
</repositories>
```

## Usage Examples

### Configure Salesforce SOAP Endpoint

```java
import org.flossware.commons.util.SoapUtil;

// Create a port and set Salesforce endpoint
MetadataPortType port = portFactory.createPort();
SoapUtil.setUrl(port, "https://na1.salesforce.com/services/Soap/m/58.0");
```

### Set SOAP Headers for Salesforce Session

```java
import org.flossware.commons.util.SoapUtil;
import javax.xml.namespace.QName;

Service service = new MetadataService();
QName sessionHeaderQName = new QName("http://soap.sforce.com/2006/04/metadata", "SessionHeader");

SoapUtil.setHeader(service, sessionHeaderQName, sessionHeaderValue);
```

### Compute QName from Service Class

```java
import org.flossware.commons.util.SoapUtil;

QName qname = SoapUtil.computeQName(MetadataService.class);
// Returns QName based on @WebServiceClient annotation
```

### String Validation

```java
import org.flossware.commons.util.StringUtil;

// Validate non-blank strings
String apiUrl = StringUtil.requireNonBlank(url, "Salesforce URL cannot be blank");

// Generate unique IDs
String requestId = StringUtil.generateUniqueString("sfdc-request-");
```

### File Operations

```java
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.InputStream;

// Modern approach using java.nio.file.Files
Path wsdlPath = Paths.get("src/main/resources/wsdl/metadata.wsdl");
if (!Files.exists(wsdlPath)) {
    throw new IllegalArgumentException("WSDL file not found: " + wsdlPath);
}

// Create input stream
InputStream inputStream = Files.newInputStream(wsdlPath);
```

## Requirements

- **Java 17+**
- **Apache CXF 4.0+** - For SOAP client support
- **Apache Commons Lang3 3.17+** - Baseline utilities

## Building

```bash
# Build and run tests
mvn clean install

# Run tests only
mvn test

# Skip tests
mvn clean install -DskipTests
```

## Test Coverage

The library maintains **excellent test coverage** with **321 tests**:

**Coverage Metrics:**
- 🎯 **96% instruction coverage** (2,034/2,110 instructions)
- ✅ **84% branch coverage** (132/158 branches)
- 🎯 **100% method coverage** (135/135 methods)
- 🎯 **96% line coverage** (437/453 lines)
- 🎯 **100% class coverage**

**Test Suite Includes:**
- Input validation edge cases (null, empty, whitespace)
- Exception handling verification (including defensive paths)
- SOAP utilities with Mockito-based integration tests
- String operations, encoding, and serialization
- File operations with temporary files
- Reflection-based tests for private constructors and utility class enforcement
- Mock-based tests for unreachable exception paths
- Defensive code validation via reflection
- **ObjectInputFilter comprehensive security testing**:
  - ALLOWED path: trusted packages (org.flossware.*, java.lang.*, java.util.*, arrays)
  - REJECTED path: untrusted packages with warning logging
  - UNDECIDED path: null serialClass during metadata processing
- Complex object graph deserialization (ArrayList, nested structures)

All 321 tests pass with 0 failures. JaCoCo coverage reports are generated with each build.

**Remaining Branch Coverage (26 of 158 branches, 84%):**
- Minor edge cases in conditional logic

## Architecture

This library sits at the foundation of the Solenopsis stack:

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
│  commons-java               │  ← Foundation utilities (this)
└─────────────────────────┘
```

## Security Considerations

**For security vulnerability reporting, please use [GitHub Issues](https://github.com/FlossWare/commons-java/issues).**

### Java Serialization
The serialization methods in `StringUtil` are **for internal use only**:
- **WARNING**: Java deserialization of untrusted data is a security risk
- Only deserialize data from trusted sources
- Consider using JSON or XML for external data
- These methods are used internally for session caching
- **ObjectInputFilter** protection added in v1.30 to restrict deserialization to trusted packages

## Deprecation Notice

Several methods are **deprecated** and scheduled for removal in **version 2.0**:

### StringUtil Serialization Methods (Deprecated since v1.22)
- `toString(Serializable)` - Use JSON libraries (Jackson, Gson) instead
- `fromString(String)` - Use JSON libraries (Jackson, Gson) instead
- `toCompressedString(Serializable)` - Use JSON with compression instead
- `fromCompressedString(String)` - Use JSON with decompression instead

**Migration Example:**
```java
// Old (deprecated)
String serialized = StringUtil.toString(myObject);
MyClass obj = StringUtil.fromString(serialized);

// New (recommended)
ObjectMapper mapper = new ObjectMapper();
String json = mapper.writeValueAsString(myObject);
MyClass obj = mapper.readValue(json, MyClass.class);
```

### FileUtil Methods (Deprecated since v1.22)
- `getFileInputStream(File)` - Use `Files.newInputStream(Path)` instead
- `getFileInputStream(String)` - Use `Files.newInputStream(Path)` instead  
- `ensureFileExists(File)` - Use `Files.exists(Path)` with proper exception handling
- `ensureFileExists(String)` - Use `Files.exists(Path)` with proper exception handling

**Migration Example:**
```java
// Old (deprecated)
File file = FileUtil.ensureFileExists("data.txt");
FileInputStream fis = FileUtil.getFileInputStream(file);

// New (recommended)
Path path = Paths.get("data.txt");
if (!Files.exists(path)) {
    throw new IllegalArgumentException("File not found: " + path);
}
InputStream is = Files.newInputStream(path);
```

### StringUtil Validation Methods
- `ensureString(String)` - Use `requireNonBlank(String)` instead
- `ensureString(String, String)` - Use `requireNonBlank(String, String)` instead

**Timeline:**
- v1.x: Current stable branch (security fixes and critical bugs only)
- v2.0: Planned removal of all deprecated methods

See release notes in [GitHub Releases](https://github.com/FlossWare/commons-java/releases) for version history.

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for detailed guidelines including:
- Code style and standards (Google Java Style Guide)
- Testing requirements (93% coverage minimum)
- Pull request process
- Commit message format

**Quick start:**
1. Fork and clone the repository
2. Run `mvn verify` to ensure all tests pass
3. Make changes following our coding standards
4. Submit PR with tests and documentation
5. Maintain 96% coverage minimum

## License

GNU General Public License, Version 3 - See [LICENSE](LICENSE) file

## Links

- **Source**: https://github.com/FlossWare/commons-java
- **Issues**: https://github.com/FlossWare/commons-java/issues
- **Solenopsis SOAP**: https://github.com/solenopsis/soap
- **Solenopsis Session**: https://github.com/solenopsis/session
- **Package Repository**: https://packagecloud.io/flossware/java

## Version History

See [GitHub Releases](https://github.com/FlossWare/commons-java/releases) for detailed version history.
