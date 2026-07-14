# Getting Started

## Installation

Add commons-java to your Maven project:

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

Check [GitHub Releases](https://github.com/FlossWare/commons-java/releases) for the latest version.

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
import org.flossware.commons.util.FileUtil;

// Ensure file exists before processing
File wsdlFile = FileUtil.ensureFileExists("src/main/resources/wsdl/metadata.wsdl");

// Safe file input stream creation
FileInputStream fis = FileUtil.getFileInputStream(deploymentPackage);
```

## Building from Source

```bash
# Clone the repository
git clone https://github.com/FlossWare/commons-java.git
cd commons-java

# Build and run tests
./mvnw clean verify

# Run tests only
./mvnw test

# Generate coverage report
./mvnw clean verify
# View report at: target/site/jacoco/index.html
```

## Next Steps

- Read the [JavaDoc](apidocs/index.html) for detailed API documentation
- Check the [Architecture](architecture.html) page to understand the project structure
- Review [CONTRIBUTING.md](https://github.com/FlossWare/commons-java/blob/main/CONTRIBUTING.md) if you want to contribute
- View [Quality Reports](jacoco/index.html) for coverage and static analysis
