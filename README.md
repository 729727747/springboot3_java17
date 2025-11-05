# SpringBoot DeepSeek Service

基于Spring Boot 3.x和Java 17的微服务项目，集成DeepSeek大模型实现智能问答功能，并使用MyBatis Plus操作MySQL数据库。

## Running the Application

To run this application:

```bash
# Using docker-compose
docker-compose up --build
```

The application will be accessible at http://localhost:8080

## Running on Red Hat Systems

For Red Hat Linux systems, use the specialized Dockerfile and docker-compose configuration:

```bash
# Using Red Hat specific docker-compose file
docker-compose -f docker-compose.redhat.yml up --build
```

## 功能特性

- 集成DeepSeek大模型API实现智能问答
- 使用MyBatis Plus操作MySQL数据库
- 支持Docker容器化部署
- 多环境配置支持（开发/生产）
- RESTful API设计

## 技术栈

- Java 17
- Spring Boot 3.x
- MyBatis Plus
- MySQL 8.0
- Docker
- Maven

## 项目结构

```
springboot-deepseek-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/deepseek/
│   │   │       ├── DeepSeekApplication.java          # 启动类
│   │   │       ├── config/                          # 配置类
│   │   │       ├── controller/                      # 控制层
│   │   │       ├── service/                         # 业务层
│   │   │       ├── client/                          # 外部服务客户端
│   │   │       ├── mapper/                          # MyBatis Plus Mapper接口
│   │   │       ├── entity/                          # 实体类
│   │   │       └── model/                           # 数据模型
│   │   └── resources/
│   └── test/
└── Dockerfile                                       # Docker配置文件
```

## 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Docker (可选，用于容器化部署)

## 快速开始

### 1. 数据库配置

创建MySQL数据库：
```sql
CREATE DATABASE deepseek_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. 配置文件修改

修改`src/main/resources/application.yml`中的数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/deepseek_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
```

### 3. 配置DeepSeek API Key

在`application.yml`中配置您的DeepSeek API Key：
```yaml
deepseek:
  api:
    key: your_api_key_here
```

### 4. 构建和运行

```bash
# 构建项目
./mvnw clean package

# 运行项目
java -jar target/springboot-deepseek-service-1.0.0.jar
```

## Docker部署

### 构建镜像
```bash
docker build -t springboot-deepseek-service:1.0.0 .
```

### 运行容器
```bash
docker run -d -p 8080:8080 \
  --name deepseek-service \
  -e DEEPSEEK_API_KEY=your-api-key-here \
  springboot-deepseek-service:1.0.0
```

### Docker Compose部署
```bash
docker-compose up -d
```

## API接口

### 问答接口
```
POST /api/v1/qa/chat
```

请求示例：
```json
{
  "question": "什么是Spring Boot？",
  "history": [
    {
      "role": "user",
      "content": "请介绍一下Java框架"
    },
    {
      "role": "assistant",
      "content": "Java有很多优秀的框架，比如Spring、Spring Boot、Hibernate等"
    }
  ]
}
```

## 许可证

MIT