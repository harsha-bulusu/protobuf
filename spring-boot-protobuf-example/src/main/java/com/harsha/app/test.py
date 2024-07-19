import requests

url = 'http://localhost:8080'
params = {'id': 10, 'username': 'Test'}
headers = {'Accept': 'application/x-protobuf'}

response = requests.get(url, params=params, headers=headers)

print(response.content)

with open('response.bin', 'wb') as f:
    f.write(response.content)
