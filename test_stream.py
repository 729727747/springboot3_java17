import requests
import json

url = "http://localhost:8081/api/v1/qa/chat/stream"
headers = {
    "Content-Type": "application/json"
}
data = {
    "question": "你好"
}

response = requests.post(url, headers=headers, json=data, stream=True)

print("Status Code:", response.status_code)
print("Content-Type:", response.headers.get('Content-Type'))

# 读取流式响应
for line in response.iter_lines():
    if line:
        decoded_line = line.decode('utf-8')
        print(decoded_line)