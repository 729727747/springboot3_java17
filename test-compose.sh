#!/bin/bash

# 测试优化后的docker-compose.yml文件

echo "开始测试优化后的docker-compose.yml文件..."

# 检查docker-compose是否已安装
if ! command -v docker-compose &> /dev/null
then
    echo "docker-compose未安装，请先安装docker-compose"
    exit 1
fi

echo "docker-compose已安装，版本信息："
docker-compose --version

# 停止并删除可能存在的旧容器
echo "停止并删除可能存在的旧容器..."
docker-compose down

# 验证docker-compose.yml文件语法
echo "验证docker-compose.yml文件语法..."
docker-compose config

if [ $? -eq 0 ]; then
    echo "docker-compose.yml文件语法正确"
else
    echo "docker-compose.yml文件语法有误，请检查"
    exit 1
fi

# 构建并启动服务
echo "构建并启动服务..."
docker-compose up --build -d

if [ $? -eq 0 ]; then
    echo "服务构建并启动成功"
else
    echo "服务构建或启动失败"
    exit 1
fi

# 等待服务启动
echo "等待服务启动（约60秒）..."
sleep 60

# 检查服务状态
echo "检查服务状态..."
docker-compose ps

# 检查应用健康状态
echo "检查应用健康状态..."
curl -f http://localhost:8080/actuator/health

if [ $? -eq 0 ]; then
    echo "应用健康检查通过"
else
    echo "应用健康检查失败"
fi

echo "测试完成"