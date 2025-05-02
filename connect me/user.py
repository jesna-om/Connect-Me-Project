from flask import *
from database import *

user=Blueprint('user',__name__)


@user.route('/user_login')
def user_login():
    data={}
    
    uname=request.args['u']
    psw=request.args['p']
    
    qry="select * from login inner join user using(login_id) where user_name='%s' and password='%s'"%(uname,psw)
    res=select(qry)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
        
    return str(data)

@user.route('/user_register')
def user_register():
    data={}
    fname=request.args['f']
    lname=request.args['l']
    place=request.args['pl']
    email=request.args['em']
    phone=request.args['ph']
    uname=request.args['u']
    psw=request.args['p']
    lati=request.args['lati']   
    longi=request.args['logi']

    print(lati,longi,"///////////////////////////================")
    
    a="select * from login where user_name='%s'"%(uname)
    b=select(a)
    
    if b:
        data['status']='username'
    
    else:
        
        qry="insert into login values(null,'%s','%s','user')"%(uname,psw)
        id=insert(qry)
        qry1="insert into user values(null,'%s','%s','%s','%s','%s','%s','%s','%s')"%(id,fname,lname,place,email,phone,lati,longi)
        c=insert(qry1)
        
        if c:
            data['status']='success'
        
        else:
            data['status']='failed'
        
   
    return str(data)
   
   
   

@user.route('/user_shop')
def user_shop():
    data={}
    
    qry="select * from shop"
    res=select(qry)
    print(res,"///////////////")
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
        
    data['method']='view'

    # data={}

    # lati=request.args['lati']
    # logi=request.args['logi']

    # print(lati,"_________________")

    # print(logi,"___________________")

    # q="""SELECT *, 
    #     (3959 * ACOS(
    #         COS(RADIANS('%s')) * COS(RADIANS(latitude)) * COS(RADIANS(longitude) - RADIANS('%s')) + 
    #         SIN(RADIANS('%s')) * SIN(RADIANS(latitude))
    #     )) AS user_distance 
    #     FROM shop 
    #     HAVING user_distance < 11.068 
    #     ORDER BY user_distance

    # """%(lati, logi, lati)
    # res=select(q)
    # data['view']=res

    # print(res,"______________________________-")
    # if res:
    #     data['status']='success'
    #     data['data']=res
    # else:
    #     data['status']='failed'
    # data['method']='view'
        
    return str(data)

@user.route('/user_view_product')
def user_view_product():
    data={}
    qry="select * from product INNER JOIN shop using(shop_id)"
    res=select(qry)
    # print(res,"///////////////")
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    data['method']='view'
    print(data,"%%%%%%%%%%%%%%%%%%%%%%")
    return str(data)

    
    
@user.route('/User_add_to_cart')
def User_add_to_cart():
    data={}

    product_id=request.args['productid']
    userid=request.args['loginid']
    shopid=request.args['shopid']
    quantity=request.args['qty']
    price=request.args['price']

    print(product_id,"_")
    print(userid,"")
    print(shopid,"")
    print(quantity,"")
    print(price,"")


    total=int(price)*int(quantity)

    print(total,"tttttttttttttttttttttttt")

    qry="select * from order_master where status='pending' and user_id=(select user_id from user where login_id='%s')"%(userid)
    res=select(qry)

    if res:
        omid=res[0]['om_id']
        sh_id=res[0]['shop_id']

        qry5="select * from order_details where product_id='%s' and om_id='%s'"%(product_id,omid)
        res5=select(qry5)
       
        if res5:
            if sh_id == int(shopid):
                print("kkkkkkkkkkkkkk")
            
                ord_details=res5[0]['od_id']
                qry6="update order_master set total=(total+'%s') where om_id='%s'"%(total,omid)
                update(qry6)
            
                qry7="update order_details set quantity=(quantity+'%s') where om_id='%s'"%(quantity,ord_details)
                res7=update(qry7)
                if res7:
                    data['status']='success'
                else:
                    data['status']='failed'
            else:
                data['status']="can't add to cart"
            
        else:
            
            if sh_id == int(shopid):
                print("hhhhhhhhhhhhhh")

                qry1="update order_master set total=(total+'%s') where om_id='%s'"%(total,omid)
                res1=update(qry1)

                qry2="insert into order_details values(null,'%s','%s','%s','%s',now())"%(omid,product_id,quantity,price)
                res2=insert(qry2)
                
                if res2:
                    data['status']='success'
                else:
                    data['status']='failed'
            else:
                data['status']="can't add to cart"
            

    else:
        qry3="insert into order_master values(null,(select user_id from user where login_id='%s'),'%s','%s',now(),'pending')"%(userid,shopid,total)
        res3=insert(qry3)

        qry4="insert into order_details values(null,'%s','%s','%s','%s',now())"%(res3,product_id,quantity,price)
        res4=insert(qry4)
        
        if res3:
                data['status']='success'
        else:
                data['status']='failed'
            


    data['method']='add_to_cart'
    
    print(data,"+++++++++++++")

    return str(data)

