# Security Policy

## Supported Versions

Currently supported versions of jcommons:

| Version | Supported          |
| ------- | ------------------ |
| 1.x     | :white_check_mark: |
| < 1.0   | :x:                |

## Reporting a Vulnerability

**Please do not report security vulnerabilities through public GitHub issues.**

Instead, please report them via one of the following methods:

### Option 1: GitHub Security Advisories (Preferred)
1. Go to https://github.com/FlossWare/jcommons/security/advisories
2. Click "New draft security advisory"
3. Fill in the details

### Option 2: Email
Send an email to: **sfloess@gmail.com**

Please include:
- Type of vulnerability
- Full paths of source file(s) related to the vulnerability
- Location of the affected source code (tag/branch/commit)
- Step-by-step instructions to reproduce the issue
- Proof-of-concept or exploit code (if possible)
- Impact of the issue, including how an attacker might exploit it

### What to Expect
- **Acknowledgment**: Within 48 hours
- **Initial assessment**: Within 1 week
- **Fix timeline**: Depends on severity (critical: days, high: weeks, medium/low: next release)
- **Disclosure**: We follow coordinated disclosure - public disclosure after fix is available

## Security Considerations in jcommons

### Known Security Concerns
1. **Java Serialization** (`StringUtil.fromString`, `StringUtil.fromCompressedString`)
   - Deprecated since v1.22, planned for removal in v2.0
   - **Never use with untrusted data**
   - Only for internal, trusted sources
   - Consider using JSON libraries instead
   - **ObjectInputFilter** protection has been added as of v1.30 to restrict deserialization to trusted packages

2. **File Operations** (`FileUtil`)
   - Validate file paths to prevent directory traversal
   - Be cautious with user-supplied file paths

### Best Practices
- Always use the latest version of jcommons
- Review CHANGELOG.md for security-related updates
- Enable OWASP dependency checking in your builds
- Subscribe to GitHub security advisories for this repository

## Security Update Process
1. Security patch is developed and tested
2. CVE is requested (if applicable)
3. Version is released with security notes in CHANGELOG.md
4. GitHub Security Advisory is published
5. Users are notified via release notes

## Attribution
We appreciate the security research community and will credit researchers (with permission) in:
- CHANGELOG.md
- GitHub Security Advisory
- Release notes

Thank you for helping keep jcommons and its users safe!
