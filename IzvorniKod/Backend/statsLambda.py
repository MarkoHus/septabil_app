import psycopg2
import psycopg2.extras
import json

connection=psycopg2.connect(
    database="IS2_Digitalizacija",
    user="postgres",
    password="bazepodataka",
    host="is2digitalizacijav2.cwck16atirhq.eu-central-1.rds.amazonaws.com"
)

cur=connection.cursor(cursor_factory = psycopg2.extras.RealDictCursor)

def stats_handler(event, context):

    connection=psycopg2.connect(
        database="IS2_Digitalizacija",
        user="postgres",
        password="bazepodataka",
        host="is2digitalizacijav2.cwck16atirhq.eu-central-1.rds.amazonaws.com"
    )   

    cur=connection.cursor(cursor_factory = psycopg2.extras.RealDictCursor)

    page=int(event["brojstranice"])
    broj=15

    cur.execute("select userid,username,count(docid),round(count(docid) * 100.0 / (select count(*) from documents),2) as postotak from scanhistory natural join documents natural right join users group by (userid,username)")

    ljudi=cur.fetchall()[broj*(page-1):broj*page]

    response="{"

    if len(ljudi)>0:
        if len(ljudi)<broj:
            response+="'zastavica': 'polupuno',"
        else:
            response+="'zastavica': 'puno',"
        for i in range(len(ljudi)):
            dokument="'ID"+str(i+1)+"': '"+str((ljudi[i])["userid"])+"',"+"'Username"+str(i+1)+"': '"+(ljudi[i])["username"]+"',"+"'BrojScanova"+str(i+1)+"': '"+str((ljudi[i])["count"])+"',"+"'Postotak"+str(i+1)+"': '"+str((ljudi[i])["postotak"])+"',"
            response=response+dokument
        response=response[:-1]

        response+="}"
    else:
        response+="'zastavica': 'prazno'}"
    
    return eval(response)

