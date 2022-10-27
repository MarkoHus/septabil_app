import psycopg2
import psycopg2.extras
import json


def all_history_handler(event, context):

    connection=psycopg2.connect(
        database="IS2_Digitalizacija",
        user="postgres",
        password="bazepodataka",
        host="is2digitalizacijav2.cwck16atirhq.eu-central-1.rds.amazonaws.com"
    )

    cur=connection.cursor(cursor_factory = psycopg2.extras.RealDictCursor)

    page=int(event["brojstranice"])
    broj=15

    cur.execute("select * from scanhistory natural join documents natural join users")

    dokumenti=cur.fetchall()

    ukBrojDok=len(dokumenti)

    dokumenti=dokumenti[broj*(page-1):broj*page]

    cur.execute("select count(*) from users")

    ukBrojKorisnika=cur.fetchone()["count"]

    avg=ukBrojDok/ukBrojKorisnika

    response="{'BrojSveukRadnika': '"+str(ukBrojKorisnika)+"',"+"'BrojSveukDokumenata': '"+str(ukBrojDok)+"',"+"'Postotak': '"+str(avg)+"',"

    if len(dokumenti)>0:
        if len(dokumenti)<broj:
            response+="'zastavica': 'polupuno',"
        else:
            response+="'zastavica': 'puno',"
        for i in range(len(dokumenti)):
            dokument="'ID"+str(i+1)+"': '"+str((dokumenti[i])["doclabel"])+"',"+"'Scan"+str(i+1)+"': '''"+((dokumenti[i])["doctext"]).strip('\n')+"''',"+"'Vrijeme"+str(i+1)+"': '"+str((dokumenti[i])["scandate"])+"',"+"'Tip"+str(i+1)+"': '"+str((dokumenti[i])["doctype"])+"',"+"'Username"+str(i+1)+"': '"+str((dokumenti[i])["username"])+"',"

            response=response+dokument
        
        response=response[:-1]

        response+="}"
    else:
        response+="'zastavica': 'prazno'}"
    
    return eval(response)
