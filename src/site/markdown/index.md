# commons-java

Foundation utilities for the [Solenopsis](https://github.com/solenopsis) Salesforce SOAP framework.

## Overview

commons-java provides low-level utilities used by the Solenopsis framework for Salesforce SOAP operations:

- **[solenopsis/soap](https://github.com/solenopsis/soap)** - Salesforce SOAP client generation (Apex, Metadata, Enterprise, Partner, Tooling APIs)
- **[solenopsis/session](https://github.com/solenopsis/session)** - Salesforce session management and authentication

## Key Features

### SOAP Utilities
Core utilities for Apache CXF SOAP clients:
- Configure SOAP service headers and endpoints
- Compute QNames from `@WebServiceClient` annotations  
- Set custom headers for Salesforce API calls
- Manage SOAP factory instances

### String Utilities
- String concatenation with separators
- URL encoding
- Unique ID generation with UUID
- Validation (`requireNonBlank`)

### File Utilities
- Safe file I/O with validation
- File existence checking
- FileInputStream creation with proper error handling

### Additional Utilities
- **ArrayUtil** - Array validation and null-checking
- **ClassUtil** - Reflection and package utilities
- **LoggerUtil** - Enhanced logging with varargs support
- **ObjectUtil** - Object validation helpers
- **UrlUtil** - URL parsing and manipulation

## Requirements

- **Java 17+**
- **Apache CXF 4.0+** - For SOAP client support
- **Apache Commons Lang3 3.17+** - Baseline utilities

## Quality Metrics

The library maintains **excellent test coverage** with **341 tests** (321 unit + 20 integration):

- 🎯 **96% instruction coverage**
- ✅ **84% branch coverage**
- 🎯 **100% method coverage**
- 🎯 **100% class coverage**

See the [JaCoCo Coverage Report](jacoco/index.html) for details.

## Installation

### Maven

```xml
<dependency>
    <groupId>org.flossware</groupId>
    <artifactId>commons-java</artifactId>
    <version>LATEST</version>
</dependency>

<repositories>
    <repository>
        <id>flossware-packagecloud</id>
        <url>https://packagecloud.io/flossware/java/maven2</url>
    </repository>
</repositories>
```

## License

GNU General Public License, Version 3 - See [LICENSE](https://github.com/FlossWare/commons-java/blob/main/LICENSE) file

## Links

- **Source**: [https://github.com/FlossWare/commons-java](https://github.com/FlossWare/commons-java)
- **Issues**: [https://github.com/FlossWare/commons-java/issues](https://github.com/FlossWare/commons-java/issues)
- **Package Repository**: [https://packagecloud.io/flossware/java](https://packagecloud.io/flossware/java)
