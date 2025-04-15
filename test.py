import requests # type: ignore

response = requests.post('http://127.0.0.1:5000/predict', json={
   "symptoms": ["headache", "nausea", "fatigue"]
})

print(response.json())
