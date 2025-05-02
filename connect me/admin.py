from flask import *
from database import *

admin=Blueprint('admin',__name__)


@admin.route('/admin_home')
def admin_home():
    return render_template('admin_home.html')

@admin.route("/admin_view_new_shops")
def admin_view_new_shops():
    data={}
    qry="SELECT * FROM shop INNER JOIN login USING(login_id)"
    res=select(qry)
    data['view']=res
    
    if 'action' in request.args:
        action=request.args['action']
        id=request.args['log_id']
        
        if action=='verify':
            qry2="update login set user_type='shop' where login_id='%s'"%(id)
            res2=update(qry2)
            if res2:
                return "<script>alert('verified success');window.location='/admin_view_new_shops'</script>"
        if action=='reject':
            qry3="update login set user_type='reject' where login_id='%s'"%(id)
            res3=update(qry3)
            if res3:
                return "<script>alert('rejected success');window.location='/admin_view_new_shops'</script>"
        
        print(action,id,"+++++++++++++++++++++")
    
    return render_template('admin_view_new_shops.html',data=data)

@admin.route("/admin_view_new_worker")
def admin_view_new_worker():
    data={}
    qry="SELECT * FROM worker INNER JOIN login USING(login_id)"
    res=select(qry)
    data['view']=res
    
    if 'action' in request.args:
        action=request.args['action']
        id=request.args['log_id']
        
        if action=='verify':
            qry2="update login set user_type='worker' where login_id='%s'"%(id)
            res2=update(qry2)
            if res2:
                return "<script>alert('verified successfully');window.location='/admin_view_new_worker'</script>"
        if action=='reject':
            qry3="update login set user_type='reject' where login_id='%s'"%(id)
            res3=update(qry3)
            if res3:
                return "<script>alert('rejected successfully');window.location='/admin_view_new_worker'</script>"
        
    
    return render_template('admin_view_new_worker.html',data=data)
   
@admin.route("/view_complaints")
def view_complaints():
    data={}
    qry="select * from complaints"
    res=select(qry)
    data['view']=res
    return render_template('view_complaints.html',data=data)
   
@admin.route("/admin_send_reply",methods=['get','post'])
def admin_send_reply():
    id=request.args['id']
    
    if 'complt' in request.form:
        reply=request.form['reply']
        
        qry="update complaints set reply='%s' where complaint_id='%s' "%(reply,id)
        update(qry)
    
    return render_template('admin_send_reply.html')

@admin.route('/contact')
def contact():
    return render_template('contact.html')