@user.route('/view_cart')
def view_cart():
    data={}
    loginid=request.args['id']
    
    qry2="select user_id from user where login_id='%s'"%(loginid)
    res2=select(qry2)
    
    user_id=res2[0]['user_id']
    
    print(user_id,"++++++++++++++")
    
    qry="SELECT *, product.quantity AS pquantity, order_details.quantity AS oquantity FROM product INNER JOIN order_details USING(product_id) INNER JOIN order_master USING(om_id) INNER JOIN shop ON order_master.shop_id WHERE order_master.status='pending' and user_id='%s' GROUP BY product_id"%(user_id)
    res=select(qry)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    data['method']='view'
    print(data,"0000000000")
    
    return str(data)
@user.route('/make_payment')
def make_payment():
    data = {}
    om_id = request.args['omid']
    amount = request.args['amt']

    # Verify the correct column name before running the query
    qry4 = "SELECT * FROM order_details WHERE om_id='%s'" % (om_id)
    res4 = select(qry4)
    
    if res4:
        for i in res4:
            qry2 = "UPDATE product SET quantity=(quantity - '%s') WHERE product_id='%s'" % (i['quantity'], i['product_id'])
            update(qry2)

        qry1 = "INSERT INTO order_payment VALUES (NULL, '%s', '%s', CURDATE(), 'paid')" % (om_id, amount)
        res3 = insert(qry1)

        if res3:
            qry = "UPDATE order_master SET status='paid' WHERE om_id='%s'" % (om_id)
            update(qry)

            data['status'] = "success"
    else:
        data['status'] = "failed"

    return str(data)


@user.route('/review_shop')
def review_shop():
    data={}
    product_id=request.args['pid']
    uid=request.args['uid']
    
    review=request.args['review']
    rating=request.args['rating']
    
    qry1="insert into review_product values(null,'%s','%s','%s','%s')"%(product_id,review,rating,uid)
    res3=insert(qry1)
    if res3:
        
        data['status']="success"
       
    else:
        data['status']="failed"
        
    return str(data)

@user.route('/user_rate_worker')
def user_rate_worker():
    data={}
    worker_id=request.args['worker_id']
    user_id=request.args['user_id']
    rating=request.args['rating']
    review=request.args['review']

    qry1="insert into review_worker values(null,'%s','%s','%s','%s')"%(worker_id,review,rating,user_id)
    res1=insert(qry1)
    if res1:
        data['status']='success'
        # data['view']=res1
    else:
        data['status']='failed'
    data['method']='rate'
    return data



    # data = {}
  

    # lati=request.args['lati']
    # logi=request.args['logi']

    # print(lati, "_________________")
    # print(logi, "___________________")


    # q=" SELECT *, (3959 * ACOS(COS(RADIANS(%s)) * COS(RADIANS(latitude)) * COS(RADIANS(longitude) - RADIANS(%s)) + SIN(RADIANS(%s)) * SIN(RADIANS(latitude)))) AS user_distance FROM worker HAVING user_distance < 11.068 ORDER BY user_distance" % (lati,logi,lati)
    # res=select(q)


    # data['view'] = res
    # print(res, "______________________________-")

    # if res:
    #     data['status'] = 'success'
    #     data['data'] = res
    # else:
    #     data['status'] = 'failed'
    
    # data['method'] = 'user_view_workers'

# @user.route('/user_worker')
# def user_worker():
#     data={}
#     qry="select * from worker"
#     res=select(qry)
#     if res:
#         data['status']="success"
#         data['data']=res
#     else:
#         data['status']="failed"
#     data['method']='user_view_workers'  
    
#     print(data,"MMMMMMMMMMMMMMM") 

#     return str(data)


# @user.route('/user_worker')
# def user_worker():
#     data={}
#     data['data'] = []
#     user_id=int(request.args['user_id'])

#     query = "SELECT * FROM review_worker"
#     res = select(query)

#     if res:
#         df = pd.DataFrame(res)

