from flask import Flask, request
import flask_fs as fs

app = Flask(__name__)
fs.init_app(app)

@app.route('/levels/<level_id>', methods = ['GET', 'POST'])
def level(level_id):
    levels = fs.Storage('levels', 'json', overwrite=True)
    if request.method == 'GET':
        if levels.exists(id):
           return levels.read('level_id')
    if request.method == 'POST':
        levels.write(level_id, request.form)
    else:
        return 'invalid request', 405
