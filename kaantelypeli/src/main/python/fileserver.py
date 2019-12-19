from flask import Flask, request
import pathlib
import os
import json

app = Flask(__name__)
pathlib.Path('levels').mkdir(exist_ok=True, )

if __name__ == '__main__':
    app.run()


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
