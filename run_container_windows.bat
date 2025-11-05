@echo off
echo 启动Spring Boot DeepSeek服务容器...
echo.

REM 检查容器是否已存在
docker ps -a --format "{{.Names}}" | findstr /i "deepseek-service-windows" >nul
if %errorlevel% == 0 (
    echo 容器deepseek-service-windows已存在，正在启动...
    docker start deepseek-service-windows
) else (
    echo 容器deepseek-service-windows不存在，正在创建并启动...
    docker run -d -p 8080:8080 --name deepseek-service-windows springboot-deepseek-service:windows
)

if %errorlevel% == 0 (
    echo.
    echo 容器启动成功！
    echo.
    echo 您可以通过以下URL访问应用：
    echo http://localhost:8080
    echo.
    echo 要查看容器日志，请运行：
    echo docker logs deepseek-service-windows
) else (
    echo.
    echo 容器启动失败，请检查错误信息
)

echo.
echo 按任意键退出...
pause >nul