#         result = hybrid_recommendation(user_id, df)
#         print(result,"_____________")
#         if result:
#             for i in result:
#                 qry2 = "SELECT * FROM worker INNER JOIN login USING(login_id) INNER JOIN review_worker ON worker.worker_id = review_worker.worker_id WHERE worker.worker_id='%s' GROUP BY worker.worker_id" % (i)
#                 res2 = select(qry2)
#                 data['status']='success'
#                 data['data'].extend(res2)
#         else:
#             qry1="SELECT * FROM worker INNER JOIN login USING(login_id)"
#             result=select(qry1)
#             if result:
#                 data['status']='success'
#                 data['data']=result
#     else:
#         qry1="SELECT * FROM worker INNER JOIN login USING(login_id)"
#         result=select(qry1)
#         if result:
#             data['status']='success'
#             data['data']=result
#         else: 
#             data['status']='failed'
    
#     data['method']='user_view_workers'
#     print(data,"((((()))))")
#     return data


@user.route('/user_worker')
def user_worker():
    data = {}
    data['data'] = []
    user_id = int(request.args['user_id'])
    
    # First, get all workers with reviews for ranking
    query = "SELECT * FROM review_worker"
    res = select(query)
    
    reviewed_worker_ids = set()
    
    if res:
        df = pd.DataFrame(res)
        ranked_worker_ids = hybrid_recommendation(user_id, df)
        
        # Process ranked workers first
        if ranked_worker_ids:
            for worker_id in ranked_worker_ids:
                qry2 = "SELECT * FROM worker INNER JOIN login USING(login_id) INNER JOIN review_worker ON worker.worker_id = review_worker.worker_id WHERE worker.worker_id='%s' GROUP BY worker.worker_id" % (worker_id)
                res2 = select(qry2)
                if res2:
                    data['data'].extend(res2)
                    reviewed_worker_ids.add(worker_id)
    
    # Now get all workers who don't have reviews
    qry_unreviewed = """
    SELECT * FROM worker 
    INNER JOIN login USING(login_id) 
    WHERE worker.worker_id NOT IN (
        SELECT DISTINCT worker_id FROM review_worker
    )
    """
    unreviewed_workers = select(qry_unreviewed)
    
    if unreviewed_workers:
        data['data'].extend(unreviewed_workers)
    
    # Set success status if we have any data
    if data['data']:
        data['status'] = 'success'
    else:
        # Fallback to get all workers if no data found
        qry_all = "SELECT * FROM worker INNER JOIN login USING(login_id)"
        all_workers = select(qry_all)
        
        if all_workers:
            data['status'] = 'success'
            data['data'] = all_workers
        else:
            data['status'] = 'failed'
    
    data['method'] = 'user_view_workers'
    print(data, "((((()))))")
    return data

@user.route('/user_search_workers')
def user_search_workers():
    data={}
    
    # search = request.args.get('search', '')
    
    search=request.args['search']+'%'


    if search:
        qry="select * from worker where work LIKE '%s'"%(search)
        res=select(qry)
        
        if res:
            data['status']="success"
            data['data']=res
        else:
            data['status']="failed"
            
    data['method']='user_view_workers'  
       
    return str(data)

@user.route('/service_n_charges')
def service_n_charges():
    data={}
    worker_id = request.args['worker_id']
    qry="select * from service where worker_id='%s'"%(worker_id)
    res=select(qry)
    print(res,"//////////")
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    
    print(data,"////////")
        
    return str(data)

@user.route('/service_request')
def service_request():
    data={}
    sid=request.args['sid']
    user_id=request.args['uid']
    title=request.args['title']
    amount=request.args['amount']
    date=request.args['date']
    
    qry1="insert into request values(null,'%s',(select user_id from user where login_id='%s'),'%s','%s','pending','%s')"%(sid,user_id,title,amount,date)
    res3=insert(qry1)
    
    if res3:
        
        data['status']="success"
       
    else:
        data['status']="failed"
        
    return str(data)

@user.route('/user_view_request')
def user_view_request():
    data={}
    
    id=request.args['id']
    qry="select * from request where user_id=(select user_id from user where login_id='%s') and status!='paid'"%(id)
    res=select(qry)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    data['method']='view'
    
    return str(data)

