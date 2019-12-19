from flask import Flask, request
import os
import json

app = Flask(__name__)
if __name__ == '__main__':
    if not os.path.exists('levels'):
        os.makedirs('levels')
    app.run(debug=True)


@app.route('/levels', methods=['GET'])
def list_level():
    return str(os.listdir('levels'))


@app.route('/levels/<level_id>', methods=['GET', 'POST'])
def level(level_id):
    if request.method == 'GET':
        with open('levels/' + level_id + '.json', "r") as file:
            return file.read()
    if request.method == 'POST':
        with open('levels/' + level_id + '.json', "w+") as file:
            file.write(json.dumps(request.form))
            return "successful upload"
