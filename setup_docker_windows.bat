@echo off
echo 检查Docker Desktop安装状态...
echo.

REM 检查Docker是否已安装
docker --version >nul 2>&1
if %errorlevel% == 0 (
    echo Docker已安装
) else (
    echo Docker未安装，正在尝试安装...
    echo 请访问 https://www.docker.com/products/docker-desktop 下载并安装Docker Desktop
    echo 安装完成后，请重新运行此脚本
    pause
    exit /b
)

echo.
echo 检查Docker Desktop服务状态...
echo.

REM 检查Docker Desktop是否正在运行
tasklist /fi "imagename eq Docker Desktop.exe" /fo csv 2>nul | find /i "Docker Desktop.exe" >nul
if %errorlevel% == 0 (
    echo Docker Desktop正在运行
) else (
    echo Docker Desktop未运行，正在尝试启动...
    echo 请手动启动Docker Desktop应用程序
    echo 启动后，请重新运行此脚本
    pause
    exit /b
)

echo.
echo 构建Windows Docker镜像...
echo.
docker build -f Dockerfile.windows -t springboot-deepseek-service:windows .

if %errorlevel% == 0 (
    echo.
    echo 镜像构建成功！
    echo.
    echo 现在可以运行容器了：
    echo docker run -d -p 8080:8080 --name deepseek-service-windows springboot-deepseek-service:windows
) else (
    echo.
    echo 镜像构建失败，请检查错误信息
)

pause