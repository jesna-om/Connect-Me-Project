from flask import *
from database import *

worker=Blueprint('worker',__name__)


@worker.route('/worker_home')
def worker_home():
    return render_template('worker_home.html')
@worker.route('/view_worker_profile')
def view_worker_profile():
    data={}
    qry="SELECT * FROM worker where worker_id='%s'"%(session['worker_id'])
    res=select(qry)
    data['view']=res
    return render_template('view_worker_profile.html',data=data)

@worker.route('/manage_services_charges',methods=['get','post'])
def manage_services_charges():
    
    if 'services' in request.form:
        sname=request.form['service_name']
        desc=request.form['description']
        amt=request.form['amount']
        
        qry1t="insert into service values(null,'%s','%s','%s','%s')"%(session['worker_id'],sname,desc,amt)
        insert(qry1t)
    
    data={}
    qry="SELECT * FROM service where worker_id='%s'"%(session['worker_id'])
    res=select(qry)
    data['view']=res
    
    if 'action' in request.args:
        value=request.args['action']
        id=request.args['id']
        
    else:
        value=None
        
    if value=='delete':
        qry2="delete from service where service_id='%s'"%(id)
        res2=delete(qry2)
        return "<script>alert('deleted successfully');window.location='/manage_services_charges'</script>"

    if value=='update':
        print("sdfaa")
        a="select * from service where service_id='%s'"%(id)
        b=select(a)
        data['up']=b
        
    if 'update' in request.form:
        sname=request.form['service_name']
        descp=request.form['description']
        amt=request.form['amount']
        d="update service set service_name='%s',description='%s',amount='%s'  where service_id='%s'"%(sname,descp,amt,id)
        e=update(d)
        return "<script>alert('updated successfully');window.location='/manage_services_charges'</script>"
                    
    return render_template('manage_services_charges.html',data=data)

@worker.route('/view_user_reviews')
def view_user_reviews():
    data={}
    qry="select * from review_worker"
    res=select(qry)
    data['view']=res
    return render_template('view_user_reviews.html',data=data)

@worker.route("/worker_complaint",methods=['get','post'])
def worker_complaint():
    data={}
    if 'submit' in request.form:
        comp=request.form['comp']
        
        qry1="insert into complaints values(null,'%s',curdate(),'pending','%s')"%(session['worker_id'],comp)
        insert(qry1)
        
        return "<script>alert('Add successfully');window.location='/worker_complaint'</script>"
        
        
    qry="select * from complaints where sender_id='%s'"%(session['worker_id'])
    res=select(qry)
    print(res)
    if res:
        data['view']=res
    return render_template('worker_complaint.html',data=data)

@worker.route('/view_user_request',methods=['get','post'])
def view_user_request():   
    data={}
    qry1="SELECT * FROM request left JOIN service USING(service_id) where worker_id='%s' and status='pending'"%(session['worker_id'])
    res=select(qry1)
    data['view']=res  
    
    print(data,'mmmmmmmmmmmmmmmmm')
    if 'action' in request.args:
        value=request.args['action']
        id=request.args['id']
    else:
        value=None    
        
    if value=='accept':
        qry="update request set status='accepted' where request_id='%s'"%(id)
        update(qry)
        return "<script>alert('request accepted');window.location='/view_user_request'</script>"
    if value=='reject':
        qry="update request set status='rejected' where request_id='%s'"%(id)
        update(qry)
        return "<script>alert('request rejected');window.location='/view_user_request'</script>"
    if value=='finish':
        qry="update request set status='completed' where request_id='%s'"%(id)
        update(qry)
        return "<script>alert('service completed');window.location='/view_user_request'</script>"

        
        
    data1={}
    qry2="SELECT * FROM request left JOIN service USING(service_id) where worker_id='%s' and status='accepted'"%(session['worker_id'])
    res2=select(qry2)
    data1['accepted']=res2   
    
    
    return render_template("view_user_request.html",data=data,data1=data1)
   
@worker.route('/view_payment_report',methods=['get','post'])
def view_payment_report():   
    data={}
    qry1="SELECT *,request_payment.status as pay_status,request_payment.amount as total_amount FROM request_payment INNER JOIN request using(request_id) INNER JOIN service USING(service_id) where worker_id='%s'"%(session['worker_id'])
    res=select(qry1)
    data['view']=res
    return render_template("view_payment_report.html",data=data)
        

    
 



    
