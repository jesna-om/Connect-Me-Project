from flask import *
from database import *
public=Blueprint('public',__name__)


@public.route('/')
def home():
    return render_template("home.html")


@public.route('/login',methods=['get','post'])
def login():
    if 'login' in request.form:
        username=request.form['username']
        passw=request.form['password']
        
        qry="select * from login where user_name='%s' and password='%s'"%(username,passw)
        res=select(qry)
        
        if res:
            session['login_id']=res[0]['login_id']
            print(session['login_id'])
            
        if res[0]['user_type']=='admin':
                return redirect(url_for('admin.admin_home')) 
        if res[0]['user_type']=='user':
                return redirect(url_for('user.user_home'))
        if res[0]['user_type']=='shop':
            qrt="select * from shop where login_id='%s'"%(session['login_id'])
            ress=select(qrt)
            if ress:
                session['shop_id']=ress[0]['shop_id']
            return redirect(url_for('shop.shop_home'))
        if res[0]['user_type']=='worker':
            qrt="select * from worker where login_id='%s'"%(session['login_id'])
            ress=select(qrt)
            if ress:
                session['worker_id']=ress[0]['worker_id']
            return redirect(url_for('worker.worker_home'))
        
    return render_template("login.html")

@public.route('/shop_register',methods=['get','post'])
def shop_register():   
    if 'register' in request.form:
    
        shopname=request.form['shop name']
        place=request.form['place']
        pin=request.form['pin']
        email=request.form['email']
        phone=request.form['phone']
        lat=request.form['latitude']
        long=request.form['longitude']
        username=request.form['username']
        passw=request.form['password']
        
        
        
        qry="insert into login values(null,'%s','%s','shop')"%(username,passw)
        res=insert(qry)
        
        qry1="insert into shop values(null,'%s','%s','%s','%s','%s','%s','%s','%s','shop')"%(res,shopname,place,pin,email,phone,lat,long)
        insert(qry1) 
           
    return render_template("shop_register.html")

@public.route('/worker_register',methods=['get','post'])
def worker_register():   
    if 'register' in request.form:
    
        first=request.form['first_name']
        last=request.form['last_name']
        place=request.form['place']
        phone=request.form['phone']
        email=request.form['email']
        work=request.form['work']
        lat=request.form['latitude']
        long=request.form['longitude']
        username=request.form['username']
        passw=request.form['password']

        qry="insert into login values(null,'%s','%s','worker')"%(username,passw)
        res=insert(qry)
        
        
        qry1="insert into worker values(null,'%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(res,first,last,place,phone,email,work,lat,long)
        insert(qry1) 
           
    return render_template("worker_register.html")

