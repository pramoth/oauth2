OAuth 2.0
=================

[![Build Status](https://travis-ci.org/jittagornp/oauth2.svg?branch=master)](https://travis-ci.org/jittagornp/oauth2)
[![Coverage Status](https://coveralls.io/repos/github/jittagornp/oauth2/badge.svg?branch=master)](https://coveralls.io/github/jittagornp/oauth2?branch=master)
[![codecov](https://codecov.io/gh/jittagornp/oauth2/branch/master/graph/badge.svg)](https://codecov.io/gh/jittagornp/oauth2)

ตัวอย่างการเขียน OAuth 2.0 - Java Spring-Boot

Implement ตาม spec `RFC6749` : https://tools.ietf.org/html/rfc6749

> OAuth 2.0 (Open Authentication version 2.0) เป็น Framework/วิธีการ ที่อนุญาตให้ Application ใดๆ (Third-Party Application) สามารถเข้าถึงข้อมูล/ทรัพยากรของผู้ใช้งานระบบ (Resource Owner) จาก Application หนึ่ง ผ่าน Http Service ด้วยการอนุญาตจากเจ้าของทรัพยากรนั้น  

### Requires
- Java 8
- Apache Maven 3.0.4

### วิธีการ Run Project

1. build

> $ mvn clean install -U

2. run
> $ mvn spring-boot:run
