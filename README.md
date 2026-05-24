# jcommons

Foundation utilities for the [Solenopsis](https://github.com/solenopsis) Salesforce SOAP framework.

[![Build Status](https://github.com/FlossWare/jcommons/workflows/CD-CI/badge.svg)](https://github.com/FlossWare/jcommons/actions)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

## Purpose

This library provides low-level utilities used by the Solenopsis framework for Salesforce SOAP operations:
- **[solenopsis/soap](https://github.com/solenopsis/soap)** - Salesforce SOAP client generation (Apex, Metadata, Enterprise, Partner, Tooling APIs)
- **[solenopsis/session](https://github.com/solenopsis/session)** - Salesforce session management and authentication

## Features

### SOAP Utilities (`org.flossware.jcommons.util.SoapUtil`)
Core utilities for Apache CXF SOAP clients:
- Configure SOAP service headers and endpoints
- Compute QNames from `@WebServiceClient` annotations  
- Set custom headers for Salesforce API calls
- Manage SOAP factory instances

### String Utilities (`org.flossware.jcommons.util.StringUtil`)
- String concatenation with separators
- URL encoding
- Unique ID generation with UUID
- Validation (`requireNonBlank`)
- Serialization/deserialization (internal use only)

### File Utilities (`org.flossware.jcommons.util.FileUtil`)
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
    <artifactId>jcommons</artifactId>
    <version>1.21</version>
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
import org.flossware.jcommons.util.SoapUtil;

// Create a port and set Salesforce endpoint
MetadataPortType port = portFactory.createPort();
SoapUtil.setUrl(port, "https://na1.salesforce.com/services/Soap/m/58.0");
```

### Set SOAP Headers for Salesforce Session

```java
import org.flossware.jcommons.util.SoapUtil;
import javax.xml.namespace.QName;

Service service = new MetadataService();
QName sessionHeaderQName = new QName("http://soap.sforce.com/2006/04/metadata", "SessionHeader");

SoapUtil.setHeader(service, sessionHeaderQName, sessionHeaderValue);
```

### Compute QName from Service Class

```java
import org.flossware.jcommons.util.SoapUtil;

QName qname = SoapUtil.computeQName(MetadataService.class);
// Returns QName based on @WebServiceClient annotation
```

### String Validation

```java
import org.flossware.jcommons.util.StringUtil;

// Validate non-blank strings
String apiUrl = StringUtil.requireNonBlank(url, "Salesforce URL cannot be blank");

// Generate unique IDs
String requestId = StringUtil.generateUniqueString("sfdc-request-");
```

### File Operations

```java
import org.flossware.jcommons.util.FileUtil;

// Ensure file exists before processing
File wsdlFile = FileUtil.ensureFileExists("src/main/resources/wsdl/metadata.wsdl");

// Safe file input stream creation
FileInputStream fis = FileUtil.getFileInputStream(deploymentPackage);
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

The library includes **104 comprehensive unit tests** covering:
- Input validation edge cases (null, empty, whitespace)
- Exception handling verification
- SOAP utilities with proper null checks
- String operations and encoding
- File operations with temporary files
- Serialization/deserialization

All tests pass with 0 failures.

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
│  jcommons               │  ← Foundation utilities (this)
└─────────────────────────┘
```

## Security Considerations

### Java Serialization
The serialization methods in `StringUtil` are **for internal use only**:
- **WARNING**: Java deserialization of untrusted data is a security risk
- Only deserialize data from trusted sources
- Consider using JSON or XML for external data
- These methods are used internally for session caching

## Contributing

1. Ensure all tests pass: `mvn test`
2. Follow existing code style
3. Add tests for new functionality
4. Update documentation

## License

GNU General Public License, Version 3 - See [LICENSE](LICENSE) file

## Links

- **Source**: https://github.com/FlossWare/jcommons
- **Issues**: https://github.com/FlossWare/jcommons/issues
- **Solenopsis SOAP**: https://github.com/solenopsis/soap
- **Solenopsis Session**: https://github.com/solenopsis/session
- **Package Repository**: https://packagecloud.io/flossware/java

## Version History

See [CHANGELOG.md](CHANGELOG.md) for detailed version history.
