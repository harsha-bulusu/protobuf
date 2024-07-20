import requests

url = 'http://localhost:8080'

response = requests.get(url)

# print(response.content.decode())
# print(response.headers)
headers= {
    'Accept': 'application/x-protobuf'
}
response = requests.get(url, headers=headers)

print(response.content)
print(response.headers)
