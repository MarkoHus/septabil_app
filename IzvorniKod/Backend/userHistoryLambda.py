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

def user_history_handler(event, context):

    connection=psycopg2.connect(
        database="IS2_Digitalizacija",
        user="postgres",
        password="bazepodataka",
        host="is2digitalizacijav2.cwck16atirhq.eu-central-1.rds.amazonaws.com"
    )

    cur=connection.cursor(cursor_factory = psycopg2.extras.RealDictCursor)

    userID=event["userid"]
    page=int(event["brojstranice"])
    broj=15

    cur.execute("select * from scanhistory natural join documents where userid='{0}'"
    .format(userID))

    dokumenti=cur.fetchall()[broj*(page-1):broj*page]

    response="{"

    if len(dokumenti)>0:
        if len(dokumenti)<broj:
            response+="'zastavica': 'polupuno',"
        else:
            response+="'zastavica': 'puno',"
        for i in range(len(dokumenti)):
            dokument="'ID"+str(i+1)+"': '"+str((dokumenti[i])["doclabel"])+"',"+"'Scan"+str(i+1)+"': '''"+((dokumenti[i])["doctext"]).strip('\n')+"''',"+"'Vrijeme"+str(i+1)+"': '"+str((dokumenti[i])["scandate"])+"',"+"'Tip"+str(i+1)+"': '"+str((dokumenti[i])["doctype"])+"',"
            response=response+dokument
        response=response[:-1]

        response+="}"
    else:
        response+="'zastavica': 'prazno'}"
    
    return eval(response)

print(user_history_handler({
    "userid":"12",
    "brojstranice":"1"
}, ""))


