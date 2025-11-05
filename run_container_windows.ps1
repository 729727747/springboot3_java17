# 启动Spring Boot DeepSeek服务容器
Write-Host "启动Spring Boot DeepSeek服务容器..." -ForegroundColor Green
Write-Host ""

# 检查容器是否已存在
$containerExists = docker ps -a --format "{{.Names}}" | Select-String -Pattern "deepseek-service-windows" -Quiet

if ($containerExists) {
    Write-Host "容器deepseek-service-windows已存在，正在启动..." -ForegroundColor Yellow
    docker start deepseek-service-windows
} else {
    Write-Host "容器deepseek-service-windows不存在，正在创建并启动..." -ForegroundColor Yellow
    docker run -d -p 8080:8080 --name deepseek-service-windows springboot-deepseek-service:windows
}

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "容器启动成功！" -ForegroundColor Green
    Write-Host ""
    Write-Host "您可以通过以下URL访问应用：" -ForegroundColor Cyan
    Write-Host "http://localhost:8080" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "要查看容器日志，请运行：" -ForegroundColor Cyan
    Write-Host "docker logs deepseek-service-windows" -ForegroundColor Cyan
} else {
    Write-Host ""
    Write-Host "容器启动失败，请检查错误信息" -ForegroundColor Red
}

Write-Host ""
Write-Host "按任意键退出..." -ForegroundColor Gray
$host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")