from flask import Flask, request
from glob import glob
import pathlib
import os
import json

app = Flask(__name__)
pathlib.Path('levels').mkdir(exist_ok=True, )

if __name__ == '__main__':
    app.run()


@app.route('/levels', methods=['GET'])
def list_level():
    contents = os.listdir('levels')
    parsed = []
    for i in contents:
        parsed.append(i.split('.')[0])
    return str(parsed)


@app.route('/levels/<level_id>', methods=['GET', 'POST'])
def level(level_id):
    if request.method == 'GET':
        with open('levels/' + level_id + '.json', "r") as file:
            return file.read()
    if request.method == 'POST':
        with open('levels/' + level_id + '.json', "w+") as file:
            file.write(json.dumps(request.json))
            return "successful upload"
