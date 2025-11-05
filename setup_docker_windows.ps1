# 检查Docker Desktop安装状态
Write-Host "检查Docker Desktop安装状态..." -ForegroundColor Green
Write-Host ""

# 检查Docker是否已安装
try {
    $dockerVersion = docker --version
    Write-Host "Docker已安装: $dockerVersion" -ForegroundColor Green
} catch {
    Write-Host "Docker未安装，正在尝试安装..." -ForegroundColor Yellow
    Write-Host "请访问 https://www.docker.com/products/docker-desktop 下载并安装Docker Desktop" -ForegroundColor Cyan
    Write-Host "安装完成后，请重新运行此脚本" -ForegroundColor Yellow
    pause
    exit
}

Write-Host ""
Write-Host "检查Docker Desktop服务状态..." -ForegroundColor Green
Write-Host ""

# 检查Docker Desktop是否正在运行
$dockerProcess = Get-Process -Name "Docker Desktop" -ErrorAction SilentlyContinue
if ($dockerProcess) {
    Write-Host "Docker Desktop正在运行" -ForegroundColor Green
} else {
    Write-Host "Docker Desktop未运行，正在尝试启动..." -ForegroundColor Yellow
    Write-Host "请手动启动Docker Desktop应用程序" -ForegroundColor Cyan
    Write-Host "启动后，请重新运行此脚本" -ForegroundColor Yellow
    pause
    exit
}

Write-Host ""
Write-Host "构建Windows Docker镜像..." -ForegroundColor Green
Write-Host ""
docker build -f Dockerfile.windows -t springboot-deepseek-service:windows .

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "镜像构建成功！" -ForegroundColor Green
    Write-Host ""
    Write-Host "现在可以运行容器了：" -ForegroundColor Cyan
    Write-Host "docker run -d -p 8080:8080 --name deepseek-service-windows springboot-deepseek-service:windows" -ForegroundColor Cyan
} else {
    Write-Host ""
    Write-Host "镜像构建失败，请检查错误信息" -ForegroundColor Red
}

Write-Host ""
Write-Host "按任意键退出..." -ForegroundColor Gray
$host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")