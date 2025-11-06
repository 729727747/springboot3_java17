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

## Windows 环境下部署到 Linux 服务器

### 简化版部署（推荐）

直接使用用户名和密码进行部署，无需设置SSH密钥认证。该脚本会自动下载并安装PuTTY工具包来处理SSH连接。

在Windows命令提示符中执行以下命令：

```bash
# 简化版部署脚本（使用用户名和密码进行部署）
.\simple-deploy.bat
```

脚本将自动完成以下步骤：
1. 检查所需工具（plink、pscp）
2. 下载并安装PuTTY工具包（如果尚未安装）
3. 清理临时目录
4. 准备需要同步的文件
5. 创建远程项目目录
6. 同步文件到远程服务器
7. 在远程服务器执行test-compose.sh脚本
8. 清理临时文件

### 配置说明
在使用 `simple-deploy.bat` 脚本之前，请确保已正确配置以下环境变量：

- `REMOTE_USER`: 远程Linux服务器的用户名
- `REMOTE_HOST`: 远程Linux服务器的IP地址或主机名
- `REMOTE_PORT`: SSH端口号（默认为22）
- `REMOTE_PASSWORD`: 远程Linux服务器的密码
- `REMOTE_PROJECT_PATH`: 远程服务器上的项目路径

### 在 Linux 服务器上配置 systemd 服务

为了更好地管理您的应用，建议在 Linux 服务器上使用 systemd 服务。

#### 创建 systemd 服务文件

在 Linux 服务器上，创建 `/etc/systemd/system/deepseek-app.service` 文件，内容如下：

```ini
[Unit]
Description=DeepSeek App
After=network.target

[Service]
Type=simple
User=your_username
WorkingDirectory=/opt/deepseek-app
ExecStart=/usr/bin/java -jar /opt/deepseek-app/deepseek-app.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

请注意：
1. 将 `User=your_username` 替换为实际的用户名
2. 确保 `WorkingDirectory` 和 `ExecStart` 中的路径正确

#### 启用并启动服务

```bash
sudo systemctl daemon-reload
sudo systemctl enable deepseek-app
sudo systemctl start deepseek-app
```

#### 管理服务

```bash
# 查看服务状态
sudo systemctl status deepseek-app

# 停止服务
sudo systemctl stop deepseek-app

# 重启服务
sudo systemctl restart deepseek-app

# 查看日志
sudo journalctl -u deepseek-app -f
```

### 部署流程

1. 在 Windows 系统的 Trae 编辑器中运行 `deploy-to-linux.ps1` 脚本
2. 脚本会自动在本地编译、打包应用
3. 自动将应用传输到远程 Linux 服务器
4. 在服务器上，脚本会尝试停止旧服务并启动新服务
5. 如果配置了 systemd 服务，应用将作为服务运行

### 故障排除

如果部署过程中遇到问题，请检查：

1. 确保本地已安装 Maven 和 Java 开发环境
2. 确保本地可以 SSH 连接到 Linux 服务器
3. 确保 Linux 服务器上已安装 Java 运行环境
4. 确保指定的部署路径在服务器上存在且有适当的权限
5. 检查防火墙设置，确保应用端口已开放

## 许可证

MIT