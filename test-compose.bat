@echo off
title 测试优化后的docker-compose.yml文件

echo 开始测试优化后的docker-compose.yml文件...
echo.

REM 检查docker-compose是否已安装
where docker-compose >nul 2>&1
if %errorlevel% neq 0 (
    echo docker-compose未安装，请先安装docker-compose
    pause
    exit /b 1
)

echo docker-compose已安装，版本信息：
docker-compose --version
echo.

REM 停止并删除可能存在的旧容器
echo 停止并删除可能存在的旧容器...
docker-compose down
echo.

REM 验证docker-compose.yml文件语法
echo 验证docker-compose.yml文件语法...
docker-compose config

if %errorlevel% equ 0 (
    echo docker-compose.yml文件语法正确
) else (
    echo docker-compose.yml文件语法有误，请检查
    pause
    exit /b 1
)

echo.

REM 构建并启动服务
echo 构建并启动服务...
docker-compose up --build -d

if %errorlevel% equ 0 (
    echo 服务构建并启动成功
) else (
    echo 服务构建或启动失败
    pause
    exit /b 1
)

echo.

REM 等待服务启动
echo 等待服务启动（约60秒）...
timeout /t 60 /nobreak >nul

echo.

REM 检查服务状态
echo 检查服务状态...
docker-compose ps

echo.

REM 检查应用健康状态
echo 检查应用健康状态...
curl -f http://localhost:8080/actuator/health >nul 2>&1

if %errorlevel% equ 0 (
    echo 应用健康检查通过
) else (
    echo 应用健康检查失败
)

echo.
echo 测试完成
echo.
echo 按任意键退出...
pause >nul