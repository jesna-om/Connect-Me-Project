from flask import * 
from public import public
from admin import admin
from user import user
from shop import shop
from worker import worker

app=Flask(__name__)


app.secret_key="abcde"


app.register_blueprint(public)
app.register_blueprint(admin)
app.register_blueprint(user)
app.register_blueprint(shop)
app.register_blueprint(worker)

app.run(debug=True,host="0.0.0.0",port=5009)