# Windows环境下Docker部署指南

## 前提条件

1. 确保已安装Docker Desktop for Windows
2. 确保Docker服务正在运行

## Docker安装说明

1. 访问[Docker官网](https://www.docker.com/products/docker-desktop)下载Docker Desktop for Windows
2. 安装完成后启动Docker Desktop
3. 确认Docker服务正在运行（系统托盘中应有Docker图标）

## 构建镜像

使用以下命令构建Windows版本的Docker镜像：

```bash
docker build -f Dockerfile.windows -t springboot-deepseek-service:windows .
```

## 运行容器

```bash
docker run -d -p 8080:8080 --name deepseek-service-windows springboot-deepseek-service:windows
```

## Docker Compose部署

如果您想同时运行服务和MySQL数据库，可以使用docker-compose.windows.yml文件：

```bash
docker-compose -f docker-compose.windows.yml up -d
```

## 注意事项

1. Windows环境下的Dockerfile与Linux版本基本相同，因为Docker容器使用的是Linux内核
2. 如果遇到权限问题，请确保以管理员身份运行Docker Desktop
3. Windows环境下可能需要启用WSL2或Hyper-V功能