@user.route('/service_payment')
def service_payment():
    data={}
    request_id=request.args['reqid']
    amount=request.args['amt']
    
    qry1="insert into request_payment values(null,'%s','%s',now(),'paid')"%(request_id,amount)
    res3=insert(qry1)
    if res3:
        qry="update request set status='paid' where request_id='%s'"%(request_id)
        update(qry)
        
        data['status']="success"
       
    else:
        data['status']="failed"
      
    return str(data)

@user.route('/user_profile')
def user_profile():
    data={}
    lid=request.args['lid']
    qry="select * from user where login_id='%s'"%(lid)
    res=select(qry)
    print(res,"///////////////")
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
        
    print(data,"///////////")
        
    return str(data)

@user.route('/order_history')
def order_history():
    data={}
    loginid=request.args['id']
    
    qry2="select user_id from user where login_id='%s'"%(loginid)
    res2=select(qry2)
    
    user_id=res2[0]['user_id']
    
    print(user_id,"++++++++++++++")
    
    qry="SELECT *, product.quantity AS pquantity, order_details.quantity AS oquantity FROM product INNER JOIN order_details USING(product_id) INNER JOIN order_master USING(om_id) INNER JOIN shop ON order_master.shop_id WHERE order_master.status='paid' and user_id='%s' GROUP BY product_id"%(user_id)
    res=select(qry)
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    data['method']='view'
    print(data,"0000000000")
    
    return str(data)
    
@user.route('/user_complaint')
def user_complaint():
    data={}
    sender_id=request.args['user_l_id']
    complaint=request.args['c']
    
    
    qry1="insert into complaints values(null,'%s',curdate(),'pending','%s')"%(sender_id,complaint)
    res3=insert(qry1)
    if res3:
        data['status']="success"
        data['data']=res3
       
    else:
        data['status']="failed"
        
    data['method']='send'
    
        
    return str(data)


@user.route('/user_view_complaints')
def user_view_complaints():
    data={}
    lid=request.args['user_l_id']
    qry="select * from complaints where sender_id='%s'"%(lid)
    res=select(qry)
    
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
        
    data['method']='view'
        
    return str(data)

@user.route('/edit_profile')
def edit_profile():
    data={}
    id=request.args['lid']
    fname=request.args['f']
    lname=request.args['l']
    place=request.args['pl']
    email=request.args['em']
    phone=request.args['ph']
    
    qry="update user set first_name='%s',last_name='%s',place='%s',email='%s',phone='%s' where login_id='%s'"%(fname,lname,place,email,phone,id)
    res=update(qry)
    
    if res:   
        data['status']="success"
    else:
        data['status']="failed"
 
    return str(data)


@user.route('/user_search_products')
def user_search_products():
    data={}
    query=request.args['query']
    qry= f"select * from product INNER JOIN shop using(shop_id) where product_name like '%{query}%'"
    res=select(qry)
    print(res,"///////////////")
    if res:
        data['status']="success"
        data['data']=res
    else:
        data['status']="failed"
    data['method']='view'
    print(data,"&&&&&&&&&&&&&")
    return data

@user.route('/forgot_password')
def forgot_password():
    data={}
    email=request.args['email']
    qry="select * from user where email='%s'"%(email)
    res=select(qry)
    if res:
        data['status']="success"
        data['data'] = res
    else:
        data['status']="failed"
    return data

@user.route('/new_password')
def new_password():
    data={}
    psw1=request.args['new_pass']
    lid=request.args['login_id']
    
    qry1="update login set password=('%s') where login_id='%s'"%(psw1,lid)
    res=update(qry1)
    if res:
        data['status']="success"
        data['data'] = res
    else:
        data['status']="failed"
    return data


########################################3recommendation
from hybrid_rec import *
@user.route('/reccommended')
def reccommended():
    data={}
    data['data'] = []
    user_id=int(request.args['id'])

    query = "SELECT * FROM review_product"
    # df = get_data_from_db(query)
    res = select(query)

    if res:
        df = pd.DataFrame(res)

        result = hybrid_recommendation(user_id, df)
        print(result,"_")
        if result:

            for i in result:
                qry2 = "SELECT * FROM product left JOIN review_product USING(product_id) WHERE product_id='%s' group by product_name" % (i)
                res2 = select(qry2)
                data['status']='success'
                data['data'].extend(res2)


        else:
            qry1="select * from product"
            result=select(qry1)
            if result:
                data['status']='success'
                data['data']=result

        # print(data,"))))))))))))))))")
            
    else:
        qry1="select * from product"
        result=select(qry1)
        if result:
            data['status']='success'
            data['view']=result
        else: 
            data['status']='failed'
        

    
    data['method']='reccom'

    return data

       