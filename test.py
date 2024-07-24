import requests

url = 'http://localhost:8080'

response = requests.get(url)

# print(response.content.decode())
# print(response.headers)
headers= {
    'Accept': 'application/json'
}
response = requests.get(url, headers=headers)

print(response.content)
print(response.headers)
hex_representation = response.content.hex()

# Print the hexadecimal string in the desired format
formatted_hex = ' '.join(hex_representation[i:i+2].upper() for i in range(0, len(hex_representation), 2))
print(formatted_hex)