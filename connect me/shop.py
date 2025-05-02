import uuid
from flask import *
from database import *

shop=Blueprint('shop',__name__)


@shop.route('/shop_home')
def shop_home():
    return render_template('shop_home.html')

@shop.route('/view_shop_profile')
def view_shop_profile():
    data={}
    qry="SELECT * FROM shop where shop_id='%s'"%(session['shop_id'])
    res=select(qry)
    data['view']=res
    return render_template('view_shop_profile.html',data=data)

@shop.route('/product_management',methods=['get','post'])
def product_management():   
    if 'submit' in request.form:
    
        pname=request.form['product name']
        price=request.form['price']
        qty=request.form['quantity']
        image=request.files['image']
        path="static/product/"+str(uuid.uuid4())+image.filename
        image.save(path)
        descr=request.form['description']
        
        qry1="insert into product values(null,'%s','%s','%s','%s','%s','%s')"%(session['shop_id'],pname,price,qty,path,descr)
        insert(qry1) 
    data={}
    qry="SELECT * FROM product where shop_id='%s'"%(session['shop_id'])
    res=select(qry)
    data['view']=res  
    if 'action' in request.args:
        value=request.args['action']
        id=request.args['product_id']
        
        if value=='update':
            print("sdfaa")
            a="select * from product where product_id='%s'"%(id)
            b=select(a)
            if b:
                data['up']=b
                if 'update' in request.form:
                    pname=request.form['product name']
                    price=request.form['price']
                    qty=request.form['quantity']
                    image=request.form['image']
                    descr=request.form['description']
                    d="update product set product_name='%s',price='%s',quantity='%s',image='%s',description='%s' where product_id='%s'"%(pname,price,qty,image,descr,id)
                    e=update(d)
                    if e:
                        return "<script>alert('updated successfully');window.location='/product_management'</script>"
                 
        if value=='delete':
            qry2="delete from product where product_id='%s'"%(id)
            res2=delete(qry2)
            if res2:
                return "<script>alert('deleted successfully');window.location='/product_management'</script>"
           
    return render_template("product_management.html",data=data)

@shop.route('/view_order')
def view_order():
    data={}
    qry="SELECT * FROM order_details INNER JOIN order_master USING(om_id) INNER JOIN product USING(product_id) INNER JOIN user USING(user_id) "
    res=select(qry)
    data['view']=res
    return render_template('view_order.html',data=data)

@shop.route('/view_payment')
def view_payment():
    data={}
    id=request.args['id']
    qry="SELECT *,order_payment.amount AS total FROM order_payment INNER JOIN order_details USING(om_id)  WHERE om_id='%s'"%(id)
    res=select(qry)
    data['view']=res
    return render_template('view_payment.html',data=data)

@shop.route("/shop_complaint",methods=['get','post'])
def shop_complaint():
    data={}
    if 'submit' in request.form:
        comp=request.form['comp']
        
        qry1="insert into complaints values(null,'%s',curdate(),'pending','%s')"%(session['shop_id'],comp)
        insert(qry1) 
        
    qry="select * from complaints where sender_id='%s'"%(session['shop_id'])
    res=select(qry)
    
    print(res,'////////////////////////////////////////////////')
    if res:
        data['view']=res
    return render_template('shop_complaint.html',data=data)

@shop.route('/view_reviews')
def view_reviews():
    data={}
    qry="select * from review_product"
    res=select(qry)
    data['view']=res
    return render_template('view_reviews.html',data=data)

